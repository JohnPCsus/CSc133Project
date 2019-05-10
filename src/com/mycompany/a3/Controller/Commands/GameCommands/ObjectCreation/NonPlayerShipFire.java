package com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class NonPlayerShipFire extends Command {

	public NonPlayerShipFire(String command) {
		super(command);
	}

	public NonPlayerShipFire(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			boolean success = ((GameWorld) getClientProperty("Target")).nonPlayerFire();
			if (success) {
				Log.p("NPS Fires", Log.INFO);
			} else {
				Log.p("Failed to create NPS Missile", Log.INFO);
			}
		}
	}
}
