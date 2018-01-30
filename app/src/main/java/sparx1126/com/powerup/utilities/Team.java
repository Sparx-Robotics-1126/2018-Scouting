package sparx1126.com.powerup.utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Papa on 1/27/18.
 */

public class Team {
    public static String KEY = "key";
    public static String TEAM_NUMBER = "team_number";
    public static String NICKNAME = "nickname";
    public static String CITY = "city";
    public static String STATE_PROV = "state_prov";
    public static String ROOKIE_YEAR = "rookie_year";

    public String getNumber() {
        return number;
    }

    public String getTeamKey() {
        return teamKey;
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

    String number;
    String teamKey;
    String name;
    String city;
    String state;
    String rookieYear;

    public Team(JSONObject teamObj) {
        try {
            number = teamObj.getString(TEAM_NUMBER);
            teamKey = teamObj.getString(KEY);
            name = teamObj.getString(NICKNAME);
            city = teamObj.getString(CITY);
            state = teamObj.getString(STATE_PROV);
            rookieYear = teamObj.getString(ROOKIE_YEAR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
