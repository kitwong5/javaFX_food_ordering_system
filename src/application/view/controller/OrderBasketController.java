package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import application.DB.ItemTable;
import application.customcls.*;



public class OrderBasketController implements Initializable{
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
	private CheckBox ra_burrito;
	@FXML
	private Label lb_burrito_pri;
	@FXML
	private TextField txt_burrito;
	@FXML
	private CheckBox ra_fries;
	@FXML
	private Label lb_fries_pri;
	@FXML
	private TextField txt_fries;
	@FXML
	private CheckBox ra_soda;
	@FXML
	private Label lb_soda_pri;
	@FXML
	private TextField txt_soda;
	@FXML
	private CheckBox ra_meal;
	@FXML
	private Label lb_meal_pri;
	@FXML
	private TextField txt_meal;
	@FXML
	private Label lb_item;
	@FXML
	private Button confirm;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label sign_in;
	
	private String usr_str;
	
	private Double burrito_pri; 
	
	private Double fries_pri;
	
	private Double soda_pri;
	
	private Double meal_pri;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// get user name
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		// display user name
		CommonOperation.display_user(usr_str, lb_username, lb_user_tit);
		// display item price
		display_price();
	}
	// display item price
	public void display_price() {
		try {
			burrito_pri = ItemTable.getPri(1);
			lb_burrito_pri.setText("$ "+Double.toString(burrito_pri));
			fries_pri = ItemTable.getPri(2);
			lb_fries_pri.setText("$ "+Double.toString(fries_pri));
			soda_pri = ItemTable.getPri(3);
			lb_soda_pri.setText("$ "+Double.toString(soda_pri));
			meal_pri = burrito_pri+fries_pri+soda_pri-3;
			lb_meal_pri.setText("$ "+Double.toString(meal_pri));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// Event Listener on Label[#main_menu].onMouseClicked
	@FXML
	public void lb_main_menu(MouseEvent event)  {
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
        // go to Cancel page
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
	// Event Listener on RadioButton[#ra_burrito].onAction
	@FXML
	public void ra_burrito_act(ActionEvent event) {
		// enable input field if item selected, update item counter
		if (ra_burrito.isSelected() == true) { 
			txt_burrito.setDisable(false);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) + 1));
		} else {
			txt_burrito.setDisable(true);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) - 1));
		}		
	}  
	// Event Listener on RadioButton[#ra_fries].onAction
	@FXML
	public void ra_fries_act(ActionEvent event) {
		// enable input field if item selected, update item counter
		if (ra_fries.isSelected() == true) { 
			txt_fries.setDisable(false);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) + 1));
		} else {
			txt_fries.setDisable(true);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) - 1));
		}
	}
	// Event Listener on RadioButton[#ra_soda].onAction
	@FXML
	public void ra_soda_act(ActionEvent event) {
		// enable input field if item selected, update item counter
		if (ra_soda.isSelected() == true) { 
			txt_soda.setDisable(false);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) + 1));
		} else {
			txt_soda.setDisable(true);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) - 1));
		}
	}
	// Event Listener on RadioButton[#ra_meal].onAction
	@FXML
	public void ra_meal_act(ActionEvent event) {
		// enable input field if item selected, update item counter
		if (ra_meal.isSelected() == true) { 
			txt_meal.setDisable(false);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) + 1));
		} else {
			txt_meal.setDisable(true);
			lb_item.setText(Integer.toString(Integer.parseInt(lb_item.getText()) - 1));
		}
	}
	
	// Event Listener on Button[#confirm].onAction
	@FXML
	public void bt_confirm(ActionEvent event) {
		// input fields validation - Quantity
		int invalid_counter = 0;
		if (ra_burrito.isSelected() == true) {
			if (!ValidateInputController.isInt(txt_burrito.getText())) {
				invalid_counter += 1; 
			}
		}
		if (ra_fries.isSelected() == true){
			if (!ValidateInputController.isInt(txt_fries.getText())) {
				invalid_counter += 1; 
			}
		}
		if (ra_soda.isSelected() == true){
			if (!ValidateInputController.isInt(txt_soda.getText())) {
				invalid_counter += 1; 
			}
		} 
		if (ra_meal.isSelected() == true){
			if (!ValidateInputController.isInt(txt_meal.getText())) {
				invalid_counter += 1; 
			}
		}
		if (invalid_counter > 0) {
           	// input fields validation - Quantity
            Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Place Basket Failed");
            msg.setHeaderText("Invalid Input");
            msg.setContentText("Please enter valid Quantity. e.g. 1, 2, 3, ...");            
            msg.showAndWait();
		} else {
			// set orderBasketMap
			ArrayList<OrderBasket> basketList = new ArrayList<OrderBasket>();
			if (ra_burrito.isSelected() == true) {
				Double item_amount = burrito_pri * Integer.parseInt(txt_burrito.getText());
				OrderBasket bskItem = new OrderBasket("Burrito", Double.toString(burrito_pri), txt_burrito.getText(), Double.toString(item_amount));
				basketList.add(bskItem);    			
			}
			if (ra_fries.isSelected() == true) {
				Double item_amount = fries_pri * Integer.parseInt(txt_fries.getText());
				OrderBasket bskItem = new OrderBasket("Fries", Double.toString(fries_pri), txt_fries.getText(), Double.toString(item_amount));
				basketList.add(bskItem);    			    			
			}
			if (ra_soda.isSelected() == true) {
				Double item_amount = soda_pri * Integer.parseInt(txt_soda.getText());
				OrderBasket bskItem = new OrderBasket("Soda", Double.toString(soda_pri), txt_soda.getText(), Double.toString(item_amount));
				basketList.add(bskItem);    			    			    			
			}
			if (ra_meal.isSelected() == true) {
				Double item_amount = meal_pri * Integer.parseInt(txt_meal.getText());
				OrderBasket bskItem = new OrderBasket("Meal", Double.toString(meal_pri), txt_meal.getText(), Double.toString(item_amount));
				basketList.add(bskItem);    			    			    			    			
			}
    		BasketList basketList_holder = BasketList.getInstance();
    		basketList_holder.setBasketList(basketList);
    		// go to Place Basket page
	        try {
	        	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/PlaceBasket.fxml")));
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
