package application.customcls;

public class OrderSummary {
	private String order_id = null;
	private String order_date = null;
	private String order_time = null;
	private String order_items = null;
	private String order_total = null;
	private String order_status = null;

	public OrderSummary() {}
	
    public OrderSummary(String order_id,
    		String order_date, 
    		String order_time,     		
    		String order_items, 
    		String order_total, 
    		String order_status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_items = order_items;
        this.order_total = order_total;
        this.order_status = order_status;
    }
    //get order_id 
    public String getOrder_id() {
        return order_id;
    }
    //set order_id
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    //get order_date 
    public String getOrder_date() {
        return order_date;
    }
    //set order_date
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
    //get order_time
    public String getOrder_time() {
        return order_time;
    }
    //set order_time
    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
    //get order_items 
    public String getOrder_items() {
        return order_items;
    }
    //set order_items
    public void setOrder_items(String order_items) {
        this.order_items = order_items;
    }
    //get order_total 
    public String getOrder_total() {
        return order_total;
    }
    //set order_total
    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }
    //get order_status 
    public String getOrder_status() {
        return order_status;
    }
    //set order_status
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

}
