package sparx1126.com.powerup.data_components;

import org.json.JSONObject;

public class BlueAllianceEvent extends JsonData {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String WEEK = "week";
    private static final String LOCATION = "location_name";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    public BlueAllianceEvent(JSONObject _jsonObj) {
        // Initialize
        stringValuesMap.put(KEY, "");
        stringValuesMap.put(NAME, "");
        stringValuesMap.put(WEEK, "");
        stringValuesMap.put(LOCATION, "");
        stringValuesMap.put(START_DATE, "");
        stringValuesMap.put(END_DATE, "");

        restoreFromJsonObject(_jsonObj);
    }

    public String getKey() { return stringValuesMap.get(KEY); }
    public String getName() { return stringValuesMap.get(NAME); }
    public String getWeek() { return stringValuesMap.get(WEEK); }
    public String getLocation() { return stringValuesMap.get(LOCATION); }
    public String getStartDate() { return stringValuesMap.get(START_DATE); }
    public String getEndDate() { return stringValuesMap.get(END_DATE); }
}
