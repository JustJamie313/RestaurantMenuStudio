package restaurant;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        System.out.println(getRestaurantInfo("src/resources/launchCafe.txt"));
        Menu menu = new Menu("src/resources/menu.csv");
        menu.displayCompleteMenu();
        menu.addMenuItem(1.99,"Houses Salad","appetizer",false);
        menu.addMenuItem(1.99,"Houses Salad","appetizer",false);
        menu.displayMenuItem("Apple Pie");
        menu.displayCompleteMenu();
        menu.removeMenuItem("Houses Salad");
        menu.displayCompleteMenu();

    }
    private static String getRestaurantInfo(String pathToInfo){
        File restaurantInfo = new File(pathToInfo);
        String restaurantData;
        try{
            Scanner readFile = new Scanner(restaurantInfo);
            StringBuilder sb = new StringBuilder();
            while(readFile.hasNext()){
                sb.append(readFile.nextLine());
                sb.append("\n");
            }
            restaurantData = sb.toString();
            readFile.close();
        } catch(FileNotFoundException e){
            System.out.println("Restaurant Info missing");
            return "false";
        }
        return restaurantData;
    }
}
