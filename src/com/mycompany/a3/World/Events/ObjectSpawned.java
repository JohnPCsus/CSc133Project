package com.mycompany.a3.World.Events;

import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;

public class ObjectSpawned implements Event {

	private Point2D location;
	private Class<? extends GameObject> type;

	@SuppressWarnings("unchecked")
	public ObjectSpawned(GameObject spawned) {
		location = new Point2D(spawned.getX(), spawned.getY());
		type = spawned.getClass();
	}

	public Point2D getLocation() {
		return location;
	}

	public Class<? extends GameObject> getType() {
		return type;
	}
	
}
