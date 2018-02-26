package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class BlueAllianceTeam {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String TEAM_NUMBER = "team_number";
    private static final String NICKNAME = "nickname";
    private static final String CITY = "city";
    private static final String STATE_PROV = "state_prov";
    private static final String ROOKIE_YEAR = "rookie_year";

    private final JSONObject jsonObj;
    private String key;
    private String number;
    private String name;
    private String city;
    private String state;
    private String rookieYear;

    public BlueAllianceTeam(JSONObject _jsonObj) {
        jsonObj = _jsonObj;
        try {
            key = jsonObj.getString(KEY);
            number = jsonObj.getString(TEAM_NUMBER);
            name = jsonObj.getString(NICKNAME);
            city = jsonObj.getString(CITY);
            state = jsonObj.getString(STATE_PROV);
            rookieYear = jsonObj.getString(ROOKIE_YEAR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonObject() { return jsonObj; }
    public String getKey() { return key; }
    public String getNumber() {
        return number;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getRookieYear() {
        return rookieYear;
    }

    @Override
    public String toString() {
        return jsonObj.toString();
    }
}
