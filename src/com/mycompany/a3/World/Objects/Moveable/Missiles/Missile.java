package com.mycompany.a3.World.Objects.Moveable.Missiles;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.Util;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Selectable;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.MoveableObject;

public abstract class Missile extends MoveableObject implements Collidible, Selectable {

	public static final int MAX_FUEL = 10;
	private double fuel;
	private boolean selected;

	protected Missile(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		setColor(ColorUtil.rgb(255, 0, 0));

		fuel = MAX_FUEL;
		selected = false;

		setModel(new ModelFactory().getModel("Missile",getColor(),this));

	}
	
	public void setColor(int newColor) {
		if(newColor == getColor()) {
			return;
		} else {
			super.setColor(newColor);
			setModel(new ModelFactory().getModel("Missile",getColor(),this));
		}
	}

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double newFuel) {
		this.fuel = newFuel;
	}

	@Override
	public void move(int elapsedTimeMillis) {
		super.move(elapsedTimeMillis);
		setFuel(getFuel() - elapsedTimeMillis / 1000.0);
	}

	@Override
	public String toString() {

		return super.toString() + " Fuel=" + Util.formatDouble(fuel, 2);
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

	@Override
	protected void handleCollision(GameObject other) {
		if(other instanceof Asteroid) {
			handleCollision((Asteroid) other);
		}
		super.handleCollision(other);
	}
	
	protected void handleCollision(Asteroid other) {
		this.setAlive(false);
	}

}
