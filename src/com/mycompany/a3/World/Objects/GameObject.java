package com.mycompany.a3.World.Objects;


import java.util.HashSet;
import java.util.Set;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle2D;
import com.mycompany.a3.Util;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.Drawable;
import com.mycompany.a3.World.Objects.Drawable.GameObjectModel;
import com.mycompany.a3.World.Visitors.Visitor;

/**
 * The abstract base class for all objects in the game. Contains the location
 * and color attributes.
 */

public abstract class GameObject implements Drawable, Collidible {

	private Point2D location;
	private int color;
	private boolean alive;
	Set<Collidible> collisionHandled;
	private GameObjectModel model;

	public GameObject(double xLocation, double yLocation) {
		collisionHandled = new HashSet<>();
		location = new Point2D(xLocation, yLocation);
		color = ColorUtil.GRAY;
		alive = true;

	}

	public int getColor() {
		return color;
	}

	public void setColor(int newColor) {
		color = newColor;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public double getX() {
		return this.location.getX();
	}

	public double getY() {
		return this.location.getY();
	}

	public void setX(double newX) {
		location.setX(newX);
	}

	public void setY(double newY) {
		location.setY(newY);
	}

	public GameObjectModel getModel() {
		return model;
	}

	protected void setModel(GameObjectModel model) {
		this.model = model;
	}

	public void draw(Graphics g, Point2D origin) {

		getModel().draw(g, origin);


		if (Util.DEBUG) {
			g.setColor(ColorUtil.rgb(255, 0, 0));
			g.drawShape(getHitBox(), new Stroke());
			
		}

	}

	public Transform getScale() {
		return Transform.makeIdentity();
	}

	public Transform getRotation() {
		return Transform.makeIdentity();
	}

	public Transform getTranslation() {
		Transform thisTranslation = Transform.makeIdentity();
		thisTranslation.translate((float) getX(), (float) getY());
		return thisTranslation;
	}

	/**
	 * handles a collision with the given object,
	 * 
	 * 
	 */
	public boolean handleCollision(Collidible other) {
		boolean collision = false;

		if (colliding(other)) {
			if (!collisionHandled.contains(other)) {
				collision = true;
				handleCollision((GameObject) other);
				collisionHandled.add(other);
			}
		} else {
			collisionHandled.remove(other);
		}
		return collision;
	}

	private boolean colliding(Collidible other) {
		return Collidible.isColliding(this, other);
	}

	/**
	 * a Hook method allowing subclasses to override collision responses subclasses
	 * should determine the actual type of other and dispatch to protected methods
	 * which implement response behavior
	 * 
	 * this method is called by public void handleCollision
	 * 
	 * @param other
	 */
	protected void handleCollision(GameObject other) {
		// no-op enables subclasses to ignore collisions
	}

	@Override
	public Rectangle2D getHitBox() {

		return model.getBounds2D();
	}


	@SuppressWarnings("deprecation")
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + ": " + "loc=" + Util.formatDouble(location.getX(), 2) + ","
				+ Util.formatDouble(location.getY(), 2) + " color=[" + ColorUtil.red(color) + ","
				+ ColorUtil.green(color) + "," + ColorUtil.blue(color) + "]";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
