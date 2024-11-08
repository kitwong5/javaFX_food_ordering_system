package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.customcls.Orders;

public class OrderTable {

	// get order data
	public static Orders selOrder(Integer inOrderId) throws SQLException{
		String query = "select order_id, username, order_status, order_date, order_time_hh, order_time_mm, credit_card_no, expire_dt, cvv, cook_time from ORDERS WHERE order_id = ?";
		Orders o = null;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inOrderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				o = new Orders(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getDouble(10));
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return o;		
	}

	
	// get latest order id
	public static Integer chkMaxOrderId() throws SQLException{
		String query = "select ifnull(max(order_id), 0)+1 from ORDERS";
		Integer max_order_id = 1;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
	            max_order_id = resultSet.getInt(1);
	        }
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return max_order_id;	
	}

	// add new order
	public static int addOrder(Orders ord) throws SQLException{
		int row = 0;
		try {
				String query = "Insert into ORDERS(order_id, username, order_status,  order_time_hh, order_time_mm, credit_card_no, expire_dt, cvv, cook_time) values(?,?,?,?,?,?,?,?,?)";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, ord.getOrder_id());
				stmt.setObject(2, ord.getUsername());
				stmt.setObject(3, ord.getOrder_status());
				stmt.setObject(4, ord.getOrder_time_hh());
				stmt.setObject(5, ord.getOrder_time_mm());
				stmt.setObject(6, ord.getCredit_card_no());
				stmt.setObject(7, ord.getExpire_dt());
				stmt.setObject(8, ord.getCvv());
				stmt.setObject(9, ord.getCook_time());
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

	// update order time
	public static int updOrdTime(Integer inOrder_id, String inDate, Integer inTime_hh, Integer inTime_mm) throws SQLException{
		int row = 0;
		try {
				String query = "update ORDERS set order_date = ?, order_time_hh = ?, order_time_mm = ? where order_id = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inDate);
				stmt.setObject(2, inTime_hh);
				stmt.setObject(3, inTime_mm);
				stmt.setObject(4, inOrder_id);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

	// update collect time
	public static int updColTime(Integer inOrder_id, String inDate, Integer inTime_hh, Integer inTime_mm) throws SQLException{
		int row = 0;
		try {
				String query = "update ORDERS set collect_date = ?, collect_time_hh = ?, collect_time_mm = ?, order_status = 'C' where order_id = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inDate);
				stmt.setObject(2, inTime_hh);
				stmt.setObject(3, inTime_mm);
				stmt.setObject(4, inOrder_id);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}
	
	// update order status to cancel
	public static int updOrdCancel(Integer inOrder_id) throws SQLException{
		int row = 0;
		try {
				String query = "update ORDERS set  order_status = 'X'  where order_id = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inOrder_id);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

}
