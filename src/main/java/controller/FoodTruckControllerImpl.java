package controller;

import model.FoodTruck;
import service.FoodTruckFindService;
import service.FoodTruckFindServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class FoodTruckControllerImpl implements  FoodTruckController {
    private FoodTruckFindService foodTruckFindService = new FoodTruckFindServiceImpl();
    private Scanner scanner = new Scanner(System.in);

    // get food trucks that are operating NOW.
    public void getFoodTruckOpenNow() throws Exception {
        List<FoodTruck> foodTrucks = foodTruckFindService.getAvailableFoodTruckOpenNow();
        displayResult(foodTrucks, 10);
        askUserAndDisplayHours(foodTrucks);

    }

    private void displayResult(List<FoodTruck> foodTrucks, int numberOfItemsPerPage) throws IOException {
        System.out.println("==============================================================================================================");
        System.out.printf("%4s %-75s %-40s %n",   "", "|  NAME", "|  ADDRESS");
        System.out.println("==============================================================================================================");


        for(int i = 0; i < foodTrucks.size(); i++){
            System.out.printf("%3d. %-75s %-40s %n",  i + 1, "| " + foodTrucks.get(i).getName(), "| " + foodTrucks.get(i).getLocation());

            if((i+1)% numberOfItemsPerPage == 0){
                System.out.println("==============================================================================================================");
                System.out.println("\033[0m Press Enter to See More \033[34m");
                System.in.read();
            }
        }
    }

    //Extra function : in case User wants to know when the food truck closes.
    private void askUserAndDisplayHours(List<FoodTruck> foodTrucks){
        if (foodTrucks.size() != 0) {
            System.out.print("\n\033[0mDo you want to see Operating Hours of a Food Truck?  Y/N : \033[0m");
            String answer = null;
            int attemps = 0;

            while (attemps < 4) {
                answer = scanner.next().toLowerCase();
                if(answer.equals("n") || answer.equals("y")) {
                    break;
                }
                attemps ++;
                if (attemps < 4) {
                    System.out.print("Please Enter \033[31mY\033[0m or \033[31mN\033[0m: ");
                }
            }
            if(answer.equals("y")){
                getOperatingHours(foodTrucks);
                askUserAndDisplayHours(foodTrucks);
            }
            else if(answer.equals("n")){
                System.exit(0);
            }
        }
    }

    private void getOperatingHours(List<FoodTruck> foodTrucks) {
        System.out.print("\nType the number of the food truck: ");
        int truckNumber = 0;
        int attemps = 0;
        while (attemps < 4) {
            try {
                Scanner sc = new Scanner(System.in);
                truckNumber = sc.nextInt();
                if (truckNumber > 0 && truckNumber <= foodTrucks.size()) {
                    printOperatingHours(foodTrucks, truckNumber);
                    break;
                }
                attemps++;
                if (attemps < 4) {
                    System.out.print("Please Enter between \033[31m" + 1 + "\033[0m and \033[31m" + foodTrucks.size() + "\033[0m: " );
                }
            } catch (Exception e) {
                attemps++;
                if (attemps < 4) {
                    System.out.print("Please Enter the \033[31mNumber\033[0m of Food Truck: ");
                }
                continue;
            }
        }
    }

    private void printOperatingHours(List<FoodTruck> foodTrucks, int truckNumber){
        FoodTruck foodTruck = foodTrucks.get(truckNumber - 1);

        String name = foodTruck.getName();
        String address = foodTruck.getLocation();
        String start = foodTruck.getOpeningTime();
        String end = foodTruck.getClosingTime();

        System.out.println( "\033[31m" + name + " \033[34m" + address +"\033[0m : operating hours are " + start + " to " + end);
    }
}
