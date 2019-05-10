package com.mycompany.a3.World.Visitors;

import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.InvisibleWall;
import com.mycompany.a3.World.Objects.Station;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.NonPlayerShip;
import com.mycompany.a3.World.Objects.Moveable.Missiles.NpsMissile;
import com.mycompany.a3.World.Objects.Moveable.Missiles.PsMissile;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;

public abstract class Visitor {
	public void visit(Station target) {

	}

	public void visit(NonPlayerShip target) {

	}

	public void visit(PlayerShip target) {

	}

	public void visit(PsMissile target) {

	}

	public void visit(NpsMissile target) {

	}

	public void visit(InvisibleWall target) {

	}

	public void visit(Asteroid asteroid) {

	}

	public void visit(GameObject gameObject) {

	}

}
