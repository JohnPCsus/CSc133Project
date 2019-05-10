package com.mycompany.a3.Controller.Commands.GameCommands.Control;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class Jump extends Command {

	public Jump(String command) {
		super(command);
	}

	public Jump(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		boolean success = false;
		if (getClientProperty("Target") instanceof GameWorld) {
			success = ((GameWorld) getClientProperty("Target")).playerHyperspace();
		}
		if (success) {
			Log.p("Player Jumps", Log.INFO);
		} else {
			Log.p("Error no Player Ship present", Log.INFO);
		}
	}
}
