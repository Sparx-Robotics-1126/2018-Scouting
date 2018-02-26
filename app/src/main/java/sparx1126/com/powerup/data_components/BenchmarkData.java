package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class BenchmarkData {
    // keys
    private static final String TEAM_NUMBER = "teamNumber";
    private static final String TYPE_OF_DRIVE = "typeOfDrive";
    private static final String SPEED = "speed";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String PREFER_START_LEFT = "preferStartLeft";
    private static final String PREFER_START_CENTER = "preferStartCenter";
    private static final String PREFER_START_RIGHT = "preferStartRight";
    private static final String CAN_START_WITH_CUBE = "canStartWithCube";
    private static final String AUTO_CROSS_LINE = "autoCrossLine";
    private static final String AUTO_SCORE_SWITCH = "autoScoreSwitch";
    private static final String AUTO_SCORE_SCALE = "autoScoreScale";
    private static final String ACQUIRE_FLOOR = "acquireFloor";
    private static final String ACQUIRE_PORTAL = "acquirePortal";
    private static final String DEPOSIT_VAULT = "deposit_vault";
    private static final String PLACE_ON_SWITCH = "placeOnSwitch";
    private static final String TOSS_TO_SWITCH = "tossToSwitch";
    private static final String PLACE_ON_SCALE = "placeOnScale";
    private static final String TOSS_TO_SCALE = "tossToScale";
    private static final String PREFER_ACQUIRE_FLOOR = "preferAcquireFloor";
    private static final String PREFER_ACQUIRE_PORTAL = "preferAcquirePortal";
    private static final String CLIMB_RUNG = "climbRung";
    private static final String HAS_RUNGS = "hasRungs";
    private static final String CLIMB_HEIGHT = "climbHeight";
    private static final String CLIMB_ON_ROBOT = "climbOnRobot";

    private JSONObject jsonObj;

    public BenchmarkData() {
        jsonObj = new JSONObject();

        // Initialize
        setInt(TEAM_NUMBER, 0);
        setString(TYPE_OF_DRIVE, "");
        setInt(SPEED, 0);
        setInt(HEIGHT, 0);
        setInt(WEIGHT, 0);
        setBoolean(PREFER_START_LEFT, false);
        setBoolean(PREFER_START_CENTER, false);
        setBoolean(PREFER_START_RIGHT, false);
        setBoolean(CAN_START_WITH_CUBE, false);
        setBoolean(AUTO_CROSS_LINE, false);
        setBoolean(AUTO_SCORE_SWITCH, false);
        setBoolean(AUTO_SCORE_SCALE, false);
        setBoolean(ACQUIRE_FLOOR, false);
        setBoolean(ACQUIRE_PORTAL, false);
        setBoolean(DEPOSIT_VAULT, false);
        setBoolean(PLACE_ON_SWITCH, false);
        setBoolean(TOSS_TO_SWITCH, false);
        setBoolean(PLACE_ON_SCALE, false);
        setBoolean(TOSS_TO_SCALE, false);
        setBoolean(PREFER_ACQUIRE_FLOOR, false);
        setBoolean(PREFER_ACQUIRE_PORTAL, false);
        setBoolean(CLIMB_RUNG, false);
        setBoolean(HAS_RUNGS, false);
        setInt(CLIMB_HEIGHT, 0);
        setBoolean(CLIMB_ON_ROBOT, false);
    }

    public void setJsonString(String _jsonString) {
        try {
            jsonObj = new JSONObject(_jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTeamNumber() { return getInt(TEAM_NUMBER); }
    public void setTeamNumber(int _value) {
        setInt(TEAM_NUMBER, _value);
    }

    public String getTypeOfDrive() { return getString(TYPE_OF_DRIVE); }
    public void setTypeOfDrive(String _value) { setString(TYPE_OF_DRIVE, _value);}

    public int getSpeed() { return getInt(SPEED); }
    public void setSpeed(int _value) {
        setInt(SPEED, _value);
    }

    public int getHeight() { return getInt(HEIGHT); }
    public void setHeight(int _value) {
        setInt(HEIGHT, _value);
    }

    public int getWeight() { return getInt(WEIGHT); }
    public void setWeight(int _value) {
        setInt(WEIGHT, _value);
    }

    public boolean isPreferStartLeft() { return getBoolean(PREFER_START_LEFT); }
    public void setPreferStartLeft(boolean _value) {
        setBoolean(PREFER_START_LEFT, _value);
    }

    public boolean isPreferStartCenter() { return getBoolean(PREFER_START_CENTER); }
    public void setPreferStartCenter(boolean _value) {
        setBoolean(PREFER_START_CENTER, _value);
    }

    public boolean isPreferStartRight() { return getBoolean(PREFER_START_RIGHT); }
    public void setPreferStartRight(boolean _value) {
        setBoolean(PREFER_START_RIGHT, _value);
    }

    public boolean isCanStartWithCube() { return getBoolean(CAN_START_WITH_CUBE); }
    public void setCanStartWithCube(boolean _value) {
        setBoolean(CAN_START_WITH_CUBE, _value);
    }

    public boolean isAutoCrossLine() { return getBoolean(AUTO_CROSS_LINE); }
    public void setAutoCrossLine(boolean _value) {
        setBoolean(AUTO_CROSS_LINE, _value);
    }

    public boolean isAutoScoreSwitch() { return getBoolean(AUTO_SCORE_SWITCH); }
    public void setAutoScoreSwitch(boolean _value) {
        setBoolean(AUTO_SCORE_SWITCH, _value);
    }

    public boolean isAutoScoreScale() { return getBoolean(AUTO_SCORE_SCALE); }
    public void setAutoScoreScale(boolean _value) {
        setBoolean(AUTO_SCORE_SCALE, _value);
    }

    public boolean isAcquireFloor() { return getBoolean(ACQUIRE_FLOOR); }
    public void setAcquireFloor(boolean _value) {
        setBoolean(ACQUIRE_FLOOR, _value);
    }

    public boolean isAcquirePortal() { return getBoolean(ACQUIRE_PORTAL); }
    public void setAcquirePortal(boolean _value) {
        setBoolean(ACQUIRE_PORTAL, _value);
    }

    public boolean isDepositVault() { return getBoolean(DEPOSIT_VAULT); }
    public void setDepositVault(boolean _value) {
        setBoolean(DEPOSIT_VAULT, _value);
    }

    public boolean isPlaceOnSwitch() { return getBoolean(PLACE_ON_SWITCH); }
    public void setPlaceOnSwitch(boolean _value) {
        setBoolean(PLACE_ON_SWITCH, _value);
    }

    public boolean isTossToSwitch() { return getBoolean(TOSS_TO_SWITCH); }
    public void setTossToSwitch(boolean _value) {
        setBoolean(TOSS_TO_SWITCH, _value);
    }

    public boolean isPlaceOnScale() { return getBoolean(PLACE_ON_SCALE); }
    public void setPlaceOnScale(boolean _value) {
        setBoolean(PLACE_ON_SCALE, _value);
    }

    public boolean isTossToScale() { return getBoolean(TOSS_TO_SCALE); }
    public void setTossToScale(boolean _value) {
        setBoolean(TOSS_TO_SCALE, _value);
    }

    public boolean isPreferAcquireFloor() { return getBoolean(PREFER_ACQUIRE_FLOOR); }
    public void setPreferAcquireFloor(boolean _value) {
        setBoolean(PREFER_ACQUIRE_FLOOR, _value);
    }

    public boolean isPreferAcquirePortal() { return getBoolean(PREFER_ACQUIRE_PORTAL); }
    public void setPreferAcquirePortal(boolean _value) {
        setBoolean(PREFER_ACQUIRE_PORTAL, _value);
    }

    public boolean isClimbRung() { return getBoolean(CLIMB_RUNG); }
    public void setClimbRung(boolean _value) {
        setBoolean(CLIMB_RUNG, _value);
    }

    public boolean isHasRungs() { return getBoolean(HAS_RUNGS); }
    public void setHasRungs(boolean _value) {
        setBoolean(HAS_RUNGS, _value);
    }

    public int getClimbHeight() { return getInt(CLIMB_HEIGHT); }
    public void setClimbHeight(int _value) {
        setInt(CLIMB_HEIGHT, _value);
    }

    public boolean isClimbOnRobot() { return getBoolean(CLIMB_ON_ROBOT); }
    public void setClimbOnRobot(boolean _value) {
        setBoolean(CLIMB_ON_ROBOT, _value);
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

    private String getString(String _key) {
        String rtnData = "";

        try {
            rtnData = jsonObj.get(_key).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    private void setString(String _key, String _value) {
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
