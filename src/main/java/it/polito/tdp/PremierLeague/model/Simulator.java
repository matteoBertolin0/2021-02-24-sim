package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Event.EventType;



public class Simulator {

	private Team t1;
	private Team t2;
	private int n;
	
	private int goalT1;
	private int goalT2;
	private int espulsiT1;
	private int espulsiT2;
	
	private int id;
	
	private PriorityQueue<Event> queue;

	public Simulator(Team t1, Team t2, int n) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.n = n;
	}
	
	public void init() {
		goalT1 = 0;
		goalT2 = 0;
		espulsiT1 = 0;
		espulsiT2 = 0;
		id=0;
		this.queue = new PriorityQueue<>();	
		int i = 0;
		while(i<n) {
			queue.add(new Event(id,EventType.AZIONE_SALIENTE));
			id++;
			i++;
		}

	}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		
		double prob = Math.random();
		
		if(prob<0.5) {
			if(espulsiT1>espulsiT2) {
				goalT2++;
			}else if(espulsiT1<espulsiT2){
				goalT1++;
			}else {
				if(t1.isMvp()) {
					goalT1++;
				}else {
					goalT2++;
				}
			}
		}else if(prob<0.8) {
			double pr = Math.random();
			if(pr<0.6) {
				if(t1.isMvp()) {
					espulsiT1++;
				}else {
					espulsiT2++;
				}
			}else {
				if(t1.isMvp()) {
					espulsiT2++;
				}else {
					espulsiT1++;
				}
			}
		}else {
			if(Math.random()<0.5) {
				queue.add(new Event(id++,EventType.AZIONE_SALIENTE));
				queue.add(new Event(id++,EventType.AZIONE_SALIENTE));
			}else {
				queue.add(new Event(id++,EventType.AZIONE_SALIENTE));
				queue.add(new Event(id++,EventType.AZIONE_SALIENTE));
				queue.add(new Event(id++,EventType.AZIONE_SALIENTE));
			}
		}
		
		
	}

	public int getGoalT1() {
		return goalT1;
	}

	public void setGoalT1(int goalT1) {
		this.goalT1 = goalT1;
	}

	public int getGoalT2() {
		return goalT2;
	}

	public void setGoalT2(int goalT2) {
		this.goalT2 = goalT2;
	}

	public int getEspulsiT1() {
		return espulsiT1;
	}

	public void setEspulsiT1(int espulsiT1) {
		this.espulsiT1 = espulsiT1;
	}

	public int getEspulsiT2() {
		return espulsiT2;
	}

	public void setEspulsiT2(int espulsiT2) {
		this.espulsiT2 = espulsiT2;
	}
	
	
	
	
}
