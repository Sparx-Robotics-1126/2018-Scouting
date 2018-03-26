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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import sparx1126.com.powerup.custom_layouts.PlusMinusEditTextLinearLayout;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;
import sparx1126.com.powerup.utilities.FileIO;

public class Scouting extends AppCompatActivity {
    private static final String TAG = "Scouting ";

    private static DataCollection dataCollection;
    private static FileIO fileIO;
    private SharedPreferences settings;
    Map<Integer, BlueAllianceMatch> matchesInEvent;

    private TextView matchNumber;
    private Button goHomeButton;
    private View scouting_main_layout;
    private TextView teamNumber;
    private TextView allianceColor;
    private CheckBox autoLineCrossed;
    private CheckBox autoAttemptScoredSwitch;
    private CheckBox autoScoredSwitch;
    private CheckBox autoAttemptScoredScale;
    private CheckBox autoScoredScale;
    private CheckBox autoPickedUpCube;
    private CheckBox autoCubeExchange;
    private RadioButton startingPositionLeft;
    private RadioButton startingPositionCenter;
    private RadioButton startingPositionRight;
    private PlusMinusEditTextLinearLayout cubesAtttemptPlacedOnSwitch;
    private PlusMinusEditTextLinearLayout cubesPlacedOnSwitch;
    private PlusMinusEditTextLinearLayout cubesAtttemptPlacedOnScale;
    private PlusMinusEditTextLinearLayout cubesPlacedOnScale;
    private PlusMinusEditTextLinearLayout cubesPlacedInExchange;
    private PlusMinusEditTextLinearLayout cubesPickedUpFromFloor;
    private PlusMinusEditTextLinearLayout cubesAcquiredFromPlayer;
    private CheckBox onPlatform;
    private CheckBox attemptClimb;
    private RadioGroup climbInfo;
    private RadioButton climbedRung;
    private RadioButton climbedRobot;
    private RadioButton climbFailed;

    private RadioGroup assistedGroup;
    private RadioButton held1Robot;
    private RadioButton held2Robot;
    private CheckBox assistedOthersClimb;
    private CheckBox playedDefense;
    private RadioGroup defenseGroup;
    private RadioButton effectiveDefense;
    private RadioButton ineffectiveDefense;
    private CheckBox climbedUnder15Secs;
    private CheckBox disabled;
    private EditText comments;
    private Button submitButton;

    @Override
    public void onBackPressed() {
        Toast.makeText(Scouting.this, "To exit press the submit / return home button", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);
        dataCollection = DataCollection.getInstance();
        fileIO = FileIO.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        matchesInEvent = dataCollection.getQualificationMatches();

        matchNumber = findViewById(R.id.matchNumInput);
        matchNumber.setTransformationMethod(null);
        matchNumber.addTextChangedListener(new TextWatcher() {

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

                    for (BlueAllianceMatch match : matchesInEvent.values()) {
                        if (match.getMatchNumber().equals(matchNumberStr)) {
                            SparseArray<String> teamKeys;
                            boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
                            if (pref_BlueAlliance) {
                                teamKeys = match.getBlueTeamKeys();
                                allianceColor.setText("Blue Alliance");
                                allianceColor.setBackgroundColor(Color.BLUE);
                            } else {
                                teamKeys = match.getRedTeamKeys();
                                allianceColor.setText("Red Alliance");
                                allianceColor.setBackgroundColor(Color.RED);
                            }

                            int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 0);

                            String teamKeyStr = teamKeys.get(pref_TeamPosition);
                            String teamKeyToNumberStr = teamKeyStr.replace("frc", "");
                            teamNumber.setText(teamKeyToNumberStr);
                            if (matchNumberStr.length() >= 2) {
                                dismissKeyboard();
                            }
                            int currenteamNumber = Integer.parseInt(teamKeyToNumberStr);
                            int currentMatchNumber = Integer.parseInt(matchNumberStr);
                            restorePreferences(currenteamNumber, currentMatchNumber);
                            scouting_main_layout.setVisibility(View.VISIBLE);

                            break;
                        } else {
                            scouting_main_layout.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Problem w/ match # txt", e.toString());
                }

            }
        });
        goHomeButton = findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Scouting.this, "Current data not saved", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        scouting_main_layout = findViewById(R.id.scouting_main_layout);
        scouting_main_layout.setVisibility(View.INVISIBLE);
        teamNumber = findViewById(R.id.teamnumber);
        allianceColor = findViewById(R.id.allianceColor);
        autoLineCrossed = findViewById(R.id.autolinecheck);
        autoAttemptScoredSwitch = findViewById(R.id.autoAttemptScoredSwitch);
        autoScoredSwitch = findViewById(R.id.autoScoredSwitch);
        autoAttemptScoredScale = findViewById(R.id.autoAttemptScoredScale);
        autoScoredScale = findViewById(R.id.autoScoredScale);
        autoPickedUpCube = findViewById(R.id.pickupcubecheck);
        autoCubeExchange = findViewById(R.id.exchangecubecheck);
        startingPositionLeft = findViewById(R.id.startLeftbtn);
        startingPositionCenter = findViewById(R.id.startCenterbtn);
        startingPositionRight = findViewById(R.id.startRightbtn);
        cubesAtttemptPlacedOnSwitch = findViewById(R.id.atempttimesscoredswitchpicker);
        cubesPlacedOnSwitch = findViewById(R.id.timesscoredswitchpicker);
        cubesAtttemptPlacedOnScale = findViewById(R.id.attempttimesscoredscalepicker);
        cubesPlacedOnScale = findViewById(R.id.timesscoredscalepicker);
        cubesPlacedInExchange = findViewById(R.id.timesplacedexchangepicker);
        cubesPickedUpFromFloor = findViewById(R.id.cubesfromfloorpicker);
        cubesAcquiredFromPlayer = findViewById(R.id.cubesfromplayers);
        onPlatform = findViewById(R.id.endedOnPlatform);
        attemptClimb = findViewById(R.id.attemptToClimb);
        attemptClimb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptClimb.isChecked()) {
                    climbInfo.setVisibility(View.VISIBLE);
                } else {
                    climbInfo.setVisibility(View.GONE);
                    climbedRung.setChecked(false);
                    climbedRobot.setChecked(false);
                    climbFailed.setChecked(false);
                }
            }
        });
        climbInfo = findViewById(R.id.climbInfo);
        climbInfo.setVisibility(View.GONE);
        climbedRung = findViewById(R.id.climbRung);
        climbedRobot = findViewById(R.id.climbRobot);
        climbFailed = findViewById(R.id.climbFailed);
        assistedOthersClimb = findViewById(R.id.assistedClimb);
        assistedOthersClimb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assistedOthersClimb.isChecked()) {
                    assistedGroup.setVisibility(View.VISIBLE);
                } else {
                    assistedGroup.setVisibility(View.GONE);
                    held1Robot.setChecked(false);
                    held2Robot.setChecked(false);
                }
            }
        });
        assistedGroup = findViewById(R.id.assistedGroup);
        assistedGroup.setVisibility(View.GONE);
        held1Robot = findViewById(R.id.assistedOne);
        held2Robot = findViewById(R.id.assistedTwo);
        playedDefense = findViewById(R.id.playeddefensecheck);
        defenseGroup = findViewById(R.id.defenseGroup);
        defenseGroup.setVisibility(View.GONE);
        effectiveDefense = findViewById(R.id.effectiveDefense);
        ineffectiveDefense = findViewById(R.id.ineffectiveDefense);
        climbedUnder15Secs = findViewById(R.id.Climb15secs);
        playedDefense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playedDefense.isChecked()) {
                    defenseGroup.setVisibility(View.VISIBLE);
                } else {
                    defenseGroup.setVisibility(View.GONE);
                    effectiveDefense.setChecked(false);
                    ineffectiveDefense.setChecked(false);
                }
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////
        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitButton = findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
                ScoutingData scoutingData = new ScoutingData();
                scoutingData.setScouterName(scouterName);
                scoutingData.setMatchNumber(Integer.parseInt(matchNumber.getText().toString()));
                scoutingData.setTeamNumber(Integer.parseInt(teamNumber.getText().toString()));
                scoutingData.setAutoLineCrossed(autoLineCrossed.isChecked());
                scoutingData.setAutoAttemptScoredSwitch(autoAttemptScoredSwitch.isChecked());
                scoutingData.setAutoScoredSwitch(autoScoredSwitch.isChecked());
                scoutingData.setAutoAttemptScoredScale(autoAttemptScoredScale.isChecked());
                scoutingData.setAutoScoredScale(autoScoredScale.isChecked());
                scoutingData.setAutoPickedUpCube(autoPickedUpCube.isChecked());
                scoutingData.setAutoCubeExchange(autoCubeExchange.isChecked());
                scoutingData.setStartedLeftPosition(startingPositionLeft.isChecked());
                scoutingData.setStartedCenterPosition(startingPositionCenter.isChecked());
                scoutingData.setStartedRightPosition(startingPositionRight.isChecked());
                scoutingData.setAttemptCubesPlacedOnSwitch(cubesAtttemptPlacedOnSwitch.getValue());
                scoutingData.setCubesPlacedOnSwitch(cubesPlacedOnSwitch.getValue());
                scoutingData.setAttemptCubesPlacedOnScale(cubesAtttemptPlacedOnScale.getValue());
                scoutingData.setCubesPlacedOnScale(cubesPlacedOnScale.getValue());
                scoutingData.setCubesPlacedInExchange(cubesPlacedInExchange.getValue());
                scoutingData.setCubesPickedUpFromFloor(cubesPickedUpFromFloor.getValue());
                scoutingData.setCubesAcquireFromPlayer(cubesAcquiredFromPlayer.getValue());
                scoutingData.setEndedOnPlatform(onPlatform.isChecked());
                scoutingData.setAttemptToClimb(attemptClimb.isChecked());
                scoutingData.setClimbedRung(climbedRung.isChecked());
                scoutingData.setClimbedOnRobot(climbedRobot.isChecked());
                scoutingData.setCanBeClimbOn(assistedOthersClimb.isChecked());
                if (held1Robot.isChecked()) {
                    scoutingData.setNumberOfRobotsHeld(1);
                } else if (held2Robot.isChecked()) {
                    scoutingData.setNumberOfRobotsHeld(2);
                }
/////////////THESE TWO\\\\\\\\\\\\\\\\\\\\\\\THESE TWO//////////////////////////////
                scoutingData.setPlayedDefense(playedDefense.isChecked());
                scoutingData.setPlayedDefenseEffectively(effectiveDefense.isChecked());
                scoutingData.setPlayedDefenseIneffectively(ineffectiveDefense.isChecked());

                scoutingData.setClimbedUnder15Secs(climbedUnder15Secs.isChecked());
                scoutingData.setDisabled(disabled.isChecked());
                scoutingData.setComments(comments.getText().toString());
                dataCollection.addScoutingData(scoutingData);
                fileIO.storeScoutingData(scoutingData.getJsonString(), teamNumber.getText().toString(), matchNumber.getText().toString());
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Scouting.this, TAG + msg, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void restorePreferences(int _teamNumber, int _match) {
        ScoutingData scoutingData = dataCollection.getScoutingData(_teamNumber, _match);
        if (scoutingData != null) {
            Log.d(TAG, "Hey,scouting data is found :)");
            autoLineCrossed.setChecked(scoutingData.isAutoLineCrossed());
            autoAttemptScoredSwitch.setChecked(scoutingData.isAutoAttemptScoredSwitch());
            autoScoredSwitch.setChecked(scoutingData.isAutoScoredSwitch());
            autoAttemptScoredScale.setChecked(scoutingData.isAutoAttemptScoredScale());
            autoScoredScale.setChecked(scoutingData.isAutoScoredScale());
            autoPickedUpCube.setChecked(scoutingData.isAutoPickedUpCube());
            autoCubeExchange.setChecked(scoutingData.isAutoCubeExchange());
            startingPositionLeft.setChecked(scoutingData.isStartedLeftPosition());
            startingPositionCenter.setChecked(scoutingData.isStartedCenterPosition());
            startingPositionRight.setChecked(scoutingData.isStartedRightPosition());
            cubesAtttemptPlacedOnSwitch.setValue(scoutingData.getAttemptCubesPlacedOnSwitch());
            cubesPlacedOnSwitch.setValue(scoutingData.getCubesPlacedOnSwitch());
            cubesAtttemptPlacedOnScale.setValue(scoutingData.getAttemptCubesPlacedOnScale());
            cubesPlacedOnScale.setValue(scoutingData.getCubesPlacedOnScale());
            cubesAcquiredFromPlayer.setValue(scoutingData.getCubesAcquireFromPlayer());
            cubesPickedUpFromFloor.setValue(scoutingData.getCubesPickedUpFromFloor());
            cubesPlacedInExchange.setValue(scoutingData.getCubesPlacedInExchange());
            onPlatform.setChecked(scoutingData.isEndedOnPlatform());
            attemptClimb.setChecked(scoutingData.isAttemptToClimb());
            climbedRung.setChecked(scoutingData.isClimbedRung());
            climbedRobot.setChecked(scoutingData.isClimbedOnRobot());
            climbFailed.setChecked(attemptClimb.isChecked() && !climbedRung.isChecked() && !climbedRobot.isChecked());
            playedDefense.setChecked(scoutingData.isPlayedDefense());
            assistedOthersClimb.setChecked(scoutingData.isCanBeClimbOn());
            int test = scoutingData.getNumberOfRobotsHeld();
            String testtest = Integer.toString(test);
            Log.e(TAG, testtest);
            if (scoutingData.getNumberOfRobotsHeld() == 1) {
                held1Robot.setChecked(true);
            } else if (scoutingData.getNumberOfRobotsHeld() == 2) {
                held2Robot.setChecked(true);
            }
            assistedOthersClimb.callOnClick();
/////////////THESE TWO\\\\\\\\\\\\\\\\\\\\\\\THESE TWO//////////////////////////////
            if (scoutingData.isPlayedDefenseEffectively()) {
                effectiveDefense.setChecked(true);
            }

            if (scoutingData.isPlayedDefenseIneffectively()) {
                ineffectiveDefense.setChecked(true);
            }

            playedDefense.callOnClick();

            climbedUnder15Secs.setChecked(scoutingData.isClimbedUnder15Secs());
            disabled.setChecked(scoutingData.isDisabled());
            comments.setText(scoutingData.getComments());
        } else {
            reset();
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

    private void reset() {
        autoLineCrossed.setChecked(false);
        autoAttemptScoredSwitch.setChecked(false);
        autoScoredSwitch.setChecked(false);
        autoAttemptScoredScale.setChecked(false);
        autoScoredScale.setChecked(false);
        autoPickedUpCube.setChecked(false);
        autoCubeExchange.setChecked(false);
        startingPositionLeft.setChecked(false);
        startingPositionCenter.setChecked(false);
        startingPositionRight.setChecked(false);
        cubesAtttemptPlacedOnSwitch.setValue(0);
        cubesPlacedOnSwitch.setValue(0);
        cubesAtttemptPlacedOnScale.setValue(0);
        cubesPlacedOnScale.setValue(0);
        cubesAcquiredFromPlayer.setValue(0);
        cubesPickedUpFromFloor.setValue(0);
        cubesPlacedInExchange.setValue(0);
        onPlatform.setChecked(false);
        attemptClimb.setChecked(false);
        assistedOthersClimb.setChecked(false);
        effectiveDefense.setChecked(false);
        ineffectiveDefense.setChecked(false);
        climbedRung.setChecked(false);
        climbedRobot.setChecked(false);
        climbFailed.setChecked(false);
        playedDefense.setChecked(false);
        climbedUnder15Secs.setChecked(false);
        held1Robot.setChecked(false);
        held2Robot.setChecked(false);
        climbedUnder15Secs.setChecked(false);
        disabled.setChecked(false);
        comments.setText("");
    }
}