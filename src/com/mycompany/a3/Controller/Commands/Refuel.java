package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class Refuel extends Command {

	public Refuel(String command) {
		super(command);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		super.actionPerformed(evt);
		assert(getClientProperty("Target") != null);
		if(isEnabled()) {
			((GameWorld) getClientProperty("Target")).refuelMissile();
		}
	}
	
	

}
