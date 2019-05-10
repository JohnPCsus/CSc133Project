package com.mycompany.a3.Controller.Commands.GameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Util;
import com.mycompany.a3.World.GameWorld;

public class Tick extends Command {

	public Tick(String command, Image icon) {
		super(command, icon);
	}

	public Tick(String command) {
		super(command);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		if (getClientProperty("Target") instanceof GameWorld) {
			((GameWorld) getClientProperty("Target")).gameTick(Util.tickRate);
		}

	}

}
