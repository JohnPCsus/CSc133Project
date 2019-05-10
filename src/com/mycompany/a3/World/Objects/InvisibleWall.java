package com.mycompany.a3.World.Objects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Visitors.Visitor;

public class InvisibleWall extends GameObject {
	public final static int VERTICAL = 0;
	public final static int HORIZONTAl = 1;
	int length;
	int orientation;

	public InvisibleWall(double xLocation, double yLocation, int orientation, int length) {
		super(xLocation, yLocation);
		this.length = length;
		this.orientation = orientation;
		
		
		setColor(ColorUtil.rgb(255, 0, 0));
		setModel(new ModelFactory().getModel("InvisibleWall", getColor()));
		
		
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getOrientation() {

		return orientation;
	}
	
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	@Override
	public Transform getScale() {
		return Transform.makeScale(length, 1);
	}

	@Override
	public Transform getRotation() {
		Transform ret = Transform.makeIdentity();
		if(orientation == VERTICAL) {
			ret.rotate((float) Math.toRadians(90), 0, 0);
		}
		return ret;
	}

	@Override
	public Transform getTranslation() {
		return super.getTranslation();
	}
	
	@Override
	public void draw(Graphics g, Point2D origin) {
		super.draw(g, origin);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
