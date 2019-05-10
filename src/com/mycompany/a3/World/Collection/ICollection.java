package com.mycompany.a3.World.Collection;

import com.mycompany.a3.World.Objects.GameObject;

public interface ICollection {
	public void add(GameObject newObject);

	public void remove(GameObject target);

	public void clear();

	public IIterator getIterator();
}
