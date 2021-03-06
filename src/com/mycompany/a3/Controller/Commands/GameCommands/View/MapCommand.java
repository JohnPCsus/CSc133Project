package com.mycompany.a3.Controller.Commands.GameCommands.View;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.World.GameWorld;

public class MapCommand extends Command {

	public MapCommand(String command) {
		super(command);
	}

	public MapCommand(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isEnabled()) return;
		
		assert (getClientProperty("Target") != null);
		System.out.println(((GameWorld) getClientProperty("Target")).getMap());
	}
}
