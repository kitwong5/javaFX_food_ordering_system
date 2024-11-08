package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.DB.UserTable;
import application.customcls.User;


public class VIPController implements Initializable{ 
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
	private TextField txt_email;
	@FXML
	private CheckBox rd_prom;
	@FXML
	private Button confirm;
	@FXML
	private Button not_join;
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// get username
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
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
        // go to Order page
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
	// Event Listener on Button[#confirm].onAction
	@FXML
	public void bt_confirm(ActionEvent event) throws SQLException{
		String user_email=txt_email.getText();
		boolean is_radio = false;
		//User usr_holder = User.getInstance();
		if (rd_prom.isSelected() == true) is_radio = true;
		if (user_email != null & is_radio) {
			// upgrade VIP
			if (ValidateInputController.isEMail(user_email)) {
				int i = UserTable.updVIP(usr_str);
				// upgrade VIP success
        		if (i > 0) {
        			Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Upgrade VIP");
                    msg.setHeaderText("Sucess");
                    msg.setContentText("Please log out and log in again to access VIP functionalities.");            
                    msg.showAndWait();     
                    // go to Login page
                    try {
                    	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Login.fxml")));
                    	border_pane.getChildren().setAll(parentContent);
                    }catch(IOException e){
                        System.out.println(e);
                    }
        		}
			}else {
				// input fields validation - invalid email
	            Alert msg=new Alert(Alert.AlertType.ERROR);
	            msg.setTitle("Upgrade VIP Failed");
	            msg.setHeaderText("Invalid Email");
	            msg.setContentText("Please enter valid email address to create VIP account.");            
	            msg.showAndWait();
			}			
		}else {
			// input fields validation - missing fields
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Upgrade VIP Failed");
            msg.setHeaderText("Missing Information");
            msg.setContentText("Please fill in all fields.");            
            msg.showAndWait();
		}
	}
	// Event Listener on Button[#not_join].onAction
	@FXML
	public void btn_not_join(ActionEvent event) {
        // go to Main Menu page
        try {
        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/MainMenu.fxml")));
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
	    try {
	    	CommonOperation.signOut();
	        BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/Login.fxml")));
	        border_pane.getChildren().setAll(parentContent);
	     }catch(IOException e){
	           System.out.println(e);
	     }
	}
}
