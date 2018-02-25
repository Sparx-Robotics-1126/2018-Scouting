package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.custom_layouts.CustomExpandableListAdapter;
import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;


public class View extends AppCompatActivity {
    private static final String TAG = "View ";
    private static DataCollection dataCollection;

    private EditText teamnumber;
    private Button teamNumberButton;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        dataCollection = DataCollection.getInstance();

        teamnumber = findViewById(R.id.teamNumber);
        teamNumberButton = findViewById(R.id.teamNumberButton);
        teamNumberButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                int teamNumber = Integer.getInteger(teamNumberButton.getText().toString());

                HashMap<String, List<String>> expandableListDetail = getData(teamNumber);
                List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(View.this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

            }
        });
        expandableListView = findViewById(R.id.expandableListView);
    }

    private HashMap<String, List<String>> getData(int _teamNumber) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        expandableListDetail.put("Benchmark", getBenchmarkData(_teamNumber));
        expandableListDetail.put("Scouting", getScoutingData(_teamNumber));

        return expandableListDetail;
    }

    private List<String> getScoutingData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        List<ScoutingData> datas = dataCollection.getScoutingDatas(_teamNumber);

        int autoLineCrossed = 0;
        int autoScoredSwitch = 0;
        int autoPickedUpCube = 0;
        int autoScoredScale = 0;
        int autoCubeExchange = 0;
        float cubesPlacedOnSwitch = 0;
        float cubesPlacedOnScale = 0;
        float cubesPlacedInExchange = 0;
        float cubesPickedUpFromFloor = 0;
        float cubesAcquireFromPlayer = 0;
        int playedDefenseEffectively = 0;
        int climbedRung = 0;
        int climbedOnRobot = 0;
        int canBeClimbOn = 0;
        float numberOfRobotsHeld = 0;
        int climbedUnder15Secs = 0;
        int numberOfdatas = datas.size();

        rtnList.add("<font color=\"black\"><b>Matches scouted: </b></font>" + numberOfdatas);
        for (ScoutingData data : datas) {
            if (data.isAutoLineCrossed()) {
                autoLineCrossed++;
            }

            if (data.isAutoScoredSwitch()) {
                autoScoredSwitch++;
            }

            if (data.isAutoPickedUpCube()) {
                autoPickedUpCube++;
            }

            if (data.isAutoScoredScale()) {
                autoScoredScale++;
            }

            if (data.isAutoCubeExchange()) {
                autoCubeExchange++;
            }

            cubesPlacedOnSwitch += data.getCubesPlacedOnSwitch();
            cubesPlacedOnScale += data.getCubesPlacedOnScale();
            cubesPlacedInExchange += data.getCubesPlacedInExchange();
            cubesPickedUpFromFloor += data.getCubesPickedUpFromFloor();
            cubesAcquireFromPlayer += data.getCubesAcquireFromPlayer();

            if (data.isPlayedDefenseEffectively()) {
                playedDefenseEffectively++;
            }

            if (data.isClimbedRung()) {
                climbedRung++;
            }

            if (data.isClimbedOnRobot()) {
                climbedOnRobot++;
            }

            if (data.isCanBeClimbOn()) {
                canBeClimbOn++;
            }

            numberOfRobotsHeld += data.getNumberOfRobotsHeld();

            if (data.isClimbedUnder15Secs()) {
                climbedUnder15Secs++;
            }
        }

        if (numberOfdatas != 0) {
            rtnList.add("<font color=\"black\"><b>AUTO:</b></font>");
            rtnList.add("<font color=\"black\"><b></b></font>");
            rtnList.add("<font color=\"black\"><b> Auto Line Crossed: </b></font>" + autoLineCrossed + " times");
            rtnList.add("<font color=\"black\"><b> Auto Scored Switch: </b></font>" + autoScoredSwitch + " times");
            rtnList.add("<font color=\"black\"><b> Auto Picked Up Cube: </b></font>" + autoPickedUpCube + " times");
            rtnList.add("<font color=\"black\"><b> Auto Scored Scale: </b></font>" + autoScoredScale + " times");
            rtnList.add("<font color=\"black\"><b> Auto Cube Exchange: </b></font>" + autoCubeExchange + " times");
            cubesPlacedOnSwitch = cubesPlacedOnSwitch / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Cubes Placed On Switch: </b></font>" + cubesPlacedOnSwitch + " average");
            cubesPlacedOnScale = cubesPlacedOnScale / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Cubes placed on scale: </b></font>" + cubesPlacedOnScale + " average");
            cubesPlacedInExchange = cubesPlacedInExchange / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Cubes placed in exchange: </b></font>" + cubesPlacedInExchange + " average");
            cubesPickedUpFromFloor = cubesPickedUpFromFloor / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Cubes picked up from floor: </b></font>" + cubesPickedUpFromFloor + " average");
            cubesAcquireFromPlayer = cubesAcquireFromPlayer / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Cubes acquire fom player: </b></font>" + cubesAcquireFromPlayer + " average");
            rtnList.add("<font color=\"black\"><b> Played defense effectively: </b></font>" + playedDefenseEffectively + " times");
            rtnList.add("<font color=\"black\"><b> Climbed rung: </b></font>" + climbedRung + " times");
            rtnList.add("<font color=\"black\"><b> Climbed on robot: </b></font>" + climbedOnRobot + " times");
            rtnList.add("<font color=\"black\"><b> Climbed onto: </b></font>" + canBeClimbOn + " times");
            numberOfRobotsHeld = numberOfRobotsHeld / numberOfdatas;
            rtnList.add("<font color=\"black\"><b> Number of robots held: </b></font>" + numberOfRobotsHeld + " average");
            rtnList.add("<font color=\"black\"><b> Climed under 15 seconds: </b></font>" + climbedUnder15Secs + " times");
        }

        return rtnList;
    }

    private List<String> getBenchmarkData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        BenchmarkData data = dataCollection.getBenchmarkData(_teamNumber);

        if(data != null) {
            rtnList.add("<font color=\"black\"><b>GENERAL:</b></font>");
            rtnList.add("<font color=\"black\"><b></b></font>");
            rtnList.add("<font color=\"black\"><b> Type Of Drive: </b></font>" + data.getTypeOfDrive());
            rtnList.add("<font color=\"black\"><b> Speed: </b></font>" + data.getSpeed() + " ft/s");
            rtnList.add("<font color=\"black\"><b> Height: </b></font>" + data.getHeight() + " ft");
            rtnList.add("<font color=\"black\"><b> Weight: </b></font>" + data.getWeight() + " llb");

            rtnList.add("<font color=\"black\"><b>AUTO:</b></font>");
            rtnList.add("<font color=\"black\"><b></b></font>");
            String preferStart = "";
            if (data.isPreferStartLeft()) {
                preferStart = "Left ";
            }
            if (data.isPreferStartCenter()) {
                preferStart += "Center ";
            }
            if (data.isPreferStartRight()) {
                preferStart += "Right ";
            }
            if (preferStart.isEmpty()) {
                preferStart = "None ";
            }
            rtnList.add("<font color=\"black\"><b> Prefer Start: </b></font>" + preferStart);

            String canStartWithCube = "No";
            if (data.isCanStartWithCube()) {
                canStartWithCube = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Can start with cube: </b></font>" + canStartWithCube);

            String autoCrossLine = "No";
            if (data.isAutoCrossLine()) {
                autoCrossLine = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Auto Can Cross Line: </b></font>" + autoCrossLine);

            String autoScoreSwitch = "No";
            if (data.isAutoScoreSwitch()) {
                autoScoreSwitch = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Auto can score switch: </b></font>" + autoScoreSwitch);

            String autoScoreScale = "No";
            if (data.isAutoScoreScale()) {
                autoScoreScale = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Auto can score scale: </b></font>" + autoScoreScale);

            rtnList.add("<font color=\"black\"><b>TELE:</b></font>");
            rtnList.add("<font color=\"black\"><b></b></font>");
            String acquireFloor = "No";
            if (data.isAcquireFloor()) {
                acquireFloor = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Can acquire floor: </b></font>" + acquireFloor);

            String isAcquirePortal = "No";
            if (data.isAcquirePortal()) {
                isAcquirePortal = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Can acquire portal: </b></font>" + isAcquirePortal);

            String isDepositVault = "No";
            if (data.isDepositVault()) {
                isDepositVault = "Yes";
            }
            rtnList.add("<font color=\"black\"><b> Can deposit in vault: </b></font>" + isDepositVault);

            String scoreSwitch = "No";
            if (data.isPlaceOnSwitch()) {
                scoreSwitch = "By Placement";
            } else if (data.isTossToSwitch()) {
                scoreSwitch = "By Tossing";
            }
            rtnList.add("<font color=\"black\"><b> Score on switch: </b></font>" + scoreSwitch);

            String scoreScale = "No";
            if (data.isPlaceOnScale()) {
                scoreScale = "By Placement";
            } else if (data.isTossToScale()) {
                scoreScale = "By Tossing";
            }
            rtnList.add("<font color=\"black\"><b> Score on switch: </b></font>" + scoreScale);

            String prefAcquire = "No";
            if (data.isPreferAcquireFloor()) {
                prefAcquire = "Floor";
            } else if (data.isPreferAcquirePortal()) {
                prefAcquire = "Portal";
            }
            rtnList.add("<font color=\"black\"><b> Prefer Acquire: </b></font>" + prefAcquire);

            rtnList.add("<font color=\"black\"><b>END GAME:</b></font>");
            rtnList.add("<font color=\"black\"><b></b></font>");
            String climbRung = "No";
            if (data.isClimbRung()) {
                climbRung = "Yes ";
            }

            rtnList.add("<font color=\"black\"><b> Can climb rung: </b></font>" + climbRung);

            String hasRungs = "No";
            if (data.isHasRungs()) {
                hasRungs = "Yes ";
            }
            rtnList.add("<font color=\"black\"><b> Has rungs: </b></font>" + hasRungs);

            rtnList.add("<font color=\"black\"><b> Climb Height: </b></font>" + data.getClimbHeight() + " inches");

            String climbOnRobot = "No";
            if (data.isClimbOnRobot()) {
                climbOnRobot = "Yes ";
            }
            rtnList.add("<font color=\"black\"><b> Can climb on robot: </b></font>" + climbOnRobot);
        }

        return rtnList;
    }
}


