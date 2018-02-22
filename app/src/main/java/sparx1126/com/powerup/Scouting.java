package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Map;

import sparx1126.com.powerup.custom_layouts.PlusMinusEditTextLinearLayout;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.DataCollection;

public class Scouting extends AppCompatActivity {
    private TextView teamnum;
    private LinearLayout teamLayout;
    private  LinearLayout allianceLayout;
    private TextView allianceColor;
    private EditText matchnum;
    private Button submitbutton;
    private LinearLayout autoLayout;
    private CheckBox autolinecheck;
    private CheckBox scoreswitchcheck;
    private CheckBox scorescalecheck;
    private CheckBox pickupcubecheck;
    private CheckBox cubexchangecheck;
    private RadioButton startLeftbtn;
    private RadioButton startCenterbtn;
    private RadioButton startRightbtn;
    private LinearLayout teleLayout;
    private PlusMinusEditTextLinearLayout timeScoreswitch;
    private PlusMinusEditTextLinearLayout timeScorescale;
    private PlusMinusEditTextLinearLayout timesPlacedexhange;
    private PlusMinusEditTextLinearLayout timesPickedfromFloor;
    private PlusMinusEditTextLinearLayout cubesfromplayers;
    private CheckBox playeddefense;
    private LinearLayout climbLayout;
    private RadioButton climbRung;
    private RadioButton climbRobot;
    private RadioButton climbDoesnt;
    private CheckBox climbOn;
    private RadioButton hold1;
    private RadioButton hold2;
    private Button matchButton;
    private DataCollection freeData;
    private CheckBox climbunder15secs;
    private static GoogleDriveNetworking googleDrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);
        googleDrive = GoogleDriveNetworking.getInstance();
        freeData = DataCollection.getInstance();


        matchnum = findViewById(R.id.matchnumimput);
        matchButton = findViewById(R.id.matchButton);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchnumberstr = matchnum.getText().toString();

                Map<String, BlueAllianceMatch> matchesinevent = freeData.getEventMatches();
                if(matchesinevent == null)

                for (BlueAllianceMatch value : matchesinevent.values()) {
                    if(value.getMatchNumber().equals(matchnumberstr)) {
                        allianceLayout.setVisibility(View.VISIBLE);
                        teamLayout.setVisibility(View.VISIBLE);
                        autoLayout.setVisibility(View.VISIBLE);
                        teleLayout.setVisibility(View.VISIBLE);
                        climbLayout.setVisibility(View.VISIBLE);

                    }
                }

            }
        });


        teamLayout = findViewById(R.id.teamLayout);
        teamLayout.setVisibility(View.INVISIBLE);
        teamnum = findViewById(R.id.teamnumber);
        allianceColor = findViewById(R.id.allianceColor);
        //allianceColor.setVisibility(View.INVISIBLE);
        allianceLayout =findViewById(R.id.allianceLayout);
        allianceLayout.setVisibility(View.INVISIBLE);
        autoLayout =findViewById(R.id.autoLayout);
        autoLayout.setVisibility(View.INVISIBLE);
        autolinecheck = findViewById(R.id.autolinecheck);
        //autolinecheck.setVisibility(View.INVISIBLE);
        scorescalecheck = findViewById(R.id.autoScoredScale);
        //scorescalecheck.setVisibility(View.INVISIBLE);
        scoreswitchcheck = findViewById(R.id.autoScoredSwitch);
        //scoreswitchcheck.setVisibility(View.INVISIBLE);
        pickupcubecheck = findViewById(R.id.pickupcubecheck);
        //pickupcubecheck.setVisibility(View.INVISIBLE);
        cubexchangecheck = findViewById(R.id.cubexchangecheck);
        //cubexchangecheck.setVisibility(View.INVISIBLE);
        teleLayout =findViewById(R.id.teleLayout);
        teleLayout.setVisibility(View.INVISIBLE);
        timeScoreswitch = findViewById(R.id.timesscoredswitchpicker);
        //timeScoreswitch.setVisibility(View.INVISIBLE);
        timeScorescale = findViewById(R.id.timesscoredscalepicker);
        //timeScorescale.setVisibility(View.INVISIBLE);
        timesPlacedexhange = findViewById(R.id.timesplacedexchangepicker);
        //timesPlacedexhange.setVisibility(View.INVISIBLE);
        timesPickedfromFloor = findViewById(R.id.cubesfromfloorpicker);
        //timesPickedfromFloor.setVisibility(View.INVISIBLE);
        cubesfromplayers = findViewById(R.id.cubesfromfloorpicker);
        //cubesfromplayers.setVisibility(View.INVISIBLE);
        startLeftbtn = findViewById(R.id.startLeftbtn);
        //startLeftbtn.setVisibility(View.INVISIBLE);
        startCenterbtn = findViewById(R.id.startCenterbtn);
        //startCenterbtn.setVisibility(View.INVISIBLE);
        startRightbtn = findViewById(R.id.startRightbtn);
        //startRightbtn.setVisibility(View.INVISIBLE);
        playeddefense = findViewById(R.id.playeddefensecheck);
        //playeddefense.setVisibility(View.INVISIBLE);
        climbLayout = findViewById(R.id.climbLayout);
        climbLayout.setVisibility(View.INVISIBLE);
        climbRung = findViewById(R.id.climbRung);
        //climbRung.setVisibility(View.INVISIBLE);
        climbRobot = findViewById(R.id.climbRobot);
        //climbRobot.setVisibility(View.INVISIBLE);
        climbDoesnt = findViewById(R.id.climbDoesnt);
        //climbDoesnt.setVisibility(View.INVISIBLE);
        climbOn = findViewById(R.id.climbOn);
        //climbOn.setVisibility(View.INVISIBLE);
        hold1 = findViewById(R.id.ClimbOn1);
        //hold1.setVisibility(View.INVISIBLE);
        hold2 = findViewById(R.id.ClimbOn2);
        //hold2.setVisibility(View.INVISIBLE);
        climbunder15secs = findViewById(R.id.Climb15secs);
        //climbunder15secs.setVisibility(View.INVISIBLE);
        submitbutton = findViewById(R.id.submitbutton);
        //submitbutton.setVisibility(View.INVISIBLE);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoutingData scoutingData = new ScoutingData();
                if (!teamnum.getText().toString().isEmpty()) {
                    scoutingData.setTeamNumber(Integer.parseInt(teamnum.getText().toString()));
                }
//                if (redAlliancecolor.isChecked()) {
//                    scoutingData.setAllianceColor("Red");
//                } else if (blueAlliancecolor.isChecked()) {
//                    scoutingData.setAllianceColor("Blue");
//
//                }
                if (!teamnum.getText().toString().isEmpty()) {
                    scoutingData.setMatchNumber(Integer.parseInt(matchnum.getText().toString()));
                }
                scoutingData.setAutoLineCrossed(autolinecheck.isChecked());
                scoutingData.setAutoScoredScale(scorescalecheck.isChecked());
                scoutingData.setAutoScoredSwitch(scoreswitchcheck.isChecked());
                scoutingData.setAutoPickedUpCube(pickupcubecheck.isChecked());
                scoutingData.setAutoCubeExchange(cubexchangecheck.isChecked());
                scoutingData.setAutoStartedLeft(startLeftbtn.isChecked());
                scoutingData.setAutoStartedCenter(startCenterbtn.isChecked());
                scoutingData.setAutoStartedRight(startRightbtn.isChecked());
                scoutingData.setTimesScoredOnSwitch(timeScoreswitch.getValue());
                scoutingData.setTimesScoredOnScale(timeScorescale.getValue());
                scoutingData.setTimesPlacedInExchange(timesPlacedexhange.getValue());
                scoutingData.setTimesPickedFromFloor(timesPickedfromFloor.getValue());
                scoutingData.setCubesFromPlayers(cubesfromplayers.getValue());
                scoutingData.setPlayedDefense(playeddefense.isChecked());
                scoutingData.setClimbedRung(climbRung.isChecked());
                scoutingData.setClimbedOnRobot(climbRobot.isChecked());

                if(hold1.isChecked()){
                    scoutingData.setNumberOfRobotsHeld(1);
                }
                else if(hold2.isChecked()){
                    scoutingData.setNumberOfRobotsHeld(2);
                }
                scoutingData.setClimbedUnder15Secs(climbunder15secs.isChecked());

                Log.d("scoutingdata", scoutingData.toString());
                DataCollection.getInstance().addScoutingData(scoutingData);
                Log.d("Testing data woop", DataCollection.getInstance().getScoutingDataMap().toString());
                long epoch = getEpoch();
                String epochstring = String.valueOf(epoch);

                String fileName = "ScoutingData_" + epochstring;
                googleDrive.uploadContentToGoogleDrive(scoutingData.toString(), fileName, Scouting.this);
            }




        });

    }

    public long getEpoch() {
        Calendar c = Calendar.getInstance();
        return c.getTime().getTime();
    }
}

