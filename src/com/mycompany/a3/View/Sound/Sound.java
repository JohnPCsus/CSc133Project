package com.mycompany.a3.View.Sound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.mycompany.a3.Controller.Setting;
import com.mycompany.a3.Controller.UISettings;
import com.mycompany.a3.World.Events.Action;
import com.mycompany.a3.World.Events.Control;
import com.mycompany.a3.World.Events.Event;
import com.mycompany.a3.World.Events.EventStream;
import com.mycompany.a3.World.Events.GameStateEvent;
import com.mycompany.a3.World.Events.ObjectDestruction;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;

public class Sound implements Observer {
	
	
	private BackgroundSound background;
	private Map<String, ReplayableSound> soundEffects;
	
	private boolean soundOn;

	public Sound() {
		soundOn = true;
		soundEffects = new HashMap<>();
		soundEffects.put("AsteroidDestroyed", new EncapsulatedSound("Asteroid_Destroyed.mp3", "audio/mp3"));
		soundEffects.put("PlayerDestroyed", new EncapsulatedSound("Player_Destroyed.wav", "audio/wav"));
		soundEffects.put("Reload", new EncapsulatedSound("Reload.wav", "audio/wav"));
		soundEffects.put("MissileFire", new EncapsulatedSound("Missile_Fire.wav", "audio/wav"));
		soundEffects.put("TurretRotate", new EncapsulatedSound("Turret_Rotate.wav", "audio/wav"));
		soundEffects.put("GameOver", new EncapsulatedSound("Game_Over.wav", "audio/wav"));

		background = new BackgroundSound("background.mp3", "audio/mp3");
		background.play();
	}

	@Override
	public void update(Observable observable, Object data) {

		if (observable instanceof EventStream ) {
			handleGameEvent(((EventStream) data).getEvents());
		} else if (observable instanceof UISettings) {
			assert (data instanceof UISettings);
			handleSettingsChange((UISettings) data);
		}

	}

	private void handleGameEvent(Iterator<Event> data) {
		if(!soundOn) {
			return;
		}

		background.play();
		Iterator<Event> iterator = data;
		while (iterator.hasNext()) {

			Event e = iterator.next();
			if (e instanceof ObjectDestruction) {
				Class<? extends GameObject> type = ((ObjectDestruction) e).getType();

				if (type.equals(Asteroid.class)) {

					soundEffects.get("AsteroidDestroyed").play();
				}

				if (type.equals(PlayerShip.class)) {
					soundEffects.get("PlayerDestroyed").play();
				}
			} else if (e instanceof Control) {
				Control event = (Control) e;
				if (event.getAction() == Action.Reload && event.isSuccess()) {
					soundEffects.get("Reload").play();
				}
				if (event.getAction() == Action.Fire && event.isSuccess()
						&& event.getActor().equals(PlayerShip.class)) {
					soundEffects.get("MissileFire").play();
				}
				if ((event.getAction() == Action.AimLeft || event.getAction() == Action.AimRight) && event.isSuccess()
						&& event.getActor().equals(PlayerShip.class)) {
					soundEffects.get("TurretRotate").play();
				}
			}

			else if (e instanceof GameStateEvent) {
				GameStateEvent event = (GameStateEvent) e;
				if (event.getType() == GameStateEvent.LIVES && event.getValue() == 0) {
					background.stop();
					soundEffects.get("GameOver").play();

				}
			}

		}

	}

	private void handleSettingsChange(UISettings data) {
		soundOn = data.get(Setting.Sound) && !data.get(Setting.Pause);
		if(soundOn) {
			background.play();
		} else {
			background.stop();
		}
	}
}
