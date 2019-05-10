package com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class SpawnNonPlayerShip extends Command {

	public SpawnNonPlayerShip(String command, Image icon) {
		super(command, icon);
	}

	public SpawnNonPlayerShip(String command) {
		super(command);

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			((GameWorld) getClientProperty("Target")).addNonPlayerShip();
			Log.p("Non Player Ship added", Log.INFO);
		}
	}

}
