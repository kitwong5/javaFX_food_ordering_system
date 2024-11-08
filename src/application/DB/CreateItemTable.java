package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class CreateItemTable {

	public static void CItemTab() throws SQLException{
		final String TABLE_NAME = "ITEM";

		try (Connection con = DatabaseConnection.getConnection();
			Statement stmt = con.createStatement();) {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME 
										+ "(item_id INTEGER NOT NULL,"
										+ "item_name VARCHAR(20) NOT NULL,"
										+ "price FLOAT,"
										+ "setup_time FLOAT,"
										+ "cook_time FLOAT,"
										+ "meal_set VARCHAR(1),"
										+ "remaining_serves INTEGER,"
										+ "PRIMARY KEY (item_id))");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try (Connection con = DatabaseConnection.getConnection();
			Statement stmt = con.createStatement();) {
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + TABLE_NAME);
			
			if(!resultSet.next()){
				String query = "INSERT INTO " + TABLE_NAME +
						"(item_id, item_name, price, setup_time, cook_time, meal_set, remaining_serves) " +
						" VALUES (1, 'Burrito', 7.0, 9.0, 0, 'N', 0)," +
						"(2, 'Fries', 8.0, 4.0, 0, 'N', 0)," +
						"(3, 'Soda', 2.5, 0.0, 0, 'N', 0)," +
						"(4, 'Meal', 0.0, 0.0, 0, 'Y', 0)";
				int result = stmt.executeUpdate(query);
				
				System.out.println(result + " row(s) affected");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	
	}

}
