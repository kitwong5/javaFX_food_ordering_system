package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import application.Main;
import application.DB.OrderTable;
import application.DB.OrderDetailTable;
import application.customcls.*;
import javafx.stage.Stage;

public class OrderExportController  implements Initializable {
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
	private Label lb_order_id;
	@FXML
	private Label lb_order_date;
	@FXML
	private CheckBox ck_order_id;
	@FXML
	private CheckBox ck_order_date;
	@FXML
	private CheckBox ck_order_time;
	@FXML
	private CheckBox ck_item_name;
	@FXML
	private CheckBox ck_price;
	@FXML
	private CheckBox ck_quantity;
	@FXML
	private CheckBox ck_amount;
	@FXML
	private CheckBox ck_prepare_time;
	@FXML
	private CheckBox ck_ccn;
	@FXML
	private CheckBox ck_expire_date;
	@FXML
	private CheckBox ck_order_status;
	@FXML
	private Button bt_file_path;
	@FXML
	private TextField txt_file_path;
	@FXML
	private Button bt_export;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label sign_in;

	private String usr_str;
	
	private String ord_id;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// display user
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		CommonOperation.display_user(usr_str, lb_username, lb_user_tit);
		// display pending collection order	
		CommonOperation.display_order(usr_str, col_order_id, col_order_date, col_order_time, col_item, col_amt, col_status, tb_order, "'O','C','X'");
	}
	// Event Listener on TableView[#tb_order].onMouseClicked
	@FXML
	public void tb_order_click(MouseEvent event) {	
		// display pending orders
		OrderSummary selectedOrder = tb_order.getSelectionModel().getSelectedItem();
		ord_id = selectedOrder.getOrder_id();
		lb_order_id.setText(ord_id);
		bt_export.setDisable(false);
	}
	// Event Listener on Button[#bt_file_path].onAction
	@FXML
	public void bt_file_path_act(ActionEvent event) {
		// initial Stage, File, and DirectoryChooser
	    Stage theStage = Main.getPrimaryStage();
	    File initialDirectory = new File("C:/Users");
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Select Directory");
	    directoryChooser.setInitialDirectory(initialDirectory);
	    String file_path_str = null;
	    
		try {
			File directoryStr = directoryChooser.showDialog(theStage);
	        if (directoryStr != null) {
	            System.out.println("Selected directory: " + directoryStr.getAbsolutePath());
	            file_path_str = directoryStr.getAbsolutePath();
	            txt_file_path.setText(file_path_str);
	        } else {
	            System.out.println("No directory selected");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	// Event Listener on Button[#bt_export].onAction
	@FXML
	public void bt_export(ActionEvent event) throws Exception {
		String filePath=txt_file_path.getText();
		String csv_text = "";
		String csv_title = null;
		ArrayList<OrderDetails> OrderDetailsList=new ArrayList<>();
		Orders ord;
		Writer writer = null;
		int nullCounter = 0;
		int invalidCounter = 0;
		int lineCounter = 0;
		if (filePath == null||filePath.trim().isEmpty()) {
			Alert msg=new Alert(Alert.AlertType.ERROR);
			msg.setTitle("Order Export Failed");
			msg.setHeaderText("Missing Directory Path.");
			msg.setContentText("Pelase enter a valid directory path.");            
			msg.showAndWait();
			invalidCounter += 1;				
		}
		try {
			// construct string to export
			ord = OrderTable.selOrder(Integer.parseInt(ord_id));
			OrderDetailsList = OrderDetailTable.selOrderDetails(Integer.parseInt(ord_id));
			for (OrderDetails ordDtl: OrderDetailsList) {
				lineCounter += 1;
				if (ck_order_id.isSelected() == true) { 
					if (lineCounter == 1) {csv_title = "order_id";}
					csv_text = csv_text + Integer.toString(ord.getOrder_id());
					nullCounter +=1;
				}
				if (ck_order_date.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "order_date";}
					csv_text = csv_text + "," + ord.getOrder_date();
					nullCounter +=1;
				}
				if (ck_order_time.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "order_time";}
					String orderTimeHhStr = Integer.toString(ord.getOrder_time_hh());
					orderTimeHhStr = CommonOperation.padTime(orderTimeHhStr);
					String orderTimeMmStr = Integer.toString(ord.getOrder_time_mm());
					orderTimeMmStr = CommonOperation.padTime(orderTimeMmStr);
					csv_text = csv_text + "," + orderTimeHhStr+":"+orderTimeMmStr;
					nullCounter +=1;
				}
				if (ck_price.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "price";}
					csv_text = csv_text + "," + Double.toString(ordDtl.getPrice());
					nullCounter +=1;
				}
				if (ck_quantity.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "quantity";}
					csv_text = csv_text + "," + Integer.toString(ordDtl.getQuantity());
					nullCounter +=1;
				}
				if (ck_amount.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "amount";}
					csv_text = csv_text + "," + Double.toString(ordDtl.getAmount());
					nullCounter +=1;
				}
				if (ck_prepare_time.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "prepare_time";}
					csv_text = csv_text + "," + Double.toString(ord.getCook_time());
					nullCounter +=1;
				}
				if (ck_ccn.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "credit_card_no";}
					csv_text = csv_text + "," + ord.getCredit_card_no();
					nullCounter +=1;
				}
				if (ck_expire_date.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "expire_date";}
					csv_text = csv_text + "," + ord.getExpire_dt();
					nullCounter +=1;
				}
				if (ck_order_status.isSelected() == true) {
					if (lineCounter == 1) {csv_title = csv_title + "," + "order_status";}
					String ordStatus = ord.getOrder_status();
					System.out.println(ordStatus);
					String ordStatusTxt = null;
					if (ordStatus.trim().equals("O")) {
						ordStatusTxt = "Await for Collection";
						
					} else if (ordStatus.trim().equals("C")) {	
						ordStatusTxt = "Collected";
					} else if (ordStatus.trim().equals("X")) {	
						ordStatusTxt = "Cancelled";
					}
					csv_text = csv_text + "," + ordStatusTxt;
					nullCounter +=1;
				}	
				csv_text = csv_text + "\n";				
			}
			
			if (nullCounter == 0) {
				Alert msg=new Alert(Alert.AlertType.ERROR);
				msg.setTitle("Order Export Failed");
				msg.setHeaderText("No Field Selected.");
				msg.setContentText("Pelase select field to export.");            
				msg.showAndWait();
				invalidCounter += 1;
			} 
			if (invalidCounter == 0) {
				File file = new File(filePath +"\\orders.csv");
		        writer = new BufferedWriter(new FileWriter(file));
		        writer.write(csv_title+"\n"+csv_text);
		        writer.flush();
		        writer.close();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			invalidCounter += 1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Alert msg=new Alert(Alert.AlertType.ERROR);
			msg.setTitle("Order Export Failed");
			msg.setHeaderText(e.getMessage());
			msg.setContentText("Pelase retry.");            
			msg.showAndWait();
			invalidCounter += 1;
		}	finally {
			if (invalidCounter == 0) {
				// place order complete
				Alert msg = new Alert(Alert.AlertType.INFORMATION);
				msg.setTitle("Order Export");
				msg.setHeaderText("Sucess");
				msg.setContentText("File 'orders.csv' exported.");            
				msg.showAndWait();
			}
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
		// go to Place Order page
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
		// go to Account Order page
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
