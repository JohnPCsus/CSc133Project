package com.mycompany.a3.Controller.Commands;

import java.io.IOException;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Controller.Setting;
import com.mycompany.a3.Controller.UISettings;

public class SoundToggle extends Command {
	private Image soundOn;
	private Image soundOff;
	private UISettings settings;

	public SoundToggle(String command, UISettings settings) {
		super(command);
		this.settings = settings;

		try {
			soundOff = Image.createImage("/disabled.png").scaled(30, 30);
			soundOn = Image.createImage("/enabled.png").scaled(30, 30);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIcon(soundOn);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(!isEnabled()) return;
		
		Component source = evt.getComponent();
		if (source instanceof Button) {
			if (settings.get(Setting.Sound)) {
				((Button) source).setIcon(soundOff);
				settings.set(Setting.Sound, false);
			} else {
				((Button) source).setIcon(soundOn);
				settings.set(Setting.Sound, true);
			}
		}
	}

}
