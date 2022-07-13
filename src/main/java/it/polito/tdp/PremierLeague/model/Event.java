package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		AZIONE_SALIENTE,
	}
	
	private Team t;
	private EventType type;
	private int id;
	
	public Event(int id,EventType type) {
		this.type = type;
		this.id=id;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	@Override
	public int compareTo(Event o) {
		return this.id-o.id;
	}

	
	
	
}
