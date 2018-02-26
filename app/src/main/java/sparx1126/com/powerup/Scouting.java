package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sparx1126.com.powerup.custom_layouts.PlusMinusEditTextLinearLayout;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;

public class Scouting extends AppCompatActivity {
    private static final String TAG = "Scouting ";

    private static DataCollection dataCollection;
    private SharedPreferences settings;
    SparseArray<BlueAllianceMatch> matchesInEvent;

    private AutoCompleteTextView matchNumber;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);

        dataCollection = DataCollection.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        matchesInEvent = dataCollection.getEventMatchesByMatchNumber();
        if (matchesInEvent.size() == 0) {
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
        }

        matchNumber = findViewById(R.id.matchnumimput);
        Button matchButton = findViewById(R.id.matchButton);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matchNumberStr = matchNumber.getText().toString();

                for (int index = 0; index < matchesInEvent.size(); index++) {
                    BlueAllianceMatch match = matchesInEvent.get(index);
                    if (match.getMatchNumber().equals(matchNumberStr)) {
                        SparseArray<String> teamKeys;
                        boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
                        if (pref_BlueAlliance) {
                            teamKeys = match.getBlueTeamKeys();
                            allianceColor.setText("Blue Alliance");
                            allianceColor.setBackgroundColor(getResources().getColor(R.color.Blue));
                        } else {
                            teamKeys = match.getRedTeamKeys();
                            allianceColor.setText("Red Alliance");
                            allianceColor.setBackgroundColor(getResources().getColor(R.color.sparxRedVeryLight));
                        }

                        int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 0);

                        String teamKeyToNumberStr = teamKeys.get(pref_TeamPosition).replace("frc", "");
                        teamNumber.setText(teamKeyToNumberStr);
                        dismissKeyboard();
                        scouting_main_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        scouting_main_layout = findViewById(R.id.scouting_main_layout);
        scouting_main_layout.setVisibility(View.INVISIBLE);
        teamNumber = findViewById(R.id.teamNumber);
        allianceColor = findViewById(R.id.allianceColor);
        autoLineCrossed = findViewById(R.id.autoLineCrossed);
        autoScoredScale = findViewById(R.id.autoScoredScale);
        autoScoredSwitch = findViewById(R.id.autoScoredSwitch);
        autoPickedUpCube = findViewById(R.id.pickupcubecheck);
        autoCubeExchange = findViewById(R.id.cubexchangecheck);
        cubesPlacedOnSwitch = findViewById(R.id.timesscoredswitchpicker);
        cubesPlacedOnScale = findViewById(R.id.timesscoredscalepicker);
        cubesPlacedInExchange = findViewById(R.id.timesplacedexchangepicker);
        cubesPickedUpFromFloor = findViewById(R.id.cubesfromfloorpicker);
        cubesAcquiredFromPlayer = findViewById(R.id.cubesAcquireFromPlayer);
        playedDefenseEffectively = findViewById(R.id.playeddefensecheck);
        climbedRung = findViewById(R.id.climbedRung);
        climbedRobot = findViewById(R.id.climbRobot);
        canBeClimbOn = findViewById(R.id.climbOn);
        held1Robot = findViewById(R.id.ClimbOn1);
        held2Robot = findViewById(R.id.ClimbOn2);
        climbedUnder15Secs = findViewById(R.id.Climb15secs);
        Button submitButton = findViewById(R.id.submitbutton);
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
                matchNumber.setText("");
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Scouting.this, TAG + msg, Toast.LENGTH_LONG).show();
            }


        });
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

