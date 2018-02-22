package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    private SharedPreferences settings;
    private static FileIO fileIO;

    private EditText matchNumber;
    private Button matchButton;
    private LinearLayout teamLayout;
    private TextView teamNumber;
    private LinearLayout allianceLayout;
    private TextView allianceColor;
    private LinearLayout autoLayout;
    private CheckBox autoLineCrossed;
    private CheckBox autoScoredSwitch;
    private CheckBox autoScoredScale;
    private CheckBox autoPickedUpCube;
    private CheckBox autoCubeExchange;
    private LinearLayout teleLayout;
    private PlusMinusEditTextLinearLayout cubesPlacedOnSwitch;
    private PlusMinusEditTextLinearLayout cubesPlacedOnScale;
    private PlusMinusEditTextLinearLayout cubesPlacedInExchange;
    private PlusMinusEditTextLinearLayout cubesPickedUpFromFloor;
    private PlusMinusEditTextLinearLayout cubesAcquiredFromPlayer;
    private CheckBox playedDefenseEffectively;
    private LinearLayout climbLayout;
    private RadioButton climbedRung;
    private RadioButton climbedRobot;
    private CheckBox canBeClimbOn;
    private RadioButton held1Robot;
    private RadioButton held2Robot;
    private CheckBox climbedUnder15Secs;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);

        dataCollection = DataCollection.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        fileIO = FileIO.getInstance();

        matchNumber = findViewById(R.id.matchnumimput);
        matchButton = findViewById(R.id.matchButton);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchNumberStr = matchNumber.getText().toString();

                Map<String, BlueAllianceMatch> matchesInEvent = dataCollection.getEventMatches();
                if (matchesInEvent == null) {
                    String msg = "Have an Admin Setup Tablet!";
                    Log.e(TAG, msg);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Scouting.this);

                    builder.setTitle(TAG);
                    builder.setMessage(msg);
                    builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });

                    Dialog dialog = builder.create();
                    dialog.show();
                } else {
                    for (BlueAllianceMatch value : matchesInEvent.values()) {
                        if (value.getMatchNumber().equals(matchNumberStr)) {
                            Map<Integer, String> teamKeys;
                            boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
                            if (pref_BlueAlliance) {
                                teamKeys = value.getBlueTeamKeys();
                                allianceColor.setText("Blue Alliance");
                                allianceColor.setBackgroundColor(getResources().getColor(R.color.Blue));
                            } else {
                                teamKeys = value.getRedTeamKeys();
                                allianceColor.setText("Red Alliance");
                                allianceColor.setBackgroundColor(getResources().getColor(R.color.sparxRedVeryLight));
                            }

                            int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 0);

                            String teamKeyToNumberStr = teamKeys.get(new Integer(pref_TeamPosition)).replace("frc","");
                            teamNumber.setText(teamKeyToNumberStr);
                            showButtons(true);
                            dismissKeyboard();
                        }
                    }
                }

            }
        });

        teamLayout = findViewById(R.id.teamLayout);
        teamLayout.setVisibility(View.INVISIBLE);
        teamNumber = findViewById(R.id.teamNumber);
        allianceLayout = findViewById(R.id.allianceLayout);
        allianceLayout.setVisibility(View.INVISIBLE);
        allianceColor = findViewById(R.id.allianceColor);
        autoLayout = findViewById(R.id.autoLayout);
        autoLayout.setVisibility(View.INVISIBLE);
        autoLineCrossed = findViewById(R.id.autoLineCrossed);
        autoScoredScale = findViewById(R.id.autoScoredScale);
        autoScoredSwitch = findViewById(R.id.autoScoredSwitch);
        autoPickedUpCube = findViewById(R.id.pickupcubecheck);
        autoCubeExchange = findViewById(R.id.cubexchangecheck);
        teleLayout = findViewById(R.id.teleLayout);
        teleLayout.setVisibility(View.INVISIBLE);
        cubesPlacedOnSwitch = findViewById(R.id.timesscoredswitchpicker);
        cubesPlacedOnScale = findViewById(R.id.timesscoredscalepicker);
        cubesPlacedInExchange = findViewById(R.id.timesplacedexchangepicker);
        cubesPickedUpFromFloor = findViewById(R.id.cubesfromfloorpicker);
        cubesAcquiredFromPlayer = findViewById(R.id.cubesAcquireFromPlayer);
        playedDefenseEffectively = findViewById(R.id.playeddefensecheck);
        climbLayout = findViewById(R.id.climbLayout);
        climbLayout.setVisibility(View.INVISIBLE);
        climbedRung = findViewById(R.id.climbedRung);
        climbedRobot = findViewById(R.id.climbRobot);
        canBeClimbOn = findViewById(R.id.climbOn);
        held1Robot = findViewById(R.id.ClimbOn1);
        held2Robot = findViewById(R.id.ClimbOn2);
        climbedUnder15Secs = findViewById(R.id.Climb15secs);
        submitButton = findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoutingData scoutingData = new ScoutingData();
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

                DataCollection.getInstance().addScoutingData(scoutingData);
                fileIO.storeScoutingData(scoutingData.toString());
                matchNumber.setText("");
                showButtons(false);
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Scouting.this, TAG + msg, Toast.LENGTH_LONG).show();
            }


        });
    }

    private void showButtons(boolean _show) {
        if (_show) {
            allianceLayout.setVisibility(View.VISIBLE);
            teamLayout.setVisibility(View.VISIBLE);
            autoLayout.setVisibility(View.VISIBLE);
            teleLayout.setVisibility(View.VISIBLE);
            climbLayout.setVisibility(View.VISIBLE);
        } else {
            allianceLayout.setVisibility(View.INVISIBLE);
            teamLayout.setVisibility(View.INVISIBLE);
            autoLayout.setVisibility(View.INVISIBLE);
            teleLayout.setVisibility(View.INVISIBLE);
            climbLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void dismissKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}

