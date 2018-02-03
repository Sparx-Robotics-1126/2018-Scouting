package sparx1126.com.powerup.data_components;

import sparx1126.com.powerup.custom_layouts.PlusMinusEditTextLinearLayout;

public class ScoutingData {
    private int teamnumber;
    private String allianceColor;

    public boolean isClimbunder15secs() {
        return climbunder15secs;
    }

    public void setClimbunder15secs(boolean climbunder15secs) {
        this.climbunder15secs = climbunder15secs;
    }

    private boolean climbunder15secs;
    public String getCanHold() {
        return canHold;
    }

    public void setCanHold(String canHold) {
        this.canHold = canHold;
    }

    private String canHold;
    public boolean isClimbOn() {
        return climbOn;
    }

    public void setClimbOn(boolean climbOn) {
        this.climbOn = climbOn;
    }

    private boolean climbOn;
    public String getClimbmethod() {
        return climbmethod;
    }

    public void setClimbmethod(String climbmethod) {
        this.climbmethod = climbmethod;
    }

    private String climbmethod;
    public boolean isPlayeddefense() {
        return playeddefense;
    }

    public void setPlayeddefense(boolean playeddefense) {
        this.playeddefense = playeddefense;
    }

    private boolean playeddefense;
    public int getTimesPickedfromfloor() {
        return timesPickedfromfloor;
    }

    public void setTimesPickedfromfloor(int timesPickedfromfloor) {
        this.timesPickedfromfloor = timesPickedfromfloor;
    }

    private int timesPickedfromfloor;
    public int getCubesfromplayers() {
        return cubesfromplayers;
    }

    public void setCubesfromplayers(int cubesfromplayers) {
        this.cubesfromplayers = cubesfromplayers;
    }

    private int cubesfromplayers;


    public int getTimesplacedexchange() {
        return timesplacedexchange;
    }

    public void setTimesplacedexchange(int timesplacedexchange) {
        this.timesplacedexchange = timesplacedexchange;
    }

    private int timesplacedexchange;
    public int getTimescoredscale() {
        return timescoredscale;
    }

    public void setTimescoredscale(int timescoredscale) {
        this.timescoredscale = timescoredscale;
    }

    private int timescoredscale;

    public int getTimesscoredswitch() {
        return timesscoredswitch;
    }

    public void setTimesscoredswitch(int timesscoredswitch) {
        this.timesscoredswitch = timesscoredswitch;
    }

    private int timesscoredswitch;

    public String getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(String startingPosition) {
        this.startingPosition = startingPosition;
    }

    private String startingPosition;

    public String getStartRightbtn() {
        return startRightbtn;
    }

    public void setStartRightbtn(String startRightbtn) {
        this.startRightbtn = startRightbtn;
    }

    private String startRightbtn;

    public String getStartLeftbtn() {
        return startLeftbtn;
    }

    public void setStartLeftbtn(String startLeftbtn) {
        this.startLeftbtn = startLeftbtn;
    }

    private String startLeftbtn;

    public String getStartCenterbtn() {
        return startCenterbtn;
    }

    public void setStartCenterbtn(String startCenterbtn) {
        this.startCenterbtn = startCenterbtn;
    }

    private String startCenterbtn;

    public boolean isScoreswitchcheck() {
        return scoreswitchcheck;
    }

    public void setScoreswitchcheck(boolean scoreswitchcheck) {
        this.scoreswitchcheck = scoreswitchcheck;
    }

    private boolean scoreswitchcheck;

    public boolean isScorescalecheck() {
        return scorescalecheck;
    }

    public void setScorescalecheck(boolean scorescalecheck) {
        this.scorescalecheck = scorescalecheck;
    }

    private boolean scorescalecheck;

    public boolean isPickedupcubecheck() {
        return pickedupcubecheck;
    }

    public void setPickedupcubecheck(boolean pickedupcubecheck) {
        this.pickedupcubecheck = pickedupcubecheck;
    }

    private boolean pickedupcubecheck;

    public boolean isCubexhangecheck() {
        return cubexhangecheck;
    }

    public void setCubexhangecheck(boolean cubexhangecheck) {
        this.cubexhangecheck = cubexhangecheck;
    }

    private boolean cubexhangecheck;

    public boolean isAutolinecheck() {
        return autolinecheck;
    }

    public void setAutolinecheck(boolean autolinecheck) {
        this.autolinecheck = autolinecheck;
    }

    private boolean autolinecheck;
    public int getMatchnum() {
        return matchnum;
    }

    public void setMatchnum(int matchnum) {
        this.matchnum = matchnum;
    }

    private int matchnum;

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
        returnString += "Scored Switch:" + scoreswitchcheck + "\n";
        returnString += "Scored Scale:" + scorescalecheck + "\n";
        returnString += "Picked Up Cube:" + pickedupcubecheck + "\n";
        returnString += "Placed cube in exchange:" + cubexhangecheck + "\n";
        returnString += "Starting postion:" + startingPosition + "\n";
        returnString += "Times scored switch:" + timesscoredswitch + "\n";
        returnString += "Times scored scale:" + timescoredscale + "\n";
        returnString += "Times placed cube in exchange:" + timesplacedexchange + "\n";
        returnString += "Times picked up cube from floor:" + timesPickedfromfloor + "\n";
        returnString += "Times cubes acquired from player:" + cubesfromplayers + "\n";
        returnString += "Effectively played defense:" + playeddefense + "\n";
        returnString += "Climb:" + climbmethod + "\n";
        returnString += "Can be climbed on:" + climbOn + "\n";
        returnString += "Can hold:" + canHold + "\n";
        returnString += "Can Climb under 15 seconds:" + climbunder15secs + "\n";






        return returnString;
    }
}
