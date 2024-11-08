package application.customcls;

public class OrderBasket {
	private String item_name = null;
	private String item_pri = null;
	private String item_qty = null;
	private String item_amt = null;
	
	private final static OrderBasket INSTANCE = new OrderBasket();
	
	public OrderBasket() {}
	
    public OrderBasket(String item_name,   
    		String item_pri, 
    		String item_qty, 
    		String item_amt) {
        this.item_name = item_name;
        this.item_pri = item_pri;
        this.item_qty = item_qty;
        this.item_amt = item_amt;
    }
	
	public static OrderBasket getInstance() {
	    return INSTANCE;
	  }
	
    //get item_name 
    public String getItem_name() {
        return item_name;
    }
    //get item_qty 
    public String getItem_qty() {
        return item_qty;
    }
    //get item_pri 
    public String getItem_pri() {
        return item_pri;
    }
    //get item_amt 
    public String getItem_amt() {
        return item_amt;
    }
    //set item_name
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    //set item_qty
    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }
    //set item_pri
    public void setItem_pri(String item_pri) {
        this.item_pri = item_pri;
    }
    //set item_amt
    public void setItem_amt(String item_amt) {
        this.item_amt = item_amt;
    }

}
