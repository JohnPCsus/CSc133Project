package com.mycompany.a3.World;

public class GameState {

	private int score;
	private int playerLives;
	private long time;
	private boolean gameActive;

	GameState() {
		score = 0;
		time = 0;
		playerLives = 3;

		gameActive = true;// If Game is not active state is immutable
	}

	public int getScore() {
		return score;
	}

	public int getPlayerLives() {

		return playerLives;
	}

	public long getTime() {
		return time;
	}

	public boolean isGameActive() {
		return gameActive;
	}

	// TODO add score increases
	void setScore(int score) {
		if (gameActive) {
			this.score = score;
		}
	}

	void setPlayerLives(int playerLives) {
		if (gameActive) {
			this.playerLives = playerLives;
		}
	}

	void setTime(long time) {
		if (gameActive) {
			this.time = time;
		}
	}

	void setGameActive(boolean gameActive) {
		if (this.gameActive) {
			this.gameActive = gameActive;
		}
	}

}