package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private Integer teamID;
	private String name;
	private List<Player> rosa;
	private boolean mvp;
	
	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		rosa = new ArrayList<>();
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getRosa() {
		return rosa;
	}

	public void setRosa(List<Player> rosa) {
		this.rosa = rosa;
	}

	public boolean isMvp() {
		return mvp;
	}

	public void setMvp(boolean mvp) {
		this.mvp = mvp;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}
	
}
