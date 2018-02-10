package sparx1126.com.powerup.data_components;

public class ScoutingData {
    private int teamnumber;
    private String allianceColor;
    private int matchnum;
    private boolean climbunder15secs;
    private int numRobotsHeld;
    private boolean climbRung;
    private boolean climbOnRobot;
    private boolean playeddefense;
    private int timesPickedfromfloor;
    private int cubesfromplayers;
    private int timesplacedexchange;
    private int timescoredscale;
    private int timesscoredswitch;
    private boolean autoStartedLeft;
    private boolean autoStartedCenter;
    private boolean autoStartedRight;
    private boolean autoScoredSwitch;
    private boolean autoScoredScale;
    private boolean autoPickedUpCube;
    private boolean autoCubeExchange;
    private boolean autolinecheck;

    public boolean isClimbunder15secs() {
        return climbunder15secs;
    }

    public void setClimbunder15secs(boolean climbunder15secs) {
        this.climbunder15secs = climbunder15secs;
    }

    public int getNumRobotsHeld() {
        return numRobotsHeld;
    }

    public void setNumRobotsHeld(int numRobotsHeld) {
        this.numRobotsHeld = numRobotsHeld;
    }

    public boolean getClimbRung() {
        return climbRung;
    }

    public void setClimbRung(boolean climbRung) {
        this.climbRung = climbRung;
    }

    public boolean getClimbOnRobot() {
        return climbOnRobot;
    }

    public void setClimbOnRobot(boolean climbOnRobot) {
        this.climbOnRobot = climbOnRobot;
    }

    public boolean isPlayeddefense() {
        return playeddefense;
    }

    public void setPlayeddefense(boolean playeddefense) {
        this.playeddefense = playeddefense;
    }

    public int getTimesPickedfromfloor() {
        return timesPickedfromfloor;
    }

    public void setTimesPickedfromfloor(int timesPickedfromfloor) {
        this.timesPickedfromfloor = timesPickedfromfloor;
    }

    public int getCubesfromplayers() {
        return cubesfromplayers;
    }

    public void setCubesfromplayers(int cubesfromplayers) {
        this.cubesfromplayers = cubesfromplayers;
    }

    public int getTimesplacedexchange() {
        return timesplacedexchange;
    }

    public void setTimesplacedexchange(int timesplacedexchange) {
        this.timesplacedexchange = timesplacedexchange;
    }

    public int getTimescoredscale() {
        return timescoredscale;
    }

    public void setTimescoredscale(int timescoredscale) {
        this.timescoredscale = timescoredscale;
    }

    public int getTimesscoredswitch() {
        return timesscoredswitch;
    }

    public void setTimesscoredswitch(int timesscoredswitch) {
        this.timesscoredswitch = timesscoredswitch;
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

    public boolean isAutolinecheck() {
        return autolinecheck;
    }

    public void setAutolinecheck(boolean autolinecheck) {
        this.autolinecheck = autolinecheck;
    }

    public int getMatchnum() {
        return matchnum;
    }

    public void setMatchnum(int matchnum) {
        this.matchnum = matchnum;
    }

    public String getAllianceColor() {
        return allianceColor;
    }

    public void setAllianceColor(String allianceColor) {
        this.allianceColor = allianceColor;
    }

    public int getTeamnumber() {
        return teamnumber;
    }

    public void setTeamnumber(int teamnumber) {
        this.teamnumber = teamnumber;
    }

    public String toString() {
        String returnString = "";

        returnString += "Team Number:" + String.valueOf(teamnumber) +"\n";
        returnString += "Alliance Color:" + allianceColor + "\n";
        returnString += "Match Number:" + String.valueOf(matchnum) +"\n";
        returnString += "Crossed Auto Line:" + autolinecheck + "\n";
        returnString += "Scored Switch:" + autoScoredSwitch + "\n";
        returnString += "Scored Scale:" + autoScoredScale + "\n";
        returnString += "Picked Up Cube:" + autoPickedUpCube + "\n";
        returnString += "Placed cube in exchange:" + autoCubeExchange + "\n";
        returnString += "Started Left:" + autoStartedLeft + "\n";
        returnString += "Started Center:" + autoStartedCenter + "\n";
        returnString += "Started Right:" + autoStartedRight + "\n";
        returnString += "Times scored switch:" + timesscoredswitch + "\n";
        returnString += "Times scored scale:" + timescoredscale + "\n";
        returnString += "Times placed cube in exchange:" + timesplacedexchange + "\n";
        returnString += "Times picked up cube from floor:" + timesPickedfromfloor + "\n";
        returnString += "Times cubes acquired from player:" + cubesfromplayers + "\n";
        returnString += "Effectively played defense:" + playeddefense + "\n";
        returnString += "Climb Rung:" + climbRung + "\n";
        returnString += "Climb On Robot:" + climbOnRobot + "\n";
        returnString += "numRobotsHeld:" + numRobotsHeld + "\n";
        returnString += "Can Climb under 15 seconds:" + climbunder15secs + "\n";

        return returnString;
    }
}
