package sparx1126.com.powerup.data_components;

public class ScoutingData {
    private int teamNumber;
    private String allianceColor;
    private int matchNumber;
    private boolean climbedUnder15Secs;
    private int numberOfRobotsHeld;
    private boolean climbedRung;
    private boolean climbedOnRobot;
    private boolean playedDefense;
    private int timesPickedFromFloor;
    private int cubesFromPlayers;
    private int timesPlacedInExchange;
    private int timesScoredOnScale;
    private int timesScoredOnSwitch;
    private boolean autoStartedLeft;
    private boolean autoStartedCenter;
    private boolean autoStartedRight;
    private boolean autoScoredSwitch;
    private boolean autoScoredScale;
    private boolean autoPickedUpCube;
    private boolean autoCubeExchange;
    private boolean autoLineCrossed;

    public boolean isClimbedUnder15Secs() {
        return climbedUnder15Secs;
    }
    public void setClimbedUnder15Secs(boolean climbedUnder15Secs) {
        this.climbedUnder15Secs = climbedUnder15Secs;
    }

    public int getNumberOfRobotsHeld() {
        return numberOfRobotsHeld;
    }
    public void setNumberOfRobotsHeld(int numberOfRobotsHeld) {
        this.numberOfRobotsHeld = numberOfRobotsHeld;
    }

    public boolean getClimbedRung() {
        return climbedRung;
    }
    public void setClimbedRung(boolean climbedRung) {
        this.climbedRung = climbedRung;
    }

    public boolean getClimbedOnRobot() {
        return climbedOnRobot;
    }
    public void setClimbedOnRobot(boolean climbedOnRobot) {
        this.climbedOnRobot = climbedOnRobot;
    }

    public boolean isPlayedDefense() {
        return playedDefense;
    }
    public void setPlayedDefense(boolean playedDefense) {
        this.playedDefense = playedDefense;
    }

    public int getTimesPickedFromFloor() {
        return timesPickedFromFloor;
    }
    public void setTimesPickedFromFloor(int timesPickedFromFloor) {
        this.timesPickedFromFloor = timesPickedFromFloor;
    }

    public int getCubesFromPlayers() {
        return cubesFromPlayers;
    }
    public void setCubesFromPlayers(int cubesFromPlayers) {
        this.cubesFromPlayers = cubesFromPlayers;
    }

    public int getTimesPlacedInExchange() {
        return timesPlacedInExchange;
    }
    public void setTimesPlacedInExchange(int timesPlacedInExchange) {
        this.timesPlacedInExchange = timesPlacedInExchange;
    }

    public int getTimesScoredOnScale() {
        return timesScoredOnScale;
    }
    public void setTimesScoredOnScale(int timesScoredOnScale) {
        this.timesScoredOnScale = timesScoredOnScale;
    }

    public int getTimesScoredOnSwitch() {
        return timesScoredOnSwitch;
    }
    public void setTimesScoredOnSwitch(int timesScoredOnSwitch) {
        this.timesScoredOnSwitch = timesScoredOnSwitch;
    }

    public boolean getAutoStartedLeft() {
        return autoStartedLeft;
    }
    public void setAutoStartedLeft(boolean autoStartedLeft) {
        this.autoStartedLeft = autoStartedLeft;
    }

    public boolean getAutoStartedCenter() {
        return autoStartedCenter;
    }
    public void setAutoStartedCenter(boolean autoStartedCenter) {
        this.autoStartedCenter = autoStartedCenter;
    }

    public boolean getAutoStartedRight() {
        return autoStartedRight;
    }
    public void setAutoStartedRight(boolean autoStartedRight) {
        this.autoStartedRight = autoStartedRight;
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

    public boolean isAutoLineCrossed() {
        return autoLineCrossed;
    }
    public void setAutoLineCrossed(boolean autoLineCrossed) {
        this.autoLineCrossed = autoLineCrossed;
    }

    public int getMatchNumber() {
        return matchNumber;
    }
    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getAllianceColor() {
        return allianceColor;
    }
    public void setAllianceColor(String allianceColor) {
        this.allianceColor = allianceColor;
    }

    public int getTeamNumber() {
        return teamNumber;
    }
    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String toString() {
        String returnString = "";

        returnString += "Team Number:" + String.valueOf(teamNumber) +"\n";
        returnString += "Alliance Color:" + allianceColor + "\n";
        returnString += "Match Number:" + String.valueOf(matchNumber) +"\n";
        returnString += "Crossed Auto Line:" + autoLineCrossed + "\n";
        returnString += "Scored Switch:" + autoScoredSwitch + "\n";
        returnString += "Scored Scale:" + autoScoredScale + "\n";
        returnString += "Picked Up Cube:" + autoPickedUpCube + "\n";
        returnString += "Placed cube in exchange:" + autoCubeExchange + "\n";
        returnString += "Started Left:" + autoStartedLeft + "\n";
        returnString += "Started Center:" + autoStartedCenter + "\n";
        returnString += "Started Right:" + autoStartedRight + "\n";
        returnString += "Times scored switch:" + timesScoredOnSwitch + "\n";
        returnString += "Times scored scale:" + timesScoredOnScale + "\n";
        returnString += "Times placed cube in exchange:" + timesPlacedInExchange + "\n";
        returnString += "Times picked up cube from floor:" + timesPickedFromFloor + "\n";
        returnString += "Times cubes acquired from player:" + cubesFromPlayers + "\n";
        returnString += "Effectively played defense:" + playedDefense + "\n";
        returnString += "Climb Rung:" + climbedRung + "\n";
        returnString += "Climb On Robot:" + climbedOnRobot + "\n";
        returnString += "numberOfRobotsHeld:" + numberOfRobotsHeld + "\n";
        returnString += "Can Climb under 15 seconds:" + climbedUnder15Secs + "\n";

        return returnString;
    }
}
