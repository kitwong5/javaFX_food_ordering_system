package application.customcls;

import java.util.ArrayList;


public class BasketList {
	private ArrayList<OrderBasket> basketList = new ArrayList<OrderBasket>();
	
	private final static BasketList INSTANCE = new BasketList();
	
	private BasketList() {}
	
	public static BasketList getInstance() {
	    return INSTANCE;
	  }
	
    //get basketList 
    public ArrayList<OrderBasket> getBasketList() {
        return basketList;
    }

    //set basketList
    public void setBasketList(ArrayList<OrderBasket> basketList) {
        this.basketList = basketList;
    }

}
