package application.customcls;

public class Items {
	private String item_id;
	private String item_name;
    private Double price;
    private Double setup_time;
    private Double cook_time;
    private String meal_set;
    private Integer remaining_serves;
    
    public Items() {
    }

    public Items(String item_id,
    		String item_name,
    		Double price,
    		Double setup_time,
    		Double cook_time,
    		String meal_set,
    		Integer remaining_serves) {
    	this.item_id = item_id;
    	this.item_name = item_name;
    	this.price = price;
    	this.setup_time = setup_time;
    	this.cook_time = cook_time;
    	this.meal_set = meal_set;
    	this.remaining_serves = remaining_serves;
    }

    //get item_Id 
    public String getItem_id() {
        return item_id;
    }
    //set item_id
    public void setItem_id(String item_id) {
        this.item_id = item_id;
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
    //get setup_time 
    public Double getSetup_time() {
        return setup_time;
    }
    //set setup_time
    public void setSetup_time(Double setup_time) {
        this.setup_time = setup_time;
    }
    //get cook_time 
    public Double getCook_time() {
        return cook_time;
    }
    //set cook_time
    public void setCook_time(Double cook_time) {
        this.cook_time = cook_time;
    }
    //get meal_set  
    public String getMeal_set () {
        return meal_set ;
    }
    //set meal_set 
    public void setMeal_set (String meal_set) {
        this.meal_set  = meal_set ;
    }
    //get remaining_serves 
    public Integer getRemaining_serves() {
        return remaining_serves;
    }
    //set cook_time
    public void setRemaining_serves(Integer remaining_serves) {
        this.remaining_serves = remaining_serves;
    }

}
