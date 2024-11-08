package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import application.DB.UserTable;
import application.customcls.User;


public class AccountController implements Initializable{
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
	private Label lb_username;
	@FXML
	private TextField txt_first_name;
	@FXML
	private TextField txt_last_name;
	@FXML
	private TextField txt_password;
	@FXML
	private Button bt_update;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label sign_in;
	
	private User user_detail;
	
	private String usr_str;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// display username
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		lb_username.setText(usr_str);
		load_user_detail();
	}
	
	// load user details
	private void load_user_detail(){
		try {
			if (usr_str != null) {
				user_detail = UserTable.selUser(usr_str);
				if (user_detail != null) {
					txt_first_name.setText(user_detail.getFirst_name());
					txt_last_name.setText(user_detail.getLast_name());
					txt_password.setText(user_detail.getUser_pwd());
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}
	

	// Event Listener on Button[#bt_update].onAction
	@FXML
	public void bt_update_act(ActionEvent event) {
		String first_name=txt_first_name.getText();
		String last_name=txt_last_name.getText();
		String pwd=txt_password.getText();
		int err_counter = 0;
		// input fields validation - Missing field
		if (first_name == null || first_name.trim().isEmpty() || last_name.trim() == null || last_name.trim().isEmpty() || pwd.trim() == null || pwd.trim().isEmpty()) {
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Update Account Failed");
            msg.setHeaderText("Missing field");
            msg.setContentText("Please enter all field.");            
            msg.showAndWait();
            err_counter += 1;
		} 
		// input fields validation - over max length
		if (!ValidateInputController.isWitinLegth(first_name, 50) || !ValidateInputController.isWitinLegth(last_name, 50) || !ValidateInputController.isWitinLegth(pwd, 10)) {
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Update Account Failed");
            msg.setHeaderText("Field length over limit");
            msg.setContentText("Password field length 10, and First/Last Name field length 50.");            
            msg.showAndWait();
            err_counter += 1;
		}
		// input fields validation - password at least 8 character
		if (!ValidateInputController.isAtLeastLegth(pwd, 8)) {
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Update Account Failed");
            msg.setHeaderText("Password field length too short.");
            msg.setContentText("Pelase enter password for require field length.");            
            msg.showAndWait();
            err_counter += 1;
		}
		if (err_counter==0) {
			try {
				User upd_usr = new User(null, usr_str, pwd, first_name, last_name, null, 0.0, null, null);
				int i = UserTable.updUser(upd_usr);
				if (i > 0) {
					Alert msg = new Alert(Alert.AlertType.INFORMATION);
					msg.setTitle("Update Account Sucess");
					msg.setHeaderText("Sucess");
					msg.setContentText("Account has updated.");            
					msg.showAndWait();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	// Event Listener on Label[#main_menu].onMouseClicked
	@FXML
	public void lb_main_menu(MouseEvent event) {
        // go to Account page
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
        // go to Order page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderBasket.fxml")));
        	border_pane.getChildren().setAll(parentContent);
        }catch(IOException e){
            System.out.println(e);
        }
	}
	// Event Listener on Label[#collect_order].onMouseClicked
	@FXML
	public void lb_collect_order(MouseEvent event) {
        // go to Collect Order page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderCollect.fxml")));
        	border_pane.getChildren().setAll(parentContent);  
        }catch(IOException e){
            System.out.println(e);
        }
	}
	// Event Listener on Label[#cancel_order].onMouseClicked
	@FXML
	public void lb_cancel_order(MouseEvent event) {
        // go to Cancel Order page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderCancel.fxml")));
        	border_pane.getChildren().setAll(parentContent);  
        }catch(IOException e){
            System.out.println(e);
        }
	}
	// Event Listener on Label[#view_order].onMouseClicked
	@FXML
	public void lb_view_order(MouseEvent event) {
		// go to View Order page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderView.fxml")));
        	border_pane.getChildren().setAll(parentContent);
        }catch(IOException e){
            System.out.println(e);
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
	// Event Listener on Label[#acct].onMouseClicked
	@FXML
	public void lb_acct(MouseEvent event) {
		// go to Account page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Account.fxml")));
        	border_pane.getChildren().setAll(parentContent);
        }catch(IOException e){
            System.out.println(e);
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
		// Logout
		if (usr_str != null) {
			CommonOperation.signOut();
		} 
	    // Login
	    try {
	        BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Login.fxml")));
	        border_pane.getChildren().setAll(parentContent);
	     }catch(IOException e){
	           System.out.println(e);
	     }
	}
}
