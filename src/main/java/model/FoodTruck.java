package model;

import consts.FoodTruckJsonKeys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class FoodTruck {

    private String name;
    private String location;
    private String dayOfWeek;
    private String closingTime;
    private String openingTime;

    public FoodTruck(JSONObject jsonObject) throws JSONException {
        this.dayOfWeek = (String) jsonObject.get(FoodTruckJsonKeys.DAY_OF_WEEK_STR);
        this.name = (String) jsonObject.get(FoodTruckJsonKeys.NAME);
        this.location = (String) jsonObject.get(FoodTruckJsonKeys.LOCATION);
        this.closingTime = (String) jsonObject.get(FoodTruckJsonKeys.CLOSING_TIME);
        this.openingTime = (String) jsonObject.get(FoodTruckJsonKeys.OPEN_TIME);
    }
}
