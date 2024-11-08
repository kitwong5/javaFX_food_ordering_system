package application.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.customcls.User;

public class UserTable {
	// check is user exist
	public static boolean chkUser(String inUsername) throws SQLException{
		String query = "SELECT username FROM  USER WHERE username=?";
		boolean existing_user = false;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inUsername);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
	            existing_user = true;
	        }
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return existing_user;	
	}
	
	// check is user vip
	public static boolean chkVIP(String inUsername) throws SQLException{
		String query = "SELECT username FROM  USER WHERE vip_user = 'Y' and username=?";
		boolean existing_user = false;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inUsername);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
	            existing_user = true;
	        }
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return existing_user;	
	}
	
	// check is user and password correct
	public static boolean chkUserPwd(User usr) throws SQLException{
		String query = "SELECT user_pwd FROM  USER WHERE username=? and user_pwd=?";
		boolean existing_user = false;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, usr.getUsername());
			stmt.setObject(2, usr.getUser_pwd());
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
	            if (resultSet.getString(1).equals(usr.getUser_pwd())) {
	            	existing_user = true;
	            }
	        }
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return existing_user;	
	}

	// get user data
	public static User selUser(String inUsername) throws SQLException{
		String query = "SELECT user_id, username, user_pwd, first_name, last_name, vip_user, ifnull(vip_credit,0), rece_promotions, user_email FROM  USER WHERE username=?";
		User usr = null;
		try {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setObject(1, inUsername);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				usr = new User(Integer.toString(rs.getInt(1)),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9));
	        } 
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return usr;		
	}

	
	// add new user to User table
	public static int addUser(User usr) throws SQLException{
		int row = 0;
		try {
				String query = "Insert into USER(username, user_pwd, first_name, last_name) values(?,?,?,?)";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, usr.getUsername());
				stmt.setObject(2, usr.getUser_pwd());
				stmt.setObject(3, usr.getFirst_name());
				stmt.setObject(4, usr.getLast_name());        
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		return row;
		}

	// update existing user to VIP
	public static int updVIP(String inUsername) throws SQLException{
		int row = 0;
		try {
				String query = "update USER set vip_user = 'Y', vip_credit = 0 where username = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inUsername);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

	// update user first/last name or password
	public static int updUser(User usr) throws SQLException{
		int row = 0;
		try {
				String query = "update USER set first_name = ?, last_name = ?, user_pwd = ? where username = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, usr.getFirst_name());
				stmt.setObject(2, usr.getLast_name());
				stmt.setObject(3, usr.getUser_pwd());
				stmt.setObject(4, usr.getUsername());
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

	// update user vip_credit
	public static int updVipCredit(String inUsername, Double inVipCredit) throws SQLException{
		int row = 0;
		try {
				String query = "update USER set vip_credit = ? where username = ?";
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setObject(1, inVipCredit);
				stmt.setObject(2, inUsername);
				row =  stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return row;
		}

	
}
