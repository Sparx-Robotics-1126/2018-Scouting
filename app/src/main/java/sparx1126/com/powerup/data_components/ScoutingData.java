package sparx1126.com.powerup.data_components;

public class ScoutingData {
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
    private int cubesAdquireFromPlayer;
    private boolean playedDefenseEffectivily;

    private boolean climbedRung;
    private boolean climbedOnRobot;
    private boolean canBeClimdOn;
    private int numberOfRobotsHeld;
    private boolean climbedUnder15Secs;

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

    public int getCubesAdquireFromPlayer() {
        return cubesAdquireFromPlayer;
    }

    public void setCubesAdquireFromPlayer(int cubesAdquireFromPlayer) {
        this.cubesAdquireFromPlayer = cubesAdquireFromPlayer;
    }

    public boolean isPlayedDefenseEffectivily() {
        return playedDefenseEffectivily;
    }

    public void setPlayedDefenseEffectivily(boolean playedDefenseEffectivily) {
        this.playedDefenseEffectivily = playedDefenseEffectivily;
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

    public boolean isCanBeClimdOn() {
        return canBeClimdOn;
    }

    public void setCanBeClimdOn(boolean canBeClimdOn) {
        this.canBeClimdOn = canBeClimdOn;
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

    public String toString() {
        String returnString = "";

        returnString += "Team Number:" + String.valueOf(teamNumber) +"\n";
        returnString += "Match Number:" + String.valueOf(matchNumber) +"\n";
        returnString += "Crossed Auto Line:" + autoLineCrossed + "\n";
        returnString += "Scored Switch:" + autoScoredSwitch + "\n";
        returnString += "Scored Scale:" + autoScoredScale + "\n";
        returnString += "Picked Up Cube:" + autoPickedUpCube + "\n";
        returnString += "Placed cube in exchange:" + autoCubeExchange + "\n";
        returnString += "Times scored switch:" + cubesPlacedOnSwitch + "\n";
        returnString += "Times scored scale:" + cubesPlacedOnScale + "\n";
        returnString += "Times placed cube in exchange:" + cubesPlacedInExchange + "\n";
        returnString += "Times picked up cube from floor:" + cubesPickedUpFromFloor + "\n";
        returnString += "Times cubes acquired from player:" + cubesAdquireFromPlayer + "\n";
        returnString += "Effectively played defense:" + playedDefenseEffectivily + "\n";
        returnString += "Climb Rung:" + climbedRung + "\n";
        returnString += "Climb On Robot:" + climbedOnRobot + "\n";
        returnString += "numberOfRobotsHeld:" + numberOfRobotsHeld + "\n";
        returnString += "Can Climb under 15 seconds:" + climbedUnder15Secs + "\n";

        return returnString;
    }
}
