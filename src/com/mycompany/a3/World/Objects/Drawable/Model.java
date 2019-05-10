package com.mycompany.a3.World.Objects.Drawable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.geom.Shape;

/**
 * represents a GameModel built exclusively out of straight lines drawn in the
 * order added to the object;
 * 
 * @author mhitv
 *
 */
public class Model implements Drawable {


	private GeneralPath shape;
	private int color;
	private Transform translation;
	private Transform rotation;
	private Transform scale;
	private boolean fill;
	private Queue<Model> subModels;

	public Model() {

		translation = Transform.makeIdentity();
		rotation = Transform.makeIdentity();
		scale = Transform.makeIdentity();
		subModels = new ArrayDeque<>();
		fill = false;

	}

	@Override
	public void draw(Graphics g, Point2D origin) {
		if (shape == null) {
			return;
		}
		Transform original = Transform.makeIdentity();
		g.getTransform(original);
		Transform local = original.copy();

		local.translate((int) origin.getX(), (int) origin.getY());
		local.concatenate(getTranslation());
		local.concatenate(getRotation());
		local.concatenate(getScale());
		local.translate(-(int) origin.getX(), -(int) origin.getY());

		g.setTransform(local);
		g.setColor(color);
		if (fill) {
			g.fillShape(shape);
		} else {
			g.drawShape(shape, new Stroke());
		}

		for (Model m : subModels) {
			m.draw(g, origin);
		}

		g.setTransform(original);

	}
	
	public Collection<Shape> getShapes(Transform t){
		Collection<Shape> shapes = new ArrayList<>();
		return this.buildShapes(shapes, t);
	}
	
	public Collection<Shape> getShapes() {

		return getShapes( Transform.IDENTITY());
	}
	
	private Collection<Shape> buildShapes(Collection<Shape> shapes, Transform t){
		if(shape != null) {
			shapes.add(shape.createTransformedShape(t));
		}
		if(!(subModels == null || subModels.isEmpty())){
			for(Model m : subModels) {
				m.buildShapes(shapes, t);
			}
		}
		
		return shapes;
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

	public void setShape(GeneralPath shape) {
		this.shape = shape;
	}

	public void addSubModel(Model newModel) {
		subModels.add(newModel);
	}

	public void setFill(boolean newFill) {
		fill = newFill;
	}

	public void setColor(int color2) {
		this.color = color2;
	}
}
