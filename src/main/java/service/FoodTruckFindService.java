package service;

import model.FoodTruck;

import java.util.List;

public interface FoodTruckFindService {
    List<FoodTruck> getAvailableFoodTruckOpenNow() throws Exception;
}
