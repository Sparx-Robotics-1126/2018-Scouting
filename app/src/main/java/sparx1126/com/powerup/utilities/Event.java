package sparx1126.com.powerup.utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Papa on 1/27/18.
 */

public class Event {
    public static String END_DATE = "end_date";
    public static String KEY = "key";
    public static String LOCATION = "location_name";
    public static String NAME = "name";
    public static String START_DATE = "start_date";
    public static String WEEK = "week";

    String name;
    String startdate;
    String end_date;
    String wk;

    public String getName() {
        return name;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getWk() {
        return wk;
    }

    public String getLocation() {
        return location;
    }

    public String getKey() {
        return key;
    }

    String location;
    String key;

    public Event(JSONObject eventObj) {
        try {
            name = eventObj.getString(NAME);
            location = eventObj.getString(LOCATION);
            key = eventObj.getString(KEY);
            end_date = eventObj.getString(END_DATE);
            startdate= eventObj.getString(START_DATE);
            wk= eventObj.getString(WEEK);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
