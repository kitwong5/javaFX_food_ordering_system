package application.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import application.DB.OrderDetailTable;
import application.DB.OrderTable;
import application.customcls.*;



public class OrderCollectController implements Initializable{
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
	private Label lb_order_time;
	@FXML
	private Label lb_prepare_time;
	@FXML
	private DatePicker dt_collect_date;
	@FXML
	private TextField txt_collect_hh;
	@FXML
	private TextField txt_collect_mm;
	@FXML
	private Button bt_collect_order;
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
		display_order(usr_str);	
	}
	
	public void display_order(String InUsr_str) {
		try {
			if (InUsr_str != null) {
				// set table cell
				ArrayList<OrderSummary> ordSumList = OrderDetailTable.selOrdSum(InUsr_str, "'O'");
				col_order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
				col_order_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));
				col_order_time.setCellValueFactory(new PropertyValueFactory<>("order_time"));
				col_item.setCellValueFactory(new PropertyValueFactory<>("order_items"));
				col_amt.setCellValueFactory(new PropertyValueFactory<>("order_total"));
				col_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));
				// load table cell
				for (OrderSummary s: ordSumList) {
					tb_order.getItems().add(s);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		 
	}

	// Event Listener on TableView[#tb_order].onMouseClicked
	@FXML
	public void tb_order_click(MouseEvent event) {
		try {
			// display pending orders
			OrderSummary selectedOrder = tb_order.getSelectionModel().getSelectedItem();
			ord_id = selectedOrder.getOrder_id();
			Orders ordDtl = OrderTable.selOrder(Integer.parseInt(ord_id));
			String orderTimeHhStr = Integer.toString(ordDtl.getOrder_time_hh());
			orderTimeHhStr = CommonOperation.padTime(orderTimeHhStr);
			String orderTimeMmStr = Integer.toString(ordDtl.getOrder_time_mm());
			orderTimeMmStr = CommonOperation.padTime(orderTimeMmStr);
			lb_order_id.setText(selectedOrder.getOrder_id());
			lb_order_date.setText(ordDtl.getOrder_date());
			lb_order_time.setText(orderTimeHhStr+":"+orderTimeMmStr+ " (in 24hr format)");
			lb_prepare_time.setText(Double.toString(ordDtl.getCook_time())+" mins");
			// enable input fields
			dt_collect_date.setDisable(false);
			txt_collect_hh.setDisable(false);
			txt_collect_mm.setDisable(false);
			bt_collect_order.setDisable(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		 
	}
	// Event Listener on Button[#bt_collect_order].onAction
	@FXML
	public void bt_collect_order_act(ActionEvent event) {
		String TimeHhStr=txt_collect_hh.getText();
		String TimeMmStr=txt_collect_mm.getText();
		LocalDate collectDate = dt_collect_date.getValue();
		int invalidCounter = 0;
		
		// input valuation - datetime and business hours valuation 
		invalidCounter = CommonOperation.dateValidation(TimeHhStr, TimeMmStr, collectDate, "Order Collection Failed");
		
		// input valuation - collect-time after order-placed time + time to prepare
		if (invalidCounter == 0) {
			try {
				Orders ordDtl = OrderTable.selOrder(Integer.parseInt(ord_id));
				Double cookTime = ordDtl.getCook_time();
				String orderDateStr = ordDtl.getOrder_date();
				LocalDate orderDate = LocalDate.parse(orderDateStr);
				int orderTimeHh = ordDtl.getOrder_time_hh();
				int orderTimeMm = ordDtl.getOrder_time_mm(); 
				// adjust order time to included the cooking time
				if ((orderTimeMm + cookTime) > 59) {
					orderTimeMm = orderTimeMm + cookTime.intValue() - 60;
					orderTimeHh = orderTimeHh + 1;						
				} else {
					orderTimeMm = orderTimeMm + cookTime.intValue();
				}				
				// input valuation - for same date order, collect-time must be at least order-placed time + time to prepare
				if (collectDate.isEqual(orderDate)) {
					if ((Integer.parseInt(TimeHhStr) < orderTimeHh) || ((Integer.parseInt(TimeHhStr)==orderTimeHh)  & (Integer.parseInt(TimeMmStr) <= orderTimeMm))) {
						Alert msg=new Alert(Alert.AlertType.ERROR);
						msg.setTitle("Order Collection Failed");
						msg.setHeaderText("Invalid Date Input.");
						msg.setContentText("Pelase enter a collect time after the order time plus prepare time.");            
						msg.showAndWait();
						invalidCounter += 1;				
					}
				// input valuation - for non same date order, collect date time must be after order date	
				} else if (orderDate.isAfter(collectDate)) {
					Alert msg=new Alert(Alert.AlertType.ERROR);
					msg.setTitle("Order Collection Failed");
					msg.setHeaderText("Invalid Date Input.");
					msg.setContentText("Pelase enter a collect time after the order time plus cooking time.");            
					msg.showAndWait();
					invalidCounter += 1;									
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}		 
		}

		// update collect status and collect datetime in DB
		if (invalidCounter == 0) {
			try {
				//update order time to DB
				int n = OrderTable.updColTime(Integer.parseInt(ord_id), collectDate.toString(), Integer.parseInt(TimeHhStr), Integer.parseInt(TimeMmStr));
				if (n > 0) {
					// place order complete
					Alert msg = new Alert(Alert.AlertType.INFORMATION);
					msg.setTitle("Order Collection");
					msg.setHeaderText("Sucess");
					msg.setContentText("Order Collected.");            
					msg.showAndWait();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			// refresh page
			try {
				BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/OrderCollect.fxml")));
				border_pane.getChildren().setAll(parentContent);
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}
	// Event Listener on Label[#main_menu].onMouseClicked
	@FXML
	public void lb_main_menu(MouseEvent event) {
		// go to MainMenu page
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
