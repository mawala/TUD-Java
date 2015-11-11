package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RelationshipManager {
	
	private Connection con;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableCakeIngredient = "CREATE TABLE Cake_has_Ingredient(idCake bigint, idIngredient bigint, " +
													"PRIMARY KEY (idCake, idIngredient), " +
													"FOREIGN KEY (idCake) REFERENCES Cake(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
													"FOREIGN KEY (idIngredient) REFERENCES Ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)";
	private Statement stmt;
	
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
				if("Cake_has_Ingredient".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;					
				}
				if("Cake".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableCakeExists = true;
				}
				if("Ingredient".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableIngredientExists = true;
				}
			}
			if(!tableExists && tableCakeExists && tableIngredientExists)
				stmt.executeUpdate(createTableCakeIngredient);
			
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
	
}
