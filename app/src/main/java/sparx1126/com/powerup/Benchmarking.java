package sparx1126.com.powerup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.utilities.DataCollection;

public class Benchmarking extends AppCompatActivity {
    private static final String TAG = "Benchmarking ";

    private static DataCollection dataCollection;
    private List<Integer> teamsInEvent;
    private String[] driveTypesArray;
    private String[] wheelTypesArray;

    private AutoCompleteTextView team_number_input;
    private Button teamButton;
    private View benchmark_main_layout;
    private Spinner driveTypeSpinner;
    private EditText customDrive;
    private Spinner wheelTypeSpinner;
    private EditText customWheel;
    private EditText numWheels;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private EditText groundClearance;
    private RadioGroup prefGroup;
    private RadioButton pref_left;
    private RadioButton pref_center;
    private RadioButton pref_right;
    private Button chooseAgainButton;
    private CheckBox start_w_cube;
    private CheckBox move_past_line;
    private CheckBox canSwtichAuto;
    private CheckBox switchPlaceAuto;
    private CheckBox switchTossAuto;
    private EditText howManySwitchPlaceAuto;
    private EditText howManySwitchTossAuto;
    private CheckBox canScaleAuto;
    private CheckBox scalePlaceAuto;
    private CheckBox scaleTossAuto;
    private EditText howManyScalePlaceAuto;
    private EditText howManyScaleTossAuto;
    private Button pickUpCubesInAuto;
    private CheckBox fromPortalAuto;
    private CheckBox fromFloorAuto;
    private CheckBox pickUpCubesInTeleop;
    private CheckBox fromPortalTele;
    private CheckBox fromFloorTele;
    private CheckBox deposit_vault;
    private CheckBox scoreSwitchTele;
    private CheckBox switchPlaceTele;
    private CheckBox switchTossTele;



    private EditText climb_height;
    private EditText customClimbAssist;
    private CheckBox auto_switch;
    private CheckBox auto_scale;
    private CheckBox acquire_floor;
    private CheckBox get_from_portal;
    private CheckBox score_switch;
    private CheckBox score_scale;
    private CheckBox climb_rung;
    private CheckBox has_rungs;
    private CheckBox attach_robot;
    private CheckBox scoreScaleTele;
    private CheckBox scaleTossTele;
    private CheckBox scalePlaceTele;
    TextView howHighText;

    private RadioButton place_switch;
    private RadioButton toss_switch;
    private RadioButton place_scale;
    private RadioButton toss_scale;
    private RadioButton pref_floor;
    private RadioButton pref_portal;

    private TextView rankChoices;
    private Spinner climbAssistTypeSpinner;
    private Button submit_button;
    private String driveType;
    private String wheelType;
    private String climbAssistType;
    private String prefStart1 = "none";
    private String prefStart2 = "none";
    private String prefStart3 = "none";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);

        dataCollection = DataCollection.getInstance();
        teamsInEvent = dataCollection.getTeamsInEvent();

        team_number_input = findViewById(R.id.team_number);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamsInEvent);
        team_number_input.setAdapter(adapter);
        team_number_input.setThreshold(1);

        teamButton = findViewById(R.id.teamButton);
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamNumberStrg = team_number_input.getText().toString();
                int teamNumber = Integer.valueOf(teamNumberStrg);
                boolean teamNumberFound = teamsInEvent.contains(teamNumber);
                if (teamNumberFound) {
                    Log.d(TAG, teamNumberStrg);
                    BenchmarkData data = dataCollection.getBenchmarkData(teamNumber);
                    if (data != null) {
                        String msg = "Found Benchmark for " + teamNumber;
                        Log.e(TAG, msg);
                        Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                        restorePreferences(data);
                    }
                    benchmark_main_layout.setVisibility(View.VISIBLE);
                } else {
                    String msg = "Team number " + teamNumber + " not found!";
                    Log.e(TAG, msg);
                    Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        benchmark_main_layout = findViewById(R.id.benchmark_main_layout);
        benchmark_main_layout.setVisibility(View.INVISIBLE);
        driveTypeSpinner = findViewById(R.id.drive_type_spinner);
        //for drive type dropdown selector
        driveTypesArray = getResources().getStringArray(R.array.driveTypes);
        SpinnerAdapter driveAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, driveTypesArray);
        driveTypeSpinner.setAdapter(driveAdapter);
        driveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driveType = driveTypesArray[position];
                if (driveType.equals("Other")) {
                    customDrive.setVisibility(View.VISIBLE);
                } else {
                    customDrive.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        customDrive = findViewById(R.id.customDrive);
        //https://stackoverflow.com/questions/18708955/invisible-components-still-take-up-space
        customDrive.setVisibility(View.GONE);
        wheelTypeSpinner = findViewById(R.id.wheel_type_spinner);
        //for wheel type dropdown selector items
        wheelTypesArray = getResources().getStringArray(R.array.wheelTypes);
        SpinnerAdapter wheelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wheelTypesArray);
        wheelTypeSpinner.setAdapter(wheelAdapter);
        wheelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wheelType = wheelTypesArray[position];
                if (wheelType.equals("Other")) {
                    customWheel.setVisibility(View.VISIBLE);
                } else {
                    customWheel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        customWheel = findViewById(R.id.customWheel);
        customWheel.setVisibility(View.GONE);
        numWheels = findViewById(R.id.numWheels);
        speed = findViewById(R.id.speed);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        groundClearance = findViewById(R.id.groundClearance);
        prefGroup = findViewById(R.id.prefStartGroup);
        prefGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pref_left:
                        if (prefStart1.equals("none")) {
                            prefStart1 = "Left";
                        } else if (prefStart2.equals("none")) {
                            prefStart2 = "Left";
                        } else {
                            prefStart3 = "Left";
                        }
                        prefGroup.removeView(findViewById(R.id.pref_left));
                        break;
                    case R.id.pref_center:
                        if (prefStart1.equals("none")) {
                            prefStart1 = "Center";
                        } else if (prefStart2.equals("none")) {
                            prefStart2 = "Center";
                        } else {
                            prefStart3 = "Center";
                        }
                        prefGroup.removeView(findViewById(R.id.pref_center));
                        break;
                    case R.id.pref_right:
                        if (prefStart1.equals("none")) {
                            prefStart1 = "Right";
                        } else if (prefStart2.equals("none")) {
                            prefStart2 = "Right";
                        } else {
                            prefStart3 = "Right";
                        }
                        prefGroup.removeView(findViewById(R.id.pref_right));
                        break;
                }

                rankChoices.setText("Favored Start Positions:                               " + "1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);
            }
        });
        pref_left = findViewById(R.id.pref_left);
        pref_center = findViewById(R.id.pref_center);
        pref_right = findViewById(R.id.pref_right);
        chooseAgainButton = findViewById(R.id.chooseAgainButton);
        //resets the choices for the ranker
        chooseAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefStart1 = "none";
                prefStart2 = "none";
                prefStart3 = "none";

                //removed view so that they come back all in order; fresh start
                prefGroup.removeView(pref_left);
                prefGroup.removeView(pref_center);
                prefGroup.removeView(pref_right);
                //https://stackoverflow.com/questions/19929295/creating-radiogroup-programmatically
                prefGroup.addView(pref_left);
                prefGroup.addView(pref_center);
                prefGroup.addView(pref_right);

                pref_left.setChecked(false);
                pref_right.setChecked(false);
                pref_center.setChecked(false);
                rankChoices.setText("Favored Start Positions:                               " + "1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);

                //only worked if the button was pressed twice, just copy pasted the code and it works perfectly
                prefStart1 = "none";
                prefStart2 = "none";
                prefStart3 = "none";

                //removed view so that they come back all in order; fresh start
                prefGroup.removeView(pref_left);
                prefGroup.removeView(pref_center);
                prefGroup.removeView(pref_right);
                //https://stackoverflow.com/questions/19929295/creating-radiogroup-programmatically
                prefGroup.addView(pref_left);
                prefGroup.addView(pref_center);
                prefGroup.addView(pref_right);

                pref_left.setChecked(false);
                pref_right.setChecked(false);
                pref_center.setChecked(false);
                rankChoices.setText("Favored Start Positions:                               " + "1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);
            }
        });
        start_w_cube = findViewById(R.id.start_w_cube);
        move_past_line = findViewById(R.id.move_past_line);
        canSwtichAuto = findViewById(R.id.canScoreSwitchAuto);
        canSwtichAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTossAuto.getVisibility() == View.VISIBLE) {
                    switchTossAuto.setVisibility(View.GONE);
                    switchPlaceAuto.setVisibility(View.GONE);
                    switchTossAuto.setChecked(false);
                    switchPlaceAuto.setChecked(false);
                    howManySwitchPlaceAuto.setVisibility(View.GONE);
                    howManySwitchPlaceAuto.setText("");
                    howManySwitchTossAuto.setVisibility(View.GONE);
                    howManySwitchTossAuto.setText("");
                } else {
                    switchTossAuto.setVisibility(View.VISIBLE);
                    switchPlaceAuto.setVisibility(View.VISIBLE);
                }
            }
        });
        switchPlaceAuto = findViewById(R.id.scoreSwitchPlaceAuto);
        switchPlaceAuto.setVisibility(View.GONE);
        switchPlaceAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howManySwitchPlaceAuto.setText("");
                if (switchPlaceAuto.isChecked()) {
                    howManySwitchPlaceAuto.setVisibility(View.VISIBLE);
                } else {
                    howManySwitchPlaceAuto.setVisibility(View.INVISIBLE);
                }
            }
        });
        switchTossAuto = findViewById(R.id.scoreSwitchTossAuto);
        switchTossAuto.setVisibility(View.GONE);
        switchTossAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howManySwitchTossAuto.setText("");
                if (switchTossAuto.isChecked()) {
                    howManySwitchTossAuto.setVisibility(View.VISIBLE);
                } else {
                    howManySwitchTossAuto.setVisibility(View.INVISIBLE);
                }

            }
        });
        howManySwitchPlaceAuto = findViewById(R.id.howManySwitchPlace);
        howManySwitchPlaceAuto.setVisibility(View.GONE);
        howManySwitchTossAuto = findViewById(R.id.howManySwitchToss);
        howManySwitchTossAuto.setVisibility(View.GONE);
        canScaleAuto = findViewById(R.id.canScoreScaleAuto);
        canScaleAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scaleTossAuto.getVisibility() == View.VISIBLE) {
                    scaleTossAuto.setVisibility(View.GONE);
                    scalePlaceAuto.setVisibility(View.GONE);
                    scaleTossAuto.setChecked(false);
                    scalePlaceAuto.setChecked(false);
                    howManyScalePlaceAuto.setVisibility(View.GONE);
                    howManyScalePlaceAuto.setText("");
                    howManyScaleTossAuto.setVisibility(View.GONE);
                    howManyScaleTossAuto.setText("");
                } else {
                    scaleTossAuto.setVisibility(View.VISIBLE);
                    scalePlaceAuto.setVisibility(View.VISIBLE);
                }
            }
        });
        scalePlaceAuto = findViewById(R.id.scoreScalePlaceAuto);
        scalePlaceAuto.setVisibility(View.GONE);
        scalePlaceAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howManyScalePlaceAuto.setText("");
                if (scalePlaceAuto.isChecked()) {
                    howManyScalePlaceAuto.setVisibility(View.VISIBLE);
                } else {
                    howManyScalePlaceAuto.setVisibility(View.INVISIBLE);
                }
            }
        });
        scaleTossAuto = findViewById(R.id.scoreScaleTossAuto);
        scaleTossAuto.setVisibility(View.GONE);
        scaleTossAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howManyScaleTossAuto.setText("");
                if (scaleTossAuto.isChecked()) {
                    howManyScaleTossAuto.setVisibility(View.VISIBLE);
                } else {
                    howManyScaleTossAuto.setVisibility(View.INVISIBLE);
                }

            }
        });
        howManyScalePlaceAuto = findViewById(R.id.howManyScalePlace);
        howManyScalePlaceAuto.setVisibility(View.GONE);
        howManyScaleTossAuto = findViewById(R.id.howManyScaleToss);
        howManyScaleTossAuto.setVisibility(View.GONE);
        pickUpCubesInAuto = findViewById(R.id.pickUpCubesInAuto);
        pickUpCubesInAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromPortalAuto.getVisibility() == View.VISIBLE) {
                    fromPortalAuto.setVisibility(View.GONE);
                    fromFloorAuto.setVisibility(View.GONE);
                    fromPortalAuto.setChecked(false);
                    fromFloorAuto.setChecked(false);
                } else {
                    fromPortalAuto.setVisibility(View.VISIBLE);
                    fromFloorAuto.setVisibility(View.VISIBLE);
                }
            }
        });
        fromPortalAuto = findViewById(R.id.fromPortal);
        fromPortalAuto.setVisibility(View.GONE);
        fromFloorAuto = findViewById(R.id.fromFloor);
        fromFloorAuto.setVisibility(View.GONE);
        pickUpCubesInTeleop = findViewById(R.id.pickUpCubesInTeleop);
        pickUpCubesInTeleop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromPortalTele.getVisibility() == View.VISIBLE) {
                    fromPortalTele.setVisibility(View.GONE);
                    fromFloorTele.setVisibility(View.GONE);
                    fromPortalTele.setChecked(false);
                    fromFloorTele.setChecked(false);
                } else {
                    fromPortalTele.setVisibility(View.VISIBLE);
                    fromFloorTele.setVisibility(View.VISIBLE);
                }
            }
        });
        fromPortalTele = findViewById(R.id.fromPortalTele);
        fromPortalTele.setVisibility(View.GONE);
        fromFloorTele = findViewById(R.id.fromFloorTele);
        fromFloorTele.setVisibility(View.GONE);
        deposit_vault = findViewById(R.id.deposit_vault);
        scoreSwitchTele = findViewById(R.id.canScoreSwitchTele);
        scoreSwitchTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTossTele.getVisibility() == View.VISIBLE) {
                    switchTossTele.setVisibility(View.GONE);
                    switchPlaceTele.setVisibility(View.GONE);
                    switchTossTele.setChecked(false);
                    switchPlaceTele.setChecked(false);
                } else {
                    switchTossTele.setVisibility(View.VISIBLE);
                    switchPlaceTele.setVisibility(View.VISIBLE);
                }
            }
        });
        switchPlaceTele = findViewById(R.id.scoreSwitchPlaceTele);
        switchPlaceTele.setVisibility(View.GONE);
        switchTossTele = findViewById(R.id.scoreSwitchTossTele);
        switchTossTele.setVisibility(View.GONE);




        climb_height = findViewById(R.id.climb_height);
        climb_height.setVisibility(View.GONE);
        customClimbAssist = findViewById(R.id.customClimbAssist);
        customClimbAssist.setVisibility(View.GONE);





        howHighText = findViewById(R.id.howHighText);
        howHighText.setVisibility(View.GONE);

        climb_rung = findViewById(R.id.climb_rung);
        attach_robot = findViewById(R.id.attach_robot);










        scoreScaleTele = findViewById(R.id.canScoreScaleTele);
        scaleTossTele = findViewById(R.id.scoreScaleTossTele);
        scaleTossTele.setVisibility(View.GONE);
        scalePlaceTele = findViewById(R.id.scoreScalePlaceTele);
        scalePlaceTele.setVisibility(View.GONE);







        rankChoices = findViewById(R.id.rankStartTextView);
        climbAssistTypeSpinner = findViewById(R.id.climbAssistTypeSpinner);
        climbAssistTypeSpinner.setVisibility(View.GONE);
        submit_button = findViewById(R.id.submit_button);







        climb_rung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (climbAssistTypeSpinner.getVisibility() == View.GONE) {
                    climbAssistTypeSpinner.setVisibility(View.VISIBLE);
                    climb_height.setVisibility(View.VISIBLE);
                    howHighText.setVisibility(View.VISIBLE);
                } else {
                    climbAssistTypeSpinner.setVisibility(View.GONE);
                    climb_height.setVisibility(View.GONE);
                    howHighText.setVisibility(View.GONE);
                }
            }
        });

        //for climber type dropdown selector
        final String[] climbAssistTypeArraySpinner = new String[]{
                "Select Climb Assist Type", "Rungs", "Ramp / Platform", "Forklift", "Other"
        };

        ArrayAdapter<String> climbAssistAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, climbAssistTypeArraySpinner);
        climbAssistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climbAssistTypeSpinner.setAdapter(climbAssistAdapter);

        climbAssistTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                climbAssistType = climbAssistTypeArraySpinner[position];
                if (climbAssistType.equals("Other")) {
                    customClimbAssist.setVisibility(View.VISIBLE);
                } else {
                    customClimbAssist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


// do something tactile when submit button


        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BenchmarkData data = new BenchmarkData();
                data.setTeamNumber(Integer.parseInt(team_number_input.getText().toString()));
                if (!speed.getText().toString().isEmpty()) {
                    data.setSpeed(Integer.parseInt(speed.getText().toString()));
                }
                if (!height.getText().toString().isEmpty()) {
                    data.setHeight(Integer.parseInt(height.getText().toString()));
                }
                if (!weight.getText().toString().isEmpty()) {
                    data.setWeight(Integer.parseInt(weight.getText().toString()));
                }
                data.setPreferStartLeft(pref_left.isChecked());
                data.setPreferStartCenter(pref_center.isChecked());
                data.setPreferStartRight(pref_right.isChecked());
                data.setCanStartWithCube(start_w_cube.isChecked());
                data.setAutoCrossLine(move_past_line.isChecked());
                data.setAutoScoreSwitch(auto_switch.isChecked());
                data.setAutoScoreScale(auto_scale.isChecked());
                data.setAcquireFloor(acquire_floor.isChecked());
                data.setAcquirePortal(get_from_portal.isChecked());
                data.setDepositVault(deposit_vault.isChecked());
                data.setPlaceOnSwitch(place_switch.isChecked());
                data.setTossToSwitch(toss_switch.isChecked());
                data.setPlaceOnScale(place_scale.isChecked());
                data.setTossToScale(toss_scale.isChecked());
                data.setPreferAcquireFloor(pref_floor.isChecked());
                data.setPreferAcquirePortal(pref_portal.isChecked());
                data.setClimbRung(climb_rung.isChecked());
                data.setHasRungs(has_rungs.isChecked());
                if (!climb_height.getText().toString().isEmpty()) {
                    data.setClimbHeight(Integer.parseInt(climb_height.getText().toString()));
                }
                data.setClimbOnRobot(attach_robot.isChecked());

                dataCollection.addBenchmarkData(data);
                team_number_input.setText("");
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void restorePreferences(BenchmarkData _data) {
        speed.setText(String.valueOf(_data.getSpeed()));
        height.setText(String.valueOf(_data.getHeight()));
        weight.setText(String.valueOf(_data.getWeight()));
        pref_left.setSelected(_data.isPreferStartLeft());
        pref_center.setSelected(_data.isPreferStartCenter());
        pref_right.setSelected(_data.isPreferStartRight());
        start_w_cube.setSelected(_data.isCanStartWithCube());
        move_past_line.setSelected(_data.isAutoCrossLine());
        auto_switch.setSelected(_data.isAutoScoreSwitch());
        auto_scale.setSelected(_data.isAutoScoreScale());
        acquire_floor.setSelected(_data.isAcquireFloor());
        get_from_portal.setSelected(_data.isAcquirePortal());
        deposit_vault.setSelected(_data.isDepositVault());
        if (_data.isPlaceOnSwitch() || _data.isTossToSwitch()) {
            place_switch.setSelected(_data.isPlaceOnSwitch());
            toss_switch.setSelected(_data.isTossToSwitch());
        }
        if (_data.isPlaceOnScale() || _data.isTossToScale()) {
            place_scale.setSelected(_data.isPlaceOnScale());
            toss_scale.setSelected(_data.isTossToScale());
        }
        pref_floor.setSelected(_data.isPreferAcquireFloor());
        pref_portal.setSelected(_data.isPreferAcquirePortal());
        climb_rung.setSelected(_data.isClimbRung());
        has_rungs.setSelected(_data.isHasRungs());
        climb_height.setText(String.valueOf(_data.getClimbHeight()));
        attach_robot.setSelected(_data.isClimbOnRobot());
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

    //https://ubuntuforums.org/showthread.php?t=752729
    public int numTimesRegex(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = str.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
            }
        }
        return count;
    }
}


