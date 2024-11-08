package application.view.controller;

//import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.HashMap;

import application.DB.OrderDetailTable;
import application.DB.UserTable;
import application.customcls.*;
//import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.BorderPane;

public class CommonOperation {

	// sing out operation
	public static void signOut() {
		User usrHolder = User.getInstance();
		usrHolder.setUsername(null);
		ArrayList<OrderBasket> basketList_signout = new ArrayList<OrderBasket>();
		basketList_signout.clear();
		BasketList b = BasketList.getInstance();
		b.setBasketList(basketList_signout);
	}

	// enable menu if user login
	public static void enable_menu(String InUsr_str, Label InAcct, Label InPlace_order, Label InCollect_order, Label InCancel_order, Label InView_order, Label InExport_all_order) {
		if (InUsr_str != null) {
			InAcct.setDisable(false);
			InPlace_order.setDisable(false);
			InCollect_order.setDisable(false);
			InCancel_order.setDisable(false);
			InView_order.setDisable(false);
			InExport_all_order.setDisable(false);

		}
	}

	// display user name
	public static void display_user(String InUsr_str, Label InName, Label InUser_tit) {
		try {
			User userDetail = UserTable.selUser(InUsr_str);
			InName.setText(userDetail.getFirst_name()+" "+userDetail.getLast_name()+" ("+InUsr_str+")");
			InUser_tit.setText("User: ");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		 
	}
	
	// display summary table
	public static void display_order(String InUsr_str, TableColumn<OrderSummary, String> colOrderId, TableColumn<OrderSummary, String> colOrderDate, TableColumn<OrderSummary, String> colOrderTime, TableColumn<OrderSummary, String> colItem, TableColumn<OrderSummary, String> colAmt, TableColumn<OrderSummary, String> colStatus, TableView<OrderSummary> tbOrder, String InStatus) {
		try {
			if (InUsr_str != null) {
				// set table cell
				ArrayList<OrderSummary> ordSumList = OrderDetailTable.selOrdSum(InUsr_str, InStatus);
				colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
				colOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
				colOrderTime.setCellValueFactory(new PropertyValueFactory<>("order_time"));
				colItem.setCellValueFactory(new PropertyValueFactory<>("order_items"));
				colAmt.setCellValueFactory(new PropertyValueFactory<>("order_total"));
				colStatus.setCellValueFactory(new PropertyValueFactory<>("order_status"));
				// load table cell
				for (OrderSummary s: ordSumList) {
					tbOrder.getItems().add(s);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		 
	}
	
	// Validation Date Time Input
	public static int dateValidation(String TimeHhStr, String TimeMmStr, LocalDate collectDate, String alertMsg) {
		LocalDate nowDate = LocalDate.now(); 
		LocalDateTime nowDateTime = LocalDateTime.now();
	    DateTimeFormatter nowHhFormat = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter nowMmFormat = DateTimeFormatter.ofPattern("mm");	
        String nowHh = nowDateTime.format(nowHhFormat);
        String nowMm = nowDateTime.format(nowMmFormat);
		int TimeHh = 0;
		int TimeMm = 0;
		int invalidCounter = 0;
		// input fields validation - Missing field
		if (collectDate == null||TimeHhStr == null||TimeHhStr.trim().isEmpty()||TimeMmStr== null||TimeMmStr.trim().isEmpty()) {
     		Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle(alertMsg);
            msg.setHeaderText("Missing field");
            msg.setContentText("Please enter Date, Hour (HH), and Mintues (mm).");            
            msg.showAndWait();
            invalidCounter += 1;
		} else {
			// input fields validation - input passed date
			if (nowDate.isAfter(collectDate)) {
				Alert msg=new Alert(Alert.AlertType.ERROR);
				msg.setTitle(alertMsg);
				msg.setHeaderText("Invalid Date Input.");
				msg.setContentText("Pelase enter today date or a future date.");            
				msg.showAndWait();
				invalidCounter += 1;				
			}
			// input fields validation - integer
			if (!ValidateInputController.isInt0(TimeHhStr)||!ValidateInputController.isInt0(TimeMmStr)) {
				Alert msg=new Alert(Alert.AlertType.ERROR);
				msg.setTitle(alertMsg);
				msg.setHeaderText("Invalid Time Input.");
				msg.setContentText("Pelase enter integer value.");            
				msg.showAndWait();
				invalidCounter += 1;
			} else {
				TimeHh = Integer.parseInt(TimeHhStr);
				TimeMm = Integer.parseInt(TimeMmStr);
			}
			// input fields validation - time within range
			if (!(TimeHh >= 9 & TimeHh <= 17)||!(TimeMm >= 0 & TimeMm <= 59)) {
				Alert msg=new Alert(Alert.AlertType.ERROR);
				msg.setTitle(alertMsg);
				msg.setHeaderText("Invalid Order Time Input.");
				msg.setContentText("Pelase enter within business hours time. (hh: 00-17, mm: 00-59)");            
				msg.showAndWait();
				invalidCounter += 1;				
			}
			// input fields validation - future time
			if (nowDate.isEqual(collectDate)) {
				if((TimeHh < Integer.parseInt(nowHh)) || ((TimeHh == Integer.parseInt(nowHh))& (TimeMm < Integer.parseInt(nowMm)))) {
					Alert msg=new Alert(Alert.AlertType.ERROR);
					msg.setTitle(alertMsg);
					msg.setHeaderText("Invalid Order Time Input.");
					msg.setContentText("Pelase enter a future time.");            
					msg.showAndWait();
					invalidCounter += 1;				
				}
			}	
		}
		return invalidCounter;
	}
	
	// lpad time
	public static String padTime(String inTime) {
		if (inTime.length() == 1) {
			inTime="0"+inTime;
	    }
		return inTime;
	}

}
