package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import sparx1126.com.powerup.custom_layouts.CustomExpandableListAdapter;


public class View extends AppCompatActivity {

    private EditText teamnumber;
    //temporaryteamlist
    List<String> teamlistnew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        teamlistnew.add("1126");
        teamlistnew.add("123");

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
        /*TeamData displayedInfo = TeamData.getCurrentTeam();
        BenchmarkingData benchmarkingData = displayedInfo.getBenchmarkingData();
        boolean benchmarked = benchmarkingData.isBenchmarkingWasDoneButton();
        List<String> drives = new ArrayList<>();
        if (benchmarkingData.getDriveSystem().isEmpty()) {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> ");
        } else {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> " + benchmarkingData.getDriveSystem());
        }

        String drivesSpeed = "";
        if (benchmarkingData.getDrivesSpeed() != Double.MAX_VALUE) {
            drivesSpeed = String.valueOf(benchmarkingData.getDrivesSpeed());
        }
        if (drivesSpeed.isEmpty()) {
            drives.add("<font color=\"black\"><b>Speed: </b></font> ");
        } else {
            drives.add("<font color=\"black\"><b>Speed: </b></font> " + drivesSpeed + "m/s");
        }
        if (!benchmarked) {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>");
        } else {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>" + benchmarkingData.isCanPlayDefenseBenchButton());
        }

        List<String> shooting = new ArrayList<>();
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>" + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>" + benchmarkingData.isAbilityToShootLowGoalBenchButton());

        }
        if (benchmarkingData.getTypeOfShooterBenchInput().isEmpty()) {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>" + benchmarkingData.getTypeOfShooterBenchInput());
        }
        String ballsPerSecond = "";
        if (benchmarkingData.getBallsPerSecondBenchInput() != Double.MAX_VALUE) {
            ballsPerSecond = String.valueOf(benchmarkingData.getBallsPerSecondBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls per second: </b></font>" + ballsPerSecond);
        String ballsInCycleInput = "";
        if (benchmarkingData.getBallsInCycleBenchInput() != Integer.MAX_VALUE) {
            ballsInCycleInput = String.valueOf(benchmarkingData.getBallsInCycleBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls in one high cycle: </b></font>" + ballsInCycleInput);
        String cycleTimeHighBenchInput = "";
        if (benchmarkingData.getCycleTimeHighBenchInput() != 2147483647) {
            cycleTimeHighBenchInput = String.valueOf(benchmarkingData.getCycleTimeHighBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Cycle time High: </b></font>" + cycleTimeHighBenchInput);
        String cycleTimeLowBenchInput = "";
        if (benchmarkingData.getCycleTimeLowBenchInput() != Integer.MAX_VALUE) {
            cycleTimeLowBenchInput = String.valueOf(benchmarkingData.getCycleTimeLowBenchInput());
        }
        String cycleNumberLowBenchInput = "";
        if (benchmarkingData.getCycleNumberLowBenchInput() != Integer.MAX_VALUE) {
            cycleNumberLowBenchInput = String.valueOf(benchmarkingData.getCycleNumberLowBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Number of low cycles: </b></font>" + cycleNumberLowBenchInput);
        shooting.add("<font color=\"black\"><b>Cycle time low: </b></font>" + cycleTimeLowBenchInput);
        String shootingRangeBenchInput = "";
        if (benchmarkingData.getShootingRangeBenchInput() != Double.MAX_VALUE) {
            shootingRangeBenchInput = String.valueOf(benchmarkingData.getShootingRangeBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Shooting range: </b></font>" + shootingRangeBenchInput);
        if (benchmarkingData.getPreferredShootingLocationBenchInput().isEmpty()) {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>" + benchmarkingData.getPreferredShootingLocationBenchInput());
        }

        String accuracyHighBenchInput = "";
        if (benchmarkingData.getAccuracyHighBenchInput() != Double.MAX_VALUE) {
            accuracyHighBenchInput = String.valueOf(benchmarkingData.getAccuracyHighBenchInput());
        }
        if (accuracyHighBenchInput.isEmpty()) {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput);
        } else {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput + "%");
        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>" + benchmarkingData.isPickupBallHopperBenchButton());

        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>" + benchmarkingData.isPickupBallFloorBenchButton());

        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>" + benchmarkingData.isPickupBallHumanBenchButton());

        }
        String preferredBall = "";
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallHuman")) {
            preferredBall = "Human Player";
        }
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallHopper")) {
            preferredBall = "Hopper";
        }
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallFloor")) {
            preferredBall = "Floor";
        }
        shooting.add("<font color=\"black\"><b>Prefers to get balls from: </b></font>" + preferredBall);
        String maximumBallCapacity = "";
        if (benchmarkingData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            maximumBallCapacity = Integer.toString(benchmarkingData.getMaximumBallCapacityBenchInput());
        }
        if (maximumBallCapacity.isEmpty()) {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>" + maximumBallCapacity + " balls");
        }
        List<String> gears = new ArrayList<>();
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>" + benchmarkingData.isCanScoreGearsBenchButton());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>" + benchmarkingData.isCanGearLeftBench());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>" + benchmarkingData.isCanGearCenterBench());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>" + benchmarkingData.isCanGearRightBench());
        }
        String preferredGearScoring = "";
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearRight")) {
            preferredGearScoring = "Right";
        }
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearCenter")) {
            preferredGearScoring = "Center";
        }
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearLeft")) {
            preferredGearScoring = "Left";
        }
        gears.add("<font color=\"black\"><b>Prefers to score gears: </b></font>" + preferredGearScoring);
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>" + benchmarkingData.isPickupGearFloorBenchButton());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>" + benchmarkingData.isPickupGearRetrievalBenchButton());
        }


        String gearPickup = "";
        if (benchmarkingData.getPickupGearPreferred().equals("radioFloor")) {
            gearPickup = "Floor";
        }
        if (benchmarkingData.getPickupGearPreferred().equals("radioZone")) {
            gearPickup = "Retrieval Zone";
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Has an active gear system: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Has an active gear system: </b></font>" + benchmarkingData.isHasActiveGearSystemButton());
        }
        gears.add("<font color=\"black\"><b>Preferred gear pickup location: </b></font>" + gearPickup);
        String cycleTimeGearsBenchInput = "";
        if (benchmarkingData.getCycleTimeGearsBenchInput() != Integer.MAX_VALUE) {
            cycleTimeGearsBenchInput = String.valueOf(benchmarkingData.getCycleTimeGearsBenchInput());
        }
        gears.add("<font color=\"black\"><b>Cycle time: </b></font>" + cycleTimeGearsBenchInput);

        List<String> scouting = new ArrayList<>();
        scouting.add("<font color=\"black\"><b>Matches scouted: </b></font>" + displayedInfo.getScoutingDatas().size());
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

