package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import application.DB.UserTable;
import application.customcls.User;
import javafx.fxml.Initializable;

public class NewAcctController implements Initializable{ 
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
	private TextField txt_username;
	@FXML
	private PasswordField txt_pwd;
	@FXML
	private TextField txt_firstname;
	@FXML
	private TextField txt_lastname;
	@FXML
	private Button confirm;
	@FXML
	private VBox msg;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label sign_in;
	
	private String usr_str;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// get username
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		if (usr_str == null) {
			sign_in.setText("Login");
		} else {
			sign_in.setText("Logout");
		}		
	}	
	
	// Event Listener on Label[#main_menu].onMouseClicked
	@FXML
	public void lb_main_menu(MouseEvent event) {
		//go to Main Menu page
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
        // go to Account page
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
		// TODO Autogenerated
	}
	// Event Listener on Label[#cancel_order].onMouseClicked
	@FXML
	public void lb_cancel_order(MouseEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Label[#view_order].onMouseClicked
	@FXML
	public void lb_view_order(MouseEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Label[#export_all_order].onMouseClicked
	@FXML
	public void lb_export_all_order(MouseEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#confirm].onAction
	@FXML
	public void bt_confirm(ActionEvent event) throws SQLException{
		String user_name=txt_username.getText();
        String user_pwd=txt_pwd.getText();
        String user_firstname=txt_firstname.getText();
        String user_lastname=txt_lastname.getText();
        int err_counter = 0;
        
        // input fields validation - Missing field
     	if (user_name == null || user_name.trim().isEmpty() || user_firstname == null || user_firstname.trim().isEmpty() || user_lastname.trim() == null || user_lastname.trim().isEmpty() || user_pwd.trim() == null || user_pwd.trim().isEmpty()) {
     		Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Create Account Failed");
            msg.setHeaderText("Missing field");
            msg.setContentText("Please enter all field.");            
            msg.showAndWait();
            err_counter += 1;
     	}
		// input fields validation - over max length
		if (!ValidateInputController.isWitinLegth(user_firstname, 50) || !ValidateInputController.isWitinLegth(user_lastname, 50) || !ValidateInputController.isWitinLegth(user_pwd, 10) || !ValidateInputController.isWitinLegth(user_name, 8)) {
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Create Account Failed");
            msg.setHeaderText("Field length over limit");
            msg.setContentText("Username field length 8, Password field length 10, and First/Last Name field length 50.");            
            msg.showAndWait();
            err_counter += 1;
		}
		// input fields validation - password at least 8 character
		if (!ValidateInputController.isAtLeastLegth(user_pwd, 8)) {
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Create Account Failed");
            msg.setHeaderText("Password field length too short.");
            msg.setContentText("Pelase enter password for require field length.");            
            msg.showAndWait();
            err_counter += 1;
		}
        
        if (err_counter == 0) {
        	// create user account
        	boolean check_user=UserTable.chkUser(user_name);
        	if (check_user) {
            	// input fields validation - user exist
                Alert msg=new Alert(Alert.AlertType.ERROR);
                msg.setTitle("Create Account Failed");
                msg.setHeaderText("Username Exist");
                msg.setContentText("Please fill in another username.");            
                msg.showAndWait();        		
        	}else {
        		// add user
                User usr = new User(null, user_name, user_pwd, user_firstname, user_lastname, null, 0.0, null, null);
        		int i = UserTable.addUser(usr);
        		// add user success
        		if (i > 0) {
        			// set user name
            		User usr_holder = User.getInstance();
                    usr_holder.setUsername(user_name);
        			Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Account Creation");
                    msg.setHeaderText("Sucess");
                    msg.setContentText("Account has added.");            
                    msg.showAndWait();
                    // go to VIP page
                    try {
                    	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/VIP.fxml")));
                    	border_pane.getChildren().setAll(parentContent);
                    }catch(IOException e){
                        System.out.println(e);
                    }
        		}
        	}
        } 
        
	}
	// Event Listener on Label[#acct].onMouseClicked
	@FXML
	public void lb_acct(MouseEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Label[#new_acct].onMouseClicked
	@FXML
	public void lb_new_acct(MouseEvent event) {
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
