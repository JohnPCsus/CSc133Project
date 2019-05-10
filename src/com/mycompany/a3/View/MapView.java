package com.mycompany.a3.View;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.World.IWorldView;
import com.mycompany.a3.World.Collection.IIterator;
import com.mycompany.a3.World.Objects.Drawable.Drawable;
import com.mycompany.a3.World.Objects.Moveable.MoveableObject;
import com.mycompany.a3.World.Objects.Moveable.Steerable.ISteerable;

public class MapView extends Container implements Observer {
	private IWorldView target;
	// private Transform displayMap;

	public MapView(IWorldView target) {
		this.target = target;
		this.setLayout(new LayeredLayout());
		this.add(new Pane(Pane.FIXED));
		this.add(new Pane(Pane.MOVEABLE));
		this.add(new Pane(Pane.PLAYER));

		this.getAllStyles().setBorder(Border.createDashedBorder(2));

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof IWorldView) {
			repaint();
		}
	}
	
	class Pane extends Container {
		int level;
		public static final int PLAYER = 0;
		public static final int MOVEABLE = 1;
		public static final int FIXED = 2;

		Pane(int level) {
			this.level = level;
		}

		@Override
		public void paint(Graphics g) {
			IIterator drawables = target.getObjects();
			Transform displayMapping = Transform.makeIdentity();

			displayMapping.translate(getAbsoluteX(), getAbsoluteY());
			displayMapping.translate(0, getHeight());
			displayMapping.scale(1, -1);
			displayMapping.translate(-getAbsoluteX(), -getAbsoluteY());

			g.setTransform(displayMapping);

			while (drawables.hasNext()) {
				Drawable d = drawables.getNext();
				switch (level) {
				case 0:
					if (d instanceof ISteerable) {
						d.draw(g, new Point2D(this.getAbsoluteX(), this.getAbsoluteY()));
					}
					break;
				case 1:
					if (d instanceof MoveableObject && !(d instanceof ISteerable)) {
						d.draw(g, new Point2D(this.getAbsoluteX(), this.getAbsoluteY()));
					}
					break;
				case 2:
					if (!(d instanceof MoveableObject)) {
						d.draw(g, new Point2D(this.getAbsoluteX(), this.getAbsoluteY()));
					}
					break;
				default:
					assert (false);
				}

			}
			g.resetAffine();

		}
	}

}
