package application.view.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.DB.ItemTable;
import application.DB.OrderDetailTable;
import application.DB.OrderTable;
import application.DB.UserTable;
import application.customcls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class PlaceBasketController implements Initializable{
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
	private Label lb_rmin_ser;
	@FXML
	private TableView<OrderBasket> tb_order;
	@FXML
	private TableColumn<OrderBasket, String> tb_item;
	@FXML
	private TableColumn<OrderBasket, String> tb_pri;
	@FXML
	private TableColumn<OrderBasket, String> tb_qty;
	@FXML
	private TableColumn<OrderBasket, String> tb_amt;
	@FXML
	private Label lb_discount;
	@FXML
	private Label lb_discount_amt;
	@FXML
	private Label lb_credit_point_tit;
	@FXML
	private Label lb_credit_point;
	@FXML
	private Label lb_credit_point_cmt;
	@FXML
	private Label lb_tot_amt;
	@FXML
	private Label lb_waiting_time;
	@FXML
	private Label lb_redeem_tit;
	@FXML
	private TextField txt_redeem;
	@FXML
	private Label lb_adj_amt_tit;
	@FXML
	private Label lb_adj_amt;
	@FXML
	private Button bt_confirm;
	@FXML
	private Button bt_payment;
	@FXML
	private HBox top_pane;
	@FXML
	private Label acct;
	@FXML
	private Label new_acct;
	@FXML
	private Label lb_ccn;
	@FXML
	private TextField txt_ccn;
	@FXML
	private Label lb_ccn_msg;
	@FXML
	private Label lb_exp;
	@FXML
	private TextField txt_exp;
	@FXML
	private Label lb_exp_msg;
	@FXML
	private Label lb_cvv;
	@FXML
	private TextField txt_cvv;
	@FXML
	private Label lb_cvv_msg;
	@FXML
	private Label lb_order_no_tit;
	@FXML
	private Label lb_order_no;
	@FXML
	private Label lb_order_time;
	@FXML
	private TextField txt_order_time_hh;
	@FXML
	private Label lb_order_time_hh;
	@FXML
	private TextField txt_order_time_mm;
	@FXML
	private Label lb_order_time_mm;
	@FXML
	private Button bt_submit;
	@FXML
	private DatePicker dt_order_date;

	//private Label sign_in;
	
	private boolean usr_vip;
	
	private String usr_str;
	
	private double total_amount;
	
	private double payment_amount;
	
	private double redeem_points;
	
	private double credit_points;
	
	private double cook_time;
	
	private int rem_ser;
	
	private int order_id;
	
	
	ArrayList<OrderBasket> basketList=new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// display username
		User usr_holder = User.getInstance();
		usr_str = usr_holder.getUsername();
		// display user name
		CommonOperation.display_user(usr_str, lb_username, lb_user_tit);
		// check is VIP user
		is_user_vip();
		// display order table
		display_order_table();
		// display discount amount
		display_discount_amt(basketList, usr_vip);
		// display total amount
		total_amount = clacTotal(basketList, usr_vip);
		lb_tot_amt.setText("$ "+Double.toString(total_amount));
		// display user credits
		credit_points = display_credit_point(usr_str);
		lb_credit_point.setText(Double.toString(credit_points));
		// display waiting time
		cook_time = waiting_time(basketList);
		lb_waiting_time.setText(Double.toString(cook_time)+" mins");
	}
	// Display order basket table
	public void display_order_table() {
		BasketList basketList_holder = BasketList.getInstance();
		basketList = basketList_holder.getBasketList();		
		try {
			// get order details
			BasketList basketList_holder1 = BasketList.getInstance();
			basketList = basketList_holder1.getBasketList();
			// set table cell
			tb_item.setCellValueFactory(new PropertyValueFactory<>("item_name"));
			tb_pri.setCellValueFactory(new PropertyValueFactory<>("item_pri"));
			tb_qty.setCellValueFactory(new PropertyValueFactory<>("item_qty"));
			tb_amt.setCellValueFactory(new PropertyValueFactory<>("item_amt"));
			// load table cell
			for (OrderBasket bkt: basketList) {
				tb_order.getItems().add(bkt);
			}
		 }catch (Exception e){
	            e.printStackTrace();
	     }
	}
	// Display discount total
	public void display_discount_amt(ArrayList<OrderBasket> basketListIn, boolean usr_vipIn) {
		for (OrderBasket bkt : basketListIn) {
			if (usr_vipIn & bkt.getItem_name()=="Meal") {
				int discount_amt = 3 * Integer.parseInt(bkt.getItem_qty());
				lb_discount_amt.setText("$ "+discount_amt+".0");
			}
		}
	}	
	// Display user credit point
	public static double display_credit_point(String usr_strIn) {
		double cr_pt = 0;
		try {
			User user_detail = UserTable.selUser(usr_strIn);
			if (user_detail.getVip_credit() != null) {
				cr_pt = user_detail.getVip_credit();
			}
		 }catch (Exception e){
	            e.printStackTrace();
	     }
		return cr_pt;
	}

	// Calculate Total Cost
	public static double clacTotal(ArrayList<OrderBasket> basketListIn, boolean usr_vipIn) {
		double tot_cost = 0;
		int discount_amt;
		try {
			if (usr_vipIn) {
				// total amount for non VIP user
				for (OrderBasket bkt : basketListIn) {
					if (bkt.getItem_name()=="Meal") {
						discount_amt = 3 * Integer.parseInt(bkt.getItem_qty());
						tot_cost = tot_cost + (Integer.parseInt(bkt.getItem_qty()) * ItemTable.getMealPri());
						// discount for VIP user ordering a meal
						tot_cost = tot_cost - discount_amt;
					} else {
						tot_cost = tot_cost + (Integer.parseInt(bkt.getItem_qty()) * Double.parseDouble(bkt.getItem_pri()));
					}
				}			
			}else {
				// total amount for VIP user
				for (OrderBasket bkt : basketListIn) {
					if (bkt.getItem_name()=="Meal") {
						tot_cost = tot_cost + (Integer.parseInt(bkt.getItem_qty()) * ItemTable.getMealPri());
					} else {
						tot_cost = tot_cost + (Integer.parseInt(bkt.getItem_qty()) * Double.parseDouble(bkt.getItem_pri()));
					}
				}
			}
		 }catch (Exception e){
	            e.printStackTrace();
	     }
		return tot_cost;
	}	
	// check is user a vip member
	public void is_user_vip() {
		try {
			usr_vip=UserTable.chkVIP(usr_str);
			if(usr_vip) {
				lb_discount.setDisable(false);
				lb_discount_amt.setDisable(false);
				lb_credit_point_tit.setDisable(false);
				lb_credit_point.setDisable(false);
				lb_credit_point_cmt.setDisable(false);
				lb_redeem_tit.setDisable(false);
				txt_redeem.setDisable(false);
				lb_adj_amt_tit.setDisable(false);
				lb_adj_amt.setDisable(false);
			}else {
				lb_discount.setDisable(true);
				lb_discount_amt.setDisable(true);
				lb_credit_point_tit.setDisable(true);
				lb_credit_point.setDisable(true);
				lb_credit_point_cmt.setDisable(true);
				lb_redeem_tit.setDisable(true);
				txt_redeem.setDisable(true);
				lb_adj_amt_tit.setDisable(true);
				lb_adj_amt.setDisable(true);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// Display waiting time
	public double waiting_time(ArrayList<OrderBasket> basketListIn) {
		double wait_time = 0;
		int rem_ser = 0;
		double time_burritos=0;
		double time_fries=0;
		try {
			for (OrderBasket bkt : basketListIn) {
				Items item_detail = ItemTable.selItems(bkt.getItem_name());
				rem_ser = item_detail.getRemaining_serves();
				if (bkt.getItem_name()=="Meal") {
					wait_time = wait_time + Math.max(cookTimeForBurritos(Integer.parseInt(bkt.getItem_qty())), cookTimeForFries(Integer.parseInt(bkt.getItem_qty()), rem_ser));
					time_burritos = cookTimeForBurritos(Integer.parseInt(bkt.getItem_qty()));
				} else if (bkt.getItem_name()=="Fries") {	
					time_fries = cookTimeForFries(Integer.parseInt(bkt.getItem_qty()), rem_ser);
				}
				wait_time = wait_time + Math.max(time_burritos, time_fries);
				lb_waiting_time.setText(Double.toString(wait_time)+" mins");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return wait_time;
	}	

	// calculate Burritos cook time 
	// Reference: Lawrence C (2024). Assignment1 Sample Solution. RMIT.
	// Reference Available: https://rmit.instructure.com/courses/124803/discussion_topics/2373668
	public static double cookTimeForBurritos(int quantityIn){
		int batchPrepTime = 9;
		int batchSize = 2;
		double burritos_cook_time = 0;
		burritos_cook_time = batchPrepTime * ((int) Math.ceil(quantityIn / ((double)batchSize)));
		return burritos_cook_time;
	}
	// Fries actual quantity needed
	// Reference: Lawrence C (2024). Assignment1 Sample Solution. RMIT.
	// Reference Available: https://rmit.instructure.com/courses/124803/discussion_topics/2373668
	private static int getFiresActualQuantityNeeded(int quantityIn, int remainingServes) {
		if(remainingServes >= quantityIn) {
			return 0;
		}else if(remainingServes > 0){
			return quantityIn - remainingServes;
		}else {
			return quantityIn;
		}
	}
	// calculate Fries cook time 
	// Reference: Lawrence C (2024). Assignment1 Sample Solution. RMIT.
	// Reference Available: https://rmit.instructure.com/courses/124803/discussion_topics/2373668
	public static double cookTimeForFries(int quantityIn, int remainingServes){
		int prepTimeForOneServe = 8;
		int batchSize = 5;
		int actualQuantityNeeded = 0;
		double fries_cook_time = 0;
			
		if(remainingServes >= quantityIn) {
			actualQuantityNeeded=  0;
		}else if(remainingServes > 0){
			actualQuantityNeeded = quantityIn - remainingServes;
		}else {
			actualQuantityNeeded = quantityIn;
		}
		//fries_cook_time =  prepTimeForOneServe * (Math.ceil(actualQuantityNeeded / ((double) batchSize)));
		actualQuantityNeeded = getFiresActualQuantityNeeded(quantityIn, remainingServes);
		fries_cook_time =  prepTimeForOneServe * (Math.ceil(actualQuantityNeeded / ((double) batchSize)));
		return fries_cook_time;
	}
	// calculate Fries batch size
	// Reference: Lawrence C (2024). Assignment1 Sample Solution. RMIT.
	// Reference Available: https://rmit.instructure.com/courses/124803/discussion_topics/2373668
	private int getFiresActualQuantityCooked(int quantityIn, int remainingServes) {
		int batchSize = 5;
		int getActualBatchesCooked = (int) Math.ceil(getFiresActualQuantityNeeded(quantityIn,remainingServes) / ((double) batchSize));
		return getActualBatchesCooked * batchSize;
	}



	// Event Listener on Button[#bt_confirm].onAction
	@FXML
	public void bt_confirm_act(ActionEvent event) {
		String redeem_str=txt_redeem.getText();
		int redeem_int = 0;
		int invalid_counter = 0;
		//confirm order
		if (redeem_str == null || redeem_str.trim().isEmpty()) {
			redeem_int = 0;
		} else {
			// input fields validation - integer
			if (!ValidateInputController.isInt0(redeem_str)) {
	            Alert msg=new Alert(Alert.AlertType.ERROR);
	            msg.setTitle("Confirm Order Failed");
	            msg.setHeaderText("Invalid Redeem Points Input.");
	            msg.setContentText("Pelase enter integer value.");            
	            msg.showAndWait();
	            invalid_counter += 1; 
			} else {
				redeem_int = Integer.parseInt(redeem_str);
			}
			if (redeem_int > 0) {
				// input fields validation -  input value bigger than credit points
				if (redeem_int > credit_points) {
					Alert msg=new Alert(Alert.AlertType.ERROR);
					msg.setTitle("Confirm Order Failed");
					msg.setHeaderText("Redeem Points Input.");
					msg.setContentText("Redeem points geater than outstanding credit points.");            
					msg.showAndWait();		
					invalid_counter += 1; 
				}
				// input fields validation -  in per 100 credit unit
				if ((redeem_int%100)!=0) {
					Alert msg=new Alert(Alert.AlertType.ERROR);
					msg.setTitle("Confirm Order Failed");
					msg.setHeaderText("Redeem Points Input.");
					msg.setContentText("Please redeem in per 100 credit unit.");            
					msg.showAndWait();	
					invalid_counter += 1; 
				}
			}
			if (invalid_counter == 0) {
				redeem_points = Double.parseDouble(redeem_str);
				// adjust payment amount
				payment_amount = total_amount - (redeem_int/100);
				lb_adj_amt.setText("$ "+Double.toString(payment_amount));
				// enable payment process
				bt_confirm.setDisable(true);
				txt_redeem.setDisable(true);
				bt_payment.setDisable(false);
				lb_cvv.setDisable(false);
				lb_cvv_msg.setDisable(false);
				txt_cvv.setDisable(false);
				lb_ccn.setDisable(false);
				lb_ccn_msg.setDisable(false);
				txt_ccn.setDisable(false);
				lb_exp.setDisable(false);
				lb_exp_msg.setDisable(false);
				txt_exp.setDisable(false);
			}
		}
	}
	// Event Listener on Button[#bt_payment].onAction
	@FXML
	public void bt_payment_act(ActionEvent event) {
		String cvv_str=txt_cvv.getText();
		String exp_str=txt_exp.getText();
		String ccn_str=txt_ccn.getText();
		cvv_str = cvv_str.trim();
		ccn_str = ccn_str.trim(); 
		exp_str = exp_str.trim();

		int invalid_counter = 0;
        // input fields validation - Missing field
     	if (cvv_str == null || cvv_str.trim().isEmpty() || ccn_str == null || ccn_str.trim().isEmpty() || exp_str.trim() == null || exp_str.trim().isEmpty() ) {
     		Alert msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Payment Failed");
            msg.setHeaderText("Missing field");
            msg.setContentText("Please enter credit card no., expiry date, and cvv field.");            
            msg.showAndWait();
            invalid_counter += 1;
     	} 
		// input fields validation - integer
		if (!ValidateInputController.isValidCardNumber(ccn_str) || !ValidateInputController.isInt0(cvv_str) || !ValidateInputController.isInt0(exp_str)) {
			Alert msg=new Alert(Alert.AlertType.ERROR);
	        msg.setTitle("Payment Failed");
	        msg.setHeaderText("Invalid Input Value.");
	        msg.setContentText("Pelase enter numberic value.");            
	        msg.showAndWait();
	        invalid_counter += 1; 
		} 
		// input fields validation - field length
		if (!ValidateInputController.isNotEqualLegth(cvv_str,3)||!ValidateInputController.isNotEqualLegth(ccn_str,16)||!ValidateInputController.isNotEqualLegth(exp_str,4)) {
			Alert msg=new Alert(Alert.AlertType.ERROR);
	        msg.setTitle("Payment Failed");
	        msg.setHeaderText("Invalid Input Length");
	        msg.setContentText("Pelase enter valid digit (credit card no. 16 digits, expire date 4 digits, cvv 3 digit.");            
	        msg.showAndWait();
	        invalid_counter += 1; 
		} 
		// input fields validation - expire date
		if (invalid_counter == 0) {
			String exp_yy = exp_str.substring(2);
			String exp_mm = exp_str.substring(0,2);
	        String thisYear = Year.now().toString();
	        int yy_now = Integer.parseInt(thisYear.substring(2));
	        LocalDate today = LocalDate.now();
	        int mm_now = today.getMonthValue();
	     // input fields validation - credit card number 
			if (Integer.parseInt(exp_mm) > 12 || Integer.parseInt(exp_mm) < 1 || Integer.parseInt(exp_yy) < yy_now || (Integer.parseInt(exp_yy) == yy_now && Integer.parseInt(exp_mm) < mm_now)) {
				Alert msg=new Alert(Alert.AlertType.ERROR);
		        msg.setTitle("Payment Failed");
		        msg.setHeaderText("Invalid Expire Date");
		        msg.setContentText("Pelase enter valid expire date in MMYY format.");            
		        msg.showAndWait();
		        invalid_counter += 1; 				
			}
		}
		if (invalid_counter == 0) {
			bt_payment.setDisable(true);
			lb_cvv.setDisable(true);
			lb_cvv_msg.setDisable(true);
			txt_cvv.setDisable(true);
			lb_ccn.setDisable(true);
			lb_ccn_msg.setDisable(true);
			txt_ccn.setDisable(true);
			lb_exp.setDisable(true);
			lb_exp_msg.setDisable(true);
			txt_exp.setDisable(true);
			lb_order_no_tit.setDisable(false);
			lb_order_no.setDisable(false);
			lb_order_time.setDisable(false);
			lb_order_time_hh.setDisable(false);
			txt_order_time_hh.setDisable(false);
			lb_order_time_mm.setDisable(false);
			txt_order_time_mm.setDisable(false);
			dt_order_date.setDisable(false);
			bt_submit.setDisable(false);
			try {
				int i = 0;
				int j = 0;
				// get latest order id
				int ordId = OrderTable.chkMaxOrderId();
				// add order in DB
				Orders ord = new Orders(ordId, usr_str, "O", null, 0, 0, ccn_str, exp_str, cvv_str, cook_time);
				i = OrderTable.addOrder(ord);
				if(usr_vip & redeem_points <= 0) {
					// update user credit points without credit points redeem
					UserTable.updVipCredit(usr_str,credit_points+total_amount);
				} else if (usr_vip & redeem_points > 0) {
					// update user credit points with credit points redeem
					UserTable.updVipCredit(usr_str,credit_points+total_amount-redeem_points);					
				}
				// add order details in DB
				for (OrderBasket bkt: basketList) {
					OrderDetails ordDet = new OrderDetails(null, ordId, bkt.getItem_name(), Double.parseDouble(bkt.getItem_pri()), Integer.parseInt(bkt.getItem_qty()), Double.parseDouble(bkt.getItem_amt()));
					j = OrderDetailTable.addOrderDtl(ordDet);
					if (bkt.getItem_name() == "Fries") {
						Items itemDetail = ItemTable.selItems(bkt.getItem_name());
						int remSer = itemDetail.getRemaining_serves();
						int fireCookQty = getFiresActualQuantityCooked(Integer.parseInt(bkt.getItem_qty()), remSer);
						int newRemSer = fireCookQty-Integer.parseInt(bkt.getItem_qty());
						lb_rmin_ser.setText(newRemSer + " serves of fries left for next order");
						// update item remaining_serves in DB
						ItemTable.updRemSer(bkt.getItem_name(), rem_ser);
					}
				}
				if ((i > 0) & (j > 0)) {
					order_id = ordId;
					lb_order_no.setText(Integer.toString(ordId));
				}
 
				// to specify a “fake” time at which the order is placed.
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	// Event Listener on Button[#bt_submit].onAction
	@FXML
	public void bt_submit_act(ActionEvent event) {
		String orderTimeHhStr=txt_order_time_hh.getText();
		String orderTimeMmStr=txt_order_time_mm.getText();
		LocalDate orderDate = dt_order_date.getValue();
		int invalidCounter = 0;
		
		// input filed validation - datetime & business time
		invalidCounter = CommonOperation.dateValidation(orderTimeHhStr, orderTimeMmStr, orderDate, "Update Order Time Failed");
		
		if (invalidCounter == 0) {
			try {
				//update order time to DB
				int n = OrderTable.updOrdTime(order_id, orderDate.toString(), Integer.parseInt(orderTimeHhStr), Integer.parseInt(orderTimeMmStr));
				if (n > 0) {
	        		// place order complete
	        		Alert msg = new Alert(Alert.AlertType.INFORMATION);
	                msg.setTitle("Order Creation Complete");
	                msg.setHeaderText("Sucess");
	                msg.setContentText("Order Added. Back to Main Menu.");            
	                msg.showAndWait();
	                // go to Main Menu page
	                try {
	                	BorderPane parentContent = FXMLLoader.load(getClass().getResource(("/application/view/fxml/MainMenu.fxml")));
	                	border_pane.getChildren().setAll(parentContent);
	                }catch(IOException e){
	                    System.out.println(e);
	                }
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
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
        // go to Order Collect page
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
        // go to Order Cancel page
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
        // go to Account page
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
