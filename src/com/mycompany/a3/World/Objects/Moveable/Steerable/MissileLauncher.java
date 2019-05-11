package com.mycompany.a3.World.Objects.Moveable.Steerable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Rectangle2D;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Objects.Moveable.MoveableObject;

public class MissileLauncher extends MoveableObject implements ISteerable {

	public MissileLauncher(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		setColor(ColorUtil.rgb(255,0,0));
		setModel(new ModelFactory().getModel("MissileLauncher", getColor(),this));

	}

	/**
	 * @param deltaTheta angle in degrees, Positive values for clockwise, negative
	 *                   for counterclockwise
	 */
	public void steer(double deltaTheta) {
		setDirection(getDirection() + deltaTheta);
	}

	@Override
	public Rectangle2D getHitBox() {
		return new Rectangle2D(getX(),getY(),0,0);
	}
	
	@Override
	public Transform getScale() {
		return Transform.makeScale(35,1);
	}
	
}
