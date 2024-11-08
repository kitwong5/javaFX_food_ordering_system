package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	
	public static void CTable() throws SQLException{
		// Create USER table
		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USER " 
										+ "(user_id INTEGER NOT NULL,"
										+ "username VARCHAR(8) NOT NULL,"
										+ "user_pwd  VARCHAR(8) NOT NULL,"
										+ "first_name VARCHAR(50),"
										+ "last_name VARCHAR(50),"
										+ "vip_user VARCHAR(1),"
										+ "vip_credit  FLOAT,"
										+ "rece_promotions VARCHAR(1),"
										+ "user_email VARCHAR(100),"
										+ "PRIMARY KEY (user_id))");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Create ORDER table
		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ORDERS " 
					+ "(order_id INTEGER NOT NULL,"
					+ "username VARCHAR(8) NOT NULL,"
					+ "order_status  VARCHAR(1) NOT NULL,"
					+ "order_date TEXT,"
					+ "order_time_hh INTEGER,"
					+ "order_time_mm INTEGER," 
					+ "credit_card_no VARCHAR(50),"
					+ "expire_dt VARCHAR(10),"
					+ "cvv  VARCHAR(3),"
					+ "cook_time FLOAT,"
					+ "collect_date TEXT,"
					+ "collect_time_hh INTEGER,"
					+ "collect_time_mm INTEGER," 
					+ "PRIMARY KEY (order_id))");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Create ORDER_DETAILS table
		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ORDER_DETAILS " 
					+ "(order_detail_id INTEGER NOT NULL,"
					+ "order_id INTEGER NOT NULL,"
					+ "item_name VARCHAR(20) NOT NULL,"
					+ "price  FLOAT,"
					+ "quantity  INTEGER,"
					+ "amount FLOAT,"
					+ "PRIMARY KEY (order_detail_id))");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
