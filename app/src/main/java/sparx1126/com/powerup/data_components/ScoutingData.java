package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScoutingData extends JsonData {
    // keys
    public static final String SCOUTER_NAME = "gen_scouterName";
    public static final String MATCH_NUMBER = "gen_matchNumber";
    public static final String TEAM_NUMBER = "gen_teamNumber";
    public static final String STARTED_LEFT_POSITION = "start_leftPosition";
    public static final String STARTED_CENTER_POSITION = "start_centerPosition";
    public static final String STARTED_RIGHT_POSITION = "start_rightPosition";
    public static final String AUTO_LINE_CROSSED = "auto_LineCrossed";
    public static final String AUTO_ATTEMPT_SCORED_SWITCH = "auto_AttemptScoredSwitch";
    public static final String AUTO_SCORED_SWITCH = "auto_ScoredSwitch";
    public static final String AUTO_ATTEMPT_SCORED_SCALE = "auto_AttemptScoredScale";
    public static final String AUTO_SCORED_SCALE = "auto_ScoredScale";
    public static final String AUTO_PICKED_UP_CUBE = "auto_PickedUpCube";
    public static final String AUTO_CUBE_EXCHANGE = "auto_PlacedCubeExchange";/*changed*/
    public static final String ATTEMPT_CUBES_PLACED_ON_SWITCH = "tele_AttemptCubesPlacedOnSwitch";
    public static final String CUBES_PLACED_ON_SWITCH = "tele_CubesPlacedOnSwitch";
    public static final String ATTEMPT_CUBES_PLACED_ON_SCALE = "tele_AttemptCubesPlacedOnScale";
    public static final String CUBES_PLACED_ON_SCALE = "tele_CubesPlacedOnScale";
    public static final String CUBES_PLACED_IN_EXCHANGE = "tele_CubesPlacedInExchange";
    public static final String CUBES_PICKED_UP_FROM_FLOOR = "tele_CubesPickedUpFromFloor";
    public static final String CUBES_ACQUIRE_FROM_PLAYER = "tele_CubesAcquireFromPlayer";
    public static final String PLAYED_DEFENSE = "tele_PlayedDefense";
    public static final String PLAYED_DEFENSE_EFFECTIVELY = "tele_PlayedDefenseEffectively";
    public static final String PLAYED_DEFENSE_INEFFECTIVELY = "tele_PlayedDefenseIneffectiveness";
    public static final String ENDED_ON_PLATFORM = "end_OnPlatform";
    public static final String ATTEMPT_CLIMB = "end_AttemptToClimb";
    public static final String CLIMBED_RUNG = "end_ClimbedRung";
    public static final String CLIMBED_UNDER_15_SECS = "end_ClimbedUnder15Secs";
    public static final String CAN_BE_CLIMB_ON = "end_AssistedOthersInClimb";
    public static final String NUMBER_OF_ROBOTS_HELD = "end_NumberOfRobotsAssisted";
    public static final String CLIMBED_ON_ROBOT = "end_ClimbedOnRobot";
    public static final String DISABLED = "end_disabled";
    public static final String COMMENTS = "gen_comments";

    public ScoutingData() {
        // Initialize
        stringValuesMap.put(SCOUTER_NAME, "");
        stringValuesMap.put(COMMENTS, "");

        intValuesMap.put(MATCH_NUMBER, 0);
        intValuesMap.put(TEAM_NUMBER, 0);
        intValuesMap.put(ATTEMPT_CUBES_PLACED_ON_SWITCH, 0);
        intValuesMap.put(CUBES_PLACED_ON_SWITCH, 0);
        intValuesMap.put(ATTEMPT_CUBES_PLACED_ON_SCALE, 0);
        intValuesMap.put(CUBES_PLACED_ON_SCALE, 0);
        intValuesMap.put(CUBES_PLACED_IN_EXCHANGE, 0);
        intValuesMap.put(CUBES_PICKED_UP_FROM_FLOOR, 0);
        intValuesMap.put(CUBES_ACQUIRE_FROM_PLAYER, 0);
        intValuesMap.put(NUMBER_OF_ROBOTS_HELD, 0);
        booleanValuesMap.put(AUTO_LINE_CROSSED, false);
        booleanValuesMap.put(AUTO_ATTEMPT_SCORED_SWITCH, false);
        booleanValuesMap.put(AUTO_SCORED_SWITCH, false);
        booleanValuesMap.put(AUTO_ATTEMPT_SCORED_SCALE, false);
        booleanValuesMap.put(AUTO_SCORED_SCALE, false);
        booleanValuesMap.put(AUTO_PICKED_UP_CUBE, false);
        booleanValuesMap.put(AUTO_CUBE_EXCHANGE, false);
        booleanValuesMap.put(STARTED_LEFT_POSITION, false);
        booleanValuesMap.put(STARTED_CENTER_POSITION, false);
        booleanValuesMap.put(STARTED_RIGHT_POSITION, false);
        booleanValuesMap.put(PLAYED_DEFENSE, false);
        booleanValuesMap.put(PLAYED_DEFENSE_EFFECTIVELY, false);
        booleanValuesMap.put(PLAYED_DEFENSE_INEFFECTIVELY, false);
        booleanValuesMap.put(ENDED_ON_PLATFORM, false);
        booleanValuesMap.put(ATTEMPT_CLIMB, false);
        booleanValuesMap.put(CLIMBED_RUNG, false);
        booleanValuesMap.put(CLIMBED_UNDER_15_SECS, false);
        booleanValuesMap.put(CLIMBED_ON_ROBOT, false);
        booleanValuesMap.put(CAN_BE_CLIMB_ON, false);
        booleanValuesMap.put(DISABLED, false);
    }

    public String getScouterName() { return stringValuesMap.get(SCOUTER_NAME); }
    public void setScouterName(String _value) { stringValuesMap.put(SCOUTER_NAME, _value);}

    public int getMatchNumber() { return intValuesMap.get(MATCH_NUMBER); }
    public void setMatchNumber(int _value) {
        intValuesMap.put(MATCH_NUMBER, _value);
    }

    public int getTeamNumber() { return intValuesMap.get(TEAM_NUMBER); }
    public void setTeamNumber(int _value) {
        intValuesMap.put(TEAM_NUMBER, _value);
    }

    public boolean isAutoLineCrossed() { return booleanValuesMap.get(AUTO_LINE_CROSSED); }
    public void setAutoLineCrossed(boolean _value) {
        booleanValuesMap.put(AUTO_LINE_CROSSED, _value);
    }

    public boolean isAutoAttemptScoredSwitch() { return booleanValuesMap.get(AUTO_ATTEMPT_SCORED_SWITCH); }
    public void setAutoAttemptScoredSwitch(boolean _value) {
        booleanValuesMap.put(AUTO_ATTEMPT_SCORED_SWITCH, _value);
    }

    public boolean isAutoScoredSwitch() { return booleanValuesMap.get(AUTO_SCORED_SWITCH); }
    public void setAutoScoredSwitch(boolean _value) {
        booleanValuesMap.put(AUTO_SCORED_SWITCH, _value);
    }

    public boolean isAutoAttemptScoredScale() { return booleanValuesMap.get(AUTO_ATTEMPT_SCORED_SCALE); }
    public void setAutoAttemptScoredScale(boolean _value) {
        booleanValuesMap.put(AUTO_ATTEMPT_SCORED_SCALE, _value);
    }

    public boolean isAutoScoredScale() { return booleanValuesMap.get(AUTO_SCORED_SCALE); }
    public void setAutoScoredScale(boolean _value) {
        booleanValuesMap.put(AUTO_SCORED_SCALE, _value);
    }

    public boolean isAutoPickedUpCube() { return booleanValuesMap.get(AUTO_PICKED_UP_CUBE); }
    public void setAutoPickedUpCube(boolean _value) {
        booleanValuesMap.put(AUTO_PICKED_UP_CUBE, _value);
    }

    public boolean isAutoCubeExchange() { return booleanValuesMap.get(AUTO_CUBE_EXCHANGE); }
    public void setAutoCubeExchange(boolean _value) {
        booleanValuesMap.put(AUTO_CUBE_EXCHANGE, _value);
    }

    public boolean isStartedLeftPosition() { return booleanValuesMap.get(STARTED_LEFT_POSITION); }
    public void setStartedLeftPosition(boolean _value) {
        booleanValuesMap.put(STARTED_LEFT_POSITION, _value);
    }

    public boolean isStartedCenterPosition() { return booleanValuesMap.get(STARTED_CENTER_POSITION); }
    public void setStartedCenterPosition(boolean _value) {
        booleanValuesMap.put(STARTED_CENTER_POSITION, _value);
    }

    public boolean isStartedRightPosition() { return booleanValuesMap.get(STARTED_RIGHT_POSITION); }
    public void setStartedRightPosition(boolean _value) {
        booleanValuesMap.put(STARTED_RIGHT_POSITION, _value);
    }

    public int getAttemptCubesPlacedOnSwitch() { return intValuesMap.get(ATTEMPT_CUBES_PLACED_ON_SWITCH); }
    public void setAttemptCubesPlacedOnSwitch(int _value) {
        intValuesMap.put(ATTEMPT_CUBES_PLACED_ON_SWITCH, _value);
    }

    public int getCubesPlacedOnSwitch() { return intValuesMap.get(CUBES_PLACED_ON_SWITCH); }
    public void setCubesPlacedOnSwitch(int _value) {
        intValuesMap.put(CUBES_PLACED_ON_SWITCH, _value);
    }

    public int getAttemptCubesPlacedOnScale() { return intValuesMap.get(ATTEMPT_CUBES_PLACED_ON_SCALE); }
    public void setAttemptCubesPlacedOnScale(int _value) {
        intValuesMap.put(ATTEMPT_CUBES_PLACED_ON_SCALE, _value);
    }

    public int getCubesPlacedOnScale() { return intValuesMap.get(CUBES_PLACED_ON_SCALE); }
    public void setCubesPlacedOnScale(int _value) {
        intValuesMap.put(CUBES_PLACED_ON_SCALE, _value);
    }

    public int getCubesPlacedInExchange() { return intValuesMap.get(CUBES_PLACED_IN_EXCHANGE); }
    public void setCubesPlacedInExchange(int _value) {
        intValuesMap.put(CUBES_PLACED_IN_EXCHANGE, _value);
    }

    public int getCubesPickedUpFromFloor() { return intValuesMap.get(CUBES_PICKED_UP_FROM_FLOOR); }
    public void setCubesPickedUpFromFloor(int _value) {
        intValuesMap.put(CUBES_PICKED_UP_FROM_FLOOR, _value);
    }

    public int getCubesAcquireFromPlayer() { return intValuesMap.get(CUBES_ACQUIRE_FROM_PLAYER); }
    public void setCubesAcquireFromPlayer(int _value) {
        intValuesMap.put(CUBES_ACQUIRE_FROM_PLAYER, _value);
    }

    public boolean isPlayedDefense() { return booleanValuesMap.get(PLAYED_DEFENSE); }
    public void setPlayedDefense(boolean _value) {
        booleanValuesMap.put(PLAYED_DEFENSE, _value);
    }

    public boolean isPlayedDefenseEffectively() { return booleanValuesMap.get(PLAYED_DEFENSE_EFFECTIVELY); }
    public void setPlayedDefenseEffectively(boolean _value) {
        booleanValuesMap.put(PLAYED_DEFENSE_EFFECTIVELY, _value);
    }

    public boolean isPlayedDefenseIneffectively() { return booleanValuesMap.get(PLAYED_DEFENSE_INEFFECTIVELY); }
    public void setPlayedDefenseIneffectively(boolean _value) {
        booleanValuesMap.put(PLAYED_DEFENSE_INEFFECTIVELY, _value);
    }

    public boolean isEndedOnPlatform() { return booleanValuesMap.get(ENDED_ON_PLATFORM); }
    public void setEndedOnPlatform(boolean _value) {
        booleanValuesMap.put(ENDED_ON_PLATFORM, _value);
    }

    public boolean isAttemptToClimb() { return booleanValuesMap.get(ATTEMPT_CLIMB); }
    public void setAttemptToClimb(boolean _value) {
        booleanValuesMap.put(ATTEMPT_CLIMB, _value);
    }

    public boolean isClimbedRung() { return booleanValuesMap.get(CLIMBED_RUNG); }
    public void setClimbedRung(boolean _value) {
        booleanValuesMap.put(CLIMBED_RUNG, _value);
    }

    public boolean isClimbedUnder15Secs() { return booleanValuesMap.get(CLIMBED_UNDER_15_SECS); }
    public void setClimbedUnder15Secs(boolean _value) {
        booleanValuesMap.put(CLIMBED_UNDER_15_SECS, _value);
    }

    public boolean isClimbedOnRobot() { return booleanValuesMap.get(CLIMBED_ON_ROBOT); }
    public void setClimbedOnRobot(boolean _value) {
        booleanValuesMap.put(CLIMBED_ON_ROBOT, _value);
    }

    public boolean isCanBeClimbOn() { return booleanValuesMap.get(CAN_BE_CLIMB_ON); }
    public void setCanBeClimbOn(boolean _value) {
        booleanValuesMap.put(CAN_BE_CLIMB_ON, _value);
    }

    public int getNumberOfRobotsHeld() { return intValuesMap.get(NUMBER_OF_ROBOTS_HELD); }
    public void setNumberOfRobotsHeld(int _value) {
        intValuesMap.put(NUMBER_OF_ROBOTS_HELD, _value);
    }

    public boolean isDisabled() { return booleanValuesMap.get(DISABLED); }
    public void setDisabled(boolean _value) {
        booleanValuesMap.put(DISABLED, _value);
    }

    public String getComments() { return stringValuesMap.get(COMMENTS); }
    public void setComments(String _value) { stringValuesMap.put(COMMENTS, _value);}
}
