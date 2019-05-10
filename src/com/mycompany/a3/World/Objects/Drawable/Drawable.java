
package com.mycompany.a3.World.Objects.Drawable;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public interface Drawable {
	
	/**
	 * 
	 * @param g the graphics context of this object
	 * @param origin the origin of the container holding this object
	 */
	public void draw(Graphics g, Point2D origin );

}
