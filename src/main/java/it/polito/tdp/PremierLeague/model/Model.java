package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;


public class Model {
	private PremierLeagueDAO dao = new PremierLeagueDAO();
	private Graph<Player,DefaultWeightedEdge> grafo;
	private List<Player> lista;
	
	public void creaGrafo(Match m) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.lista = new ArrayList<>();
		lista = dao.listAllPlayersByMatch(m);
		
		Graphs.addAllVertices(this.grafo, lista);
		
		for(CoppiePlayers c : dao.listAllCoppiePlayersByMatch(m)) {
			if(c.getE1()>=c.getE2()) {
				Graphs.addEdgeWithVertices(this.grafo, c.getP1(), c.getP2(), Math.abs(c.getE1()-c.getE2()));
			}else if(c.getE2()>c.getE1()) {
				Graphs.addEdgeWithVertices(this.grafo, c.getP2(), c.getP1(), Math.abs(c.getE1()-c.getE2()));
			}
		}
		
		
		System.out.println("#VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("#ARCHI: " + this.grafo.edgeSet().size());
	}
	
	public Player getMigliore() {
		double max=0;
		Player migliore = null;
		for(Player p : this.grafo.vertexSet()) {
			double in=0;
			double out=0;
			for(Player p1 : Graphs.successorListOf(this.grafo, p)) {
				out+=this.grafo.getEdgeWeight(this.grafo.getEdge(p, p1));
			}
			for(Player p2 : Graphs.predecessorListOf(this.grafo, p)) {
				in+=this.grafo.getEdgeWeight(this.grafo.getEdge(p2, p));
			}
//			for(DefaultWeightedEdge e1 : this.grafo.outgoingEdgesOf(p)) {
//				out+=this.grafo.getEdgeWeight(e1);
//			}
//			for(DefaultWeightedEdge e2 : this.grafo.incomingEdgesOf(p)) {
//				in+=this.grafo.getEdgeWeight(e2);
//			}
			double eff = out-in;
			if(eff>max) {
				max=eff;
				migliore = p;
			}
		}
		migliore.setEff(max);
		
		for(Team t : dao.listAllTeams()) {
			if(t.getRosa()!=null) {				
				if(t.getRosa().size()>0) {
					if(t.getRosa().contains(migliore)) {
						t.setMvp(true);
					}
				}
			}
		}
		return migliore;
	}
	
	public List<Match> getAllMatches(){
		return dao.listAllMatches();
	}
	
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public void loadPlayer(Match m) {
		for(Team t : dao.listAllTeams()) {
			if(t.getTeamID()==m.getTeamHomeID()) {
				dao.loadAllTeamPlayer(t);
			}
			if(t.getTeamID()==m.getTeamAwayID()) {
				dao.loadAllTeamPlayer(t);
			}
		}
	}
	
	public String simula(Match m, int N) {
		Team t1 = null;
		Team t2 = null;
		
		for(Team t : dao.listAllTeams()) {
			if(t.getTeamID()==m.getTeamHomeID()) {
				t1=t;
			}
			if(t.getTeamID()==m.getTeamAwayID()) {
				t2=t;
			}
		}
		Simulator sim = new Simulator(t1,t2,N);
		sim.init();
		sim.run();
		String str = t1.getName()+" "+sim.getGoalT1()+" - "+sim.getGoalT2()+" "+t2.getName() + "\n"+ "Espulsi:\n-"+t1.getName()+": "+sim.getEspulsiT1()+"\n-"+t2.getName()+": "+sim.getEspulsiT2();
		
		System.out.println(t1.getName()+" "+sim.getGoalT1()+" - "+sim.getGoalT2()+" "+t2.getName());
		System.out.println("Espulsi:\n-"+t1.getName()+": "+sim.getEspulsiT1()+"\n-"+t2.getName()+": "+sim.getEspulsiT2());
		
		return str;
	} 
}
