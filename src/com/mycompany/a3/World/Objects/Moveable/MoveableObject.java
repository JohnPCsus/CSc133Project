package com.mycompany.a3.World.Objects.Moveable;

import com.codename1.ui.Transform;
import com.codename1.util.MathUtil;
import com.mycompany.a3.Util;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.InvisibleWall;

public abstract class MoveableObject extends GameObject implements IMovable {
	private static final int MAX_SPEED = 15;
	private static final int MIN_SPEED = 0;
	private static final int SPEED_SCALE_FACTOR = 10;
	private double speed;
	private double direction;

	public MoveableObject(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		this.setDirection(0);
		this.setSpeed(0);
	}

	public double getSpeed() {
		return this.speed;
	}

	public double getDirection() {
		return this.direction;
	}

	/**
	 * Set the speed of the object to the given newSpeed If newSpeed is outside of
	 * the valid range (0-15) then speed is set to the nearest legal value
	 *
	 * @param newSpeed
	 */
	public void setSpeed(double newSpeed) {
		if (newSpeed < MIN_SPEED) {
			speed = MIN_SPEED;
		} else if (newSpeed > MAX_SPEED) {
			speed = MAX_SPEED;
		} else {
			speed = newSpeed;
		}
		assert (MIN_SPEED <= speed && speed <= MAX_SPEED);
	}

	/**
	 * Set the direction of the object to the give newDirection If the new direction
	 * is out of the valid range (0-359) then the direction is interpreted by mod
	 * 360 arithmetic and is normalized i.e. 360 = 0 , -1 = 359
	 *
	 * @param newDirection
	 */
	public void setDirection(double newDirection) {
		this.direction = Util.normalizeAngleDeg(newDirection);
		assert (0 <= direction && direction < 360);
	}

	public void move(int elapsedTimeMillis) {

		double directionInRadians = Math.toRadians(90 - getDirection());

		setX(getX() + (getSpeed() * Math.cos(directionInRadians) * elapsedTimeMillis / 1000 * SPEED_SCALE_FACTOR));
		setY(getY() + (getSpeed() * Math.sin(directionInRadians) * elapsedTimeMillis / 1000 * SPEED_SCALE_FACTOR));
	}

	@Override
	public Transform getRotation() {
		Transform thisRotation = Transform.makeIdentity();
		thisRotation.rotate((float) Math.toRadians(90 - getDirection()), 0, 0);

		return thisRotation;
	}

	@Override
	public String toString() {
		return super.toString() + " speed=" + speed + " dir=" + direction;
	}
	
	@Override 
	protected void handleCollision(GameObject other) {
		if (other instanceof InvisibleWall) {
			handleCollision((InvisibleWall) other);
		}
		super.handleCollision(other);
	}

	protected void handleCollision(InvisibleWall other) {

		// get the angle of the object and normalize
		double directionDeg = Util.normalizeAngleDeg(90 - getDirection());
		// convert the direction to a unit direction vector
		float[] directionVector = { (float) (Math.cos(Math.toRadians(directionDeg))),
				(float) (Math.sin(Math.toRadians(directionDeg))) };

		// handle collision with vertial wall
		if (other.getOrientation() == InvisibleWall.VERTICAL) {

			// reflect the unit direction vector about the y axis
			Transform t = Transform.makeScale(-1, 1);
			float[] transformed = t.transformPoint(directionVector);

			// convert Unit direction vector to degrees and normalize
			double newDirectionDeg = Util
					.normalizeAngleDeg(90 - Math.toDegrees(MathUtil.atan(transformed[1] / transformed[0])));

			/*
			 * check if original angle was in quadrant 1 or 4 and roatate newDirection 180
			 * degrees if so rotate it 180 degrees because of the range of arctan
			 */
			if ((directionDeg < 90 && directionDeg >= 0) || (directionDeg > 270 && directionDeg < 360)) {
				newDirectionDeg += 180;
			}

			setDirection(newDirectionDeg);

			// handle collision with Horizontal wall
		} else if (other.getOrientation() == InvisibleWall.HORIZONTAl) {

			// reflect the unit direction vector about the x axis
			Transform t = Transform.makeScale(1, -1);
			float[] transformed = t.transformPoint(directionVector);

			// convert unit direction vector to degrees and normalize
			double newDirectionDeg = Util
					.normalizeAngleDeg(90 - Math.toDegrees(MathUtil.atan(transformed[1] / transformed[0])));

			if (!((directionDeg <= 90 && directionDeg >= 0) || (directionDeg > 270 && directionDeg < 360))) {
				newDirectionDeg += 180;
			}
			setDirection(newDirectionDeg);
		}

	}
}
