package com.mycompany.a3.World.Objects.Collidable;

import com.codename1.ui.geom.Rectangle2D;

/**
 * 
 * @author John Pettet Top level interface for Objects that have a physical
 *         location and need to be capable of testing for collision with other
 *         objects
 */
public interface Collidible {

	/**
	 * 
	 * @return the rectangle bounding the area in which the object occupies
	 */
	Rectangle2D getHitBox();
	
	/**
	 * @param other the object to handle collision with
	 * @return true if the collision handled was new
	 */
	public boolean handleCollision(Collidible other);

	/**
	 * 
	 * @param object the other collidible object to test for collision with
	 * @return true if objects hitboxes overlap
	 */
	static boolean isColliding(Collidible object1, Collidible object2) {

		if(object1 == object2) {
			return false;
		}
		
		// [x, y, width, height]
		float[] hitBox1 = object1.getHitBox().getBounds2D();

		double left1 = hitBox1[0];
		double right1 = hitBox1[0] + hitBox1[2];
		double top1 = hitBox1[1];
		double bottom1 = hitBox1[1] + hitBox1[3];

		float[] hitBox2 = object2.getHitBox().getBounds2D();

		double left2 = hitBox2[0];
		double right2 = hitBox2[0] + hitBox2[2];
		double top2 = hitBox2[1];
		double bottom2 = hitBox2[1] + hitBox2[3];

//		boolean leftRight = (right1 < left2) || (left1 > right2);
//		boolean topBottom = (top2 < bottom1 || top1 < bottom2);
//
//		return !(leftRight && topBottom);
		
		return (left1 < right2 &&
				right1 > left2 &&
				top1 < bottom2 &&
				bottom1 > top2);

	}
}
