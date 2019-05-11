package com.mycompany.a3.World.Objects.Drawable;

import com.codename1.ui.Transform;
import com.codename1.ui.geom.Rectangle2D;

public abstract class Model implements Drawable {
	
	private Transform translation;
	private Transform rotation;
	private Transform scale;
	
	public Model() {
		translation = Transform.makeIdentity();
		rotation = Transform.makeIdentity();
		scale = Transform.makeIdentity();
	}
	
	public Transform getTranslation() {
		return translation;
	}

	public void setTranslation(Transform translation) {
		this.translation = translation;
	}

	public Transform getRotation() {
		return rotation;
	}

	public void setRotation(Transform rotation) {
		this.rotation = rotation;
	}

	public Transform getScale() {
		return scale;
	}

	public void setScale(Transform scale) {
		this.scale = scale;
	}

	public abstract Rectangle2D getBounds2D(Transform localTransform);
	
}
