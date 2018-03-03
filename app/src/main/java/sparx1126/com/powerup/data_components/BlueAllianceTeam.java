package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class BlueAllianceTeam extends JsonData {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String TEAM_NUMBER = "team_number";
    private static final String NICKNAME = "nickname";
    private static final String CITY = "city";
    private static final String STATE_PROV = "state_prov";
    private static final String ROOKIE_YEAR = "rookie_year";

    public BlueAllianceTeam(JSONObject _jsonObj) {
        // Initialize
        stringValuesMap.put(KEY, "");
        stringValuesMap.put(TEAM_NUMBER, "");
        stringValuesMap.put(NICKNAME, "");
        stringValuesMap.put(CITY, "");
        stringValuesMap.put(STATE_PROV, "");
        stringValuesMap.put(ROOKIE_YEAR, "");

        restoreFromJsonObject(_jsonObj);
    }

    public String getKey() { return stringValuesMap.get(KEY); }
    public String getNumber() { return stringValuesMap.get(TEAM_NUMBER); }
    public String getWeek() { return stringValuesMap.get(NICKNAME); }
    public String getLocation() { return stringValuesMap.get(CITY); }
    public String getStartDate() { return stringValuesMap.get(STATE_PROV); }
    public String getEndDate() { return stringValuesMap.get(ROOKIE_YEAR); }
}
