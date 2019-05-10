package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

public class Quit extends Command {

	public Quit(String command) {
		super(command);
	}

	public Quit(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		boolean confirmQuit = Dialog.show("Confirm quit", "Are you sure you want to quit?", "Yes", "No");
		if (confirmQuit) {
			System.exit(0);
		}
	}

}
