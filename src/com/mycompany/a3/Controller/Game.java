package com.mycompany.a3.Controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Util;
import com.mycompany.a3.Controller.Commands.About;
import com.mycompany.a3.Controller.Commands.New;
import com.mycompany.a3.Controller.Commands.Quit;
import com.mycompany.a3.Controller.Commands.Refuel;
import com.mycompany.a3.Controller.Commands.Save;
import com.mycompany.a3.Controller.Commands.Select;
import com.mycompany.a3.Controller.Commands.SoundToggle;
import com.mycompany.a3.Controller.Commands.Undo;
import com.mycompany.a3.Controller.Commands.Unselect;
import com.mycompany.a3.Controller.Commands.GameCommands.Tick;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.Jump;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerAccelerate;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerAimLeft;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerAimRight;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerDeccelerate;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerTurnLeft;
import com.mycompany.a3.Controller.Commands.GameCommands.Control.PlayerTurnRight;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.NonPlayerShipFire;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.PlayerFire;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.SpawnAsteroid;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.SpawnNonPlayerShip;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.SpawnPlayer;
import com.mycompany.a3.Controller.Commands.GameCommands.ObjectCreation.SpawnSpaceStation;
import com.mycompany.a3.Controller.Commands.GameCommands.View.MapCommand;
import com.mycompany.a3.Controller.Commands.GameCommands.View.ScoreCommand;
import com.mycompany.a3.View.MapView;
import com.mycompany.a3.View.ScoreView;
import com.mycompany.a3.View.Components.SidePanelButton;
import com.mycompany.a3.View.Sound.Sound;
import com.mycompany.a3.World.GameWorld;
import com.mycompany.a3.World.Events.Event;
import com.mycompany.a3.World.Events.EventStream;
import com.mycompany.a3.World.Events.GameStateEvent;

public class Game extends Form implements Runnable {

	private GameWorld world;
	private MapView mapView;
	private Command tick;
	private Command unSelect;
	private UITimer timer;
	private UISettings settings;
	private Sound soundDriver;
	private Collection<Command> disableOnPauseCommands;
	private Collection<Command> disableOnPlayCommands;
	private Collection<Button> disableOnPauseButtons;
	private Collection<Button> disableOnPlayButtons;

	public Game() {
		super("Spring 133 Asteroids");

		settings = new UISettings();
		world = new GameWorld();
		//Sound soundDriver;
		if (!Util.DEBUG) {
			 soundDriver = new Sound();
		}
		this.setLayout(new BorderLayout());

		// build top bar
		ScoreView scoreBar = new ScoreView();
		this.add(BorderLayout.NORTH, scoreBar);
		// end top bar

		mapView = new MapView(world);

		this.add(BorderLayout.CENTER, mapView);
		// end center

		// setup observer relationships
		settings.addObserver(this::settingsChanged);
		if (!Util.DEBUG) {
		settings.addObserver(soundDriver);}
		settings.addObserver(scoreBar);
		if (!Util.DEBUG) {
		world.getEventStream().addObserver(soundDriver);}

		world.addObserver(scoreBar);
		world.addObserver(mapView);

		// end observer relationships

		// instantiate all commands
		
		Command spawnAsteroid = new SpawnAsteroid("+ Asteroid");
		spawnAsteroid.putClientProperty("Target", world);
		
		Command spawnNonPlayerShip = new SpawnNonPlayerShip("+ NPS");
		spawnNonPlayerShip.putClientProperty("Target", world);
		
		Command spawnSpaceStation = new SpawnSpaceStation("+ Space Station");
		spawnSpaceStation.putClientProperty("Target", world);
		
		Command spawnPlayer = new SpawnPlayer("+ PS(1)");
		spawnPlayer.putClientProperty("Target", world);
		
		Command playerAccelerate = new PlayerAccelerate("PS Speed (+)");
		playerAccelerate.putClientProperty("Target", world);
		
		Command playerDeccelerate = new PlayerDeccelerate("PS Speed (-)");
		playerDeccelerate.putClientProperty("Target", world);
		
		Command playerTurnLeft = new PlayerTurnLeft("PS Left");
		playerTurnLeft.putClientProperty("Target", world);
		
		Command playerTurnRight = new PlayerTurnRight("PS Right");
		playerTurnRight.putClientProperty("Target", world);
		
		Command playerAimLeft = new PlayerAimLeft("MSL Left");
		playerAimLeft.putClientProperty("Target", world);
		
		Command playerAimRight = new PlayerAimRight("MSL Right");
		playerAimRight.putClientProperty("Target", world);
		
		Command playerFire = new PlayerFire("PS Fire");
		playerFire.putClientProperty("Target", world);
		
		Command npsFire = new NonPlayerShipFire("NPS Fire");
		npsFire.putClientProperty("Target", world);
		
		Command playerJump = new Jump("Jump");
		playerJump.putClientProperty("Target", world);
		
		tick = new Tick("Tick");
		tick.putClientProperty("Target", world);
		
		Command map = new MapCommand("Map");
		map.putClientProperty("Target", world);
		
		Command score = new ScoreCommand("Print");
		score.putClientProperty("Target", world);
		
		Command select = new Select("Select");
		select.putClientProperty("View", mapView);
		select.putClientProperty("World", world);
		mapView.addPointerPressedListener(select);
		mapView.setFocusable(true);
		
		unSelect = new Unselect("");
		unSelect.putClientProperty("World",world);
		
		Command refuel = new Refuel("Refuel");
		refuel.putClientProperty("Target", world);

		Command pause = Command.create("Pause", null, (ActionEvent e) -> {
			settings.set(Setting.Pause, !settings.get(Setting.Pause));

			if (e.getComponent() instanceof Button) {
				if (settings.get(Setting.Pause))
					((Button) e.getComponent()).setText("Play");
				else
					((Button) e.getComponent()).setText("Pause");
			}
		}); 

		Command quit = new Quit("Quit");
		Command about = new About("About");
		Command sound = new SoundToggle("", settings);
		Command newGame = new New("New");
		newGame.putClientProperty("GUI", this);
		Command save = new Save("Save");
		Command undo = new Undo("Undo");

		disableOnPause(spawnAsteroid);
		disableOnPause(spawnNonPlayerShip);
		disableOnPause(spawnSpaceStation);
		disableOnPause(spawnPlayer);
		disableOnPause(playerAccelerate);
		disableOnPause(playerDeccelerate);
		disableOnPause(playerTurnRight);
		disableOnPause(playerTurnLeft);
		disableOnPause(playerAimLeft);
		disableOnPause(playerAimRight);
		disableOnPause(playerFire);
		disableOnPause(npsFire);
		disableOnPause(playerJump);

		disableOnPlay(select);
		disableOnPlay(refuel);

		Container leftBar = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftBar.setFocusable(false);
		leftBar.setScrollableY(true);
		Label leftBarTitle = new Label("Commands");
		
		leftBar.add(leftBarTitle);

		Collection<Command> leftBarCommands = new LinkedList<>();

		leftBarCommands.add(spawnAsteroid);
		leftBarCommands.add(spawnNonPlayerShip);
		leftBarCommands.add(spawnSpaceStation);
		leftBarCommands.add(spawnPlayer);
		leftBarCommands.add(playerFire);
		leftBarCommands.add(npsFire);
		leftBarCommands.add(playerTurnLeft);
		leftBarCommands.add(playerTurnRight);
		leftBarCommands.add(playerAccelerate);
		leftBarCommands.add(playerDeccelerate);
		leftBarCommands.add(playerTurnLeft);
		leftBarCommands.add(playerAimRight);
		leftBarCommands.add(playerFire);
		leftBarCommands.add(playerJump);
		leftBarCommands.add(playerFire);
		leftBarCommands.add(pause);
		leftBarCommands.add(refuel);

		for (Command command : leftBarCommands) {

			Button b = new SidePanelButton(command.getCommandName());
			if (disableOnPauseCommands.contains(command)) {
				disableOnPause(b);
			}
			if (disableOnPlayCommands.contains(command)) {
				disableOnPlay(b);
			}

			/*
			 * This allows the use of the workaround mentioned by Prof. Nguyen Regarding the
			 * spacebar invoking the previous command without adding the code to every
			 * Command class.
			 * 
			 */
			b.addActionListener((e) -> {
				if (e.getKeyEvent() != -1) {
					command.actionPerformed(e);
				}
			});

			leftBar.add(b);
		}

		this.add(BorderLayout.WEST, leftBar);
		// end left bar

		// build toolbar
		Toolbar.setGlobalToolbar(true);
		Toolbar toolBar = new Toolbar();
		this.setToolbar(toolBar);

		Collection<Command> toolBarCommands = new LinkedList<>();
		toolBarCommands.add(quit);
		toolBarCommands.add(about);
		toolBarCommands.add(sound);
		toolBarCommands.add(newGame);
		toolBarCommands.add(save);
		toolBarCommands.add(undo);

		for (Command c : toolBarCommands) {

			toolBar.addCommandToSideMenu(c);
		}
		// end toolbar

		// set Key Bindings
		this.addKeyListener(-91, playerAccelerate); // up arrow
		this.addKeyListener('i', playerAccelerate);
		this.addKeyListener('d', playerDeccelerate);
		this.addKeyListener(-92, playerDeccelerate); // down arrow
		this.addKeyListener('l', playerTurnLeft);
		this.addKeyListener(-93, playerTurnLeft); // left arrow
		this.addKeyListener('r', playerTurnRight);
		this.addKeyListener(-94, playerTurnRight); // Right Arrow
		this.addKeyListener(',', playerAimLeft);
		this.addKeyListener('<', playerAimLeft);
		this.addKeyListener('.', playerAimRight);
		this.addKeyListener('>', playerAimRight);
		this.addKeyListener(-90, playerFire); // spacebar
		this.addKeyListener('j', playerJump);
		this.addKeyListener('q', quit);
		this.addKeyListener('Q', quit); // capital and lower case are both bound for CN1 workaround
		this.addKeyListener('m', map);
		this.addKeyListener('p', score);
		this.addKeyListener('P', score); // capital and lower case are both bound for CN1 workaround
		this.addKeyListener('t', tick);
		// end key Bindings

		world.getEventStream().addObserver((Observable source, Object data) -> {
			if (data instanceof EventStream) {
				for (Event v : (EventStream) data) {
					if (v instanceof GameStateEvent) {
						if (((GameStateEvent) v).getType() == GameStateEvent.LIVES
								&& ((GameStateEvent) v).getValue() == 0) {
							timer.cancel();
							Dialog d = new Dialog("GAME OVER!!");
							d.setLayout(new BorderLayout());

							Container body = new Container(new FlowLayout());

							Map<String, String> finalState = world.getGameState();
							body.add(new Label("Final Score: "));
							body.add(new Label(finalState.get("Score")));

							FlowLayout footLayout = new FlowLayout();
							footLayout.setFillRows(true);
							Container foot = new Container(footLayout);
							foot.add(new Button(newGame));
							foot.add(new Button(quit));

							d.add(BorderLayout.CENTER, body);
							d.add(BorderLayout.SOUTH, foot);
							d.showModeless();

						}
					}
				}
			}
		});

		this.show();

		// world initialization

		newGame();
		settings.init();
	}

	public void newGame() {
		world.init();
		timer = new UITimer(this);
		timer.schedule(Util.tickRate, true, this);
	}

	@Override
	public void repaint() {
		if (mapView != null) {
			world.setXBounds(mapView.getWidth());
			world.setYBounds(mapView.getHeight());
		}
		super.repaint();
	}

	@Override
	public void run() {
		tick.actionPerformed(null);
	}

	private void settingsChanged(Observable source, Object data) {
		if (source instanceof UISettings) {

			if (((UISettings) data).get(Setting.Pause)) {
				pause();
				
			} else {
				resume();
			}
		}
	}

	private void pause() {
		timer.cancel();
		
		if (disableOnPauseCommands != null) {
			for (Command c : disableOnPauseCommands) {
				c.setEnabled(false);
			}
		}
		if (disableOnPauseButtons != null) {
			for (Button b : disableOnPauseButtons) {
				b.setEnabled(false);
			}
		}
		if (disableOnPlayCommands != null) {
			for (Command c : disableOnPlayCommands) {
				c.setEnabled(true);
			}
		}
		if (disableOnPlayButtons != null) {
			for (Button b : disableOnPlayButtons) {
				b.setEnabled(true);
			}
		}

	}

	private void resume() {
		timer.schedule(Util.tickRate, true, this);
		
		unSelect.actionPerformed(null);

		if (disableOnPauseCommands != null) {
			for (Command c : disableOnPauseCommands) {
				c.setEnabled(true);
			}
		}
		if (disableOnPauseButtons != null) {
			for (Button b : disableOnPauseButtons) {
				b.setEnabled(true);
			}
		}
		if (disableOnPlayCommands != null) {
			for (Command c : disableOnPlayCommands) {
				c.setEnabled(false);
			}
		}
		if (disableOnPlayButtons != null) {
			for (Button b : disableOnPlayButtons) {
				b.setEnabled(false);
			}
		}
	}

	private void disableOnPause(Command c) {
		if (disableOnPauseCommands == null) {
			disableOnPauseCommands = new LinkedList<>();
		}
		disableOnPauseCommands.add(c);
	}

	private void disableOnPause(Button b) {
		if (disableOnPauseButtons == null) {
			disableOnPauseButtons = new LinkedList<>();
		}
		disableOnPauseButtons.add(b);
	}

	private void disableOnPlay(Command c) {
		if (disableOnPlayCommands == null) {
			disableOnPlayCommands = new LinkedList<>();
		}
		disableOnPlayCommands.add(c);
	}

	private void disableOnPlay(Button b) {
		if (disableOnPlayButtons == null) {
			disableOnPlayButtons = new LinkedList<>();
		}
		disableOnPlayButtons.add(b);
	}
}