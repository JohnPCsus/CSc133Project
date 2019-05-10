package com.mycompany.a3.World.Objects.Moveable.Missiles;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Moveable.NonPlayerShip;
import com.mycompany.a3.World.Visitors.Visitor;

public class PsMissile extends Missile {
	private static final int[] GRADIENT = { 0xFF0000, 0xFF1100, 0xFF2300, 0xFF3400, 0xFF4600, 0xFF5700, 0xFF6900,
			0xFF7B00, 0xFF8C00, 0xFF9E00, 0xFFAF00, 0xFFC100, 0xFFD300, 0xFFE400, 0xFFF600, 0xF7FF00, 0xE5FF00,
			0xD4FF00, 0xC2FF00, 0xB0FF00, 0x9FFF00, 0x8DFF00, 0x7CFF00, 0x6AFF00, 0x58FF00, 0x47FF00, 0x35FF00,
			0x24FF00, 0x12FF00, 0x00FF00 };

	public PsMissile(double xLocation, double yLocation) {
		super(xLocation, yLocation);
	}

	@Override
	public void draw(Graphics g, Point2D origin) {

		if (isSelected()) {
			setColor(ColorUtil.YELLOW);
		} else {
			setColor(GRADIENT[(int) (getFuel() * (GRADIENT.length - 1) / MAX_FUEL)]);
		}
		super.draw(g, origin);
	}
	


	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected void handleCollision(GameObject other) {
		if (other instanceof NonPlayerShip) {
			handleCollision((NonPlayerShip) other);
		}

		super.handleCollision(other);
	}

	protected void handleCollision(NonPlayerShip other) {
		this.setAlive(false);
	}

}
