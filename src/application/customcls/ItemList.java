package application.customcls;

import java.util.ArrayList;

public class ItemList {
	private ArrayList<Items> itemList = new ArrayList<Items>();
	
	private final static ItemList INSTANCE = new ItemList();
	
	private ItemList() {}
	
	public static ItemList getInstance() {
	    return INSTANCE;
	  }
	
    //get basketList 
    public ArrayList<Items> getItemList() {
        return itemList;
    }

    //set basketList
    public void setItemList(ArrayList<Items> itemList) {
        this.itemList = itemList;
    }

}
