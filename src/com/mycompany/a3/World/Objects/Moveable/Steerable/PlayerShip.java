package com.mycompany.a3.World.Objects.Moveable.Steerable;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Station;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.MoveableObject;
import com.mycompany.a3.World.Objects.Moveable.NonPlayerShip;
import com.mycompany.a3.World.Objects.Moveable.Missiles.NpsMissile;
import com.mycompany.a3.World.Visitors.Visitor;

public class PlayerShip extends MoveableObject implements ISteerable, Collidible {

	private static final int MAX_MISSILES = 10;
	private static final int WIDTH_PROPORTION = 2;
	private static final int HEIGHT_PROPORTION = 2;

	private int missilesCount;
	private MissileLauncher launcher;

	public PlayerShip(double xLocation, double yLocation) {
		super(xLocation, yLocation);

		missilesCount = MAX_MISSILES;
		launcher = new MissileLauncher(xLocation, yLocation);

		setModel(new ModelFactory().getModel("PlayerShip", this.getColor()));

	}

	@Override
	public void setX(double newX) {
		super.setX(newX);
		launcher.setX(newX);
	}

	@Override
	public void setY(double newY) {
		super.setY(newY);
		launcher.setY(newY);
	}

	public int getMissilesCount() {
		return missilesCount;
	}

	public void setMissilesCount(int newMissilesCount) {

		if (newMissilesCount < 0) {
			missilesCount = 0;
		} else if (newMissilesCount > MAX_MISSILES) {
			missilesCount = MAX_MISSILES;
		} else {
			missilesCount = newMissilesCount;
		}

	}

	public void setSpeed(int newSpeed) {
		super.setSpeed(newSpeed);
		launcher.setSpeed(newSpeed);

	}

	public void aim(int deltaTheta) {
		launcher.steer(deltaTheta);
	}

	public double getLauncherDirection() {
		return launcher.getDirection();
	}

	@Override
	public void steer(double deltaTheta) {
		setDirection(getDirection() + deltaTheta);
	}

	@Override
	public String toString() {
		return super.toString() + " missiles=" + missilesCount + " launcherDir=" + launcher.getDirection();
	}

	public void reload() {
		setMissilesCount(MAX_MISSILES);
	}

	@Override
	public Transform getScale() {
		return Transform.makeScale(WIDTH_PROPORTION * 15, HEIGHT_PROPORTION * 15);
	}

	@Override
	public void draw(Graphics g, Point2D origin) {
		super.draw(g, origin);
		launcher.draw(g, origin);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		if (other instanceof Asteroid) {
			handleCollision((Asteroid) other);
		} else if (other instanceof NonPlayerShip) {
			handleCollision((NonPlayerShip) other);
		} else if (other instanceof NpsMissile) {
			handleCollision((NpsMissile) other);
		} else if (other instanceof Station) {
			handleCollision((Station) other);
		}
		
		super.handleCollision(other);
	}

	protected void handleCollision(Asteroid other) {
		this.setAlive(false);
	}

	protected void handleCollision(NonPlayerShip other) {
		this.setAlive(false);
	}

	protected void handleCollision(NpsMissile other) {
		this.setAlive(false);
	}
	
	protected void handleCollision(Station other) {
		this.setMissilesCount(MAX_MISSILES);
	}

}
