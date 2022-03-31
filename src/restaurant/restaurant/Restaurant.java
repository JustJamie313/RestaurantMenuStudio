package restaurant;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        //display restaurant information from file
        System.out.println(getRestaurantInfo("src/resources/launchCafe.txt"));
        //Instantiate Menu class with data from file
        Menu menu = new Menu("src/resources/menu.csv");
        //Why?
        //So my data isn't hardcoded into my program

        //display the full menu
        menu.displayCompleteMenu();

        //add item to menu
        menu.addMenuItem(1.99,"Houses Salad","appetizer",false);

        //attempt to add same item a second time - outputs "Menu already contains <item description>"
        menu.addMenuItem(1.99,"Houses Salad","appetizer",false);

        //display full menu again to verify item added only once
        menu.displayCompleteMenu();

        //display single menu item
        menu.displayMenuItem("Apple Pie");

        //remove previously added menu item
        menu.removeMenuItem("Houses Salad");

        //display full menu again to verify item removed
        menu.displayCompleteMenu();

    }
    private static String getRestaurantInfo(String pathToInfo){
        //Instantiate File class with path to restaurant info
        File restaurantInfo = new File(pathToInfo);
        //create a string variable to store our restaurant data
        String restaurantData;
        //used try/catch to handle FileNotFoundException
        try{
            //Instantiate Scanner to read the data from file
            Scanner readFile = new Scanner(restaurantInfo);
            //Instantiate StringBuilder to store the data as we read each line
            //Why?
            //IntelliJ IDE was giving "String concatenation in loop" warning
            StringBuilder sb = new StringBuilder();
            //while we haven't reached the end of the file ...
            while(readFile.hasNext()){
                //...append line to StringBuilder variable followed by newline
                sb.append(readFile.nextLine()).append("\n");
            }
            //assign value of sb to restaurantData
            restaurantData = sb.toString();
            //close Scanner
            readFile.close();
        } catch(FileNotFoundException e){
            //if program cannot find file - output message;
            System.out.println("Restaurant Info missing");
            //return (String) "false" and exit method
            return "false";
        }
        //return (String) restaurantData
        return restaurantData;
    }
}
