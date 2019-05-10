package com.mycompany.a3.World.Objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public interface Selectable {
	/**
	 * set the selection state of the selectable
	 * @param selected
	 */
	void setSelected(boolean selected);
	/**
	 * return the selection state of the selectable
	 * @return
	 */
	boolean isSelected();
	/**
	 * checks if the selectable's selection area contains the point given
	 * @param point
	 * @return
	 */
	boolean contains(Point2D point);
	/**
	 * Supports drawing selectable's differently based on selection status
	 * @param g
	 * @param origin
	 */
	void draw(Graphics g, Point2D origin);
	
}
