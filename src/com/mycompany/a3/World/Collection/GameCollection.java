package com.mycompany.a3.World.Collection;

import java.util.ArrayList;
import java.util.Collection;

import com.mycompany.a3.World.Objects.GameObject;

public class GameCollection implements ICollection{

	private Collection<GameObject> objects;

	public GameCollection() {
		objects = new ArrayList<>();
	}

	@Override
	public void add(GameObject newObject) {
		objects.add(newObject);

	}

	@Override
	public void remove(GameObject target) {
		objects.remove(target);

	}

	@Override
	public void clear() {
		objects.clear();
	}

	@Override
	public IIterator getIterator() {
		return new iterator();
	}

	private class iterator implements IIterator {
		GameObject[] collection;
		int index;

		private iterator() {
			collection = new GameObject[objects.size()];
			objects.toArray(collection);
			index = -1;
		}

		@Override
		public boolean hasNext() {
			return index + 1 < collection.length;
		}

		@Override
		public GameObject getNext() {
			index++;
			return collection[index];
		}
	}
}
