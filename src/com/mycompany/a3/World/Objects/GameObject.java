package com.mycompany.a3.World.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle2D;
import com.codename1.ui.geom.Shape;
import com.mycompany.a3.Util;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.Drawable;
import com.mycompany.a3.World.Objects.Drawable.Model;
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
	private Model model;

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

	public Model getModel() {
		return model;
	}

	protected void setModel(Model model) {
		this.model = model;
	}

	public void draw(Graphics g, Point2D origin) {

		g.setColor(this.getColor());
		Transform graphicsTransform = Transform.makeIdentity();

		g.getTransform(graphicsTransform);
		Transform original = graphicsTransform.copy();

		graphicsTransform.translate((float) origin.getX(), (float) origin.getY());

		graphicsTransform.concatenate(getTranslation());
		graphicsTransform.concatenate(getRotation());
		graphicsTransform.concatenate(getScale());

		graphicsTransform.translate(-(float) origin.getX(), -(float) origin.getY());

		g.setTransform(graphicsTransform);
		getModel().draw(g, origin);

		g.setTransform(original);

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

//		Transform hitBoxTransform = Transform.makeIdentity();
//		hitBoxTransform.concatenate(getTranslation());
//		hitBoxTransform.concatenate(getRotation());
//		hitBoxTransform.concatenate(getScale());
//
//		float[] bounds = getModel().getShape(hitBoxTransform).getBounds2D();
//		return new Rectangle2D(bounds[0], bounds[1], bounds[2], bounds[3]);
		return getHitBoxComposite();
	}

	private Rectangle2D getHitBoxComposite() {
		Transform hitBoxTransform = Transform.makeIdentity();
		hitBoxTransform.concatenate(getTranslation());
		hitBoxTransform.concatenate(getRotation());
		hitBoxTransform.concatenate(getScale());

		Collection<Rectangle2D> hitBoxes = new ArrayList<>();
		for (Shape s : getModel().getShapes(hitBoxTransform)) {
			float[] bounds = s.getBounds2D();
			hitBoxes.add(new Rectangle2D(bounds[0], bounds[1], bounds[2], bounds[3]));
			
		}
		float[] hitboxcoords = { 0, 0, 0, 0 };
		GeneralPath path = new GeneralPath();
		for (Rectangle2D r : hitBoxes) {
			path.append(r.getPathIterator(), false);

//			float[] rcoords = { (float) r.getX(), (float) r.getY(), (float) (r.getX() + r.getWidth()),
//					(float) (r.getY() + r.getHeight()) };
//
//			if (hitboxcoords[0] == 0 && hitboxcoords[1] == 0 && hitboxcoords[2] == 0 && hitboxcoords[3] == 0) {
//				hitboxcoords = rcoords;
//				continue;
//			}
//
//			if(hitboxcoords[0] > rcoords[0]) {
//				hitboxcoords[0] = rcoords[0];
//			}
//			if(hitboxcoords[1] < rcoords[1]) {
//				hitboxcoords[1] = rcoords[1];
//			}
//			if(hitboxcoords[2] < rcoords[2]) {
//				hitboxcoords[2] = rcoords[2];
//			}
//			if(hitboxcoords[3] > rcoords[3] ) {
//				hitboxcoords[3] = rcoords[3];
//			}

		}
		hitboxcoords = path.getBounds2D();

		return new Rectangle2D(hitboxcoords[0], hitboxcoords[1], hitboxcoords[2] ,
				hitboxcoords[3]);
		

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
