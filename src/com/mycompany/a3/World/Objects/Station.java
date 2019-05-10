package com.mycompany.a3.World.Objects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Transform;
import com.mycompany.a3.World.Objects.Collidable.Collidible;
import com.mycompany.a3.World.Objects.Drawable.ModelFactory;
import com.mycompany.a3.World.Visitors.Visitor;

public class Station extends GameObject implements Collidible {
	private static int nextID = 0;
	private static final int MAX_BLINK_RATE = 4;
	private static final int ROTATION_RATE = 3;

	private int blinkRate;
	private boolean lightOn;
	private int ID;
	private Transform rotation;
	

	public Station(double xLocation, double yLocation) {
		super(xLocation, yLocation);
		
		ID = nextID;
		nextID++;
		lightOn = false;
		setColor(ColorUtil.CYAN);
		blinkRate = 1;
		
		
		setModel(new ModelFactory().getModel("Station",getColor()));
		
		rotation = Transform.makeIdentity();
		
			
	}

	public void setBlinkRate(int newBlinkRate) {
		if (newBlinkRate < 1) {
			blinkRate = 1;
		} else if (newBlinkRate > MAX_BLINK_RATE) {
			blinkRate = MAX_BLINK_RATE;
		} else {
			blinkRate = newBlinkRate;
		}
	}

	public void updateLight(long gameTime) {
		int timeElapsedSec = (int) (gameTime / 1000);
		if (timeElapsedSec % blinkRate == 0 && gameTime % 1000 == 0) {
			lightOn = !lightOn;
			
		}
		if(lightOn) {
			setColor(ColorUtil.CYAN);
		} else {
			setColor(ColorUtil.BLUE);
		}
		updateRotation(gameTime);
	}
	
	public void setColor(int newColor) {
		super.setColor(newColor);
		setModel(new ModelFactory().getModel("Station", newColor));
	}

	@Override
	public String toString() {
		return super.toString() + " ID=" + ID + " blinkRate=" + blinkRate + " lightOn= " + lightOn;
	}

	@Override
	public Transform getScale() {
		return Transform.makeScale(30, 30);
	}
	
	private void updateRotation(long gameTime) {
		rotation = Transform.makeRotation((float)Math.toRadians(gameTime / 1000 * ROTATION_RATE), 0, 0);
	}
	
	@Override
	public Transform getRotation() {
		return rotation;
	}

	public boolean lightOn() {
		return lightOn;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
