package application.customcls;

public class User {
	private User user;
	private String user_id;
	private String username;
    private String user_pwd;
    private String first_name;
    private String last_name;
    private String vip_user;
    private Double vip_credit;
    private String rece_promotions;
    private String user_email;
    private Double redeem_points;
    
    private final static User INSTANCE = new User();
    
    public User() {
    }
    
    public User(String user_id, 
    		String username, 
    		String user_pwd, 
    		String first_name, 
    		String last_name, 
    		String vip_user, 
    		Double vip_credit, 
    		String rece_promotions, 
    		String user_email) {
        this.user_id = user_id;
        this.username = username;
        this.user_pwd = user_pwd;
        this.first_name = first_name;
        this.last_name = last_name;
        this.vip_user = vip_user;
        this.vip_credit = vip_credit;
        this.rece_promotions = rece_promotions;
        this.user_email = user_email;        
    }


    public static User getInstance() {
        return INSTANCE;
      }

    
    //get user_Id 
    public String getUser_id() {
        return user_id;
    }
    //get user name 
    public String getUsername() {
        return username;
    }
    //get user_pwd
    public String getUser_pwd() {
        return user_pwd;
    }
    //get first_name
    public String getFirst_name() {
        return first_name;
    }
    //get first_name
    public String getLast_name() {
        return last_name;
    }
    //get vip_user
    public String getVip_user() {
        return vip_user;
    }
    //get vip_credit
    public Double getVip_credit() {
        return vip_credit;
    }
    //get rece_promotions
    public String getRece_promotions() {
        return rece_promotions;
    }
    //get user_email
    public String getUser_email() {
        return user_email;
    }
    //set user_id
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    //set username
    public void setUsername(String username) {
        this.username = username;
    }
    //set user_pwd
    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
    //set first_name
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    //set last_name
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    //set vip_user
    public void setVip_user(String vip_user) {
        this.vip_user = vip_user;
    }
    //set vip_credit
    public void setVip_credit(Double vip_credit) {
        this.vip_credit = vip_credit;
    }
    //set rece_promotions
    public void setRece_promotions(String rece_promotions) {
        this.rece_promotions = rece_promotions;
    }
    //set user_email
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    //set redeem_points
    public void setRedeem_points(Double redeem_points) {
        this.redeem_points = redeem_points;
    }
    //get redeem_points
    public Double getRedeem_points() {
        return redeem_points;
    }
}
