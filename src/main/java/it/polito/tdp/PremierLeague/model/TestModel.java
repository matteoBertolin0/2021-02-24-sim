package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		Match ma = new Match(32,3,6,null,null, null, null, null, null);
		m.creaGrafo(ma);
		
		m.loadPlayer(ma);
		System.out.println(m.getMigliore());
		m.simula(ma, 3);
	}

}
