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
        List<ScoutingData> scoutingDatas = dataCollection.getScoutingDatas(_teamNumber);
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

        List<String> scouting = new ArrayList<>();
        int numberOfScoutingDatas = scoutingDatas.size();
        scouting.add("<font color=\"black\"><b>Matches scouted: </b></font>" + numberOfScoutingDatas);
        for (ScoutingData sd : scoutingDatas) {
            if (sd.isAutoLineCrossed()) {
                autoLineCrossed++;
            }

            if (sd.isAutoScoredSwitch()) {
                autoScoredSwitch++;
            }

            if (sd.isAutoPickedUpCube()) {
                autoPickedUpCube++;
            }

            if (sd.isAutoScoredScale()) {
                autoScoredScale++;
            }

            if (sd.isAutoCubeExchange()) {
                autoCubeExchange++;
            }

            cubesPlacedOnSwitch += sd.getCubesPlacedOnSwitch();
            cubesPlacedOnScale += sd.getCubesPlacedOnScale();
            cubesPlacedInExchange += sd.getCubesPlacedInExchange();
            cubesPickedUpFromFloor += sd.getCubesPickedUpFromFloor();
            cubesAcquireFromPlayer += sd.getCubesAcquireFromPlayer();

            if (sd.isPlayedDefenseEffectively()) {
                playedDefenseEffectively++;
            }

            if (sd.isClimbedRung()) {
                climbedRung++;
            }

            if (sd.isClimbedOnRobot()) {
                climbedOnRobot++;
            }

            if (sd.isCanBeClimbOn()) {
                canBeClimbOn++;
            }

            numberOfRobotsHeld += sd.getNumberOfRobotsHeld();

            if (sd.isClimbedUnder15Secs()) {
                climbedUnder15Secs++;
            }
        }

        if (numberOfScoutingDatas != 0) {
            scouting.add("<font color=\"black\"><b>AUTO:</b></font>");
            scouting.add("<font color=\"black\"><b></b></font>");
            scouting.add("<font color=\"black\"><b> Auto Line Crossed: </b></font>" + autoLineCrossed + " times");
            scouting.add("<font color=\"black\"><b> Auto Scored Switch: </b></font>" + autoScoredSwitch + " times");
            scouting.add("<font color=\"black\"><b> Auto Picked Up Cube: </b></font>" + autoPickedUpCube + " times");
            scouting.add("<font color=\"black\"><b> Auto Scored Scale: </b></font>" + autoScoredScale + " times");
            scouting.add("<font color=\"black\"><b> Auto Cube Exchange: </b></font>" + autoCubeExchange + " times");
            cubesPlacedOnSwitch = cubesPlacedOnSwitch / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> Cubes Placed On Switch: </b></font>" + cubesPlacedOnSwitch + " average");
            cubesPlacedOnScale = cubesPlacedOnScale / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> cubesPlacedOnScale: </b></font>" + cubesPlacedOnScale + " average");
            cubesPlacedInExchange = cubesPlacedInExchange / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> cubesPlacedInExchange: </b></font>" + cubesPlacedInExchange + " average");
            cubesPickedUpFromFloor = cubesPickedUpFromFloor / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> cubesPickedUpFromFloor: </b></font>" + cubesPickedUpFromFloor + " average");
            cubesAcquireFromPlayer = cubesAcquireFromPlayer / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> cubesAcquireFromPlayer: </b></font>" + cubesAcquireFromPlayer + " average");
            scouting.add("<font color=\"black\"><b> playedDefenseEffectively: </b></font>" + playedDefenseEffectively + " times");
            scouting.add("<font color=\"black\"><b> climbedRung: </b></font>" + climbedRung + " times");
            scouting.add("<font color=\"black\"><b> climbedOnRobot: </b></font>" + climbedOnRobot + " times");
            scouting.add("<font color=\"black\"><b> canBeClimbOn: </b></font>" + canBeClimbOn + " times");
            numberOfRobotsHeld = numberOfRobotsHeld / numberOfScoutingDatas;
            scouting.add("<font color=\"black\"><b> numberOfRobotsHeld: </b></font>" + numberOfRobotsHeld + " average");
            scouting.add("<font color=\"black\"><b> climbedUnder15Secs: </b></font>" + climbedUnder15Secs + " times");
        }
        expandableListDetail.put("Scouting", scouting);

        return expandableListDetail;
    }
}


