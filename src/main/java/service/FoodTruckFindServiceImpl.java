package service;

import model.FoodTruck;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FoodTruckFindServiceImpl implements FoodTruckFindService {

    private static final String SFGOV_FOOD_TRUCK_URL = "http://data.sfgov.org/resource/bbb8-hzi6.json?dayofweekstr=";
    private Date now =  new Date();
    private final String PATTERN = "HH:mm";

    @Override
    public List<FoodTruck> getAvailableFoodTruckOpenNow() throws Exception {
        List<FoodTruck> foodTrucksOpenToday = getAvailableFoodTruckForToday();
        List<FoodTruck> availableFoodTruckOpenNow = new ArrayList<>();

        //changing today's time format to match food truck's opening and closing time.
        String nowString = new SimpleDateFormat(PATTERN).format(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        Date timeNow = simpleDateFormat.parse(nowString);

        for(FoodTruck foodTruck : foodTrucksOpenToday) {
            Date open = simpleDateFormat.parse(foodTruck.getOpeningTime());
            Date close = simpleDateFormat.parse(foodTruck.getClosingTime());

            if (open.compareTo(timeNow) < 0 && close.compareTo(timeNow) > 0){
                availableFoodTruckOpenNow.add(foodTruck);
            }
        }

        return availableFoodTruckOpenNow;
    }


    private List<FoodTruck> getAvailableFoodTruckForToday() throws Exception {
        JSONArray foodTruckJsonArray = getFoodTruckJsonArray();
        List<FoodTruck> foodTrucks = new ArrayList<>();
        for (int i = 0; i < foodTruckJsonArray.length(); i++){
            JSONObject foodTruckJson = foodTruckJsonArray.getJSONObject(i);
            foodTrucks.add(new FoodTruck(foodTruckJson));
        }
        sortByName(foodTrucks);
        return foodTrucks;
    }

    private JSONArray getFoodTruckJsonArray() throws Exception {
        String foodTrucks = apiCallGetFoodTruckForToday();

        if(StringUtils.isBlank(foodTrucks)){
            System.out.println("=======================================");
            System.out.println("  No Food Truck Info is available");
            System.out.println("=======================================");

            throw new Exception("Received empty info for getFoodTruckJsonArray()");
        }

        return new JSONArray(foodTrucks);
    }

    private String apiCallGetFoodTruckForToday() throws MalformedURLException {
        URL url = new URL(SFGOV_FOOD_TRUCK_URL + getDayOfWeek(now));
        HttpURLConnection conn = null;
        StringBuilder result = new StringBuilder();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode / 100 == 2) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("cannot close HttpUrLConnection.");
                }
            }
        }

        return result.toString();
    }

    private String getDayOfWeek(Date date){
        return new SimpleDateFormat("EEEE").format(date);
    }

    private List<FoodTruck> sortByName(List<FoodTruck> foodTrucks){
        Collections.sort(foodTrucks, (o1, o2) -> (o1.getName().compareTo(o2.getName())));
        return foodTrucks;
    }
}
