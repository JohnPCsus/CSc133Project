package com.mycompany.a3.World.Objects.Drawable;

import java.util.ArrayList;
import java.util.Collection;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.GeneralPath;

public class ModelFactory {

	public ModelFactory() {
	};
	
	public GameObjectModel getModel(String type, int color, Drawable target) {
		
		GameObjectModel product = new GameObjectModel(target);
		product.setModel(getModel(type, color));
		return product;
	}

	private Model getModel(String type, int color) {
		
		
		
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
		GeneralPath shape1 = new GeneralPath();
		shape1.moveTo(0, -3.5);
		shape1.lineTo(20, -3.5);
		shape1.lineTo(20, 3.5);
		shape1.lineTo(0, 3.5);
		shape1.lineTo(0, -3.5);
		

		LeafModel model1 = new LeafModel();
		model1.setShape(shape1);
		model1.setColor(color);
		model1.setFill(true);
		model1.setScale(Transform.makeScale(.5f, 1f));
		
		LeafModel model2 = new LeafModel();
		model2.setShape(shape1);
		model2.setColor(ColorUtil.BLACK);
		model2.setFill(true);
		model2.setRotation(Transform.makeRotation((float) Math.toRadians(180),0,0));
		model2.setScale(Transform.makeScale(.5f, 1f));
		
		Collection<Model> models = new ArrayList<>();
		models.add(model1);
		models.add(model2);
		
		CompositeModel mod = new CompositeModel(models);
		
		return mod;
	}

	private LeafModel buildMissileLauncherModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(0, -1);
		newShape.lineTo(.5, -1);
		newShape.lineTo(0.5, 1);
		newShape.lineTo(0, 1);
		newShape.lineTo(0, -1);

		LeafModel newModel = new LeafModel();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}

	private LeafModel buildAsteroidModel(int color) {
		GeneralPath newShape = new GeneralPath();

		float[] point1 = { 0, 1 };
		newShape.moveTo(point1[0], point1[1]);
		for (int i = 1; i < 7; i++) {
			Transform rotate = Transform.makeRotation((float) Math.toRadians(60 * i), 0, 0);
			newShape.lineTo(rotate.transformPoint(point1)[0], rotate.transformPoint(point1)[1]);
		}
		LeafModel newModel = new LeafModel();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);

		return newModel;
	}

	private LeafModel buildInvisibleWallModel(int color) {
		GeneralPath newShape = new GeneralPath();
		newShape.moveTo(0, 0);
		newShape.lineTo(1, 0);
		newShape.lineTo(1, -1);
		newShape.lineTo(0, -1);
		newShape.lineTo(0, 0);

		LeafModel newModel = new LeafModel();
		newModel.setShape(newShape);
		newModel.setFill(true);
		newModel.setColor(color);
		return newModel;
	}

	private LeafModel buildNpsModel(int color) {

		GeneralPath newShape = new GeneralPath();
		float[] point1 = { 1, 0 };
		newShape.moveTo(point1[0], point1[1]);

		Transform t = Transform.makeRotation((float) Math.toRadians(120), 0f, 0f);
		for (int i = 0; i < 3; i++) {

			point1 = t.transformPoint(point1);
			newShape.lineTo(point1[0], point1[1]);
		}

		LeafModel newModel = new LeafModel();
		newModel.setShape(newShape);
		newModel.setFill(true);
		newModel.setColor(color);
		return newModel;

	}

	private LeafModel buildPlayerShipModel(int color) {

		GeneralPath thisShape = new GeneralPath();
		thisShape.moveTo(0.5, 0);
		thisShape.lineTo(-0.5, -0.5);
		thisShape.lineTo(-0.9, 0);
		thisShape.lineTo(-0.5, 0.5);
		thisShape.lineTo(0.5, 0);

		LeafModel newModel = new LeafModel();
		newModel.setShape(thisShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}

	private LeafModel buildStationModel(int color) {
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
		LeafModel newModel = new LeafModel();
		newModel.setShape(newShape);
		newModel.setColor(color);
		newModel.setFill(true);
		return newModel;
	}
}
