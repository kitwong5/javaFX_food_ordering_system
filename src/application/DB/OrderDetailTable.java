package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.customcls.OrderDetails;
import application.customcls.OrderSummary;



public class OrderDetailTable {

	
	// get order detail data
	public static ArrayList<OrderDetails> selOrderDetails(Integer inOrderId) throws SQLException{
		ArrayList<OrderDetails> OrderDetailsList=new ArrayList<>();
		String query = "select order_detail_id, order_id, item_name, price, quantity, amount from ORDER_DETAILS WHERE order_id = ?";
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inOrderId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				OrderDetails ord;
				ord = new OrderDetails(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getDouble(6));
				OrderDetailsList.add(ord);
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return OrderDetailsList;		
	}

	// get active pending order summary
	public static ArrayList<OrderSummary> selOrdSum(String inUsername, String inOrdStatus) throws SQLException{
		ArrayList<OrderSummary> OrderSummaryList=new ArrayList<>();
		String query = "SELECT  o1.order_id, o2.order_date, o2.order_time, o1.items, '$ '||o2.tot_amt as tot_amt,   "
				+ "CASE WHEN o2.order_status = 'O' THEN 'Await for Collection' "
				+ "WHEN o2.order_status = 'C' THEN 'Collected' "
				+ "WHEN o2.order_status = 'X' THEN 'Cancelled' END AS order_status "
				+ "FROM ( "
				+ "SELECT DISTINCT o.order_id,  group_concat(quantity||' '||item_name, ', ') OVER (PARTITION BY o.order_id) AS items "
				+ "FROM ORDERS o, ORDER_DETAILS d "
				+ "WHERE o.order_id = d.order_id "
				+ "AND o.username = ? "
				+ "AND order_status in ( "+inOrdStatus+" ) ) o1, "
				+ "(select o.order_id, o.order_date,PRINTF('%02d',order_time_hh) ||':' || PRINTF('%02d',order_time_mm) as order_time , o.order_status, sum(amount) tot_amt "
				+ "FROM ORDERS o, ORDER_DETAILS d "
				+ "WHERE o.order_id = d.order_id "
				+ "AND o.username = ? "
				+ "AND order_status in ( "+inOrdStatus+" ) "
				+ "GROUP BY o.order_id, o.order_date, o.order_time_hh, o.order_time_mm, o.order_status) o2  "
				+ "WHERE o1.order_id = o2.order_id "
				+ "ORDER BY  o2.order_date DESC, o2.order_time DESC ";
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inUsername);
			stmt.setObject(2, inUsername);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				OrderSummary ord;
				ord = new OrderSummary(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				OrderSummaryList.add(ord);
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return OrderSummaryList;		
	}

	
	// add new order details
	public static int addOrderDtl(OrderDetails ordDtl) throws SQLException{
		int row = 0;
		try {
				String query = "Insert into ORDER_DETAILS(order_id, item_name, price, quantity, amount) values(?,?,?,?,?)";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, ordDtl.getOrder_id());
				stmt.setObject(2, ordDtl.getItem_name());
				stmt.setObject(3, ordDtl.getPrice());
				stmt.setObject(4, ordDtl.getQuantity());
				stmt.setObject(5, ordDtl.getAmount());
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}
	
}
