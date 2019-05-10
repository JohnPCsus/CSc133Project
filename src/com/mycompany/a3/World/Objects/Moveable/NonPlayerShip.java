package com.mycompany.a3.World.Objects.Moveable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Selectable;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Objects.Moveable.Missiles.PsMissile;
import com.mycompany.a3.World.Objects.Moveable.Steerable.MissileLauncher;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;
import com.mycompany.a3.World.Visitors.Visitor;

public class NonPlayerShip extends MoveableObject implements Collidible, Selectable {

	public final static int SMALL = 10;
	public final static int LARGE = 20;
	private final static int MAX_MISSILES = 2;
	private final static int DEFAULT_COLOR = ColorUtil.GREEN;
	private final static double WIDTH_PROPORTION = 1;
	private final static double HEIGHT_PROPORTION = 3;

	private int size;
	private int missilesCount;
	private MissileLauncher launcher;
	private boolean selected;

	public NonPlayerShip(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		this.setColor(DEFAULT_COLOR);

		size = SMALL;
		missilesCount = MAX_MISSILES; // ships have full magazines at spawn
		launcher = new MissileLauncher(xLocation, yLocation);
		selected = false;
		setModel(new ModelFactory().getModel("NonPlayerShip", getColor()));
	}
	
	public void setColor(int newColor) {
		super.setColor(newColor);
		setModel(new ModelFactory().getModel("NonPlayerShip", getColor()));
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

	public double getLauncherDirection() {
		return launcher.getDirection();

	}

	@Override
	public void setDirection(double newDirection) {
		super.setDirection(newDirection);
		if (launcher != null) {
			launcher.setDirection(newDirection);
		}
	}

	@Override
	public void setSpeed(double newSpeed) {
		super.setSpeed(newSpeed);
		if (launcher != null) {
			launcher.setSpeed(newSpeed);
		}
	}

	@Override
	public void move(int elapsedTimeMillis) {
		super.move(elapsedTimeMillis);
		launcher.move(elapsedTimeMillis);
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;

	}

	@Override
	public void draw(Graphics g, Point2D origin) {
		if (selected) {
			setColor(ColorUtil.YELLOW);
		} else {
			setColor(DEFAULT_COLOR);
		}
		super.draw(g, origin);
		launcher.draw(g, origin);
	}

	public Transform getScale() {
		return Transform.makeScale((float) (HEIGHT_PROPORTION * getSize()), (float) (WIDTH_PROPORTION * getSize()));
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public boolean contains(Point2D point) {
		return getHitBox().contains(point.getX(), point.getY());
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		if (other instanceof PsMissile) {
			handleCollision((PsMissile) other);
		} else if (other instanceof Asteroid) {
			handleCollision((Asteroid) other);
		} else if (other instanceof PlayerShip) {
			handleCollision((PlayerShip) other);
		}
		super.handleCollision(other);
	}

	protected void handleCollision(Asteroid other) {
		this.setAlive(false);
	}

	protected void handleCollision(PlayerShip other) {
		this.setAlive(false);
	}

	protected void handleCollision(PsMissile other) {
		this.setAlive(false);
	}

}
