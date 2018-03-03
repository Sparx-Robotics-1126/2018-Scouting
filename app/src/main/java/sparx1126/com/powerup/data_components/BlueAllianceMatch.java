package sparx1126.com.powerup.data_components;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BlueAllianceMatch  extends JsonData {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String COMP_LEVEL = "comp_level";
    private static final String MATCH_NUMBER = "match_number";
    private static final String ALLIANCES = "alliances";
    private static final String TEAM_KEYS = "team_keys";
    private static final String BLUE = "blue";
    private static final String RED = "red";

    private final SparseArray<String> blueTeamKeys;
    private final SparseArray<String> redTeamKeys;

    public BlueAllianceMatch(JSONObject _jsonObj) {
        // Initialize
        stringValuesMap.put(KEY, "");
        stringValuesMap.put(COMP_LEVEL, "");
        stringValuesMap.put(MATCH_NUMBER, "");

        jsonObjectsMap.put(ALLIANCES, null);

        restoreFromJsonObject(_jsonObj);

        blueTeamKeys = new SparseArray<>();
        redTeamKeys = new SparseArray<>();

        JSONObject redObj = getJsonObject(jsonObjectsMap.get(ALLIANCES), RED);
        JSONArray redTeamKeysArray = getJsonArray(redObj, TEAM_KEYS);
        JSONObject blueObj = getJsonObject(jsonObjectsMap.get(ALLIANCES), BLUE);
        JSONArray blueTeamKeysArray = getJsonArray(blueObj, TEAM_KEYS);

        try {
            for(int i = 0; i < redTeamKeysArray.length(); i++){
                redTeamKeys.put(i+1, redTeamKeysArray.getString(i));
            }
            for(int i = 0; i < blueTeamKeysArray.length(); i++){
                blueTeamKeys.put(i+1, blueTeamKeysArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getKey() { return stringValuesMap.get(KEY); }
    public String getCompLevel() { return stringValuesMap.get(COMP_LEVEL); }
    public String getMatchNumber() { return stringValuesMap.get(MATCH_NUMBER); }
    public SparseArray<String> getBlueTeamKeys() {
        return blueTeamKeys;
    }
    public SparseArray<String> getRedTeamKeys() {
        return redTeamKeys;
    }
}
