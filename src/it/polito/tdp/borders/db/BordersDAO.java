package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			Country paese = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			result.add(paese);
			
			}
			
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}
	
	public List<Border> getBorderpaesedata(int anno,int codice){
		String sql = "SELECT *  " + 
				"FROM country AS cou , contiguity AS con   " + 
				"WHERE cou.CCode = ? AND  (cou.CCode = con.state1no) AND con.YEAR <= ? AND conttype='1' ";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, codice);
			st.setInt(2, anno);
			
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				Border border = new Border(rs.getInt("state1no"),rs.getInt("state2no"),rs.getString("state1ab"),rs.getString("state2ab"),
						rs.getInt("year"));
						result.add(border);
					
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}
	

	public List<Country> getConfine(int anno,int codice,Map<Integer,Country> idcouMap){
		String sql = "SELECT state2no  " + 
				"FROM country AS cou , contiguity AS con   " + 
				"WHERE cou.CCode = ? AND  (cou.CCode = con.state1no) AND con.YEAR <= ? AND conttype='1'  ";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, codice);
			st.setInt(2, anno);
			
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				if(idcouMap.containsKey(rs.getInt("state2no"))==true)
					result.add(idcouMap.get(rs.getInt("state2no")));
				
					
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}
	
	
	
	
	public List<Country> getCountryPerAnno(Map<Integer,Country> idcouMap, int anno){
		
		String sql = "SELECT cou.CCode  " + 
				"FROM country AS cou , contiguity AS con  " + 
				"WHERE cou.CCode = con.state1no OR cou.CCode = con.state2no AND con.YEAR = ? " + 
				"GROUP BY cou.CCode  ";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				if(idcouMap.containsKey(rs.getInt("CCode")))
				{
					result.add(idcouMap.get(rs.getInt("CCode")));
					
				}
					
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}
	
public List<Country> getCountryVertici(Map<Integer,Country> idcouMap, int anno){
		
		String sql = "SELECT cou.CCode  " + 
				"FROM country AS cou , contiguity AS con  " + 
				"WHERE cou.CCode = con.state1no OR cou.CCode = con.state2no AND con.YEAR <= ? " + 
				"GROUP BY cou.CCode  ";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				if(idcouMap.containsKey(rs.getInt("CCode")))
				{
					result.add(idcouMap.get(rs.getInt("CCode")));
					
				}
					
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}

public List<Border> getAllBorder(){//volevo usare questometodo per crearmi la mappa dei borders ma non hanno una chiave univoca
	
	String sql = "SELECT *  " + 
			"FROM contiguity  ";
	List<Border> result = new ArrayList<Border>();
	
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
	
		
		ResultSet rs = st.executeQuery();
		

		while (rs.next()) {
			Border border = new Border(rs.getInt("state1no"),rs.getInt("state2no"),rs.getString("state1ab"),rs.getString("state2ab"),
											rs.getInt("year"));
			result.add(border);
				
		}
		
		conn.close();
		return result;

	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("Errore connessione al database");
		throw new RuntimeException("Error Connection Database");
	}
	
}

}
