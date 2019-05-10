package com.mycompany.a3.Controller.Commands.GameCommands.Control;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class PlayerAimRight extends Command {

	public PlayerAimRight(String command) {
		super(command);
	}

	public PlayerAimRight(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			boolean success = ((GameWorld) getClientProperty("Target")).playerAimRight();
			if (success) {
				Log.p("Player Aims Right", Log.INFO);
			} else {
				Log.p("Error no Player Ship present", Log.INFO);
			}
		}
	}
}
