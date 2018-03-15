package sparx1126.com.powerup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.utilities.DataCollection;
import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.Utility;

public class Benchmarking extends AppCompatActivity {
    private static final String TAG = "Benchmarking ";
    private static final int REQUEST_TAKE_PHOTO = 1;

    private static DataCollection dataCollection;
    private static FileIO fileIO;
    private static Utility utility;
    private SharedPreferences settings;
    private List<Integer> teamsInEvent;
    private String prefStart1;
    private String prefStart2;
    private String prefStart3;
    private String[] driveTypesArray;
    private String[] wheelTypesArray;
    private String[] climbAssistTypesArray;

    private AutoCompleteTextView team_number_input;
    private Button goHomeButton;
    private View benchmark_main_layout;
    private ImageButton cameraButton;
    private Spinner driveTypeSpinner;
    private EditText customDrive;
    private Spinner wheelTypeSpinner;
    private EditText customWheel;
    private EditText numWheels;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private EditText groundClearance;
    private TextView rankChoices;
    private RadioGroup prefGroup;
    private RadioButton pref_left;
    private RadioButton pref_center;
    private RadioButton pref_right;
    private Button chooseAgainButton;
    private CheckBox start_w_cube;
    private CheckBox move_past_line;
    private CheckBox canSwitchAuto;
    private CheckBox switchPlaceAuto;
    private CheckBox switchTossAuto;
    private EditText howManySwitchPlaceAuto;
    private EditText howManySwitchTossAuto;
    private CheckBox canScaleAuto;
    private CheckBox scalePlaceAuto;
    private CheckBox scaleTossAuto;
    private EditText howManyScalePlaceAuto;
    private EditText howManyScaleTossAuto;
    private CheckBox pickUpCubesInAuto;
    private CheckBox fromPortalAuto;
    private CheckBox fromFloorAuto;
    private CheckBox pickUpCubesInTeleOp;
    private CheckBox fromPortalTele;
    private CheckBox fromFloorTele;
    private CheckBox deposit_vault;
    private CheckBox scoreSwitchTele;
    private CheckBox switchPlaceTele;
    private CheckBox switchTossTele;
    private CheckBox scoreScaleTele;
    private CheckBox scalePlaceTele;
    private CheckBox scaleTossTele;
    private CheckBox climb_rung;
    private Spinner climbAssistTypeSpinner;
    private EditText customClimbAssist;
    private TextView howHighText;
    private EditText climb_height;
    private CheckBox attach_robot;
    private CheckBox canAssist;
    private TextView canAssistPrompt;
    private EditText comments;
    private Button submit_button;

    @Override
    public void onBackPressed() {
        Toast.makeText(Benchmarking.this, "To exit press the submit / return home button", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);

        dataCollection = DataCollection.getInstance();
        fileIO = FileIO.getInstance();
        utility = Utility.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        teamsInEvent = dataCollection.getTeamsInEvent();
        prefStart1 = getResources().getString(R.string.none);
        prefStart2 = getResources().getString(R.string.none);
        prefStart3 = getResources().getString(R.string.none);

        team_number_input = findViewById(R.id.team_number);
        team_number_input.setTransformationMethod(null);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.custom_list_item, teamsInEvent);
        team_number_input.setAdapter(adapter);
        team_number_input.setThreshold(1);
        team_number_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String teamNumberStringg = team_number_input.getText().toString();
                    int teamNumber = Integer.valueOf(teamNumberStringg);
                    boolean teamNumberFound = teamsInEvent.contains(teamNumber);
                    if (teamNumberFound) {
                        Log.d(TAG, teamNumberStringg);
                        BenchmarkData data = dataCollection.getBenchmarkData(teamNumber);
                        if (data != null) {
                            String msg = "Found Benchmark for " + teamNumber;
                            Log.d(TAG, msg);
                            Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                        }
                        View view = findViewById(android.R.id.content).getRootView();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        restorePreferences(teamNumber);
                        benchmark_main_layout.setVisibility(View.VISIBLE);
                    } else {
                        benchmark_main_layout.setVisibility(View.INVISIBLE);
                    }

                } catch (Exception e) {
                    Log.e("Problem w/ team # txt", e.toString());
                }

            }
        });
        goHomeButton = findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Benchmarking.this, "Current data deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        benchmark_main_layout = findViewById(R.id.benchmark_main_layout);
        benchmark_main_layout.setVisibility(View.INVISIBLE);
        cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        long epochInSeconds = System.currentTimeMillis() / 1000;
                        String imageFileName = getResources().getString(R.string.pictureHeader) +
                                team_number_input.getText().toString() + "_" +
                                String.valueOf(epochInSeconds);
                        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        photoFile = File.createTempFile(imageFileName,".jpg", storageDir);
                    } catch (IOException e) {
                        String msg = "Can't take picture";
                        Log.e(TAG, msg, e);
                        Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(Benchmarking.this,
                                "sparx1126.com.powerup.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                } else {
                    String msg = "Can't take picture";
                    Log.e(TAG, msg);
                    Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                }
            }
        });
        driveTypeSpinner = findViewById(R.id.drive_type_spinner);
        //for drive type dropdown selector
        driveTypesArray = getResources().getStringArray(R.array.driveTypes);
        SpinnerAdapter driveAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, driveTypesArray);
        driveTypeSpinner.setAdapter(driveAdapter);
        driveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String driveType = driveTypesArray[position];
                if (driveType.equals(getResources().getString(R.string.other))) {
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
        SpinnerAdapter wheelAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, wheelTypesArray);
        wheelTypeSpinner.setAdapter(wheelAdapter);
        wheelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String wheelType = wheelTypesArray[position];
                if (wheelType.equals(getResources().getString(R.string.other))) {
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
        numWheels.setTransformationMethod(null);
        speed = findViewById(R.id.speed);
        speed.setTransformationMethod(null);
        height = findViewById(R.id.height);
        height.setTransformationMethod(null);
        weight = findViewById(R.id.weight);
        weight.setTransformationMethod(null);
        groundClearance = findViewById(R.id.groundClearance);
        groundClearance.setTransformationMethod(null);
        rankChoices = findViewById(R.id.rankStartTextView);
        prefGroup = findViewById(R.id.prefStartGroup);
        prefGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setPrefGroup(checkedId);
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
                chooseAgain();
            }
        });
        start_w_cube = findViewById(R.id.start_w_cube);
        move_past_line = findViewById(R.id.move_past_line);
        canSwitchAuto = findViewById(R.id.canScoreSwitchAuto);
        canSwitchAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canSwitchAuto.isChecked()) {
                    switchTossAuto.setVisibility(View.VISIBLE);
                    switchPlaceAuto.setVisibility(View.VISIBLE);
                } else {
                    switchTossAuto.setChecked(false);
                    switchPlaceAuto.setChecked(false);
                    switchTossAuto.setVisibility(View.GONE);
                    switchPlaceAuto.setVisibility(View.GONE);
                    howManySwitchPlaceAuto.setVisibility(View.GONE);
                    howManySwitchTossAuto.setVisibility(View.GONE);
                }
            }
        });
        switchPlaceAuto = findViewById(R.id.scoreSwitchPlaceAuto);
        switchPlaceAuto.setVisibility(View.GONE);
        switchPlaceAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchPlaceAuto.isChecked()) {
                    howManySwitchPlaceAuto.setVisibility(View.VISIBLE);
                    if(!switchTossAuto.isChecked()) {
                        howManySwitchTossAuto.setVisibility(View.INVISIBLE);
                    }
                } else {
                    howManySwitchPlaceAuto.setText("");
                    howManySwitchPlaceAuto.setVisibility(View.INVISIBLE);
                }
            }
        });
        switchTossAuto = findViewById(R.id.scoreSwitchTossAuto);
        switchTossAuto.setVisibility(View.GONE);
        switchTossAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTossAuto.isChecked()) {
                    if(!switchPlaceAuto.isChecked()) {
                        howManySwitchPlaceAuto.setVisibility(View.INVISIBLE);
                    }
                    howManySwitchTossAuto.setVisibility(View.VISIBLE);
                } else {
                    howManySwitchTossAuto.setText("");
                    howManySwitchTossAuto.setVisibility(View.INVISIBLE);
                }

            }
        });
        howManySwitchPlaceAuto = findViewById(R.id.howManySwitchPlace);
        howManySwitchPlaceAuto.setVisibility(View.GONE);
        howManySwitchPlaceAuto.setTransformationMethod(null);
        howManySwitchTossAuto = findViewById(R.id.howManySwitchToss);
        howManySwitchTossAuto.setVisibility(View.GONE);
        howManySwitchTossAuto.setTransformationMethod(null);
        canScaleAuto = findViewById(R.id.canScoreScaleAuto);
        canScaleAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canScaleAuto.isChecked()) {
                    scaleTossAuto.setVisibility(View.VISIBLE);
                    scalePlaceAuto.setVisibility(View.VISIBLE);
                } else {
                    scaleTossAuto.setChecked(false);
                    scalePlaceAuto.setChecked(false);
                    scaleTossAuto.setVisibility(View.GONE);
                    scalePlaceAuto.setVisibility(View.GONE);
                    howManyScalePlaceAuto.setVisibility(View.GONE);
                    howManyScaleTossAuto.setVisibility(View.GONE);
                }
            }
        });
        scalePlaceAuto = findViewById(R.id.scoreScalePlaceAuto);
        scalePlaceAuto.setVisibility(View.GONE);
        scalePlaceAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalePlaceAuto.isChecked()) {
                    howManyScalePlaceAuto.setVisibility(View.VISIBLE);
                    if(!scaleTossAuto.isChecked()) {
                        howManyScaleTossAuto.setVisibility(View.INVISIBLE);
                    }
                } else {
                    howManyScalePlaceAuto.setText("");
                    howManyScalePlaceAuto.setVisibility(View.INVISIBLE);
                }
            }
        });
        scaleTossAuto = findViewById(R.id.scoreScaleTossAuto);
        scaleTossAuto.setVisibility(View.GONE);
        scaleTossAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scaleTossAuto.isChecked()) {
                    if(!scalePlaceAuto.isChecked()) {
                        howManyScalePlaceAuto.setVisibility(View.INVISIBLE);
                    }
                    howManyScaleTossAuto.setVisibility(View.VISIBLE);
                } else {
                    howManyScaleTossAuto.setText("");
                    howManyScaleTossAuto.setVisibility(View.INVISIBLE);
                }

            }
        });
        howManyScalePlaceAuto = findViewById(R.id.howManyScalePlace);
        howManyScalePlaceAuto.setVisibility(View.GONE);
        howManyScalePlaceAuto.setTransformationMethod(null);
        howManyScaleTossAuto = findViewById(R.id.howManyScaleToss);
        howManyScaleTossAuto.setVisibility(View.GONE);
        howManyScaleTossAuto.setTransformationMethod(null);
        pickUpCubesInAuto = findViewById(R.id.pickUpCubesInAuto);
        pickUpCubesInAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickUpCubesInAuto.isChecked()) {
                    fromPortalAuto.setVisibility(View.VISIBLE);
                    fromFloorAuto.setVisibility(View.VISIBLE);
                } else {
                    fromPortalAuto.setChecked(false);
                    fromFloorAuto.setChecked(false);
                    fromPortalAuto.setVisibility(View.GONE);
                    fromFloorAuto.setVisibility(View.GONE);
                }
            }
        });
        fromPortalAuto = findViewById(R.id.fromPortal);
        fromPortalAuto.setVisibility(View.GONE);
        fromFloorAuto = findViewById(R.id.fromFloor);
        fromFloorAuto.setVisibility(View.GONE);
        pickUpCubesInTeleOp = findViewById(R.id.pickUpCubesInTeleop);
        pickUpCubesInTeleOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickUpCubesInTeleOp.isChecked()) {
                    fromPortalTele.setVisibility(View.VISIBLE);
                    fromFloorTele.setVisibility(View.VISIBLE);
                } else {
                    fromPortalTele.setVisibility(View.GONE);
                    fromFloorTele.setVisibility(View.GONE);
                    fromPortalTele.setChecked(false);
                    fromFloorTele.setChecked(false);
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
                if (scoreSwitchTele.isChecked()) {
                    switchTossTele.setVisibility(View.VISIBLE);
                    switchPlaceTele.setVisibility(View.VISIBLE);
                } else {
                    switchTossTele.setVisibility(View.GONE);
                    switchPlaceTele.setVisibility(View.GONE);
                    switchTossTele.setChecked(false);
                    switchPlaceTele.setChecked(false);
                }
            }
        });
        switchPlaceTele = findViewById(R.id.scoreSwitchPlaceTele);
        switchPlaceTele.setVisibility(View.GONE);
        switchTossTele = findViewById(R.id.scoreSwitchTossTele);
        switchTossTele.setVisibility(View.GONE);
        scoreScaleTele = findViewById(R.id.canScoreScaleTele);
        scoreScaleTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scoreScaleTele.isChecked()) {
                    scaleTossTele.setVisibility(View.VISIBLE);
                    scalePlaceTele.setVisibility(View.VISIBLE);
                } else {
                    scaleTossTele.setVisibility(View.GONE);
                    scalePlaceTele.setVisibility(View.GONE);
                    scaleTossTele.setChecked(false);
                    scalePlaceTele.setChecked(false);
                }
            }
        });
        scalePlaceTele = findViewById(R.id.scoreScalePlaceTele);
        scalePlaceTele.setVisibility(View.GONE);
        scaleTossTele = findViewById(R.id.scoreScaleTossTele);
        scaleTossTele.setVisibility(View.GONE);

        canAssistPrompt = findViewById(R.id.canAssistOthersInClimbing);
        canAssistPrompt.setVisibility(View.GONE);
        canAssist = findViewById(R.id.canAssistOthers);
        canAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canAssist.isChecked()) {
                    climbAssistTypeSpinner.setVisibility(View.VISIBLE);
                } else {
                    climbAssistTypeSpinner.setVisibility(View.GONE);
                }
            }
        });
        canAssist.setVisibility(View.GONE);
        climb_rung = findViewById(R.id.climb_rung);
        climb_rung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (climb_rung.isChecked()) {
                    canAssist.setVisibility(View.VISIBLE);
                    canAssistPrompt.setVisibility(View.VISIBLE);
                    climb_height.setVisibility(View.VISIBLE);
                    howHighText.setVisibility(View.VISIBLE);
                } else {
                    canAssist.setVisibility(View.GONE);
                    canAssistPrompt.setVisibility(View.GONE);
                    climb_height.setVisibility(View.GONE);
                    howHighText.setVisibility(View.GONE);
                }
            }
        });

        climbAssistTypeSpinner = findViewById(R.id.climbAssistTypeSpinner);
        climbAssistTypeSpinner.setVisibility(View.GONE);
        //for wheel type dropdown selector items
        climbAssistTypesArray = getResources().getStringArray(R.array.climbAssistTypes);
        SpinnerAdapter climbAssistAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, climbAssistTypesArray);
        climbAssistTypeSpinner.setAdapter(climbAssistAdapter);
        climbAssistTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String climbAssistType = climbAssistTypesArray[position];
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
        customClimbAssist = findViewById(R.id.customClimbAssist);
        customClimbAssist.setVisibility(View.GONE);
        howHighText = findViewById(R.id.howHighText);
        howHighText.setVisibility(View.GONE);
        climb_height = findViewById(R.id.climb_height);
        climb_height.setVisibility(View.GONE);
        climb_height.setTransformationMethod(null);
        attach_robot = findViewById(R.id.attach_robot);
        comments = findViewById(R.id.comments);
        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
                BenchmarkData data = new BenchmarkData();
                data.setScouterName(scouterName);
                data.setTeamNumber(Integer.parseInt(team_number_input.getText().toString()));
                String driveType = driveTypesArray[driveTypeSpinner.getSelectedItemPosition()];
                if (driveType.equals(getResources().getString(R.string.other))) {
                    data.setTypeOfDrive(customDrive.getText().toString());
                }
                else if (!driveType.contains(getResources().getString(R.string.selectType))) {
                    data.setTypeOfDrive(driveType);
                }
                String wheelType = wheelTypesArray[wheelTypeSpinner.getSelectedItemPosition()];
                if (wheelType.equals(getResources().getString(R.string.other))) {
                    data.setTypeOfWheel(customWheel.getText().toString());
                }
                else if (!wheelType.contains(getResources().getString(R.string.selectType))) {
                    data.setTypeOfWheel(wheelType);
                }
                String NumWheelsString = numWheels.getText().toString();
                if(!NumWheelsString.isEmpty()) {
                    data.setNumberOfWheels(Integer.parseInt(NumWheelsString));
                }

                String speedString = speed.getText().toString();
                if(!speedString.isEmpty()) {
                    data.setSpeed(Integer.parseInt(speedString));
                }

                String heightString = height.getText().toString();
                if(!heightString.isEmpty()){
                    data.setHeight(Integer.parseInt(heightString));
                }

                String weightString = weight.getText().toString();
                if(!weightString.isEmpty()) {
                    data.setWeight(Integer.parseInt(weightString));
                }

                String clearanceString = groundClearance.getText().toString();
                if(!clearanceString.isEmpty()) {
                    data.setGroundClearance(Integer.parseInt(clearanceString));
                }

                data.setPreferedStartOne(prefStart1);
                data.setPreferedStartTwo(prefStart2);
                data.setPreferedStartThree(prefStart3);
                data.setCanStartWithCube(start_w_cube.isChecked());
                data.setAutoCrossLine(move_past_line.isChecked());

                String scoreSwitchPlacedString = howManySwitchPlaceAuto.getText().toString();
                if(!scoreSwitchPlacedString.isEmpty()) {
                    data.setHowManyScoreSwitchPlaced(Integer.parseInt(scoreSwitchPlacedString));
                }

                String scoreSwitchTossedString = howManySwitchTossAuto.getText().toString();
                if(!scoreSwitchTossedString.isEmpty()) {
                    data.setHowManyScoreSwitchTossed(Integer.parseInt(scoreSwitchTossedString));
                }

                String scalePlacedString = howManyScalePlaceAuto.getText().toString();
                if (!scalePlacedString.isEmpty()) {
                    data.setHowManyScoreScalePlaced(Integer.parseInt(scalePlacedString));
                }

                String scaleTossedString = howManyScaleTossAuto.getText().toString();
                if (!scaleTossedString.isEmpty()) {
                    data.setHowManyScoreScaleTossed(Integer.parseInt(scaleTossedString));
                }

                data.setAutoAcquirePortal(fromPortalAuto.isChecked());
                data.setAutoAcquireFloor(fromFloorAuto.isChecked());
                data.setTeleAcquirePortal(fromPortalTele.isChecked());
                data.setTeleAcquireFloor(fromFloorTele.isChecked());
                data.setTeleDepositVault(deposit_vault.isChecked());
                data.setTelePlaceOnSwitch(switchPlaceTele.isChecked());
                data.setTeleTossToSwitch(switchTossTele.isChecked());
                data.setTelePlaceOnScale(scalePlaceTele.isChecked());
                data.setTeleTossToScale(scaleTossTele.isChecked());
                data.setEndClimbRung(climb_rung.isChecked());
                String climbAssistType = climbAssistTypesArray[climbAssistTypeSpinner.getSelectedItemPosition()];
                if (climbAssistType.equals(getResources().getString(R.string.other))) {
                    data.setEndClimbAssistType(customClimbAssist.getText().toString());
                }
                else if (!climbAssistType.contains(getResources().getString(R.string.selectType))) {
                    data.setEndClimbAssistType(climbAssistType);
                }

                String climbHeightString = climb_height.getText().toString();
                if(!climbHeightString.isEmpty()) {
                    data.setEndClimbHeight(Integer.parseInt(climbHeightString));
                }
                data.setEndClimbOnRobot(attach_robot.isChecked());
                data.setComments(comments.getText().toString());

                dataCollection.addBenchmarkData(data);
                fileIO.storeBenchmarkData(data.getJsonString(), team_number_input.getText().toString());
                String msg = "Data Stored";
                Log.d(TAG, msg);
                Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setPrefGroup(int _checkedId) {
        switch (_checkedId) {
            case R.id.pref_left:
                if (prefStart1.equals(getResources().getString(R.string.none))) {
                    prefStart1 = getResources().getString(R.string.Left);
                } else if (prefStart2.equals(getResources().getString(R.string.none))) {
                    prefStart2 = getResources().getString(R.string.Left);
                } else if (prefStart3.equals(getResources().getString(R.string.none))) {
                    prefStart3 = getResources().getString(R.string.Left);
                }
                prefGroup.removeView(findViewById(R.id.pref_left));
                break;
            case R.id.pref_center:
                if (prefStart1.equals(getResources().getString(R.string.none))) {
                    prefStart1 = getResources().getString(R.string.Center);
                } else if (prefStart2.equals(getResources().getString(R.string.none))) {
                    prefStart2 = getResources().getString(R.string.Center);
                } else if (prefStart3.equals(getResources().getString(R.string.none))) {
                    prefStart3 = getResources().getString(R.string.Center);
                }
                prefGroup.removeView(findViewById(R.id.pref_center));
                break;
            case R.id.pref_right:
                if (prefStart1.equals(getResources().getString(R.string.none))) {
                    prefStart1 = getResources().getString(R.string.Right);
                } else if (prefStart2.equals(getResources().getString(R.string.none))) {
                    prefStart2 = getResources().getString(R.string.Right);
                } else if (prefStart3.equals(getResources().getString(R.string.none))) {
                    prefStart3 = getResources().getString(R.string.Right);
                }
                prefGroup.removeView(findViewById(R.id.pref_right));
                break;
        }

        rankChoices.setText("1. " + prefStart1 + "          2. " + prefStart2 + "          3. " + prefStart3);
        chooseAgainButton.setVisibility(View.VISIBLE);
    }

    private  void chooseAgain() {
        prefStart1 = getResources().getString(R.string.none);
        prefStart2 = getResources().getString(R.string.none);
        prefStart3 = getResources().getString(R.string.none);
        rankChoices.setText("1. " + prefStart1 + "          2. " + prefStart2 + "          3. " + prefStart3);
        pref_left.setChecked(false);
        pref_right.setChecked(false);
        pref_center.setChecked(false);

        //removed view so that they come back all in order; fresh start
        prefGroup.removeView(pref_left);
        prefGroup.removeView(pref_center);
        prefGroup.removeView(pref_right);
        //https://stackoverflow.com/questions/19929295/creating-radiogroup-programmatically
        prefGroup.addView(pref_left);
        prefGroup.addView(pref_center);
        prefGroup.addView(pref_right);

        chooseAgainButton.setVisibility(View.GONE);
    }

    void restorePrefStart(String _prefStart) {
        if(_prefStart.equals(getResources().getString(R.string.Left))) {
            setPrefGroup(R.id.pref_left);
        }
        else if(_prefStart.equals(getResources().getString(R.string.Center))) {
            setPrefGroup(R.id.pref_center);
        }
        else if(_prefStart.equals(getResources().getString(R.string.Right))) {
            setPrefGroup(R.id.pref_right);
        }
    }

    private void restorePreferences(int teamNumber) {
        BenchmarkData data = dataCollection.getBenchmarkData(teamNumber);
        if (data != null) {
            setStringInSpinner(data.getTypeOfDrive(), driveTypesArray, driveTypeSpinner, customDrive);
            setStringInSpinner(data.getTypeOfWheel(), wheelTypesArray, wheelTypeSpinner, customWheel);
            numWheels.setText(String.valueOf(data.getNumberOfWheels()));
            speed.setText(String.valueOf(data.getSpeed()));
            height.setText(String.valueOf(data.getHeight()));
            weight.setText(String.valueOf(data.getWeight()));
            groundClearance.setText(String.valueOf(data.getGroundClearance()));
            chooseAgain();
            prefStart1 = data.getPreferedStartOne();
            prefStart2 = data.getPreferedStartTwo();
            prefStart3 = data.getPreferedStartThree();
            restorePrefStart(prefStart1);
            restorePrefStart(prefStart2);
            restorePrefStart(prefStart3);
            start_w_cube.setChecked(data.isCanStartWithCube());
            move_past_line.setChecked(data.isAutoCrossLine());
            howManySwitchPlaceAuto.setText(String.valueOf(data.getHowManyScoreSwitchPlaced()));
            howManySwitchTossAuto.setText(String.valueOf(data.getHowManyScoreSwitchTossed()));
            Map<CheckBox, EditText> canSwitchAutoChildren = new HashMap<>();
            canSwitchAutoChildren.put(switchPlaceAuto, howManySwitchPlaceAuto);
            canSwitchAutoChildren.put(switchTossAuto, howManySwitchTossAuto);
            setVisibililtyOfGroup(canSwitchAuto, canSwitchAutoChildren);
            howManyScalePlaceAuto.setText(String.valueOf(data.getHowManyScoreScalePlaced()));
            howManyScaleTossAuto.setText(String.valueOf(data.getHowManyScoreScaleTossed()));
            Map<CheckBox, EditText> canScaleAutoChildren = new HashMap<>();
            canScaleAutoChildren.put(scalePlaceAuto, howManyScalePlaceAuto);
            canScaleAutoChildren.put(scaleTossAuto, howManyScaleTossAuto);
            setVisibililtyOfGroup(canScaleAuto, canScaleAutoChildren);
            fromPortalAuto.setChecked(data.isAutoAcquirePortal());
            fromFloorAuto.setChecked(data.isAutoAcquireFloor());
            pickUpCubesInAuto.setChecked(fromPortalAuto.isChecked() || fromFloorAuto.isChecked());
            pickUpCubesInAuto.callOnClick();
            fromPortalTele.setChecked(data.isTeleAcquirePortal());
            fromFloorTele.setChecked(data.isTeleAcquireFloor());
            pickUpCubesInTeleOp.setChecked(fromPortalTele.isChecked() || fromFloorTele.isChecked());
            pickUpCubesInTeleOp.callOnClick();
            deposit_vault.setChecked(data.isTeleDepositVault());
            switchPlaceTele.setChecked(data.isTelePlaceOnSwitch());
            switchTossTele.setChecked(data.isTeleTossToSwitch());
            scoreSwitchTele.setChecked(switchPlaceTele.isChecked() || switchTossTele.isChecked());
            scoreSwitchTele.callOnClick();
            scalePlaceTele.setChecked(data.isTelePlaceOnScale());
            scaleTossTele.setChecked(data.isTeleTossToScale());
            scoreScaleTele.setChecked(scalePlaceTele.isChecked() || scaleTossTele.isChecked());
            scoreScaleTele.callOnClick();
            climb_rung.setChecked(data.isEndClimbRung());
            climb_rung.callOnClick();
            if (!data.getEndClimbAssistType().isEmpty()) {
                canAssist.setChecked(true);
                canAssist.callOnClick();
            }
            setStringInSpinner(data.getEndClimbAssistType(), climbAssistTypesArray, climbAssistTypeSpinner, customClimbAssist);
            climb_height.setText(String.valueOf(data.getEndClimbHeight()));
            attach_robot.setChecked(data.isEndClimbOnRobot());
            comments.setText(data.getComments());
        }
        else {
            reset();
        }
    }

    private void setStringInSpinner(String currentValue, String[] positionArray, Spinner spinner, EditText custom){
        spinner.setSelection(0);
        custom.setText("");
        customDrive.setVisibility(View.GONE);
        if (!currentValue.isEmpty()) {
            boolean inSpinner = Arrays.asList(positionArray).contains(currentValue);
            if (inSpinner) {
                int positionInList = 0;
                for (int i = 0; i < positionArray.length; i++) {
                    if (positionArray[i].contentEquals(currentValue)) {
                        positionInList = i;
                        break;
                    }
                }
                spinner.setSelection(positionInList);
            } else {
                spinner.setSelection(positionArray.length - 1);
                custom.setText(currentValue);
                customDrive.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setVisibililtyOfGroup(CheckBox _parent, Map<CheckBox, EditText> _children) {
        for(Map.Entry<CheckBox, EditText> entry: _children.entrySet()) {
            if(!entry.getValue().getText().toString().isEmpty() && !entry.getValue().getText().toString().equals("0")) {
                _parent.setChecked(true);
                _parent.callOnClick();
                entry.getKey().setChecked(true);
                entry.getKey().callOnClick();
            }
        }
    }

    private void reset() {
        setStringInSpinner("", driveTypesArray, driveTypeSpinner, customDrive);
        setStringInSpinner("", wheelTypesArray, wheelTypeSpinner, customWheel);
        numWheels.setText("");
        speed.setText("");
        height.setText("");
        weight.setText("");
        groundClearance.setText("");
        chooseAgain();
        start_w_cube.setChecked(false);
        move_past_line.setChecked(false);
        canSwitchAuto.setChecked(false);
        switchPlaceAuto.setChecked(false);
        switchTossAuto.setChecked(false);
        howManySwitchPlaceAuto.setText("");
        howManySwitchTossAuto.setText("");
        canSwitchAuto.callOnClick(); // resets visibility
        canScaleAuto.setChecked(false);
        scalePlaceAuto.setChecked(false);
        scaleTossAuto.setChecked(false);
        howManyScalePlaceAuto.setText("");
        howManyScaleTossAuto.setText("");
        canScaleAuto.callOnClick(); // resets visibility
        fromPortalAuto.setChecked(false);
        fromFloorAuto.setChecked(false);
        pickUpCubesInAuto.setChecked(false);
        pickUpCubesInAuto.callOnClick();
        fromPortalTele.setChecked(false);
        fromFloorTele.setChecked(false);
        pickUpCubesInTeleOp.setChecked(false);
        pickUpCubesInTeleOp.callOnClick();
        deposit_vault.setChecked(false);
        switchPlaceTele.setChecked(false);
        switchTossTele.setChecked(false);
        scoreSwitchTele.setChecked(false);
        scoreSwitchTele.callOnClick();
        scalePlaceTele.setChecked(false);
        scaleTossTele.setChecked(false);
        scoreScaleTele.setChecked(false);
        scoreScaleTele.callOnClick();
        climb_rung.setChecked(false);
        climb_rung.callOnClick();
        canAssist.setChecked(false);
        canAssist.callOnClick();
        setStringInSpinner("", climbAssistTypesArray, climbAssistTypeSpinner, customClimbAssist);
        climb_height.setText("");
        attach_robot.setChecked(false);
        comments.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String msg = "Picture Saved!";
                    Log.d(TAG, msg);
                    Toast.makeText(this, TAG + msg, Toast.LENGTH_LONG).show();
                }
                else {
                    Dialog dialog = utility.getPositiveButtonDialog(this, TAG,
                            "Failed to save picture: Tell a developer!.",
                            "Okay");
                    dialog.show();
                }
                break;
        }
    }
}
