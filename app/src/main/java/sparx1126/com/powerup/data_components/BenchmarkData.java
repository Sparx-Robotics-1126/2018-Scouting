package sparx1126.com.powerup.data_components;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BenchmarkData extends JsonData {
    // keys
    private static final String SCOUTER_NAME = "scouterName";
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
    private static final String AUTO_CROSS_LINE = "crossedAutoLine";/*changed*/
    private static final String AUTO_HOW_MANY_SCORE_SWITCH_PLACED = "autoHowManyScoreSwitchPlaced";
    private static final String AUTO_HOW_MANY_SCORE_SWITCH_TOSSED = "autoHowManyScoreSwitchTossed";
    private static final String AUTO_HOW_MANY_SCORE_SCALE_PLACED = "autoHowManyScoreScalePlaced";
    private static final String AUTO_HOW_MANY_SCORE_SCALE_TOSSED = "autoHowManyScoreScaleTossed";
    private static final String AUTO_ACQUIRE_PORTAL = "canAcquirePortalAuto";/*changed*/
    private static final String AUTO_ACQUIRE_FLOOR = "canAcquireFloorAuto";/*changed*/
    private static final String TELE_ACQUIRE_PORTAL = "teleAcquirePortal";
    private static final String TELE_ACQUIRE_FLOOR = "teleAcquireFloor";
    private static final String TELE_DEPOSIT_VAULT = "teleDeposit_vault";
    private static final String TELE_PLACE_ON_SWITCH = "telePlaceOnSwitch";
    private static final String TELE_TOSS_TO_SWITCH = "teleTossToSwitch";
    private static final String TELE_PLACE_ON_SCALE = "telePlaceOnScale";
    private static final String TELE_TOSS_TO_SCALE = "teleTossToScale";
    private static final String END_CLIMB_RUNG = "endClimbWithoutAssist";/*changed*/
    private static final String END_CLIMB_ASSIST_TYPE = "endClimbAssistType";
    private static final String END_CLIMB_HEIGHT = "endClimbHeight";
    private static final String END_CLIMB_ON_ROBOT = "endAttachToRobot";/*changed*/

    public BenchmarkData() {
        // Initialize
        stringValuesMap.put(SCOUTER_NAME, "");
        stringValuesMap.put(TYPE_OF_DRIVE, "");
        stringValuesMap.put(TYPE_OF_WHEEL, "");
        stringValuesMap.put(PREFER_START_ONE, "");
        stringValuesMap.put(PREFER_START_TWO, "");
        stringValuesMap.put(PREFER_START_THREE, "");
        stringValuesMap.put(END_CLIMB_ASSIST_TYPE, "");

        intValuesMap.put(TEAM_NUMBER, 0);
        intValuesMap.put(NUMBER_OF_WHEELS, 0);
        intValuesMap.put(SPEED, 0);
        intValuesMap.put(HEIGHT, 0);
        intValuesMap.put(WEIGHT, 0);
        intValuesMap.put(GROUND_CLEARANCE, 0);
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SWITCH_PLACED, 0);
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED, 0);
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SCALE_PLACED, 0);
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SCALE_TOSSED, 0);
        intValuesMap.put(END_CLIMB_HEIGHT, 0);

        booleanValuesMap.put(CAN_START_WITH_CUBE, false);
        booleanValuesMap.put(AUTO_CROSS_LINE, false);
        booleanValuesMap.put(AUTO_ACQUIRE_PORTAL, false);
        booleanValuesMap.put(AUTO_ACQUIRE_FLOOR, false);
        booleanValuesMap.put(TELE_ACQUIRE_PORTAL, false);
        booleanValuesMap.put(TELE_ACQUIRE_FLOOR, false);
        booleanValuesMap.put(TELE_DEPOSIT_VAULT, false);
        booleanValuesMap.put(TELE_PLACE_ON_SWITCH, false);
        booleanValuesMap.put(TELE_TOSS_TO_SWITCH, false);
        booleanValuesMap.put(TELE_PLACE_ON_SCALE, false);
        booleanValuesMap.put(TELE_TOSS_TO_SCALE, false);
        booleanValuesMap.put(END_CLIMB_RUNG, false);
        booleanValuesMap.put(END_CLIMB_ON_ROBOT, false);
    }

    public String getScouterName() { return stringValuesMap.get(SCOUTER_NAME); }
    public void setScouterName(String _value) { stringValuesMap.put(SCOUTER_NAME, _value);}

    public int getTeamNumber() { return intValuesMap.get(TEAM_NUMBER); }
    public void setTeamNumber(int _value) {
        intValuesMap.put(TEAM_NUMBER, _value);
    }

    public String getTypeOfDrive() { return stringValuesMap.get(TYPE_OF_DRIVE); }
    public void setTypeOfDrive(String _value) {
        Log.e("Jaren", _value);

        stringValuesMap.put(TYPE_OF_DRIVE, _value);}

    public String getTypeOfWheel() { return stringValuesMap.get(TYPE_OF_WHEEL); }
    public void setTypeOfWheel(String _value) { stringValuesMap.put(TYPE_OF_WHEEL, _value);}

    public int getNumberOfWheels() { return intValuesMap.get(NUMBER_OF_WHEELS); }
    public void setNumberOfWheels(int _value) {
        intValuesMap.put(NUMBER_OF_WHEELS, _value);
    }

    public int getSpeed() { return intValuesMap.get(SPEED); }
    public void setSpeed(int _value) {
        intValuesMap.put(SPEED, _value);
    }

    public int getHeight() { return intValuesMap.get(HEIGHT); }
    public void setHeight(int _value) {
        intValuesMap.put(HEIGHT, _value);
    }

    public int getWeight() { return intValuesMap.get(WEIGHT); }
    public void setWeight(int _value) {
        intValuesMap.put(WEIGHT, _value);
    }

    public int getGroundClearance() { return intValuesMap.get(GROUND_CLEARANCE); }
    public void setGroundClearance(int _value) {
        intValuesMap.put(GROUND_CLEARANCE, _value);
    }

    public String getPreferedStartOne() { return stringValuesMap.get(PREFER_START_ONE); }
    public void setPreferedStartOne(String _value) { stringValuesMap.put(PREFER_START_ONE, _value);}

    public String getPreferedStartTwo() { return stringValuesMap.get(PREFER_START_TWO); }
    public void setPreferedStartTwo(String _value) { stringValuesMap.put(PREFER_START_TWO, _value);}

    public String getPreferedStartThree() { return stringValuesMap.get(PREFER_START_THREE); }
    public void setPreferedStartThree(String _value) { stringValuesMap.put(PREFER_START_THREE, _value);}

    public boolean isCanStartWithCube() { return booleanValuesMap.get(CAN_START_WITH_CUBE); }
    public void setCanStartWithCube(boolean _value) {
        booleanValuesMap.put(CAN_START_WITH_CUBE, _value);
    }

    public boolean isAutoCrossLine() { return booleanValuesMap.get(AUTO_CROSS_LINE); }
    public void setAutoCrossLine(boolean _value) {
        booleanValuesMap.put(AUTO_CROSS_LINE, _value);
    }

    public int getHowManyScoreSwitchPlaced() { return intValuesMap.get(AUTO_HOW_MANY_SCORE_SWITCH_PLACED); }
    public void setHowManyScoreSwitchPlaced(int _value) {
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SWITCH_PLACED, _value);
    }

    public int getHowManyScoreSwitchTossed() { return intValuesMap.get(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED); }
    public void setHowManyScoreSwitchTossed(int _value) {
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SWITCH_TOSSED, _value);
    }

    public int getHowManyScoreScalePlaced() { return intValuesMap.get(AUTO_HOW_MANY_SCORE_SCALE_PLACED); }
    public void setHowManyScoreScalePlaced(int _value) {
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SCALE_PLACED, _value);
    }

    public int getHowManyScoreScaleTossed() { return intValuesMap.get(AUTO_HOW_MANY_SCORE_SCALE_TOSSED); }
    public void setHowManyScoreScaleTossed(int _value) {
        intValuesMap.put(AUTO_HOW_MANY_SCORE_SCALE_TOSSED, _value);
    }

    public boolean isAutoAcquirePortal() { return booleanValuesMap.get(AUTO_ACQUIRE_PORTAL); }
    public void setAutoAcquirePortal(boolean _value) {
        booleanValuesMap.put(AUTO_ACQUIRE_PORTAL, _value);
    }

    public boolean isAutoAcquireFloor() { return booleanValuesMap.get(AUTO_ACQUIRE_FLOOR); }
    public void setAutoAcquireFloor(boolean _value) {
        booleanValuesMap.put(AUTO_ACQUIRE_FLOOR, _value);
    }

    public boolean isTeleAcquirePortal() { return booleanValuesMap.get(TELE_ACQUIRE_PORTAL); }
    public void setTeleAcquirePortal(boolean _value) {
        booleanValuesMap.put(TELE_ACQUIRE_PORTAL, _value);
    }

    public boolean isTeleAcquireFloor() { return booleanValuesMap.get(TELE_ACQUIRE_FLOOR); }
    public void setTeleAcquireFloor(boolean _value) {
        booleanValuesMap.put(TELE_ACQUIRE_FLOOR, _value);
    }

    public boolean isTeleDepositVault() { return booleanValuesMap.get(TELE_DEPOSIT_VAULT); }
    public void setTeleDepositVault(boolean _value) {
        booleanValuesMap.put(TELE_DEPOSIT_VAULT, _value);
    }

    public boolean isTelePlaceOnSwitch() { return booleanValuesMap.get(TELE_PLACE_ON_SWITCH); }
    public void setTelePlaceOnSwitch(boolean _value) {
        booleanValuesMap.put(TELE_PLACE_ON_SWITCH, _value);
    }

    public boolean isTeleTossToSwitch() { return booleanValuesMap.get(TELE_TOSS_TO_SWITCH); }
    public void setTeleTossToSwitch(boolean _value) {
        booleanValuesMap.put(TELE_TOSS_TO_SWITCH, _value);
    }

    public boolean isTelePlaceOnScale() { return booleanValuesMap.get(TELE_PLACE_ON_SCALE); }
    public void setTelePlaceOnScale(boolean _value) {
        booleanValuesMap.put(TELE_PLACE_ON_SCALE, _value);
    }

    public boolean isTeleTossToScale() { return booleanValuesMap.get(TELE_TOSS_TO_SCALE); }
    public void setTeleTossToScale(boolean _value) {
        booleanValuesMap.put(TELE_TOSS_TO_SCALE, _value);
    }

    public boolean isEndClimbRung() { return booleanValuesMap.get(END_CLIMB_RUNG); }
    public void setEndClimbRung(boolean _value) {
        booleanValuesMap.put(END_CLIMB_RUNG, _value);
    }

    public String getEndClimbAssistType() { return stringValuesMap.get(END_CLIMB_ASSIST_TYPE); }
    public void setEndClimbAssistType(String _value) { stringValuesMap.put(END_CLIMB_ASSIST_TYPE, _value);}

    public int getEndClimbHeight() { return intValuesMap.get(END_CLIMB_HEIGHT); }
    public void setEndClimbHeight(int _value) {
        intValuesMap.put(END_CLIMB_HEIGHT, _value);
    }

    public boolean isEndClimbOnRobot() { return booleanValuesMap.get(END_CLIMB_ON_ROBOT); }
    public void setEndClimbOnRobot(boolean _value) {
        booleanValuesMap.put(END_CLIMB_ON_ROBOT, _value);
    }
}
