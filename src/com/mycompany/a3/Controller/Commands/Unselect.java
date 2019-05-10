package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;
import com.mycompany.a3.World.Collection.IIterator;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Selectable;

public class Unselect extends Command {

	public Unselect(String command) {
		super(command);
	}
	
	public void actionPerformed(ActionEvent evt){
		assert(getClientProperty("World") != null);
		
		GameWorld world = (GameWorld) getClientProperty("World");
		for(IIterator objects = world.getObjects(); objects.hasNext();) {
			GameObject current = objects.getNext();
			if(current instanceof Selectable) {
				((Selectable) current).setSelected(false);
			}
		}
	}

}
