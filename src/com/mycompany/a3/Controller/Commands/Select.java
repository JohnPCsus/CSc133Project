package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.View.MapView;
import com.mycompany.a3.World.GameWorld;
import com.mycompany.a3.World.Collection.IIterator;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Selectable;

public class Select extends Command {

	public Select(String command) {
		super(command);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		assert (getClientProperty("View") != null && getClientProperty("World") != null);
		if (isEnabled()) {

			int x = evt.getX();
			int y = evt.getY();

			MapView targetableArea = (MapView) getClientProperty("View");
			GameWorld world = (GameWorld) getClientProperty("World");

			if (targetableArea.contains(x, y)) {
				x -= targetableArea.getAbsoluteX();
				y -= targetableArea.getAbsoluteY();
				y = -y + targetableArea.getHeight();

				for (IIterator objects = world.getObjects(); objects.hasNext();) {
					GameObject current = objects.getNext();
					if (current instanceof Selectable) {

						((Selectable) current).setSelected(((Selectable) current).contains(new Point2D(x, y)));

					}
				}

				targetableArea.repaint();
				super.actionPerformed(evt);
			}

		}
	}

}
