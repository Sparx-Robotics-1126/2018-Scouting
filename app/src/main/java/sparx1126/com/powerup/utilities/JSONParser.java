package sparx1126.com.powerup.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;

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

    public Map<String, BlueAllianceEvent> teamEventsStringIntoMap(String _input) {
        Map<String, BlueAllianceEvent> output = new HashMap<>();

        try {
            //Log.d("teamEventsStringIntoMap", _input);
            JSONArray array = new JSONArray(_input);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceEvent item = new BlueAllianceEvent(obj);
                output.put(item.getKey(), item);
            }
            Log.d("teamEventsStringIntoMap", output.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String teamEventsMapIntoString(Map<String, BlueAllianceEvent> _input) {
        JSONArray output = new JSONArray();
        for (Map.Entry<String, BlueAllianceEvent> entry : _input.entrySet())
        {
            BlueAllianceEvent event = entry.getValue();
            JSONObject eventJSONObject = event.getJSONObject(event);
            output.put(eventJSONObject);
        }

        return output.toString();
    }

    public Map<String, BlueAllianceTeam> eventTeamsStringIntoMap(String _contentInJSONForm) {
        Map<String, BlueAllianceTeam> rtnMap = new HashMap<>();

        try {
            //Log.d("eventTeamsStringIntoMap", _contentInJSONForm);
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceTeam item = new BlueAllianceTeam(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("eventTeamsStringIntoMap", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }
    public Map<String, BlueAllianceMatch> eventMatchesStringIntoMap(String _contentInJSONForm) {
        Map<String, BlueAllianceMatch> rtnMap = new HashMap<>();

        try {
            //Log.d("eventTeamsStringIntoMap", _contentInJSONForm);
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceMatch item = new BlueAllianceMatch(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("eventMatchesStringInM", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }

    public String eventTeamsMapIntoString(Map<String, BlueAllianceTeam> _input) {
        JSONArray output = new JSONArray();
        for (Map.Entry<String, BlueAllianceTeam> entry : _input.entrySet())
        {
            BlueAllianceTeam event = entry.getValue();
            //JSONObject eventJSONObject = event.getJSONObject(event);
            //output.put(eventJSONObject);
        }

        return output.toString();
    }
}
