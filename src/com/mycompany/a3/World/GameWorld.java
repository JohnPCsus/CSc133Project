package com.mycompany.a3.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import com.mycompany.a3.Util;
import com.mycompany.a3.World.Collection.GameCollection;
import com.mycompany.a3.World.Collection.IIterator;
import com.mycompany.a3.World.Events.Action;
import com.mycompany.a3.World.Events.CollisionEvent;
import com.mycompany.a3.World.Events.Control;
import com.mycompany.a3.World.Events.Event;
import com.mycompany.a3.World.Events.EventStream;
import com.mycompany.a3.World.Events.GameStateEvent;
import com.mycompany.a3.World.Events.ObjectDestruction;
import com.mycompany.a3.World.Events.ObjectSpawned;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.InvisibleWall;
import com.mycompany.a3.World.Objects.Station;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.NonPlayerShip;
import com.mycompany.a3.World.Objects.Moveable.Missiles.Missile;
import com.mycompany.a3.World.Objects.Moveable.Missiles.NpsMissile;
import com.mycompany.a3.World.Objects.Moveable.Missiles.PsMissile;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;
import com.mycompany.a3.World.Visitors.TimeStepVisitor;

public class GameWorld extends Observable implements IWorldView {

	private static final int ASTEROID_SCORE = 100;
	private static final int NPS_SCORE = 250;

	// default values are provided
	private int xBounds = 1024;
	private int yBounds = 768;

	private GameCollection objects;
	private IWorldView proxy;
	private GameState state;
	private EventStream events;
	private TimeStepVisitor timeStepVisitor;
	private InvisibleWall[] bounds = { null, null, null, null };

	/**
	 * Default Constructor Builds a new GameWorld object ready for a game to be
	 * started
	 */
	public GameWorld() {
		objects = new GameCollection();
		proxy = new GameWorldProxy(this);
		events = new EventStream();
		timeStepVisitor = new TimeStepVisitor();
		events.addObserver(this::handleEvents);

	}

	public void setXBounds(int x_BOUNDS) {
		if (x_BOUNDS != xBounds) {
			if (x_BOUNDS < xBounds) {
				// TODO handle shrinking world
				xBounds = x_BOUNDS;
			} else {
				xBounds = x_BOUNDS;
			}
			updateBounds();
		}
	}

	public void setYBounds(int y_BOUNDS) {
		if (y_BOUNDS != yBounds) {
			if (y_BOUNDS < yBounds) {
				// TODO handle shrinking world
				yBounds = y_BOUNDS;
			} else {
				yBounds = y_BOUNDS;
			}
			updateBounds();
		}
	}

	public IIterator getObjects() {
		return objects.getIterator();
	}

	/**
	 * The method for getting all information about all gameObjects in the Gameworld
	 *
	 * @return a String of all object states
	 */
	public String getMap() {
		StringBuilder returnValue = new StringBuilder();
		if (state.isGameActive()) {
			IIterator objectIterator = objects.getIterator();
			while (objectIterator.hasNext()) {
				returnValue.append(objectIterator.getNext()).append("\n");
			}
		} else {
			returnValue.append("Game Over!");
		}
		return returnValue.toString();
	}

	/**
	 * The method for getting score, time, and player's lives information
	 *
	 * @return score, time, and player's lives info
	 */
	public Map<String, String> getGameState() {

		int missiles = getPlayer() != null ? getPlayer().getMissilesCount() : 0;

		Map<String, String> ret = new HashMap<>();
		ret.put("Score", Integer.toString(state.getScore()));
		ret.put("Time", Integer.toString((int) (state.getTime() / 1000)));
		ret.put("Lives", Integer.toString(state.getPlayerLives()));
		ret.put("Missiles", Integer.toString(missiles));

		return ret;
	}

	public EventStream getEventStream() {
		return events;
	}

	/**
	 * Performs the setup for a new game
	 */
	public void init() {
		objects.clear();
		state = new GameState();
		for (int i = 0; i < 4; i++) {
			if (bounds[i] == null) {
				bounds[i] = new InvisibleWall(0, 0, 0, 0);
			}
			objects.add(bounds[i]);
		}
		updateBounds();

		setChanged();
		notifyObservers();
	}

	public void addScore(int points) {
		state.setScore(state.getScore() + points);
	}

	public void refuelMissile() {

		IIterator iter = objects.getIterator();
		while (iter.hasNext()) {

			GameObject g = iter.getNext();
			if (g instanceof Missile) {
				if (((Missile) g).isSelected()) {
					((Missile) g).setFuel(Missile.MAX_FUEL);
				}
			}
		}
	}

	public void addAsteroid() {
		Asteroid newAsteroid = new Asteroid(Util.randomInt(xBounds), Util.randomInt(yBounds));
		newAsteroid.setSpeed(Util.randomInt(0, 15));
		newAsteroid.setDirection(Util.randomInt(360));
		newAsteroid.setSize(Util.randomInt(1, 30));

		addGameObject(newAsteroid);
		events.addEvent(new ObjectSpawned(newAsteroid));

	}

	public void addNonPlayerShip() {
		NonPlayerShip newNonPlayerShip = new NonPlayerShip(Util.randomInt(xBounds), Util.randomInt(yBounds));
		newNonPlayerShip.setSize((1 == Util.randomInt(1)) ? NonPlayerShip.SMALL : NonPlayerShip.LARGE);
		newNonPlayerShip.setDirection(Util.randomInt(0, 360));
		newNonPlayerShip.setSpeed(Util.randomInt(0, 7));

		addGameObject(newNonPlayerShip);
		events.addEvent(new ObjectSpawned(newNonPlayerShip));
	}

	public boolean addPlayerShip() {
		boolean success = false;

		if (state.getPlayerLives() == 0) {
			System.out.println("Game is Over!");
		} else if (getPlayer() != null) {
			System.out.println("Failed to add Player Ship to Game, Already present!");
		} else {
			PlayerShip newPlayer = new PlayerShip(xBounds / 2.0, yBounds / 2.0);

			addGameObject(newPlayer);
			events.addEvent(new ObjectSpawned(newPlayer));
		}
		return success;
	}

	public void addStation() {
		Station newStation = new Station(Util.randomInt(xBounds), Util.randomInt(yBounds));
		while (checkForCollision(newStation)) {
			newStation.setX(Util.randomInt(xBounds));
			newStation.setY(Util.randomInt(yBounds));
		}
		objects.add(newStation);
		events.addEvent(new ObjectSpawned(newStation));

	}

	public boolean playerFire() {
		boolean success = false;
		PlayerShip player = getPlayer();
		if (player != null) {

			if (player.getMissilesCount() > 0) {
				player.setMissilesCount(player.getMissilesCount() - 1);

				PsMissile firedMissile = new PsMissile(player.getX(), player.getY());
				firedMissile.setSpeed(15);
				firedMissile.setDirection(player.getLauncherDirection());
				objects.add(firedMissile);
				events.addEvent(new ObjectSpawned(firedMissile));
				success = true;
			} else {

			}

		}
		events.addEvent(new Control(player, Action.Fire, success));
		return success;
	}

	public boolean nonPlayerFire() {
		boolean success = false;
		NonPlayerShip nonPlayer = getNonPlayerShip();

		if (nonPlayer != null) {
			if (nonPlayer.getMissilesCount() > 0) {
				nonPlayer.setMissilesCount(nonPlayer.getMissilesCount() - 1);

				NpsMissile firedMissile = new NpsMissile(nonPlayer.getX(), nonPlayer.getY());
				firedMissile.setSpeed(nonPlayer.getSpeed() + 1);
				firedMissile.setDirection(nonPlayer.getLauncherDirection());
				objects.add(firedMissile);
				events.addEvent(new ObjectSpawned(firedMissile));
				success = true;

			}
		}
		events.addEvent(new Control(nonPlayer, Action.Fire, success));
		return success;
	}

	/**
	 * speed the player up by 1
	 */
	public boolean playerAccelerate() {
		return playerAccelerate(1);
	}

	/**
	 * slow the player down by 1
	 * 
	 * @return
	 */
	public boolean playerDeccelerate() {
		return playerAccelerate(-1);
	}

	/**
	 * commands player to rotate counter-clockwise
	 * 
	 * @return
	 */
	public boolean playerTurnLeft() {
		boolean success;

		PlayerShip player = getPlayer();
		if (player != null) {
			player.steer(-45);

			success = true;
		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.TurnLeft, success));
		return success;
	}

	/**
	 * commands player to rotate clockwise
	 * 
	 * @return
	 */
	public boolean playerTurnRight() {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			player.steer(45);

			success = true;

		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.TurnRight, success));
		return success;
	}

	/**
	 * commands player to rotate its missile launcher counter-clockwise
	 */
	public boolean playerAimLeft() {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			player.aim(-45);

			success = true;
		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.AimLeft, success));
		return success;
	}

	/**
	 * commands player to rotate its missile launcher clockwise
	 */
	public boolean playerAimRight() {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			success = true;
			player.aim(45);

		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.AimRight, success));

		return success;
	}

	public boolean playerHyperspace() {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			player.setX(xBounds / 2.0);
			player.setY(yBounds / 2.0);
			player.setDirection(0);
			player.setSpeed(0);

			success = true;
		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.Hyperspace, success));
		return success;
	}

	public boolean playerReload() {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			player.reload();
			success = true;

		} else {
			success = false;
		}
		events.addEvent(new Control(player, Action.Reload, success));
		return success;
	}

	/**
	 * loop through all gameobjects istructing them to update their state along the
	 * way
	 * 
	 * @param timeElapsedMillis
	 */
	public void gameTick(int timeElapsedMillis) {
		if (getPlayer() == null && state.isGameActive()) {
			addPlayerShip();
		}

		state.setTime(state.getTime() + timeElapsedMillis);
		timeStepVisitor.visitAll(objects.getIterator(), state.getTime());

		checkForCollision();

		// remove dead objects
		IIterator removal = objects.getIterator();
		while (removal.hasNext()) {
			GameObject g = removal.getNext();
			if (!g.isAlive()) {
				events.addEvent(new ObjectDestruction(g));
				objects.remove(g);
			}
		}
		if (getPlayer() == null) {
			playerDeath();
		}
		events.notifyObservers();

		spawnForRound();

		

		setChanged();
		notifyObservers();

	}

	@Override
	public void notifyObservers() {
		notifyObservers(proxy);
	}

	private void spawnForRound() {
		int ticksPerSecond = 1000 / Util.tickRate;
		switch (Util.randomInt(10 * ticksPerSecond)) {
		case 1:
		case 2:
		case 3:
		case 4:
			addAsteroid();
			break;
		case 50:
		case 51:
			addNonPlayerShip();
			break;
		default:
			break;
		}
	}

	private void checkForCollision() {
		// collision detect

		for (IIterator indexA = objects.getIterator(); indexA.hasNext();) {
			GameObject objectA = indexA.getNext();

			for (IIterator indexB = objects.getIterator(); indexB.hasNext();) {
				GameObject objectB = indexB.getNext();

				boolean collision = objectA.handleCollision(objectB) && objectB.handleCollision(objectA);

				if (collision) {
					events.addEvent(new CollisionEvent(objectA, objectB));
				}
			}
		}
	}

	/**
	 * a helper method to check if a particular object is colliding with any other
	 * object, used when adding an object to the world so they don't spawn on top of
	 * one another
	 * 
	 * @param a
	 * @return true if the object is or would be colliding with another object
	 */
	private boolean checkForCollision(Collidible a) {
		boolean colliding = false;

		for (IIterator iterator = objects.getIterator(); iterator.hasNext();) {
			GameObject b = iterator.getNext();

			if (Collidible.isColliding(a, b)) {
				colliding = true;
			}
		}
		return colliding;
	}

	/**
	 * @return may be null in the case that there is no current player ship in the
	 *         gameworld
	 */

	private PlayerShip getPlayer() {
		IIterator i = objects.getIterator();
		while (i.hasNext()) {
			GameObject g = i.getNext();
			if (g instanceof PlayerShip) {
				return ((PlayerShip) g);
			}
		}
		return null;
	}

	private NonPlayerShip getNonPlayerShip() {
		IIterator i = objects.getIterator();
		while (i.hasNext()) {
			GameObject g = i.getNext();
			if (g instanceof NonPlayerShip) {
				return ((NonPlayerShip) g);
			}
		}
		return null;
	}

	private void handleEvents(Observable source, Object arg) {
		for (Iterator<Event> ev = ((EventStream) source).getEvents(); ev.hasNext();) {
			Event current = ev.next();
			if (current instanceof CollisionEvent) {
				Class<? extends GameObject> aclass = ((CollisionEvent) current).getaType();
				Class<? extends GameObject> bclass = ((CollisionEvent) current).getbType();

				if (aclass.equals(PsMissile.class)) {
					handleScore(bclass);
				}
				if (bclass.equals(PsMissile.class)) {
					handleScore(aclass);
				}
				if ((aclass.equals(PlayerShip.class) && bclass.equals(Station.class))
						|| (aclass.equals(Station.class) && (bclass.equals(PlayerShip.class)))) {
					events.addEvent(new Control(getPlayer(), Action.Reload, true));
				}
			}
		}
	}

	private void handleScore(Class<? extends GameObject> destroyed) {
		if (destroyed.equals(Asteroid.class)) {
			addScore(ASTEROID_SCORE);
		} else if (destroyed.equals(NonPlayerShip.class)) {
			addScore(NPS_SCORE);
		}
	}

	/**
	 * alter the speed of the palyer ship by an ammount deltaV
	 *
	 * @param deltaV the ammount by which to change the players speed
	 */
	private boolean playerAccelerate(int deltaV) {
		boolean success;
		PlayerShip player = getPlayer();
		if (player != null) {
			success = true;
			player.setSpeed(player.getSpeed() + deltaV);
		} else {
			success = false;
		}
		events.addEvent(new Control(player, deltaV > 0 ? Action.SpeedUp : Action.SlowDown, success));
		return success;

	}

	private void addGameObject(GameObject object) {
		while (checkForCollision(object)) {
			object.setX(Util.randomInt(xBounds));
			object.setY(Util.randomInt(yBounds));
		}
		objects.add(object);
	}

	private void playerDeath() {

		state.setPlayerLives(state.getPlayerLives() - 1);
		if (state.getPlayerLives() == 0) {
			state.setGameActive(false);
			events.addEvent(new GameStateEvent(GameStateEvent.LIVES, 0));
		}
	}

	private void updateBounds() {
		// left after display mapping
		if (bounds[0] != null) {
			bounds[0].setX(-2);
			bounds[0].setY(-2);
			bounds[0].setLength(yBounds);
			bounds[0].setOrientation(InvisibleWall.VERTICAL);
		}

		// top after display mapping
		if (bounds[1] != null) {
			bounds[1].setX(0);
			bounds[1].setY(yBounds + 2);
			bounds[1].setLength(xBounds);
			bounds[1].setOrientation(InvisibleWall.HORIZONTAl);
		}

		// right after display mapping
		if (bounds[2] != null) {
			bounds[2].setX(xBounds);
			bounds[2].setY(0);
			bounds[2].setLength(yBounds);
			bounds[2].setOrientation(InvisibleWall.VERTICAL);
		}

		// bottom after display mapping
		if (bounds[3] != null) {
			bounds[3].setX(0);
			bounds[3].setY(0);
			bounds[3].setLength(xBounds);
			bounds[3].setOrientation(InvisibleWall.HORIZONTAl);
		}
	}

	private class GameWorldProxy implements IWorldView {
		private GameWorld innerWorld;

		private GameWorldProxy(GameWorld inner) {
			this.innerWorld = inner;
		}

		public Map<String, String> getGameState() {
			return innerWorld.getGameState();
		}

		public String getMap() {
			return innerWorld.getMap();
		}

		public IIterator getObjects() {
			return innerWorld.getObjects();
		}
	}
}