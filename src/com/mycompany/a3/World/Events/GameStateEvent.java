package com.mycompany.a3.World.Events;

public class GameStateEvent implements Event {

	//public static final int GAME_OVER = 0;
	public static final int SCORE = 1;
	public static final int LIVES = 2;
	public static final int TIME = 4;
	
	private int value;
	private int type;
	
	public GameStateEvent (int type,int value ) {
		this.type = type;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getType() {
		return type;
	}
}
