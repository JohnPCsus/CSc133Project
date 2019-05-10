package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Controller.Game;

public class New extends Command {

	public New(String command) {
		super(command);
	}

	public New(String command, Image icon) {
		super(command, icon);
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		((Game) getClientProperty("GUI")).newGame();
	}

	
}
