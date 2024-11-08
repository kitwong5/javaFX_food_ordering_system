
/********************************************************/
/* COSC1295 Assignment 2 Burrito Order System Unit Test */
/* Student Name: Kit Yi Wong                			*/
/* Student ID: s3970390									*/
/* 														*/
/* Date: 17/05/2024										*/
/* 														*/
/* Version: 1.0											*/
/*********************************************************/

package test;

import org.junit.*;

import application.Main;
import application.customcls.BasketList;
import application.customcls.OrderBasket;
import application.view.controller.CommonOperation;
import application.view.controller.PlaceBasketController;
import application.view.controller.ValidateInputController;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class MainTester {
	private static final double DELTA = 1e-15;
	
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Test
	public void testPlaceBasketController() {
		String expectResultStr;
		String actualValueStr;
		double expectResultDb;
		boolean usr_vipIn; 
		PlaceBasketController  PlaceBasketControllerTester = new PlaceBasketController();
		// Test Order Total Cost - VIP User
		ArrayList<OrderBasket> orderBasketList=new ArrayList<>();
		OrderBasket bskItem1 = new OrderBasket("Burrito", "7", "2", "14");
		orderBasketList.add(bskItem1);   
		OrderBasket bskItem2 = new OrderBasket("Fries", "4", "2", "8");
		orderBasketList.add(bskItem2);   
		OrderBasket bskItem3 = new OrderBasket("Soda", "2.5", "2", "5");
		orderBasketList.add(bskItem3);   
		OrderBasket bskItem4 = new OrderBasket("Meal", "10.5", "2", "21");
		orderBasketList.add(bskItem4);   		 	
		usr_vipIn = true;
		expectResultDb = 42.0;
		assertEquals(expectResultDb, PlaceBasketController.clacTotal(orderBasketList, usr_vipIn), DELTA);
		
		// Test Order Total Cost - Non VIP User
		usr_vipIn = false;
		expectResultDb = 48.0;
		assertEquals(expectResultDb, PlaceBasketController.clacTotal(orderBasketList, usr_vipIn), DELTA);
		
		// Test Prepare Time - Burrito test every 2 serve time
		int quantityIn = 3;
		int remainingServesIn = 0;
		expectResultDb = 18;
		assertEquals(expectResultDb, PlaceBasketController.cookTimeForBurritos(quantityIn), DELTA);

		// Test Prepare Time - Burrito test single serve time
		quantityIn = 1;
		remainingServesIn = 0;
		expectResultDb = 9;
		assertEquals(expectResultDb, PlaceBasketController.cookTimeForBurritos(quantityIn), DELTA);

		
		// Test Prepare Time - Fries without remaining servers
		quantityIn = 1;
		remainingServesIn = 0;
		expectResultDb = 8;
		assertEquals(expectResultDb, PlaceBasketController.cookTimeForFries(quantityIn, remainingServesIn), DELTA);
		
		// Test Prepare Time - Fries with remaining servers
		quantityIn = 1;
		remainingServesIn = 2;
		expectResultDb = 0;
		assertEquals(expectResultDb, PlaceBasketController.cookTimeForFries(quantityIn, remainingServesIn), DELTA);

		
		// Test credit point - Non VIP User
		//expectResultDb = 0;
		//assertEquals(expectResultDb, PlaceBasketController.display_credit_point("testCr"), DELTA);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidateInputController() {
		String expectResultStr;
		String actualValueStr;
		String txt;
		Integer length_limit;
		boolean expectResultBl;
		ValidateInputController  ValidateInputControllerTester = new ValidateInputController();
		// Test is input text float
		actualValueStr = "xx";
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isFloat(actualValueStr));

		// Test is input text float
		actualValueStr = "1.0";
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isFloat(actualValueStr));

		
		// Test is input text integer
		actualValueStr = "1.2";
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isInt(actualValueStr));

		// Test is input text integer included 0 value
		actualValueStr = "0";
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isInt0(actualValueStr));

		// Test is string within length limit
		txt = "0123456789";
		length_limit = 8;
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isWitinLegth(txt,length_limit));
		
		// Test is string at least certain length 
		txt = "12345678";
		length_limit = 8;
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isAtLeastLegth(txt,length_limit));
		
		// Test is string equal exact length 
		txt = "12345678";
		length_limit = 8;
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isNotEqualLegth(txt,length_limit));
		
		// Test is credit card number 16 digit 
		txt = "12345678901234567";
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isValidCardNumber(txt));
		
		// Test is credit card number 16 digit 
		txt = "1234567890123456";
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isValidCardNumber(txt));

		
		// Test is email valid 
		txt = "xxx";
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isEMail(txt));

		// Test is email valid 
		txt = "xxx.gmail.com";
		expectResultBl = false;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isEMail(txt));
		
		// Test is email valid
		txt = "xxx@gmail.com";
		expectResultBl = true;	
		assertEquals(expectResultBl, ValidateInputControllerTester.isEMail(txt));

	}

	
	@SuppressWarnings("static-access")
	@Test
	public void testCommonOperation() {
		String expectResultStr;
		String actualValueStr; 
		CommonOperation  CommonOperationTester = new CommonOperation();
		// Test padTime
		actualValueStr = "9";
		expectResultStr = "09";	
		
		assertEquals(expectResultStr, CommonOperationTester.padTime(actualValueStr));
		actualValueStr = "10";
		expectResultStr = "10";	
		assertEquals(expectResultStr, CommonOperationTester.padTime(actualValueStr));

	}
	
}
