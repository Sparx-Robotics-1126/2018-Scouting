package sparx1126.com.powerup.data_components;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BlueAllianceMatch {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String COMP_LEVEL = "comp_level";
    private static final String MATCH_NUMBER = "match_number";
    private static final String TEAM_KEYS = "team_keys";
    private static final String ALLIANCES = "alliances";
    private static final String BLUE = "blue";
    private static final String RED = "red";

    private String key;
    private String compLevel;
    private String matchNumber;
    private Map<Integer, String> blueTeamKeys;
    private Map<Integer, String> redTeamKeys;

    public BlueAllianceMatch(JSONObject eventObj) {
        blueTeamKeys = new HashMap<>();
        redTeamKeys = new HashMap<>();
        try {
            key = eventObj.getString(KEY);
            compLevel = eventObj.getString(COMP_LEVEL);
            matchNumber = eventObj.getString(MATCH_NUMBER);
            JSONObject allianceObj =  eventObj.getJSONObject(ALLIANCES);
            JSONObject redObj = allianceObj.getJSONObject(RED);
            JSONArray redTeamKeysArray = redObj.getJSONArray(TEAM_KEYS);
            for(int i = 0; i < redTeamKeysArray.length(); i++){
                redTeamKeys.put(i+1, redTeamKeysArray.getString(i));
            }
            JSONObject blueObj = allianceObj.getJSONObject(BLUE);
            JSONArray blueTeamKeysArray = blueObj.getJSONArray(TEAM_KEYS);
            for(int i = 0; i < blueTeamKeysArray.length(); i++){
                blueTeamKeys.put(i+1, blueTeamKeysArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getKey() { return key; }


    public JSONObject getJSONObject(BlueAllianceMatch _input) {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY, key);
            json.put(COMP_LEVEL,compLevel);
            json.put(MATCH_NUMBER, matchNumber);
            JSONArray blueTeamKeysArray = new JSONArray();
            for(String team : blueTeamKeys.values()){
                blueTeamKeysArray.put(team);
            }
            JSONObject blueObj = new JSONObject();
            blueObj.put(TEAM_KEYS, blueTeamKeysArray);


            JSONArray redTeamKeysArray = new JSONArray();
            for(String team : redTeamKeys.values()){
                redTeamKeysArray.put(team);
            }
            JSONObject redObj = new JSONObject();
            redObj.put(TEAM_KEYS, redTeamKeysArray);
            JSONObject allianceObj = new JSONObject();
            allianceObj.put(RED, redObj);
            allianceObj.put(BLUE, blueObj);
            json.put(ALLIANCES, allianceObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
