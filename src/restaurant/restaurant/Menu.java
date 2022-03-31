package restaurant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Menu {
    //fields
    Date lastUpdated;
    TreeMap<String,ArrayList<MenuItem>> menuItems = new TreeMap<>();
    String pathToMenu;

    //getters
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public TreeMap<String, ArrayList<MenuItem>> getMenuItems() {
        return menuItems;
    }
    public String getPathToMenu() {
        return pathToMenu;
    }

    //setters
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setPathToMenu(String pathToMenu) {
        this.pathToMenu = pathToMenu;
    }
    public void setMenuItems(TreeMap<String, ArrayList<MenuItem>> menuItems) {
        this.menuItems = menuItems;
    }

    //constructors
    public Menu(){
        this.setLastUpdated(new Date());
    }
    public Menu(TreeMap<String,ArrayList<MenuItem>> menuMap){
        this.setLastUpdated(new Date());
        this.menuItems = menuMap;
    }
    public Menu(String pathToMenuCSV){
        this.setLastUpdated(new Date());
        this.setPathToMenu(pathToMenuCSV);
        buildMenuFromFile();
    }
    public void addMenuItem(Double price, String description, String category,Boolean isNew){
        this.setLastUpdated(new Date());
        MenuItem newMenuItem = new MenuItem(price, description, category, isNew);
        ArrayList<MenuItem> newArrayList = new ArrayList<>();
        newArrayList.add(newMenuItem);
        if(this.menuItems.isEmpty()){
            this.menuItems.put(category,newArrayList);
            saveMenuToCSV();
            return;
        }
        if(!this.menuItems.containsKey(category)){
            this.menuItems.put(category,newArrayList);
            saveMenuToCSV();
            return;
        }
        if(!this.menuItems.get(category).contains(newMenuItem)){
            ArrayList<MenuItem> currentList = this.menuItems.get(category);
            currentList.add(newMenuItem);
            this.menuItems.put(category,currentList);
            saveMenuToCSV();
            return;
        }
        System.out.println("Menu already contains "+description);
    }
    public void removeMenuItem(String itemDescription){
        this.setLastUpdated(new Date());
        for(Map.Entry<String,ArrayList<MenuItem>> entry : this.menuItems.entrySet()){
            for(MenuItem item: entry.getValue()){
                if(item.getDescription().equals(itemDescription)){
                    ArrayList<MenuItem> currentList = entry.getValue();
                    currentList.remove(item);
                    break;
                }
            }
        }
        saveMenuToCSV();
    }
    public void displayCompleteMenu(){
        for(Map.Entry<String,ArrayList<MenuItem>> entry: menuItems.entrySet()){
            System.out.println(entry.getKey().toUpperCase());
            entry.getValue().sort(Comparator.comparing(MenuItem::getPrice));
            for(MenuItem item:entry.getValue()){
                Boolean newItem = item.getNew();
                if(newItem){
                    System.out.println("$"+item.getPrice()+"\t"+item.getDescription() + " ***New***");
                } else {
                    System.out.println("$"+item.getPrice()+"\t"+item.getDescription());
                }
            }
        }
        System.out.println("Menu Last Updated: "+this.getLastUpdated());
    }
    public void displayMenuItem(String description){
        for(Map.Entry<String,ArrayList<MenuItem>> entry:this.menuItems.entrySet()){
            for(MenuItem item:entry.getValue()){
                if(item.getDescription().equals(description)){
                    System.out.println(item);
                }
            }
        }
    }
    private void buildMenuFromFile(){
        File file = new File(pathToMenu);
        menuItems = new TreeMap<>();
        try{
            Scanner readFile = new Scanner(file);
            if(!readFile.hasNext()){
                System.out.println("Menu Empty");
            }
            while(readFile.hasNext()){
                String[] splitLine = readFile.nextLine().split(",");
                this.addMenuItem(Double.parseDouble(splitLine[0]),splitLine[1],splitLine[2],Boolean.parseBoolean(splitLine[3]));
            }
            readFile.close();
        } catch(FileNotFoundException e){
            System.out.println("Menu File Not Found");
        }
    }
    private void saveMenuToCSV(){
        try{
            FileWriter outputFile = new FileWriter(pathToMenu);
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,ArrayList<MenuItem>> entry : this.menuItems.entrySet()){
                for(MenuItem item:entry.getValue()){
                    sb.append(item.getPrice()).append(",").append(item.getDescription()).append(",").append(item.getCategory()).append(",").append(item.getNew()).append("\n");
                }
            }
            outputFile.write(sb.toString());
            outputFile.flush();
            outputFile.close();
        } catch(IOException e){
            System.out.println("Unable to write to: "+pathToMenu);
        }
    }
}
