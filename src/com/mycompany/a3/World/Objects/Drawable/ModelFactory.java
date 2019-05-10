package com.mycompany.a3.World.Objects.Drawable;

import com.codename1.ui.Transform;
import com.codename1.ui.geom.GeneralPath;

public class ModelFactory {

	public ModelFactory() {
	};

	public Model getModel(String type, int color) {
		if (type.equalsIgnoreCase("NonPlayerShip")) {
			return buildNpsModel(color);
		} else if (type.equalsIgnoreCase("PlayerShip")) {
			return buildPlayerShipModel(color);
		} else if (type.equalsIgnoreCase("Station")) {
			return buildStationModel(color);
		} else if (type.equalsIgnoreCase("InvisibleWall")) {
			return buildInvisibleWallModel(color);
		} else if (type.equalsIgnoreCase("Asteroid")) {
			return buildAsteroidModel(color);
		} else if (type.equalsIgnoreCase("MissileLauncher")) {
			return buildMissileLauncherModel(color);
		} else if (type.equalsIgnoreCase("Missile")) {
			return buildMissileModel(color);
		}

		else {
			return null;
		}
	}

	private Model buildMissileModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(0, -3.5);
		newShape.lineTo(20, -3.5);
		newShape.lineTo(20, 3.5);
		newShape.lineTo(0, 3.5);
		newShape.lineTo(0, -3.5);
		
		
		

		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);
		
		return newModel;
	}

	private Model buildMissileLauncherModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(0, -1);
		newShape.lineTo(.5, -1);
		newShape.lineTo(0.5, 1);
		newShape.lineTo(0, 1);
		newShape.lineTo(0, -1);

		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}

	private Model buildAsteroidModel(int color) {
		GeneralPath newShape = new GeneralPath();

		float[] point1 = { 0, 1 };
		newShape.moveTo(point1[0], point1[1]);
		for (int i = 1; i < 7; i++) {
			Transform rotate = Transform.makeRotation((float) Math.toRadians(60 * i), 0, 0);
			newShape.lineTo(rotate.transformPoint(point1)[0], rotate.transformPoint(point1)[1]);
		}
		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);

		return newModel;
	}

	private Model buildInvisibleWallModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(0, 0);
		newShape.lineTo(1, 0);
		newShape.lineTo(1, -1);
		newShape.lineTo(0, -1);
		newShape.lineTo(0, 0);

		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setFill(true);
		newModel.setColor(color);
		return newModel;
	}

	private Model buildNpsModel(int color) {

		GeneralPath newShape = new GeneralPath();
		float[] point1 = { 1, 0 };
		newShape.moveTo(point1[0], point1[1]);

		Transform t = Transform.makeRotation((float) Math.toRadians(120), 0f, 0f);
		for (int i = 0; i < 3; i++) {

			point1 = t.transformPoint(point1);
			newShape.lineTo(point1[0], point1[1]);
		}

		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setFill(true);
		newModel.setColor(color);
		return newModel;

	}

	private Model buildPlayerShipModel(int color) {

		GeneralPath thisShape = new GeneralPath();
		thisShape.moveTo(0.5, 0);
		thisShape.lineTo(-0.5, -0.5);
		thisShape.lineTo(-0.9, 0);
		thisShape.lineTo(-0.5, 0.5);
		thisShape.lineTo(0.5, 0);

		Model newModel = new Model();
		newModel.setShape(thisShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}

	private Model buildStationModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(-1, 1);
		newShape.lineTo(1, 1);

		for (int i = 1; i < 6; i++) {

			float[] point1 = { -1, 1f };
			float[] point2 = { 1f, 1f };

			Transform rotate = Transform.makeRotation((float) Math.toRadians(60 * i), 0, 0);
			newShape.lineTo(rotate.transformPoint(point1)[0], rotate.transformPoint(point1)[1]);

			newShape.lineTo(rotate.transformPoint(point2)[0], rotate.transformPoint(point2)[1]);
		}
		Model newModel = new Model();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}
}
