package application.customcls;

public class Orders {
	private Integer order_id;
	private String username;
	private String order_status;
	private String order_date;
	private Integer order_time_hh;
	private Integer order_time_mm;
	private String credit_card_no;
	private String expire_dt;
	private String cvv;
	private Double cook_time;
	
    public Orders() {
    }

    public Orders(Integer order_id,
    		String username,
    		String order_status,
    		String order_date,
    		Integer order_time_hh,
    		Integer order_time_mm,
    		String credit_card_no,
    		String expire_dt,
    		String cvv,
    		Double cook_time) {
    	this.order_id = order_id;
    	this.username  = username;
    	this.order_status = order_status;
    	this.order_date = order_date;
    	this.order_time_hh = order_time_hh;
    	this.order_time_mm = order_time_mm;
    	this.credit_card_no = credit_card_no;
    	this.expire_dt = expire_dt;
    	this.cvv = cvv;
    	this.cook_time = cook_time;
    }

    //get order_id 
    public Integer getOrder_id() {
        return order_id;
    }
    //set order_id
    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
    //get username 
    public String getUsername () {
        return username ;
    }
    //set username 
    public void setUsername (String username ) {
        this.username  = username ;
    }
    //get order_status
    public String getOrder_status() {
        return order_status;
    }
    //set order_status
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
    //get order_date
    public String getOrder_date() {
        return order_date;
    }
    //set order_date
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
    //get order_time_hh
    public Integer getOrder_time_hh() {
        return order_time_hh;
    }
    //set order_time_hh
    public void setOrder_time_hh(Integer order_time_hh) {
        this.order_time_hh = order_time_hh;
    }
    //get order_time_mm
    public Integer getOrder_time_mm() {
        return order_time_mm;
    }
    //set order_time_mm
    public void setOrder_time_mm(Integer order_time_mm) {
        this.order_time_mm = order_time_mm;
    }
    //get credit_card_no
    public String getCredit_card_no() {
        return credit_card_no;
    }
    //set credit_card_no
    public void setCredit_card_no(String credit_card_no) {
        this.credit_card_no = credit_card_no;
    }
    //get expire_dt
    public String getExpire_dt() {
        return expire_dt;
    }
    //set expire_dt
    public void setExpire_dt(String expire_dt) {
        this.expire_dt = expire_dt;
    }
    //get cvv
    public String getCvv() {
        return cvv;
    }
    //set cvv
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    //get cook_time
    public Double getCook_time() {
        return cook_time;
    }
    //set cook_time
    public void setCook_time(Double cook_time) {
        this.cook_time = cook_time;
    }

    
}
