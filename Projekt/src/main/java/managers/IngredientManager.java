package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Ingredient;

public class IngredientManager {

	private Connection con;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableIngredient = "CREATE TABLE Ingredient(id bigint GENERATED BY DEFAULT AS IDENTITY UNIQUE, " +
										"name varchar(40), kind varchar(40))";
	private Statement stmt;
	
	private PreparedStatement getStmt;
	private PreparedStatement getAllStmt;
	private PreparedStatement removeAllStmt;
	
	private PreparedStatement addStmt;
	
	public IngredientManager() {
		try {
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			
			ResultSet rs = con.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			
			while(rs.next()){
				if("Ingredient".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;					
				}
			}
			if(!tableExists)
				stmt.executeUpdate(createTableIngredient);
						
			getStmt = con.prepareStatement("SELECT * FROM Ingredient WHERE id=?");
			getAllStmt = con.prepareStatement("SELECT * FROM Ingredient");
			removeAllStmt = con.prepareStatement("DELETE FROM Ingredient");
			
			addStmt = con.prepareStatement("INSERT INTO Ingredient (name, kind) VALUES (?, ?)");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public Connection getConnection() {
		return con;
	}
		
	public Ingredient getOne(long id) {
		
		Ingredient ing = new Ingredient();
		
		try {
			getStmt.setLong(1, id);
			ResultSet rs = getStmt.executeQuery();
			rs.next();
			
			ing.setId(rs.getLong("id"));
			ing.setName(rs.getString("name"));
			ing.setKind(rs.getString("kind"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ing;
	}
	
	public List<Ingredient> getAll() {
		
		List<Ingredient> ings = new ArrayList<Ingredient>();
		
		try {
			ResultSet rs = getAllStmt.executeQuery();
			while(rs.next()) {
				Ingredient i = new Ingredient();
				i.setId(rs.getLong("id"));
				i.setName(rs.getString("name"));
				i.setKind(rs.getString("kind"));
				ings.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ings;
	}
	
	public void removeAll() {
		try {
			removeAllStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addIngredient(Ingredient ing) {
		try {
			addStmt.setString(1, ing.getName());
			addStmt.setString(2, ing.getKind());
			
			addStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
