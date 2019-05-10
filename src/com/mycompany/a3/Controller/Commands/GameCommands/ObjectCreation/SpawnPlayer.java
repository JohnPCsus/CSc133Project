package com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class SpawnPlayer extends Command {

	public SpawnPlayer(String command) {
		super(command);
	}

	public SpawnPlayer(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);

		if (getClientProperty("Target") instanceof GameWorld) {
			boolean success = ((GameWorld) getClientProperty("Target")).addPlayerShip();
			if (success) {
				Log.p("Player Ship added", Log.INFO);
			} else {
				Log.p("Failed to add Player Ship");
			}
		}
	}
}
