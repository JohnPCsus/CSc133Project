package com.mycompany.a3.World.Events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class EventStream extends Observable implements Iterable<Event> {
	private Queue<Event> newEvents;
	private Queue<Event> oldEvents;

	public EventStream() {
		newEvents = new LinkedList<>();
	}
	
	public void addEvent(Event v) {
		newEvents.add(v);
		setChanged();
	}
	
	public Iterator<Event> getEvents(){
		return oldEvents.iterator();
	}
	
	@Override
	public void notifyObservers() {
		oldEvents = newEvents;
		newEvents = new LinkedList<>();
		super.notifyObservers(this);
		clear();
		
	}
	
	public void clear() {
		oldEvents.clear();
	}

	@Override
	public Iterator<Event> iterator() {
		return getEvents();
	}

}
