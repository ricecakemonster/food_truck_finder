import controller.FoodTruckControllerImpl;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Date now =  new Date();
        String day = new SimpleDateFormat("EEEE").format(now).toUpperCase();
        String timeNow = new SimpleDateFormat("MM/dd hh:mm a").format(now);
        FoodTruckControllerImpl foodTruckController = new FoodTruckControllerImpl();
        System.out.println("\n\n");
        System.out.println("==============================================================================================================\n");
        System.out.println("\033[34m    WELCOME TO SAN FRANCISCO FOOD TRUCK FINDER\n\033[0m");
        System.out.println("==============================================================================================================\n");

        System.out.println("\n\033[31m FOOD TRUCKS OPEN TODAY " + day + " " + timeNow + "\033[34m");
        foodTruckController.getFoodTruckOpenNow();
    }
}
