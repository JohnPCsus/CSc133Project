package com.mycompany.a3.World.Collection;

import com.mycompany.a3.World.Objects.GameObject;

public interface IIterator {
	public boolean hasNext();

	public GameObject getNext();
}
