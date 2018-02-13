package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import sparx1126.com.powerup.custom_layouts.CustomExpandableListAdapter;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;


public class View extends AppCompatActivity {
    private static final String TAG = "View";

    //temporaryteamlist
    List<String> teamlistnew;
    private DataCollection dataCollection;

    private EditText teamnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        teamlistnew = new ArrayList<>();
        teamlistnew.add("1126");
        teamlistnew.add("123");

        dataCollection = DataCollection.getInstance();

        teamnumber = findViewById(R.id.teamnumber);
        teamnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s.toString());
                if (teamlistnew.contains(s.toString())) {
                    ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
                    HashMap<String, List<String>> expandableListDetail = getData();
                    List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(View.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                }
            }
        });
    }

    private HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        Map<Integer, List<ScoutingData>> teamsScouted = dataCollection.getScoutingDataMap();

        // for testing
        if(teamsScouted.containsKey(1126)) {
            List<ScoutingData> teamDatas = teamsScouted.get(1126);
            int climbunder15secs = 0;
            int numRobotsHeld = 0;
            int climbRung = 0;
            int climbOnRobot = 0;
            int playeddefense = 0;
            int timesPickedfromfloor = 0;
            int cubesfromplayers = 0;
            int timesplacedexchange = 0;
            int timescoredscale = 0;
            int timesscoredswitch = 0;
            int autoStartedLeft = 0;
            int autoStartedCenter = 0;
            int autoStartedRight = 0;
            int autoScoredSwitch = 0;
            int autoScoredScale = 0;
            int autoPickedUpCube = 0;
            int autoCubeExchange = 0;
            int autolinecheck = 0;



            List<String> scouting = new ArrayList<>();
            scouting.add("<font color=\"black\"><b>Matches scouted: </b></font>" + teamDatas.size());
            for (ScoutingData sd :  teamDatas) {
                if(sd.isClimbunder15secs()) {
                    climbunder15secs++;
                }

                    numRobotsHeld += sd.getNumRobotsHeld();
                if(sd.getClimbRung()) {
                    climbRung++;
                }
                if(sd.getClimbOnRobot()) {
                    climbOnRobot++;
                }
                if(sd.isPlayeddefense()) {
                    playeddefense++;
                }
                 timesPickedfromfloor +=  timesPickedfromfloor;
                 cubesfromplayers += cubesfromplayers;
                 timesplacedexchange +=  timesplacedexchange;
                 timescoredscale += timescoredscale;
                 timesscoredswitch += timesscoredswitch;
                if(sd.getAutoStartedLeft()) {
                    autoStartedLeft++;
                }
                
//                 autoStartedCenter++;
//                 autoStartedRight;
//                 autoScoredSwitch;
//                 autoScoredScale;
//                 autoPickedUpCube;
//                 autoCubeExchange;
//                 autolinecheck;

            }

            }
        else {
            Log.e(TAG, "team not found");
        }

        /*


        float hoppersDumped = 0;
        float gearsScoredRight = 0;
        float gearsScoredCenter = 0;
        float gearsScoredLeft = 0;
        float didScale = 0;
        float gearsScored = 0;
        float gearsDelivered = 0;
        float gearsCollectedFromFloor = 0;
        float gearsCollectedFromHuman = 0;
        float crossedBaseline = 0;
        int highAutoShooting = 0;
        int lowAutoShooting = 0;
        int noAutoShooting = 0;
        int highTeleop = 0;
        String highTeleopString = "";
        int lowTeleop = 0;
        String lowTeleopString = "";
        float fuelInHighCycle = 0;
        //float numberOfHighCycles = 0;        <<<<<< NEED TO ADD OR I NEED GLASSES
        float fuelInLowCycle = 0;
        float numberOfLowCycles = 0;
        float fuelCollectedHuman = 0;
        float fuelCollectedHopper = 0;
        float fuelCollectedFloor = 0;
        int highGoalAcc = 0;
        String highGoalAccString = "";

        for (ScoutingData sd : displayedInfo.getScoutingDatas()) {
            hoppersDumped += sd.getHoppersDumped();
            gearsScored += sd.getGearsScored();
            gearsCollectedFromFloor += sd.getGearsCollectedFromFloor();
            gearsCollectedFromHuman += sd.getGearsFromHuman();
            fuelInHighCycle += sd.getBallsInHighCycle();
            fuelInLowCycle += sd.getFuelInLowCycle();
            fuelCollectedHuman += sd.getBallsFromHuman();
            fuelCollectedHopper += sd.getBallsFromHopper();
            fuelCollectedFloor += sd.getBallsFromFloor();

            if (Objects.equals(sd.getHighGoalAccuracy(), "Great")) {
                highGoalAcc += 2;
            } else if (Objects.equals(sd.getHighGoalAccuracy(), "OK")) {
                highGoalAcc++;
            } else if (Objects.equals(sd.getHighGoalAccuracy(), "Poor")) {
                //USELESS BUT I ADDED FOR SOME REASON
            }

            if (Objects.equals(sd.getAutoShooting(), "Shoots High")) {
                highAutoShooting++;
            } else if (Objects.equals(sd.getAutoShooting(), "Shoots Low")) {
                lowAutoShooting++;
            } else if (Objects.equals(sd.getAutoShooting(), "Doesn't Shoot")) {
                noAutoShooting++;
            }


            if (sd.isCrossedBaseline()) {
                crossedBaseline++;
            }
            if (sd.isGearScoredRightAuto()) {
                gearsScoredRight++;
            }
            if (sd.isGearScoredCenterAuto()) {
                gearsScoredCenter++;
            }
            if (sd.isGearScoredLeftAuto()) {
                gearsScoredLeft++;
            }
            if (sd.isDidScale()) {
                didScale++;
            }

        }
        if (displayedInfo.getScoutingDatas().size() != 0) {
            hoppersDumped = hoppersDumped / displayedInfo.getScoutingDatas().size();
            gearsScored = gearsScored / displayedInfo.getScoutingDatas().size();
            gearsDelivered = gearsDelivered / displayedInfo.getScoutingDatas().size();
            gearsCollectedFromFloor = gearsCollectedFromFloor / displayedInfo.getScoutingDatas().size();
            gearsCollectedFromHuman = gearsCollectedFromHuman / displayedInfo.getScoutingDatas().size();
            highTeleop = (highTeleop / displayedInfo.getScoutingDatas().size());
            numberOfLowCycles = (numberOfLowCycles / displayedInfo.getScoutingDatas().size());
            fuelCollectedHuman = (fuelCollectedHuman / displayedInfo.getScoutingDatas().size());
            fuelCollectedHopper = (fuelCollectedHopper / displayedInfo.getScoutingDatas().size());
            fuelCollectedFloor = (fuelCollectedFloor / displayedInfo.getScoutingDatas().size());
            if (highTeleop == 0) {
                highTeleopString = "Never";
            } else if ((highTeleop > 0) && (highTeleop < 1)) {
                highTeleopString = "rarely";
            } else if (highTeleop == 1) {
                highTeleopString = "Sometimes";
            } else if ((highTeleop > 1) && (highTeleop < 2)) {
                highTeleopString = "Often";
            } else if (highTeleop == 2) {
                highTeleopString = "Very often";
            }
            if (lowTeleop == 0) {
                lowTeleopString = "Never";
            } else if ((lowTeleop > 0) && (highTeleop < 1)) {
                lowTeleopString = "rarely";
            } else if (lowTeleop == 1) {
                lowTeleopString = "Sometimes";
            } else if ((lowTeleop > 1) && (highTeleop < 2)) {
                lowTeleopString = "Often";
            } else if (lowTeleop == 2) {
                lowTeleopString = "Very often";
            }
            if (highGoalAcc == 0) {
                highGoalAccString = "Really bad";
            } else if ((highGoalAcc > 0) && (highGoalAcc < 1)) {
                highGoalAccString = "poor";
            } else if (highGoalAcc == 1) {
                highGoalAccString = "ok";
            } else if ((highGoalAcc > 1) && (highGoalAcc < 2)) {
                highGoalAccString = "pretty decent";
            } else if (highGoalAcc == 2) {
                highGoalAccString = "very good";
            }
            highAutoShooting = (highAutoShooting / displayedInfo.getScoutingDatas().size()) * 100;
            lowAutoShooting = (lowAutoShooting / displayedInfo.getScoutingDatas().size()) * 100;
            noAutoShooting = (noAutoShooting / displayedInfo.getScoutingDatas().size()) * 100;
            crossedBaseline = (crossedBaseline / displayedInfo.getScoutingDatas().size()) * 100;
            gearsScoredRight = (gearsScoredRight / displayedInfo.getScoutingDatas().size()) * 100;
            gearsScoredCenter = (gearsScoredCenter / displayedInfo.getScoutingDatas().size()) * 100;
            gearsScoredLeft = (gearsScoredLeft / displayedInfo.getScoutingDatas().size()) * 100;
            didScale = (didScale / displayedInfo.getScoutingDatas().size()) * 100;
            fuelInHighCycle = (fuelInHighCycle / displayedInfo.getScoutingDatas().size()) * 100;
            fuelInLowCycle = (fuelInLowCycle / displayedInfo.getScoutingDatas().size()) * 100;
        }

        scouting.add("<font color=\"black\"><b>AUTO</b></font>");
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>Percent of matches baseline crossed:  </b></font>" + crossedBaseline + "%");
        scouting.add("<font color=\"black\"><b>Average hoppers dumped:  </b></font>" + hoppersDumped);
        scouting.add("<font color=\"black\"><b>Percent of gears scored right:  </b></font>" + gearsScoredRight + "%");
        scouting.add("<font color=\"black\"><b>Percent of gears scored center:  </b></font>" + gearsScoredCenter + "%");
        scouting.add("<font color=\"black\"><b>Percent of gears scored left:  </b></font>" + gearsScoredLeft + "%");
        scouting.add("<font color=\"black\"><b>Percent of times shot high:  </b></font>" + highAutoShooting + "%");
        scouting.add("<font color=\"black\"><b>Percent of times shot low:  </b></font>" + lowAutoShooting + "%");
        scouting.add("<font color=\"black\"><b>Percent of times did not shoot:  </b></font>" + noAutoShooting + "%");

        scouting.add("<font color=\"black\"><b>TELEOP</b></font>");
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>Gears</b></font>");
        scouting.add("<font color=\"black\"><b>Average gears scored:  </b></font>" + gearsScored);
        scouting.add("<font color=\"black\"><b>Average gears collected from floor:  </b></font>" + gearsCollectedFromFloor);
        scouting.add("<font color=\"black\"><b>Average gears collected from human:  </b></font>" + gearsCollectedFromHuman);
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>FUEL</b></font>");
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>Scores in high goal </b></font>" + highTeleopString);
        scouting.add("<font color=\"black\"><b>Scores in low goal </b></font>" + lowTeleopString);
        scouting.add("<font color=\"black\"><b>Average fuel in high cycle: </b></font>" + fuelInHighCycle);
        scouting.add("<font color=\"black\"><b>Average amount of high cycles: </b></font>" + "(not currently in code)");
        scouting.add("<font color=\"black\"><b>Average fuel in low cycle: </b></font>" + fuelInLowCycle);
        scouting.add("<font color=\"black\"><b>Average number of low cycles: </b></font>" + numberOfLowCycles);
        scouting.add("<font color=\"black\"><b>Average fuel collected from human: </b></font>" + fuelCollectedHuman);
        scouting.add("<font color=\"black\"><b>Average fuel collected from hopper: </b></font>" + fuelCollectedHopper);
        scouting.add("<font color=\"black\"><b>Average fuel collected from floor: </b></font>" + fuelCollectedFloor);
        scouting.add("<font color=\"black\"><b>High goal accuracy: </b></font>" + highGoalAccString);
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>END GAME</b></font>");
        scouting.add("<font color=\"black\"><b></b></font>");

        scouting.add("<font color=\"black\"><b>Percent of matches scaled:  </b></font>" + didScale + "%");
        scouting.add("<font color=\"black\"><b>Usually scaled from: JK not adding this because it's a manually entered string</b></font>");


        List<String> scaling = new ArrayList<>();
        if (!benchmarked) {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>");
        } else {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>" + benchmarkingData.isAbilityScaleBenchButton());
        }
        if (benchmarkingData.getPreferredPlacesScaleInput().isEmpty()) {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>");
        } else {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>" + benchmarkingData.getPreferredPlacesScaleInput());
        }

        List<String> auto = new ArrayList<>();
        String autoAbilities = "";
        if (benchmarkingData.getAutoAbilitiesBench() != null) {
            autoAbilities = benchmarkingData.getAutoAbilitiesBench();
        }
        auto.add("<font color=\"black\"><b>Can </b></font>" + autoAbilities);

        List<String> otherComments = new ArrayList<>();
        if (benchmarkingData.getCommentsBench().isEmpty()) {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>");
        } else {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>" + benchmarkingData.getCommentsBench());
        }
        expandableListDetail.put("Drives", drives);
        expandableListDetail.put("Shooting", shooting);
        expandableListDetail.put("Gears", gears);
        // expandableListDetail.put("Low Goal", lowGoal);
        expandableListDetail.put("Scaling", scaling);
        expandableListDetail.put("Auto", auto);
        expandableListDetail.put("Other Comments", otherComments);
        expandableListDetail.put("Scouting", scouting);*/
        return expandableListDetail;
    }
}

