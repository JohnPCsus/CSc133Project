package com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class SpawnSpaceStation extends Command {

	public SpawnSpaceStation(String command) {
		super(command);
	}

	public SpawnSpaceStation(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			((GameWorld) getClientProperty("Target")).addStation();
			Log.p("Station added", Log.INFO);
		}
	}
}
