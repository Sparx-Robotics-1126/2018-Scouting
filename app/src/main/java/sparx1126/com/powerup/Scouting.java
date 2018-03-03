package sparx1126.com.powerup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import sparx1126.com.powerup.custom_layouts.PlusMinusEditTextLinearLayout;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;

public class Scouting extends AppCompatActivity {
    private static final String TAG = "Scouting ";

    private static DataCollection dataCollection;
    private SharedPreferences settings;
    Map<Integer, BlueAllianceMatch> matchesInEvent;

    private TextView matchNumber;
    private View scouting_main_layout;
    private TextView teamNumber;
    private TextView allianceColor;
    private CheckBox autoLineCrossed;
    private CheckBox autoScoredSwitch;
    private CheckBox autoScoredScale;
    private CheckBox autoPickedUpCube;
    private CheckBox autoCubeExchange;
    private PlusMinusEditTextLinearLayout cubesPlacedOnSwitch;
    private PlusMinusEditTextLinearLayout cubesPlacedOnScale;
    private PlusMinusEditTextLinearLayout cubesPlacedInExchange;
    private PlusMinusEditTextLinearLayout cubesPickedUpFromFloor;
    private PlusMinusEditTextLinearLayout cubesAcquiredFromPlayer;
    private CheckBox playedDefenseEffectively;
    private RadioButton climbedRung;
    private RadioButton climbedRobot;
    private CheckBox canBeClimbOn;
    private RadioButton held1Robot;
    private RadioButton held2Robot;
    private CheckBox climbedUnder15Secs;


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                String matchNumberStr = matchNumber.getText().toString();

                for (BlueAllianceMatch match: matchesInEvent.values()) {
                    if (match.getMatchNumber().equals(matchNumberStr)) {
                        SparseArray<String> teamKeys;
                        boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
                        if (pref_BlueAlliance) {
                            teamKeys = match.getBlueTeamKeys();
                            allianceColor.setText("Blue Alliance");
                            allianceColor.setTextColor(Color.BLUE);
                        } else {
                            teamKeys = match.getRedTeamKeys();
                            allianceColor.setText("Red Alliance");
                            allianceColor.setTextColor(Color.RED);
                        }

                        int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 0);

                        String teamKeyStr = teamKeys.get(pref_TeamPosition);
                        String teamKeyToNumberStr = teamKeyStr.replace("frc", "");
                        teamNumber.setText(teamKeyToNumberStr);
                        if(matchNumberStr.length() >= 2) {
                            dismissKeyboard();
                        }
                        int currenteamNumber = Integer.parseInt(teamKeyToNumberStr);
                        int currentMatchNumber =Integer.parseInt(matchNumberStr);
                        restorePreferences(currenteamNumber, currentMatchNumber);
                        scouting_main_layout.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                Log.e("Problem w/ match # txt", e.toString());
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);

        dataCollection = DataCollection.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        matchesInEvent = dataCollection.getQualificationMatches();

        matchNumber = findViewById(R.id.matchNumInput);
        matchNumber.setTransformationMethod(null);
        matchNumber.addTextChangedListener(watcher);

        scouting_main_layout = findViewById(R.id.scouting_main_layout);
        scouting_main_layout.setVisibility(View.INVISIBLE);
        teamNumber = findViewById(R.id.teamnumber);
        allianceColor = findViewById(R.id.allianceColor);
        autoLineCrossed = findViewById(R.id.autolinecheck);
        autoScoredScale = findViewById(R.id.autoScoredScale);
        autoScoredSwitch = findViewById(R.id.autoScoredSwitch);
        autoPickedUpCube = findViewById(R.id.pickupcubecheck);
        autoCubeExchange = findViewById(R.id.exchangecubecheck);
        cubesPlacedOnSwitch = findViewById(R.id.timesscoredswitchpicker);
        cubesPlacedOnScale = findViewById(R.id.timesscoredscalepicker);
        cubesPlacedInExchange = findViewById(R.id.timesplacedexchangepicker);
        cubesPickedUpFromFloor = findViewById(R.id.cubesfromfloorpicker);
        cubesAcquiredFromPlayer = findViewById(R.id.cubesfromplayers);
        playedDefenseEffectively = findViewById(R.id.playeddefensecheck);
        climbedRung = findViewById(R.id.climbRung);
        climbedRobot = findViewById(R.id.climbRobot);
        canBeClimbOn = findViewById(R.id.climbOn);
        held1Robot = findViewById(R.id.ClimbOn1);
        held2Robot = findViewById(R.id.ClimbOn2);
        climbedUnder15Secs = findViewById(R.id.Climb15secs);
        Button submitButton = findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
                ScoutingData scoutingData = new ScoutingData();
                scoutingData.setScouterName(scouterName);
                scoutingData.setMatchNumber(Integer.parseInt(matchNumber.getText().toString()));
                scoutingData.setTeamNumber(Integer.parseInt(teamNumber.getText().toString()));
                scoutingData.setAutoLineCrossed(autoLineCrossed.isChecked());
                scoutingData.setAutoScoredScale(autoScoredSwitch.isChecked());
                scoutingData.setAutoScoredSwitch(autoScoredScale.isChecked());
                scoutingData.setAutoPickedUpCube(autoPickedUpCube.isChecked());
                scoutingData.setAutoCubeExchange(autoCubeExchange.isChecked());

                scoutingData.setCubesPlacedOnSwitch(cubesPlacedOnSwitch.getValue());
                scoutingData.setCubesPlacedOnScale(cubesPlacedOnScale.getValue());
                scoutingData.setCubesPlacedInExchange(cubesPlacedInExchange.getValue());
                scoutingData.setCubesPickedUpFromFloor(cubesPickedUpFromFloor.getValue());
                scoutingData.setCubesAcquireFromPlayer(cubesAcquiredFromPlayer.getValue());
                scoutingData.setPlayedDefenseEffectively(playedDefenseEffectively.isChecked());
                scoutingData.setClimbedRung(climbedRung.isChecked());
                scoutingData.setClimbedOnRobot(climbedRobot.isChecked());
                scoutingData.setCanBeClimbOn(canBeClimbOn.isChecked());

                if (held1Robot.isChecked()) {
                    scoutingData.setNumberOfRobotsHeld(1);
                } else if (held2Robot.isChecked()) {
                    scoutingData.setNumberOfRobotsHeld(2);
                }
                scoutingData.setClimbedUnder15Secs(climbedUnder15Secs.isChecked());

                dataCollection.addScoutingData(scoutingData);
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Scouting.this, TAG + msg, Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    private void restorePreferences(int _teamNumber, int _match){
        ScoutingData scoutingData = dataCollection.getScoutingData(_teamNumber, _match);
        if(scoutingData != null){
            autoLineCrossed.setChecked(scoutingData.isAutoLineCrossed());
            autoScoredSwitch.setChecked(scoutingData.isAutoScoredSwitch());
            autoScoredScale.setChecked(scoutingData.isAutoScoredScale());
            autoPickedUpCube.setChecked(scoutingData.isAutoPickedUpCube());
            autoCubeExchange.setChecked(scoutingData.isAutoCubeExchange());
            cubesPlacedOnScale.setValue(scoutingData.getCubesPlacedOnScale());
            cubesPlacedOnSwitch.setValue(scoutingData.getCubesPlacedOnSwitch());
            cubesAcquiredFromPlayer.setValue(scoutingData.getCubesAcquireFromPlayer());
            cubesPickedUpFromFloor.setValue(scoutingData.getCubesPickedUpFromFloor());
            cubesPlacedInExchange.setValue(scoutingData.getCubesPlacedInExchange());
            playedDefenseEffectively.setChecked(scoutingData.isPlayedDefenseEffectively());
            climbedRung.setChecked(scoutingData.isClimbedRung());
            climbedRobot.setChecked(scoutingData.isClimbedOnRobot());
            canBeClimbOn.setChecked(scoutingData.isCanBeClimbOn());
            if (scoutingData.getNumberOfRobotsHeld()== 1) {
                held1Robot.setChecked(true);
            } else if (scoutingData.getNumberOfRobotsHeld() == 2) {
                held2Robot.setChecked(true);
            }
            climbedUnder15Secs.setChecked(scoutingData.isClimbedUnder15Secs());

        }
    }

    private void dismissKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}

