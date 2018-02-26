package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class BlueAllianceEvent {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String WEEK = "week";
    private static final String LOCATION = "location_name";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    private final JSONObject jsonObj;
    private String key;
    private String name;
    private String week;
    private String location;
    private String startDate;
    private String endDate;

    public BlueAllianceEvent(JSONObject _jsonObj) {
        jsonObj = _jsonObj;
        try {
            key = jsonObj.getString(KEY);
            name = jsonObj.getString(NAME);
            week= jsonObj.getString(WEEK);
            location = jsonObj.getString(LOCATION);
            startDate= jsonObj.getString(START_DATE);
            endDate = jsonObj.getString(END_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonObject() { return jsonObj; }
    public String getKey() { return key; }
    public String getName() {
        return name;
    }
    public String getWeek() {
        return week;
    }
    public String getLocation() {
        return location;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return jsonObj.toString();
    }
}
