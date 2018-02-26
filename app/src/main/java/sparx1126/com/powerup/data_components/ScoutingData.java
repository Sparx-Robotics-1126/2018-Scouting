package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class ScoutingData {
    // keys
    private static final String MATCH_NUMBER = "matchNumber";
    private static final String TEAM_NUMBER = "teamNumber";
    private static final String AUTO_LINE_CROSSED = "autoLineCrossed";
    private static final String AUTO_SCORED_SWITCH = "autoScoredSwitch";
    private static final String AUTO_PICKED_UP_CUBE = "autoPickedUpCube";
    private static final String AUTO_SCORED_SCALE = "autoScoredScale";
    private static final String AUTO_CUBE_EXCHANGE = "autoCubeExchange";
    private static final String CUBES_PLACED_ON_SWITCH = "cubesPlacedOnSwitch";
    private static final String CUBES_PLACED_ON_SCALE = "cubesPlacedOnScale";
    private static final String CUBES_PLACED_IN_EXCHANGE = "cubesPlacedInExchange";
    private static final String CUBES_PICKED_UP_FROM_FLOOR = "cubesPickedUpFromFloor";
    private static final String CUBES_ACQUIRE_FROM_PLAYER = "cubesAcquireFromPlayer";
    private static final String PLAYED_DEFENSE_EFFECTIVELY = "playedDefenseEffectively";
    private static final String CLIMBED_RUNG = "climbedRung";
    private static final String CLIMBED_ON_ROBOT = "climbedOnRobot";
    private static final String CAN_BE_CLIMB_ON = "canBeClimbOn";
    private static final String NUMBER_OF_ROBOTS_HELD = "numberOfRobotsHeld";
    private static final String CLIMBED_UNDER_15_SECS = "climbedUnder15Secs";

    private JSONObject jsonObj;

    public ScoutingData() {
        jsonObj = new JSONObject();

        // Initialize
        setInt(MATCH_NUMBER, 0);
        setInt(TEAM_NUMBER, 0);
        setBoolean(AUTO_LINE_CROSSED, false);
        setBoolean(AUTO_SCORED_SWITCH, false);
        setBoolean(AUTO_SCORED_SCALE, false);
        setBoolean(AUTO_PICKED_UP_CUBE, false);
        setBoolean(AUTO_CUBE_EXCHANGE, false);
        setInt(CUBES_PLACED_ON_SWITCH, 0);
        setInt(CUBES_PLACED_ON_SCALE, 0);
        setInt(CUBES_PLACED_IN_EXCHANGE, 0);
        setInt(CUBES_PICKED_UP_FROM_FLOOR, 0);
        setInt(CUBES_ACQUIRE_FROM_PLAYER, 0);
        setBoolean(PLAYED_DEFENSE_EFFECTIVELY, false);
        setBoolean(CLIMBED_RUNG, false);
        setBoolean(CLIMBED_ON_ROBOT, false);
        setBoolean(CAN_BE_CLIMB_ON, false);
        setInt(NUMBER_OF_ROBOTS_HELD, 0);
        setBoolean(CLIMBED_UNDER_15_SECS, false);
    }

    public void setJsonString(String _jsonString) {
        try {
            jsonObj = new JSONObject(_jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMatchNumber() { return getInt(MATCH_NUMBER); }
    public void setMatchNumber(int _value) {
        setInt(MATCH_NUMBER, _value);
    }

    public int getTeamNumber() { return getInt(TEAM_NUMBER); }
    public void setTeamNumber(int _value) {
        setInt(TEAM_NUMBER, _value);
    }

    public boolean isAutoLineCrossed() { return getBoolean(AUTO_LINE_CROSSED); }
    public void setAutoLineCrossed(boolean _value) {
        setBoolean(AUTO_LINE_CROSSED, _value);
    }

    public boolean isAutoScoredSwitch() { return getBoolean(AUTO_SCORED_SWITCH); }
    public void setAutoScoredSwitch(boolean _value) {
        setBoolean(AUTO_SCORED_SWITCH, _value);
    }

    public boolean isAutoScoredScale() { return getBoolean(AUTO_SCORED_SCALE); }
    public void setAutoScoredScale(boolean _value) {
        setBoolean(AUTO_SCORED_SCALE, _value);
    }

    public boolean isAutoPickedUpCube() { return getBoolean(AUTO_PICKED_UP_CUBE); }
    public void setAutoPickedUpCube(boolean _value) {
        setBoolean(AUTO_PICKED_UP_CUBE, _value);
    }

    public boolean isAutoCubeExchange() { return getBoolean(AUTO_CUBE_EXCHANGE); }
    public void setAutoCubeExchange(boolean _value) {
        setBoolean(AUTO_CUBE_EXCHANGE, _value);
    }

    public int getCubesPlacedOnSwitch() { return getInt(CUBES_PLACED_ON_SWITCH); }
    public void setCubesPlacedOnSwitch(int _value) {
        setInt(CUBES_PLACED_ON_SWITCH, _value);
    }

    public int getCubesPlacedOnScale() { return getInt(CUBES_PLACED_ON_SCALE); }
    public void setCubesPlacedOnScale(int _value) {
        setInt(CUBES_PLACED_ON_SCALE, _value);
    }

    public int getCubesPlacedInExchange() { return getInt(CUBES_PLACED_IN_EXCHANGE); }
    public void setCubesPlacedInExchange(int _value) {
        setInt(CUBES_PLACED_IN_EXCHANGE, _value);
    }

    public int getCubesPickedUpFromFloor() { return getInt(CUBES_PICKED_UP_FROM_FLOOR); }
    public void setCubesPickedUpFromFloor(int _value) {
        setInt(CUBES_PICKED_UP_FROM_FLOOR, _value);
    }

    public int getCubesAcquireFromPlayer() { return getInt(CUBES_ACQUIRE_FROM_PLAYER); }
    public void setCubesAcquireFromPlayer(int _value) {
        setInt(CUBES_ACQUIRE_FROM_PLAYER, _value);
    }

    public boolean isPlayedDefenseEffectively() { return getBoolean(PLAYED_DEFENSE_EFFECTIVELY); }
    public void setPlayedDefenseEffectively(boolean _value) {
        setBoolean(PLAYED_DEFENSE_EFFECTIVELY, _value);
    }

    public boolean isClimbedRung() { return getBoolean(CLIMBED_RUNG); }
    public void setClimbedRung(boolean _value) {
        setBoolean(CLIMBED_RUNG, _value);
    }

    public boolean isClimbedOnRobot() { return getBoolean(CLIMBED_ON_ROBOT); }
    public void setClimbedOnRobot(boolean _value) {
        setBoolean(CLIMBED_ON_ROBOT, _value);
    }

    public boolean isCanBeClimbOn() { return getBoolean(CAN_BE_CLIMB_ON); }
    public void setCanBeClimbOn(boolean _value) {
        setBoolean(CAN_BE_CLIMB_ON, _value);
    }

    public int getNumberOfRobotsHeld() { return getInt(NUMBER_OF_ROBOTS_HELD); }
    public void setNumberOfRobotsHeld(int _value) {
        setInt(NUMBER_OF_ROBOTS_HELD, _value);
    }

    public boolean isClimbedUnder15Secs() { return getBoolean(CLIMBED_UNDER_15_SECS); }
    public void setClimbedUnder15Secs(boolean _value) {
        setBoolean(CLIMBED_UNDER_15_SECS, _value);
    }

    private int getInt(String _key) {
        int rtnData = 0;

        try {
            rtnData = jsonObj.getInt(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    private void setInt(String _key, int _value) {
        try {
            jsonObj.put(_key, _value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean getBoolean(String _key) {
        boolean rtnData = false;

        try {
            rtnData = jsonObj.getBoolean(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    private void setBoolean(String _key, boolean _value) {
        try {
            jsonObj.put(_key, _value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return jsonObj.toString();
    }
}
