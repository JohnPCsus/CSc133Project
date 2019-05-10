package com.mycompany.a3.Controller.Commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;

public class About extends Command {

	public About(String command) {
		super(command);
	}

	public About(String command, Image icon) {
		super(command, icon);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
//		Dialog info = new Dialog("About");
//		info.add(new Label("info"));
//		Button okButton = new Button("OK");
//		okButton.addActionListener((e)->
//		{
//			e.getComponent().get
//		});
//		info.show();
		
		Dialog.show(
				// TODO better info
				"About", "Creator: John Pettet \n CSc 133 \n Spring 2019", "OK", "");

	}
	
	
}
