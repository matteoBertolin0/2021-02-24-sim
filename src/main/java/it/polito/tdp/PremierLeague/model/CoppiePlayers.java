package it.polito.tdp.PremierLeague.model;

public class CoppiePlayers {
	private Player p1;
	private Player p2;
	private double e1;
	private double e2;
	
	public CoppiePlayers(Player p1, Player p2, double e1, double e2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.e1 = e1;
		this.e2 = e2;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public double getE1() {
		return e1;
	}
	public void setE1(double e1) {
		this.e1 = e1;
	}
	public double getE2() {
		return e2;
	}
	public void setE2(double e2) {
		this.e2 = e2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
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
		CoppiePlayers other = (CoppiePlayers) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		return true;
	}
	
	
}
