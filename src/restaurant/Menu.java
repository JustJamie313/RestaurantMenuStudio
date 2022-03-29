package restaurant;

import java.util.ArrayList;
import java.util.Date;

public class Menu {
    Date lastUpdated;

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    ArrayList<MenuItem> menuItems = new ArrayList<>();

    public Menu() {
        this.lastUpdated = new Date();
    }
    public Menu(Date lastUpdated,ArrayList<MenuItem> menu){
        this.lastUpdated = lastUpdated;
        this.menuItems = menu;
    }
    public void addMenuItem(Double price, String description, String category, Boolean isNew){
        this.menuItems.add(new MenuItem(price, description, category, isNew));
        updateDateUpdated();
    }
    public void removeMenuItem(String description){
        if(this.menuItems.contains(description)){
            this.menuItems.remove(description);
            updateDateUpdated();
        }
    }
    private void updateDateUpdated(){
        this.lastUpdated = new Date();
    }
}
