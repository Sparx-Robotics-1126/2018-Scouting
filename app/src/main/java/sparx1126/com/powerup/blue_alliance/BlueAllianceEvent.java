package sparx1126.com.powerup.blue_alliance;

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

    private String key;
    private String name;
    private String week;
    private String location;
    private String startDate;
    private String endDate;

    public BlueAllianceEvent(JSONObject eventObj) {
        try {
            key = eventObj.getString(KEY);
            name = eventObj.getString(NAME);
            week= eventObj.getString(WEEK);
            location = eventObj.getString(LOCATION);
            startDate= eventObj.getString(START_DATE);
            endDate = eventObj.getString(END_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    public JSONObject getJSONObject(BlueAllianceEvent _input) {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY, key);
            json.put(NAME, name);
            json.put(WEEK, week);
            json.put(LOCATION, location);
            json.put(START_DATE, startDate);

            json.put(END_DATE, endDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
