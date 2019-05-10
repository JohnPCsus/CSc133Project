package com.mycompany.a3.World.Events;

import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Rectangle2D;
import com.mycompany.a3.World.Objects.GameObject;

public class CollisionEvent implements Event {

	private Point2D aLocation;
	private Point2D bLocation;
	
	private Rectangle2D aHitBox;
	private Rectangle2D bHitBox;
	
	private Class<? extends GameObject> aType;
	private Class<? extends GameObject> bType;
	
	@SuppressWarnings("unchecked")
	public CollisionEvent(GameObject a, GameObject b) {
		aLocation = new Point2D(a.getX(), b.getY());
		bLocation = new Point2D(b.getX(), b.getY());
		
		aHitBox = a.getHitBox();
		bHitBox = b.getHitBox();
		
		aType = a.getClass();
		bType = b.getClass();
	}

	public Point2D getaLocation() {
		return aLocation;
	}

	public Point2D getbLocation() {
		return bLocation;
	}

	public Rectangle2D getaHitBox() {
		return aHitBox;
	}

	public Rectangle2D getbHitBox() {
		return bHitBox;
	}

	public Class<? extends GameObject> getaType() {
		return aType;
	}

	public Class<? extends GameObject> getbType() {
		return bType;
	}
}
