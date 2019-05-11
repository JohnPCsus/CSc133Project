package com.mycompany.a3.World.Objects.Moveable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Selectable;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Objects.Moveable.Missiles.Missile;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;
import com.mycompany.a3.World.Visitors.Visitor;

public class Asteroid extends MoveableObject implements Collidible, Selectable {
	private static final int MAX_SIZE = 30;
	private static final int MIN_SIZE = 6;
	private static final int ROTATION_RATE = 50;
	private int size;
	private Transform rotation;
	private boolean selected;

	public Asteroid(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		size = MIN_SIZE;
		selected = false;
		
		setModel(new ModelFactory().getModel("Asteroid", getColor(),this));
		
		
		setRotation(Transform.makeIdentity());

		assert (MIN_SIZE <= size && size <= MAX_SIZE);
	}
	
	public void setSize(int newSize) {
		if ( newSize < MIN_SIZE) {
			size = MIN_SIZE;
			
		} else if ( newSize > MAX_SIZE) {
			size = MAX_SIZE;
		} else {
			size = newSize;
		}
	}
	
	public void setColor(int newColor) {
		super.setColor(newColor);
		setModel(new ModelFactory().getModel("Asteroid", newColor, this));
	}
	
	@Override 
	public void move(int elapsedTimeMillis) {
		super.move(elapsedTimeMillis);
		updateRotation(elapsedTimeMillis);
	}
	
	@Override
	public Transform getScale() {
		return Transform.makeScale(size,size);
	}
	
	public Transform getRotation() {
		return rotation;
	}
	
	private void setRotation(Transform newRotation) {
		rotation = newRotation;
	}
	
	private void updateRotation(int elapsedTimeMillis) {
		getRotation().rotate((float)Math.toRadians(elapsedTimeMillis / 1000.0 * ROTATION_RATE), 0, 0);
	}

	@Override
	public String toString() {
		return super.toString() + " size=" + size;
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
	public void draw(Graphics g, Point2D origin){
		if(selected) {
			setColor(ColorUtil.YELLOW);
		} else {
			setColor(ColorUtil.GRAY);
		}
		super.draw(g, origin);
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		
		if(other instanceof PlayerShip) {
			handleCollision((PlayerShip) other);
		} else if (other instanceof NonPlayerShip) {
			handleCollision((NonPlayerShip) other);
		} else if (other instanceof Asteroid) {
			handleCollision((Asteroid) other);
		} else if (other instanceof Missile) {
			handleCollision((Missile) other);
		}
		super.handleCollision(other);
		
	}
	
	protected void handleCollision(PlayerShip other){
		this.setAlive(false);
	}
	
	protected void handleCollision(NonPlayerShip other) {
		this.setAlive(false);
	}
	
	protected void handleCollision(Asteroid other) {
		this.setAlive(false);
	}
	
	protected void handleCollision(Missile other) {
		this.setAlive(false);
	}
}
