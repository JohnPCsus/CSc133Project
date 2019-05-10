package com.mycompany.a3.World.Events;

import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;

public class Control implements Event {

	private Action action;
	private Point2D location;
	private Class<? extends GameObject> actor;
	private boolean success;
	
	@SuppressWarnings("unchecked")
	public Control(GameObject g, Action a, boolean s) {
		if(g != null) {
		actor = g.getClass();
		this.location = new Point2D(g.getX(),g.getY());
		}
		action = a;
		success = s;
	}

	public Action getAction() {
		return action;
	}

	public Point2D getLocation() {
		return location;
	}

	public Class<? extends GameObject> getActor() {
		return actor;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
	
}
