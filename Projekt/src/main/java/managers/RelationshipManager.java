package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.*;

public class RelationshipManager {
	
	private Connection con;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableCakeIngredient = "CREATE TABLE Cake_has_Ingredient(idCake bigint, idIngredient bigint, " +
													"PRIMARY KEY (idCake, idIngredient), " +
													"FOREIGN KEY (idCake) REFERENCES Cake(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
													"FOREIGN KEY (idIngredient) REFERENCES Ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)";
	private Statement stmt;
	
	private PreparedStatement getAllStmt;
	private PreparedStatement removeAllStmt;
	
	private PreparedStatement addStmt;
	private PreparedStatement deleteStmt;
	
	private PreparedStatement getIngsOfCakeStmt;
	
	public RelationshipManager() {
		try {
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			
			ResultSet rs = con.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			boolean tableCakeExists = false;
			boolean tableIngredientExists = false;
			
			while(rs.next()){
				if("Cake".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableCakeExists = true;
				}
				if("Ingredient".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableIngredientExists = true;
				}
				if("Cake_has_Ingredient".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;					
				}
			}
			if(!tableExists && tableCakeExists && tableIngredientExists)
				stmt.executeUpdate(createTableCakeIngredient);
			
			getAllStmt = con.prepareStatement("SELECT * FROM Cake_has_Ingredient");
			removeAllStmt = con.prepareStatement("DELETE FROM Cake_has_Ingredient");
			
			addStmt = con.prepareStatement("INSERT INTO Cake_has_Ingredient VALUES (?, ?)");
			deleteStmt = con.prepareStatement("DELETE FROM Cake_has_Ingredient WHERE idCake=? AND idIngredient=?");
			
			getIngsOfCakeStmt = con.prepareStatement("SELECT i.id, i.name, i.kind FROM Cake c JOIN Cake_has_Ingredient ci " +
					"ON c.id=ci.idCake JOIN Ingredient i ON i.id=ci.idIngredient");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public List<Relationship> getAll() {
		
		List<Relationship> relations = new ArrayList<Relationship>();
		
		try {
			ResultSet rs = getAllStmt.executeQuery();
			while(rs.next()) {
				Relationship r = new Relationship();
				r.setCakeId(rs.getLong("idCake"));
				r.setIngredientId(rs.getLong("idIngredient"));
				relations.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return relations;
	}
	
	public void removeAll() {
		
		try {
			removeAllStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addRelationship(Cake cake, Ingredient ing) {
		
		try {
			addStmt.setLong(1, cake.getId());
			addStmt.setLong(2, ing.getId());
			
			addStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeRelationship(Cake cake, Ingredient ing) {
		
		try {
			deleteStmt.setLong(1, cake.getId());
			deleteStmt.setLong(2, ing.getId());
			
			deleteStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
