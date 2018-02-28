package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class BenchmarkData {
    // keys
    private static final String TEAM_NUMBER = "teamNumber";
    private static final String TYPE_OF_DRIVE = "typeOfDrive";
    private static final String TYPE_OF_WHEEL = "typeOfWheel";
    private static final String NUMBER_OF_WHEELS = "numberOfWheels";
    private static final String SPEED = "speed";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String GROUND_CLEARANCE = "groundClearance";
    private static final String PREFER_START_ONE = "preferStartOne";
    private static final String PREFER_START_TWO = "preferStartTwo";
    private static final String PREFER_START_THREE = "preferStartThree";
    private static final String CAN_START_WITH_CUBE = "canStartWithCube";
    private static final String AUTO_CROSS_LINE = "autoCrossLine";
    private static final String AUTO_HOW_MANY_SCORE_SWITCH_PLACED = "autoHowManyScoreSwitchPlaced";
    private static final String AUTO_HOW_MANY_SCORE_SWITCH_TOSSED = "autoHowManyScoreSwitchTossed";
    private static final String AUTO_HOW_MANY_SCORE_SCALE_PLACED = "autoHowManyScoreScalePlaced";
    private static final String AUTO_HOW_MANY_SCORE_SCALE_TOSSED = "autoHowManyScoreScaleTossed";
    private static final String AUTO_ACQUIRE_PORTAL = "autoAcquirePortal";
    private static final String AUTO_ACQUIRE_FLOOR = "autoAcquireFloor";
    private static final String TELE_ACQUIRE_FLOOR = "teleAcquireFloor";
    private static final String TELE_ACQUIRE_PORTAL = "teleAcquirePortal";
    private static final String TELE_DEPOSIT_VAULT = "teleDeposit_vault";
    private static final String TELE_PLACE_ON_SWITCH = "telePlaceOnSwitch";
    private static final String TELE_TOSS_TO_SWITCH = "teleTossToSwitch";
    private static final String TELE_PLACE_ON_SCALE = "telePlaceOnScale";
    private static final String TELE_TOSS_TO_SCALE = "teleTossToScale";
    private static final String TELE_PREFER_ACQUIRE_FLOOR = "telePreferAcquireFloor";
    private static final String TELE_PREFER_ACQUIRE_PORTAL = "telePreferAcquirePortal";
    private static final String END_CLIMB_RUNG = "endClimbRung";
    private static final String END_CLIMB_ASSIST_TYPE = "endClimbAssistType";
    private static final String END_CLIMB_HEIGHT = "endClimbHeight";
    private static final String END_CLIMB_ON_ROBOT = "endClimbOnRobot";


    private JSONObject jsonObj;

    public BenchmarkData() {
        jsonObj = new JSONObject();

        // Initialize
        setInt(TEAM_NUMBER, 0);
        setString(TYPE_OF_DRIVE, "");
        setString(TYPE_OF_WHEEL, "");
        setInt(NUMBER_OF_WHEELS, 0);
        setInt(SPEED, 0);
        setInt(HEIGHT, 0);
        setInt(WEIGHT, 0);
        setInt(GROUND_CLEARANCE, 0);
        setString(PREFER_START_ONE, "");
        setString(PREFER_START_TWO, "");
        setString(PREFER_START_THREE, "");
        setBoolean(CAN_START_WITH_CUBE, false);
        setBoolean(AUTO_CROSS_LINE, false);
        setInt(AUTO_HOW_MANY_SCORE_SWITCH_PLACED, 0);
        setInt(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED, 0);
        setInt(AUTO_HOW_MANY_SCORE_SCALE_PLACED, 0);
        setInt(AUTO_HOW_MANY_SCORE_SCALE_TOSSED, 0);
        setBoolean(AUTO_ACQUIRE_PORTAL, false);
        setBoolean(AUTO_ACQUIRE_FLOOR, false);
        setBoolean(TELE_ACQUIRE_FLOOR, false);
        setBoolean(TELE_ACQUIRE_PORTAL, false);
        setBoolean(TELE_DEPOSIT_VAULT, false);
        setBoolean(TELE_PLACE_ON_SWITCH, false);
        setBoolean(TELE_TOSS_TO_SWITCH, false);
        setBoolean(TELE_PLACE_ON_SCALE, false);
        setBoolean(TELE_TOSS_TO_SCALE, false);
        setBoolean(TELE_PREFER_ACQUIRE_FLOOR, false);
        setBoolean(TELE_PREFER_ACQUIRE_PORTAL, false);
        setBoolean(END_CLIMB_RUNG, false);
        setString(END_CLIMB_ASSIST_TYPE, "");
        setInt(END_CLIMB_HEIGHT, 0);
        setBoolean(END_CLIMB_ON_ROBOT, false);
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

    public String getTypeOfWheel() { return getString(TYPE_OF_WHEEL); }
    public void setTypeOfWheel(String _value) { setString(TYPE_OF_WHEEL, _value);}

    public int getNumberOfWheels() { return getInt(NUMBER_OF_WHEELS); }
    public void setNumberOfWheels(int _value) {
        setInt(NUMBER_OF_WHEELS, _value);
    }

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

    public int getGroundClearance() { return getInt(GROUND_CLEARANCE); }
    public void setGroundClearance(int _value) {
        setInt(GROUND_CLEARANCE, _value);
    }

    public String getPreferedStartOne() { return getString(PREFER_START_ONE); }
    public void setPreferedStartOne(String _value) { setString(PREFER_START_ONE, _value);}

    public String getPreferedStartTwo() { return getString(PREFER_START_TWO); }
    public void setPreferedStartTwo(String _value) { setString(PREFER_START_TWO, _value);}

    public String getPreferedStartThree() { return getString(PREFER_START_THREE); }
    public void setPreferedStartThree(String _value) { setString(PREFER_START_THREE, _value);}

    public boolean isCanStartWithCube() { return getBoolean(CAN_START_WITH_CUBE); }
    public void setCanStartWithCube(boolean _value) {
        setBoolean(CAN_START_WITH_CUBE, _value);
    }

    public boolean isAutoCrossLine() { return getBoolean(AUTO_CROSS_LINE); }
    public void setAutoCrossLine(boolean _value) {
        setBoolean(AUTO_CROSS_LINE, _value);
    }

    public int getHowManyScoreSwitchPlaced() { return getInt(AUTO_HOW_MANY_SCORE_SWITCH_PLACED); }
    public void setHowManyScoreSwitchPlaced(int _value) {
        setInt(AUTO_HOW_MANY_SCORE_SWITCH_PLACED, _value);
    }

    public int getHowManyScoreSwitchTossed() { return getInt(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED); }
    public void setHowManyScoreSwitchTossed(int _value) {
        setInt(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED, _value);
    }

    public int getHowManyScoreScalePlaced() { return getInt(AUTO_HOW_MANY_SCORE_SCALE_PLACED); }
    public void setHowManyScoreScalePlaced(int _value) {
        setInt(AUTO_HOW_MANY_SCORE_SCALE_PLACED, _value);
    }

    public int getHowManyScoreScaleTossed() { return getInt(AUTO_HOW_MANY_SCORE_SCALE_TOSSED); }
    public void setHowManyScoreScaleTossed(int _value) {
        setInt(AUTO_HOW_MANY_SCORE_SCALE_TOSSED, _value);
    }

    public boolean isAutoAcquirePortal() { return getBoolean(AUTO_ACQUIRE_PORTAL); }
    public void setAutoAcquirePortal(boolean _value) {
        setBoolean(AUTO_ACQUIRE_PORTAL, _value);
    }

    public boolean isAutoAcquireFloor() { return getBoolean(AUTO_ACQUIRE_FLOOR); }
    public void setAutoAcquireFloor(boolean _value) {
        setBoolean(AUTO_ACQUIRE_FLOOR, _value);
    }

    public boolean isTeleAcquireFloor() { return getBoolean(TELE_ACQUIRE_FLOOR); }
    public void setTeleAcquireFloor(boolean _value) {
        setBoolean(TELE_ACQUIRE_FLOOR, _value);
    }

    public boolean isTeleAcquirePortal() { return getBoolean(TELE_ACQUIRE_PORTAL); }
    public void setTeleAcquirePortal(boolean _value) {
        setBoolean(TELE_ACQUIRE_PORTAL, _value);
    }

    public boolean isTeleDepositVault() { return getBoolean(TELE_DEPOSIT_VAULT); }
    public void setTeleDepositVault(boolean _value) {
        setBoolean(TELE_DEPOSIT_VAULT, _value);
    }

    public boolean isTelePlaceOnSwitch() { return getBoolean(TELE_PLACE_ON_SWITCH); }
    public void setTelePlaceOnSwitch(boolean _value) {
        setBoolean(TELE_PLACE_ON_SWITCH, _value);
    }

    public boolean isTeleTossToSwitch() { return getBoolean(TELE_TOSS_TO_SWITCH); }
    public void setTeleTossToSwitch(boolean _value) {
        setBoolean(TELE_TOSS_TO_SWITCH, _value);
    }

    public boolean isTelePlaceOnScale() { return getBoolean(TELE_PLACE_ON_SCALE); }
    public void setTelePlaceOnScale(boolean _value) {
        setBoolean(TELE_PLACE_ON_SCALE, _value);
    }

    public boolean isTeleTossToScale() { return getBoolean(TELE_TOSS_TO_SCALE); }
    public void setTeleTossToScale(boolean _value) {
        setBoolean(TELE_TOSS_TO_SCALE, _value);
    }

    public boolean isTelePreferAcquireFloor() { return getBoolean(TELE_PREFER_ACQUIRE_FLOOR); }
    public void setTelePreferAcquireFloor(boolean _value) {
        setBoolean(TELE_PREFER_ACQUIRE_FLOOR, _value);
    }

    public boolean isTelePreferAcquirePortal() { return getBoolean(TELE_PREFER_ACQUIRE_PORTAL); }
    public void setTelePreferAcquirePortal(boolean _value) {
        setBoolean(TELE_PREFER_ACQUIRE_PORTAL, _value);
    }

    public boolean isEndClimbRung() { return getBoolean(END_CLIMB_RUNG); }
    public void setEndClimbRung(boolean _value) {
        setBoolean(END_CLIMB_RUNG, _value);
    }

    public String getEndClimbAssistType() { return getString(END_CLIMB_ASSIST_TYPE); }
    public void setEndClimbAssistType(String _value) { setString(END_CLIMB_ASSIST_TYPE, _value);}

    public int getEndClimbHeight() { return getInt(END_CLIMB_HEIGHT); }
    public void setEndClimbHeight(int _value) {
        setInt(END_CLIMB_HEIGHT, _value);
    }

    public boolean isEndClimbOnRobot() { return getBoolean(END_CLIMB_ON_ROBOT); }
    public void setEndClimbOnRobot(boolean _value) {
        setBoolean(END_CLIMB_ON_ROBOT, _value);
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
