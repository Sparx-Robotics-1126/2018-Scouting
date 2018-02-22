package sparx1126.com.powerup.data_components;

import org.json.JSONException;
import org.json.JSONObject;

public class ScoutingData {
    // keys
    private static final String MATCH_NUMBER = "matchNumber";
    private static final String TEAM_NUMBER = "teamNumber";
    private static final String AUTO_LINE_CROSSED = "autoLineCrossed";
    private static final String AUTO_SCORE_SCALE = "autoScoredSwitch";
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
    private int matchNumber;
    private int teamNumber;

    private boolean autoLineCrossed;
    private boolean autoScoredSwitch;
    private boolean autoScoredScale;
    private boolean autoPickedUpCube;
    private boolean autoCubeExchange;

    private int cubesPlacedOnSwitch;
    private int cubesPlacedOnScale;
    private int cubesPlacedInExchange;
    private int cubesPickedUpFromFloor;
    private int cubesAcquireFromPlayer;
    private boolean playedDefenseEffectively;

    private boolean climbedRung;
    private boolean climbedOnRobot;
    private boolean canBeClimbOn;
    private int numberOfRobotsHeld;
    private boolean climbedUnder15Secs;

    public ScoutingData() {
        jsonObj = new JSONObject();
        try {
            jsonObj.put(MATCH_NUMBER, matchNumber);
            jsonObj.put(TEAM_NUMBER, teamNumber);
            jsonObj.put(AUTO_LINE_CROSSED, autoLineCrossed);
            jsonObj.put(AUTO_SCORE_SCALE, autoScoredSwitch);
            jsonObj.put(AUTO_SCORED_SCALE, autoScoredScale);
            jsonObj.put(AUTO_CUBE_EXCHANGE, autoCubeExchange);
            jsonObj.put(CUBES_PLACED_ON_SWITCH, cubesPlacedOnSwitch);
            jsonObj.put(CUBES_PLACED_ON_SCALE, cubesPlacedOnScale);
            jsonObj.put(CUBES_PLACED_IN_EXCHANGE, cubesPlacedInExchange);
            jsonObj.put(CUBES_PICKED_UP_FROM_FLOOR, cubesPickedUpFromFloor);
            jsonObj.put(CUBES_ACQUIRE_FROM_PLAYER, cubesAcquireFromPlayer);
            jsonObj.put(PLAYED_DEFENSE_EFFECTIVELY, playedDefenseEffectively);
            jsonObj.put(CLIMBED_RUNG, climbedRung);
            jsonObj.put(CLIMBED_ON_ROBOT, climbedOnRobot);
            jsonObj.put(CAN_BE_CLIMB_ON, canBeClimbOn);
            jsonObj.put(NUMBER_OF_ROBOTS_HELD, numberOfRobotsHeld);
            jsonObj.put(CLIMBED_UNDER_15_SECS, climbedUnder15Secs);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public boolean isAutoLineCrossed() {
        return autoLineCrossed;
    }

    public void setAutoLineCrossed(boolean autoLineCrossed) {
        this.autoLineCrossed = autoLineCrossed;
    }

    public boolean isAutoScoredSwitch() {
        return autoScoredSwitch;
    }

    public void setAutoScoredSwitch(boolean autoScoredSwitch) {
        this.autoScoredSwitch = autoScoredSwitch;
    }

    public boolean isAutoScoredScale() {
        return autoScoredScale;
    }

    public void setAutoScoredScale(boolean autoScoredScale) {
        this.autoScoredScale = autoScoredScale;
    }

    public boolean isAutoPickedUpCube() {
        return autoPickedUpCube;
    }

    public void setAutoPickedUpCube(boolean autoPickedUpCube) {
        this.autoPickedUpCube = autoPickedUpCube;
    }

    public boolean isAutoCubeExchange() {
        return autoCubeExchange;
    }

    public void setAutoCubeExchange(boolean autoCubeExchange) {
        this.autoCubeExchange = autoCubeExchange;
    }

    public int getCubesPlacedOnSwitch() {
        return cubesPlacedOnSwitch;
    }

    public void setCubesPlacedOnSwitch(int cubesPlacedOnSwitch) {
        this.cubesPlacedOnSwitch = cubesPlacedOnSwitch;
    }

    public int getCubesPlacedOnScale() {
        return cubesPlacedOnScale;
    }

    public void setCubesPlacedOnScale(int cubesPlacedOnScale) {
        this.cubesPlacedOnScale = cubesPlacedOnScale;
    }

    public int getCubesPlacedInExchange() {
        return cubesPlacedInExchange;
    }

    public void setCubesPlacedInExchange(int cubesPlacedInExchange) {
        this.cubesPlacedInExchange = cubesPlacedInExchange;
    }

    public int getCubesPickedUpFromFloor() {
        return cubesPickedUpFromFloor;
    }

    public void setCubesPickedUpFromFloor(int cubesPickedUpFromFloor) {
        this.cubesPickedUpFromFloor = cubesPickedUpFromFloor;
    }

    public int getCubesAcquireFromPlayer() {
        return cubesAcquireFromPlayer;
    }

    public void setCubesAcquireFromPlayer(int cubesAcquireFromPlayer) {
        this.cubesAcquireFromPlayer = cubesAcquireFromPlayer;
    }

    public boolean isPlayedDefenseEffectively() {
        return playedDefenseEffectively;
    }

    public void setPlayedDefenseEffectively(boolean playedDefenseEffectively) {
        this.playedDefenseEffectively = playedDefenseEffectively;
    }

    public boolean isClimbedRung() {
        return climbedRung;
    }

    public void setClimbedRung(boolean climbedRung) {
        this.climbedRung = climbedRung;
    }

    public boolean isClimbedOnRobot() {
        return climbedOnRobot;
    }

    public void setClimbedOnRobot(boolean climbedOnRobot) {
        this.climbedOnRobot = climbedOnRobot;
    }

    public boolean isCanBeClimbOn() {
        return canBeClimbOn;
    }

    public void setCanBeClimbOn(boolean canBeClimbOn) {
        this.canBeClimbOn = canBeClimbOn;
    }

    public int getNumberOfRobotsHeld() {
        return numberOfRobotsHeld;
    }

    public void setNumberOfRobotsHeld(int numberOfRobotsHeld) {
        this.numberOfRobotsHeld = numberOfRobotsHeld;
    }

    public boolean isClimbedUnder15Secs() {
        return climbedUnder15Secs;
    }

    public void setClimbedUnder15Secs(boolean climbedUnder15Secs) {
        this.climbedUnder15Secs = climbedUnder15Secs;
    }

    @Override
    public String toString() {
        return jsonObj.toString();
    }
}
