package sparx1126.com.powerup.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;
import sparx1126.com.powerup.blue_alliance.BlueAllianceTeam;

public class JSONParser {
    private static JSONParser instance;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized JSONParser getInstance() {
        if (instance == null) {
            instance = new JSONParser();
        }
        return instance;
    }

    private JSONParser() {
    }

    public Map<String, BlueAllianceEvent> parseTeamEvents(String _contentInJSONForm) {
        Map<String, BlueAllianceEvent> rtnMap = new HashMap<>();

        try {
            //Log.d("parseTeamEvents", _contentInJSONForm);
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceEvent item = new BlueAllianceEvent(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("parseTeamEvents", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }

    public Map<String, BlueAllianceTeam> parseEventTeams(String _contentInJSONForm) {
        Map<String, BlueAllianceTeam> rtnMap = new HashMap<>();

        try {
            //Log.d("parseEventTeams", _contentInJSONForm);
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceTeam item = new BlueAllianceTeam(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("parseEventTeams", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }
}
