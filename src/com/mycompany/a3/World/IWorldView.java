package com.mycompany.a3.World;

import java.util.Map;

import com.mycompany.a3.World.Collection.IIterator;

public interface IWorldView {
	Map<String, String> getGameState();
	IIterator getObjects();
	String getMap();
}
