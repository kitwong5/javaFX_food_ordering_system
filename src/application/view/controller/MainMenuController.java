package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import application.customcls.*;


public class MainMenuController implements Initializable{
	@FXML
	private BorderPane border_pane;
	@FXML
	private VBox left_pane;
	@FXML
	private Label main_menu;
	@FXML
	private Label place_order;
	@FXML
	private Label collect_order;
	@FXML
	private Label cancel_order;
	@FXML
	private Label view_order;
	@FXML
	private Label export_all_order;
	@FXML
	private VBox center_pane;
	@FXML
	private Label lb_user_tit;
	@FXML
	private Label lb_username;
	@FXML
	private Label lb_login_msg;
	@FXML
	private TableView<OrderSummary> tb_order;
	@FXML
	private TableColumn<OrderSummary, String> col_order_id;
	@FXML
	private TableColumn<OrderSummary, String> col_order_date;
	@FXML
	private TableColumn<OrderSummary, String> col_order_time;
	@FXML
	private TableColumn<OrderSummary, String> col_item;
	@FXML
	private TableColumn<OrderSummary, String> col_amt;
	@FXML
	private TableColumn<OrderSummary, String> col_status;
	@FXML
	private Button bt_order;
	@FXML
	private Button bt_acct;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label sign_in;
	
	private String usr_str;
	
//	private User user_detail;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// display username
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		if (usr_str == null) {
			lb_login_msg.setText("Welcome, please login ...");
			sign_in.setText("Login");
			bt_order.setDisable(true);
			bt_acct.setDisable(true);
		} else {
			lb_login_msg.setText(" ");
			sign_in.setText("Logout");
			bt_order.setDisable(false);
			bt_acct.setDisable(false);
			tb_order.setDisable(false);
			// display user information
			CommonOperation.display_user(usr_str, lb_username, lb_user_tit);
			// enable menu if user with login
			CommonOperation.enable_menu(usr_str, acct, place_order, collect_order, cancel_order, view_order, export_all_order);
			// display pending collection order	
			CommonOperation.display_order(usr_str, col_order_id, col_order_date, col_order_time, col_item, col_amt, col_status, tb_order, "'O'");

		}
	}
	
	
	// Event Listener on Label[#main_menu].onMouseClicked
	@FXML
	public void lb_main_menu(MouseEvent event) {
   // go to Main Menu page
   try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/MainMenu.fxml")));
        	border_pane.getChildren().setAll(parentContent);
        }catch(IOException e){
            System.out.println(e);
        }
	}
	// Event Listener on Label[#place_order].onMouseClicked
	@FXML
	public void lb_place_order(MouseEvent event) {
		if (usr_str != null) {
			// go to Order Basket page
			try {
				BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderBasket.fxml")));
				border_pane.getChildren().setAll(parentContent);
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}
	// Event Listener on Label[#collect_order].onMouseClicked
	@FXML
	public void lb_collect_order(MouseEvent event) {
		if (usr_str != null) {
			// go to Order Collect page
			try {
				BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderCollect.fxml")));
				border_pane.getChildren().setAll(parentContent);
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}
	// Event Listener on Label[#cancel_order].onMouseClicked
	@FXML
	public void lb_cancel_order(MouseEvent event) {
		// go to Cancel Order page
		if (usr_str != null) {
			try {
        		BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderCancel.fxml")));
        		border_pane.getChildren().setAll(parentContent);
        	}catch(IOException e){
            	System.out.println(e);
        	}			
		}
	}
	// Event Listener on Label[#view_order].onMouseClicked
	@FXML
	public void lb_view_order(MouseEvent event) {
		// go to View Order page
		if (usr_str != null) {
			try {
        		BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderView.fxml")));
        		border_pane.getChildren().setAll(parentContent);
        	}catch(IOException e){
            	System.out.println(e);
        	}			
		}
	}
	// Event Listener on Label[#export_all_order].onMouseClicked
	@FXML
	public void lb_export_all_order(MouseEvent event) {
		// go to Export Order page
		try {
		        BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderExport.fxml")));
		        border_pane.getChildren().setAll(parentContent);
		    }catch(IOException e){
		    	System.out.println(e);
		    }
	}
	// Event Listener on Button[#bt_order].onAction
	@FXML
	public void bt_order_act(ActionEvent event) {
        // go to Order Basket page
		if (usr_str != null) {
			try {
        		BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderBasket.fxml")));
        		border_pane.getChildren().setAll(parentContent);
        	}catch(IOException e){
            	System.out.println(e);
        	}
		}
	}
	// Event Listener on Button[#bt_acct].onAction
	@FXML
	public void bt_acct_act(ActionEvent event) {
		// go to Account page
		if (usr_str != null) {
			try {
	        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Account.fxml")));
	        	border_pane.getChildren().setAll(parentContent);
	        }catch(IOException e){
	            System.out.println(e);
	        }
		}
	}
	// Event Listener on Label[#acct].onMouseClicked
	@FXML
	public void lb_acct(MouseEvent event) {
        // go to Account page
		if (usr_str != null) {
			try {
        		BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Account.fxml")));
        		border_pane.getChildren().setAll(parentContent);
        	}catch(IOException e){
            	System.out.println(e);
        	}
		}
	}
	// Event Listener on Label[#new_acct].onMouseClicked
	@FXML
	public void lb_new_acct(MouseEvent event) {
        // go to New Account page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/NewAcct.fxml")));
        	border_pane.getChildren().setAll(parentContent);
        }catch(IOException e){
            System.out.println(e);
        }
	}
	// Event Listener on Label[#sign_in].onMouseClicked
	@FXML
	public void lb_sign_in(MouseEvent event) {
		// Logout Operation
		if (usr_str != null) {
			CommonOperation.signOut();
		} 
	    // Go to Login page
	    try {
	        BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Login.fxml")));
	        border_pane.getChildren().setAll(parentContent);
	     }catch(IOException e){
	           System.out.println(e);
	     }
	}
}
