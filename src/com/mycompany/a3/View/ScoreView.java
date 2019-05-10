package com.mycompany.a3.View;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.Controller.Setting;
import com.mycompany.a3.Controller.UISettings;
import com.mycompany.a3.World.IWorldView;

public class ScoreView extends Container implements Observer {

	private Label scoreValue;
	private Label missilesValue;
	private Label timeValue;
	private Label livesValue;
	private Label soundValue;

	public ScoreView() {
		// set layout
		FlowLayout scoreBarLayout = new FlowLayout();
		// scoreBarLayout.setFillRows(true);
		this.setLayout(scoreBarLayout);

		// build score
		Label scoreTitle = new Label("Score:");
		scoreTitle.setTextPosition(RIGHT);
		scoreTitle.getStyle().setPaddingRight(0);
		this.add(scoreTitle);

		scoreValue = new Label("");
		scoreValue.setTextPosition(LEFT);
		scoreValue.getStyle().setPaddingLeft(0);
		this.add(scoreValue);
		// end score

		// build missiles
		Label missilesTitle = new Label("Missiles:");
		missilesTitle.setTextPosition(RIGHT);
		missilesTitle.getStyle().setPaddingRight(0);
		this.add(missilesTitle);

		missilesValue = new Label("");
		missilesValue.setTextPosition(LEFT);
		missilesValue.getStyle().setPaddingLeft(0);
		missilesValue.getStyle().setPaddingRight(10);
		this.add(missilesValue);
		// end missiles

		// build time
		Label timeTitle = new Label("Time:");
		timeTitle.setTextPosition(RIGHT);
		timeTitle.getStyle().setPaddingRight(0);
		this.add(timeTitle);

		timeValue = new Label("");
		timeValue.setTextPosition(LEFT);
		timeValue.getStyle().setPaddingLeft(0);
		this.add(timeValue);
		// end time

		// build lives
		Label livesTitle = new Label("Lives:");
		livesTitle.setTextPosition(RIGHT);
		livesTitle.getStyle().setPaddingRight(0);
		this.add(livesTitle);

		livesValue = new Label("");
		livesValue.setTextPosition(LEFT);
		livesValue.getStyle().setPaddingLeft(0);
		this.add(livesValue);
		// end lives

		// build sound
		Label soundTitle = new Label("Sound:");
		soundTitle.setTextPosition(RIGHT);
		soundTitle.getStyle().setPaddingRight(0);
		this.add(soundTitle);

		soundValue = new Label("");
		soundValue.setTextPosition(LEFT);
		soundValue.getStyle().setPaddingLeft(0);
		this.add(soundValue);
		// end sound
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof IWorldView) {
			IWorldView world = (IWorldView) arg;
			Map<String, String> newScore = world.getGameState();

			this.scoreValue.setText(newScore.get("Score"));

			missilesValue.setText(newScore.get("Missiles"));
			missilesValue.repaint();

			this.timeValue.setText(newScore.get("Time"));

			this.livesValue.setText(newScore.get("Lives"));

		} else if (arg instanceof UISettings) {
			boolean soundON = ((UISettings) arg).get(Setting.Sound);
			if (soundON) {
				soundValue.setText("ON");
			} else {
				soundValue.setText("OFF");
			}

		}

		this.repaint();
	}
}
