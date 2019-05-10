package com.mycompany.a3.World.Visitors;

import com.mycompany.a3.World.Collection.IIterator;
import com.mycompany.a3.World.Objects.GameObject;
import com.mycompany.a3.World.Objects.InvisibleWall;
import com.mycompany.a3.World.Objects.Station;
import com.mycompany.a3.World.Objects.Moveable.Asteroid;
import com.mycompany.a3.World.Objects.Moveable.MoveableObject;
import com.mycompany.a3.World.Objects.Moveable.NonPlayerShip;
import com.mycompany.a3.World.Objects.Moveable.Missiles.Missile;
import com.mycompany.a3.World.Objects.Moveable.Missiles.NpsMissile;
import com.mycompany.a3.World.Objects.Moveable.Missiles.PsMissile;
import com.mycompany.a3.World.Objects.Moveable.Steerable.PlayerShip;

public class TimeStepVisitor extends Visitor {
	
	private long lastTimeMillis;
	private long currentTimeMillis = 0;
		
	
	public void visitAll(IIterator objects, long timeMillis) {
		lastTimeMillis = currentTimeMillis;
		currentTimeMillis = timeMillis;
		
		for(;objects.hasNext();) {
			GameObject current = objects.getNext();
			current.accept(this);
		}
	}
	
	@Override
	public void visit(Station target) {
		target.updateLight(currentTimeMillis);
		
	}
	
	@Override
	public void visit(InvisibleWall target) {
		return;
	}
	
	@Override
	public void visit(Asteroid target) {
		move(target);
	}
	
	public void visit(NonPlayerShip target) {
		move(target);
	}
	
	@Override
	public void visit(PlayerShip target) {
		move(target);
	}
	
	@Override
	public void visit(PsMissile target) {
		move(target);
		missileFuelCheck(target);
	}
	
	@Override
	public void visit(NpsMissile target) {
		move(target);
		missileFuelCheck(target);
		
	}
	
	private void move (MoveableObject moveable) {
		moveable.move((int)(currentTimeMillis - lastTimeMillis));
	}
	
	private void missileFuelCheck(Missile missile) {
		if(missile.getFuel() <= 0) {
			missile.setAlive(false);
		}
	}
	
}
