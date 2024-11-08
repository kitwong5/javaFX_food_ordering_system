package application.customcls;

public class OrderDetails {
	private Integer order_detail_id;
	private Integer order_id;
	private String item_name;
	private Double price;
	private Integer quantity;
	private Double amount;

    public OrderDetails() {
    }

    public OrderDetails(Integer order_detail_id,
    		Integer order_id,
    		String item_name,
    		Double price,
    		Integer quantity,
    		Double amount) {
    	this.order_detail_id = order_detail_id;
    	this.order_id = order_id;
    	this.item_name = item_name;
    	this.price = price;
    	this.quantity = quantity;
    	this.amount = amount;
    }
    
    //get order_detail_id 
    public Integer getOrder_detail_id() {
        return order_detail_id;
    }
    //set order_id
    public void setOrder_detail_id(Integer order_detail_id) {
        this.order_detail_id = order_detail_id;
    }
    //get order_id 
    public Integer getOrder_id() {
        return order_id;
    }
    //set order_id
    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
    //get item_name 
    public String getItem_name() {
        return item_name;
    }
    //set item_name
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    //get price 
    public Double getPrice() {
        return price;
    }
    //set price
    public void setPrice(Double price) {
        this.price = price;
    }
    //get quantity 
    public Integer getQuantity() {
        return quantity;
    }
    //set quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    //get amount 
    public Double getAmount() {
        return amount;
    }
    //set amount
    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
