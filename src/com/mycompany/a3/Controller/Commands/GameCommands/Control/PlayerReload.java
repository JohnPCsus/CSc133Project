package com.mycompany.a3.Controller.Commands.GameCommands.Control;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class PlayerReload extends Command {

	public PlayerReload(String command) {
		super(command);
	}

	public PlayerReload(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		
		if(getClientProperty("Target") instanceof GameWorld) {
		boolean success = ((GameWorld) getClientProperty("Target")).playerReload();
		if (success) {
			Log.p("Player Reloads", Log.INFO);
		} else {
			Log.p("Error no Player Ship present", Log.INFO);
		}}
	}
}
