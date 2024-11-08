package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.customcls.Items;

public class ItemTable {
	// get price
	public static Double getPri(Integer inItemId) throws SQLException{
		String query = "SELECT price FROM  ITEM WHERE item_id=?";
		Double itemPrice = 0.0;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inItemId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				itemPrice = rs.getDouble(1);
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return itemPrice;		
	}
	// get item data
	public static Items selItems(String inItemName) throws SQLException{
		String query = "SELECT item_id, item_name, price, setup_time, cook_time, meal_set, remaining_serves FROM  ITEM WHERE item_name=?";
		Items t = null;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inItemName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				t = new Items(Integer.toString(rs.getInt(1)),rs.getString(2),rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6),rs.getInt(7));
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return t;		
	}

	// get Meal price
	public static Double getMealPri() throws SQLException{
		//meal, which is 1 burrito, 1 serve of fries and 1 soda, at a price that discounts each item in a meal by $1
		String query = "SELECT sum(price)-3 FROM  ITEM";
		Double itemPrice = 0.0;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				itemPrice = rs.getDouble(1);
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return itemPrice;		
	}
	// get all items
	public static ArrayList<Items> getAllItem() throws SQLException{
		String query = "SELECT item_id, item_name, price, setup_time, cook_time, meal_set, remaining_serves FROM ITEM";
		ArrayList<Items> itemList=new ArrayList<>();
		Items t = null;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				t = new Items(Integer.toString(rs.getInt(1)),rs.getString(2),rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6),rs.getInt(7));
				itemList.add(t);
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return itemList;		
	}

	// update remaining_serves
	public static int updRemSer(String inItemName, Integer inRemSer) throws SQLException{
		int row = 0;
		try {
				String query = "update ITEM set remaining_serves = ? where item_name = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inRemSer);
				stmt.setObject(2, inItemName);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}


}
