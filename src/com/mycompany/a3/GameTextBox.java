package com.mycompany.a3;

import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.World.GameWorld;
@Deprecated
public class GameTextBox extends Form {

	private GameWorld world;

	public GameTextBox() {
		super("Spring 133 Asteroids", new FlowLayout());
		world = new GameWorld();

		TextField commandInput = new TextField("", "Enter Command", 10, TextField.ANY);
		Button commandEnter = new Button("Enter");

		this.add(commandInput);
		this.add(commandEnter);
		this.setLeadComponent(commandInput);

		commandInput.addActionListener(this::handleInput);

	}

	private void handleInput(ActionEvent e) {
		Component active = e.getComponent();
		if (active instanceof TextField) {
			play(((TextField) active).getText());
			((TextField) active).clear();
		}
	}

	private void play(String s) {
		for (char c : s.toCharArray()) {

			Log.p("Input : " + c, Log.INFO);
			switch (c) {
			/*
			 * game control
			 */
			case 'a':
				world.addAsteroid();
				break;
			case 'y':
				world.addNonPlayerShip();
				break;
			case 's':
				world.addPlayerShip();
				break;
			case 'b':
				world.addStation();
				break;
			case 'm':
				System.out.println(world.getMap());
				break;
			/*
			 * Commands
			 */
			case 'i':
				world.playerAccelerate();
				break;
			case 'd':
				world.playerDeccelerate();
				break;
			case 'l':
				world.playerTurnLeft();
				break;
			case 'r':
				world.playerTurnRight();
				break;
			case '>':
				world.playerAimRight();
				break;
			case '<':
				world.playerAimLeft();
				break;
			case 'f':
				world.playerFire();
				break;
			case 'L':
				world.nonPlayerFire();
				break;
			case 'j':
				world.playerHyperspace();
				break;
			case 'n':
				world.playerReload();
				break;
			case 'k':
				// world.missileHitAsteroid();
//				world.removeAsteroid();
//				world.removeMissile();
				break;
			case 'e':
				// world.missileHitNonPlayerShip();
//				world.removeMissile();
//				world.removeNonPlayerShip();
				break;
			case 'E':
				// world.missileHitPlayer();
//				world.removePlayerShip();
//				world.removeMissile();
				break;
			case 'c':
				// world.asteroidHitPlayer();
//				world.removeAsteroid();
//				world.removePlayerShip();
				break;
			case 'h':
				// world.playerShipHitNonPlayerShip();
//				world.removeNonPlayerShip();
//				world.removeNonPlayerShip();
				break;
			case 'x':
//				world.removeAsteroid();
//				world.removeAsteroid();
				break;
			case 'I':
				// world.asteroidHitNonPlayerShip();
//				world.removeAsteroid();
//				world.removeNonPlayerShip();
				break;
			case 't':
			//	world.gameTick();
				break;
			case 'p':
				System.out.println(world.getGameState());
				break;
			case 'q':
				boolean confirmQuit = Dialog.show("Confirm quit", "Are you sure you want to quit?", "Yes", "No");
				if (confirmQuit) {
					System.exit(0);
				}
				break;
			default:// on invalid input do nothing; log bad character
				Log.p("source : " + this + ", message : invalid input" + ", input: " + c, Log.WARNING);
			}
		}
	}
}
