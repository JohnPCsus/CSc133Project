package com.mycompany.a3.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class UISettings extends Observable {
	private Map<Setting, Boolean> settings;

	public UISettings() {
		settings = new HashMap<>();
		
	}

	public void set(Setting setting, boolean value) {
		settings.put(setting, value);
		setChanged();
		notifyObservers(this);
	}

	public boolean get(Setting setting) {
		return settings.get(setting);
	}
	
	private void setDefault() {
		settings.put(Setting.Sound, true);
		settings.put(Setting.Pause, false);
	}
	
	public void init() {
		setDefault();
		setChanged();
		notifyObservers(this);
	}

}
