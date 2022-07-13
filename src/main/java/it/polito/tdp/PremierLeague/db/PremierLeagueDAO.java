package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.CoppiePlayers;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> listAllPlayersByMatch(Match m){
		String sql = "SELECT p.PlayerID, p.Name "
				+ "FROM players AS p, actions AS a "
				+ "WHERE p.PlayerID = a.PlayerID AND a.MatchID = ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("p.PlayerID"), res.getString("p.Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CoppiePlayers> listAllCoppiePlayersByMatch(Match m){
		String sql = "SELECT DISTINCT a1.PlayerID, p1.Name, a1.TotalSuccessfulPassesAll, a1.Assists, a1.TimePlayed, a2.PlayerID, p2.Name, a2.TotalSuccessfulPassesAll, a2.Assists, a2.TimePlayed "
				+ "FROM actions AS a1, actions AS a2, players AS p1, players AS p2 "
				+ "WHERE a1.MatchID = a2.MatchID AND a1.MatchID = ? AND p1.PlayerID=a1.PlayerID AND p2.PlayerID = a2.PlayerID AND a1.TeamID > a2.TeamID";
		List<CoppiePlayers> result = new ArrayList<CoppiePlayers>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int a1 = res.getInt("a1.TotalSuccessfulPassesAll");
				int a2 = res.getInt("a1.Assists");
				int a3 = res.getInt("a1.TimePlayed");
				double n1 = (double) (res.getInt("a1.TotalSuccessfulPassesAll")+res.getInt("a1.Assists"))/res.getInt("a1.TimePlayed");
				double n2 = (double) (res.getInt("a2.TotalSuccessfulPassesAll")+res.getInt("a2.Assists"))/res.getInt("a2.TimePlayed");
				CoppiePlayers coppiaPlayers = new CoppiePlayers(new Player(res.getInt("a1.PlayerID"), res.getString("p1.Name")),new Player(res.getInt("a2.PlayerID"), res.getString("p2.Name")),n1,n2);
				result.add(coppiaPlayers);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void loadAllTeamPlayer(Team t){
		String sql = "SELECT distinct p.PlayerID, p.Name "
				+ "FROM actions AS a, teams AS t, players AS p "
				+ "WHERE a.TeamID=t.TeamID AND a.PlayerID=p.PlayerID AND a.TeamID=?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("p.PlayerID"), res.getString("p.Name"));
				t.getRosa().add(player);
				
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
