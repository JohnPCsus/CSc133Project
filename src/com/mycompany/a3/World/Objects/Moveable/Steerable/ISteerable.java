package com.mycompany.a3.World.Objects.Moveable.Steerable;

public interface ISteerable {
	/**
	 * alter an objects direction by an angle theta in degrees
	 *
	 * @param deltaTheta angle in degrees, Positive values for clockwise, negative
	 *                   for counterclockwise
	 */
	int NORTH = 0;
	int EAST = 90;
	int SOUTH = 180;
	int WEST = 270;

	/**
	 * alters the direction of the steerable object according to deltaTheta
	 *
	 * @param deltaTheta positive for clockwise, negative for counter-clockwise
	 */
	public void steer(double deltaTheta);
}
