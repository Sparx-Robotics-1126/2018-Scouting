package sparx1126.com.powerup.blue_alliance;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by profe on 1/31/2018.
 */

public class BlueAllianceMatch {
    // keys from thebluealliance.com API
    private static final String KEY = "key";
    private static final String MATCH_NUMBER = "match_number";
    private static final String ALLIANCES = "alliances";
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String TEAM_KEYS = "team_keys";



    private String key;

    public String getKey() {
        return key;
    }

    public String getMatchNum() {
        return matchNum;
    }

    public String getBlue1() {
        return blue1;
    }

    public String getBlue2() {
        return blue2;
    }

    public String getBlue3() {
        return blue3;
    }

    public String getRed1() {
        return red1;
    }

    public String getRed2() {
        return red2;
    }

    public String getRed3() {
        return red3;
    }

    public ArrayList<String> getBlueTeamsArray() {
        return blueTeamsArray;
    }

    public ArrayList<String> getRedTeamsArray() {
        return redTeamsArray;
    }

    private String matchNum;
    private String blue1;
    private String blue2;
    private String blue3;
    private String red1;
    private String red2;
    private String red3;
    private ArrayList<String> blueTeamsArray;
    private ArrayList<String> redTeamsArray;


    BlueAllianceMatch(JSONObject matchObj) {
        try {
            key = matchObj.getString(KEY);
            matchNum = matchObj.getString(MATCH_NUMBER);
            JSONObject allianceObject = matchObj.getJSONObject(ALLIANCES);
            JSONObject blueObject = allianceObject.getJSONObject(BLUE);
            JSONObject redObject = allianceObject.getJSONObject(RED);
            blueTeamsArray = convertJSONArray(blueObject.getJSONArray(TEAM_KEYS));
            blue1 = blueTeamsArray.get(0);
            blue2 = blueTeamsArray.get(1);
            blue3 = blueTeamsArray.get(2);
            redTeamsArray = convertJSONArray(redObject.getJSONArray(TEAM_KEYS));
            red1 = redTeamsArray.get(0);
            red2 = redTeamsArray.get(1);
            red3 = redTeamsArray.get(2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> convertJSONArray(JSONArray array) {
        //from https://stackoverflow.com/questions/17037340/converting-jsonarray-to-arraylist
        ArrayList<String> listdata = new ArrayList<String>();
        if (array != null) {
            for (int i=0;i<array.length();i++){
                try {
                    listdata.add(array.getString(i));
                } catch (Exception e) {
                    Log.e("SOME RANDOM JSON ERROR", "error while converting json object to array");
                }
            }
        }
        return listdata;
    }

    public String getTeams() {
        String response = "";
        response += "Red Team 1" + ": " + red1 + "\n";
        response += "Red Team 2" + ": " + red2 + "\n";
        response += "Red Team 3" + ": " + red3 + "\n";
        response += "Blue Team 1" + ": " + blue1 + "\n";
        response += "Blue Team 2" + ": " + blue2 + "\n";
        response += "Blue Team 3" + ": " + blue3;
        return response;
    }

    public String toString() {
        String response = "";
        response += KEY + ": " + key + "\n";
        response += MATCH_NUMBER + ": " + matchNum + "\n";
        response += "Red Team 1" + ": " + red1 + "\n";
        response += "Red Team 2" + ": " + red2 + "\n";
        response += "Red Team 3" + ": " + red3 + "\n";
        response += "Blue Team 1" + ": " + blue1 + "\n";
        response += "Blue Team 2" + ": " + blue2 + "\n";
        response += "Blue Team 3" + ": " + blue3;
        return response;

    }

}


