package com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class SpawnAsteroid extends Command {

	public SpawnAsteroid(String command) {
		super(command);
	}

	public SpawnAsteroid(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			((GameWorld) getClientProperty("Target")).addAsteroid();
			Log.p("Asteroid added", Log.INFO);
		}
	}
}
