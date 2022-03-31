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
    //changed menuItems from ArrayList to HashMap and then to TreeMap
    //Why?
    //Using a Map instead of an ArrayList allows me to store the menu items in separate ArrayLists by category
    //Using a TreeMap instead of a HashMap because a TreeMap is naturally sorted and HashMap is not.
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
    //create empty menu with no menu items
    public Menu(){
        this.setLastUpdated(new Date());
    }
    //create menu by passing in TreeMap
    public Menu(TreeMap<String,ArrayList<MenuItem>> menuMap){
        this.setLastUpdated(new Date());
        this.menuItems = menuMap;
    }
    //create menu from CSV file
    public Menu(String pathToMenuCSV){
        this.setLastUpdated(new Date());
        this.setPathToMenu(pathToMenuCSV);
        //call method that will read menu file into menuItems
        buildMenuFromFile();
    }
    public void addMenuItem(Double price, String description, String category,Boolean isNew){
        this.setLastUpdated(new Date());
        //store parameter data into newMenuItem
        MenuItem newMenuItem = new MenuItem(price, description, category, isNew);
        //create arrayList to store our new menu item
        ArrayList<MenuItem> newArrayList = new ArrayList<>();
        //add our newMenuItem to our newArrayList
        newArrayList.add(newMenuItem);

        //if menuItems is empty then it can't be duplicate - add and exit
        if(this.menuItems.isEmpty()){
            //put newArrayList into category key of menuItems
            this.menuItems.put(category,newArrayList);
            //write addition to CSV file
            saveMenuToCSV();
            return;
        }

        //if category key isn't found in menuItems it can't be duplicate - add and exit
        if(!this.menuItems.containsKey(category)){
            //put newArrayList into category key of menuItems
            this.menuItems.put(category,newArrayList);
            //write addition to CSV file
            saveMenuToCSV();
            return;
        }

        //if menuItems not empty AND contains category key but value of key does not contain newMenuItem
        if(!this.menuItems.get(category).contains(newMenuItem)){
            //get currentList of menuItems for category
            ArrayList<MenuItem> currentList = this.menuItems.get(category);
            //add newMenuItem to currentList
            currentList.add(newMenuItem);
            //put updated currentList back into category key
            this.menuItems.put(category,currentList);
            //write updated list to CSV file
            saveMenuToCSV();
            return;
        }
        //if new item doesn't get added above - then it IS a duplicate and will not be added
        System.out.println("Menu already contains "+description);
    }
    public void removeMenuItem(String itemDescription){
        this.setLastUpdated(new Date());
        //loop through the keys in menuItems TreeMap
        for(Map.Entry<String,ArrayList<MenuItem>> entry : this.menuItems.entrySet()){
            //for each key - loop through the ArrayList of menuItems
            for(MenuItem item: entry.getValue()){
                //for each item, determine if the descriptions match (if yes, same item)
                if(item.getDescription().equals(itemDescription)){
                    //get currentList of MenuItems
                    ArrayList<MenuItem> currentList = entry.getValue();
                    //remove the matching item
                    currentList.remove(item);
                    break;
                }
            }
        }
        //write updated menu to CSV
        saveMenuToCSV();
    }
    public void displayCompleteMenu(){
        //loop through all keys in menuItems
        for(Map.Entry<String,ArrayList<MenuItem>> entry: menuItems.entrySet()){
            //output key(category)
            System.out.println(entry.getKey().toUpperCase());
            //sort category ArrayList by price (low to high)
            entry.getValue().sort(Comparator.comparing(MenuItem::getPrice));
            //loop through each of the menuItems
            for(MenuItem item:entry.getValue()){
                //determine if newItem
                Boolean newItem = item.getNew();
                if(newItem){
                    //if new - add ***NEW*** flag to end
                    System.out.println("$"+item.getPrice()+"\t"+item.getDescription() + " ***NEW***");
                } else {
                    //not new - just display price and description
                    System.out.println("$"+item.getPrice()+"\t"+item.getDescription());
                }
            }
        }
        //output last updated
        System.out.println("Menu Last Updated: "+this.getLastUpdated());
    }
    public void displayMenuItem(String description){
        //loop through all keys in menuItems
        for(Map.Entry<String,ArrayList<MenuItem>> entry:this.menuItems.entrySet()){
            //loop through ArrayList of MenuItems for each key
            for(MenuItem item:entry.getValue()){
                //check if item description matches parameter description
                if(item.getDescription().equals(description)){
                    //output matching item
                    System.out.println(item);
                }
            }
        }
    }
    private void buildMenuFromFile(){
        //create file object for our menu
        File file = new File(pathToMenu);
        //clear the current menuItems map;
        menuItems = new TreeMap<>();
        //try/catch FileNotFoundException
        try{
            //use Scanner to read file
            Scanner readFile = new Scanner(file);
            //if file is empty
            if(!readFile.hasNext()){
                System.out.println("Menu Empty");
            }
            //if file is not empty
            while(readFile.hasNext()){
                //split CSV's into array
                String[] splitLine = readFile.nextLine().split(",");
                //add item to menu using values from splitArray
                this.addMenuItem(Double.parseDouble(splitLine[0]),splitLine[1],splitLine[2],Boolean.parseBoolean(splitLine[3]));
            }
            //close Scanner
            readFile.close();
        } catch(FileNotFoundException e){
            //output if file is missing
            System.out.println("Menu File Not Found");
        }
    }
    private void saveMenuToCSV(){
        //try/catch IOException
        try{
            //Instantiate FileWriter for output file
            FileWriter outputFile = new FileWriter(pathToMenu);
            //use StringBuilder to generate output string
            StringBuilder sb = new StringBuilder();
            //loop through keys in menuItems
            for(Map.Entry<String,ArrayList<MenuItem>> entry : this.menuItems.entrySet()){
                //loop through items in key.value
                for(MenuItem item:entry.getValue()){
                    //append item details to StringBuilder
                    sb.append(item.getPrice()).append(",").append(item.getDescription()).append(",").append(item.getCategory()).append(",").append(item.getNew()).append("\n");
                }
            }
            //write string to output file
            outputFile.write(sb.toString());
            //clear the buffer
            outputFile.flush();
            //close the FileWriter
            outputFile.close();
        } catch(IOException e){
            //output if unable to save menu to CSV file
            System.out.println("Unable to write to: "+pathToMenu);
        }
    }
}
