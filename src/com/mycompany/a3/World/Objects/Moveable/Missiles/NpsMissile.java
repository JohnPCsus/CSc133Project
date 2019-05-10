package com.mycompany.a3.World.Objects.Moveable.Missiles;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;
import com.mycompany.a3.World.Visitors.Visitor;

public class NpsMissile extends Missile {

	public NpsMissile(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		setColor(ColorUtil.rgb(0, 255, 50));
	}

	@Override
	public void draw(Graphics g, Point2D origin) {
		if (isSelected()) {
			setColor(ColorUtil.YELLOW);
		} else {
			setColor(ColorUtil.rgb(0, 255, 50));
		}
		super.draw(g, origin);
	}

	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		
		if(other instanceof PlayerShip) {
			
		} 
		
		super.handleCollision(other);
	}
	
	protected void handleCollision(PlayerShip other) {
		this.setAlive(false);
	}
}
