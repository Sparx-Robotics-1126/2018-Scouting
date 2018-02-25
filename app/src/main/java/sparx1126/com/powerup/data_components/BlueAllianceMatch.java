package sparx1126.com.powerup.data_components;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    private JSONObject jsonObj;
    private String key;
    private String compLevel;
    private String matchNumber;
    private SparseArray<String> blueTeamKeys;
    private SparseArray<String> redTeamKeys;

    public BlueAllianceMatch(JSONObject _jsonObj) {
        jsonObj = _jsonObj;
        blueTeamKeys = new SparseArray<>();
        redTeamKeys = new SparseArray<>();
        try {
            key = jsonObj.getString(KEY);
            compLevel = jsonObj.getString(COMP_LEVEL);
            matchNumber = jsonObj.getString(MATCH_NUMBER);
            JSONObject allianceObj =  jsonObj.getJSONObject(ALLIANCES);
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

    public JSONObject getJsonObject() { return jsonObj; }
    public String getKey() { return key; }
    public String getCompLevel() {
        return compLevel;
    }
    public String getMatchNumber() {
        return matchNumber;
    }
    public SparseArray<String> getBlueTeamKeys() {
        return blueTeamKeys;
    }
    public SparseArray<String> getRedTeamKeys() {
        return redTeamKeys;
    }

    @Override
    public String toString() {
        return jsonObj.toString();
    }
}
