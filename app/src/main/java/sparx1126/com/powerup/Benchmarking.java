package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.utilities.Logger;

public class Benchmarking extends AppCompatActivity {

    private EditText team_number_input;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private EditText climb_height;
    private EditText customDrive;
    private EditText customWheel;

    private CheckBox start_w_cube;
    private CheckBox move_past_line;
    private CheckBox general_score_auto;
    private CheckBox auto_scale;
    private CheckBox auto_switch;
    private CheckBox acquire_floor;
    private CheckBox get_from_portal;
    private CheckBox deposit_vault;
    private CheckBox score_switch;
    private CheckBox score_scale;
    private CheckBox climb_rung;
    private CheckBox has_rungs;
    private CheckBox attach_robot;



    private RadioButton pref_left;
    private RadioButton pref_center;
    private RadioButton pref_right;
    private RadioButton place_switch;
    private RadioButton toss_switch;
    private RadioButton place_scale;
    private RadioButton toss_scale;
    private RadioButton pref_floor;
    private RadioButton pref_portal;
    private TextView rankChoices;
    private Spinner driveTypeSpinner;
    private Spinner wheelTypeSpinner;
    private Button submit_button;
    private Button chooseAgainButton;


    private String driveType;
    private String wheelType;

    private String prefStart1 = "none";
    private String prefStart2 = "none";
    private String prefStart3 = "none";

    RadioGroup prefGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);


        team_number_input = findViewById(R.id.team_number);
        speed = findViewById(R.id.speed);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        climb_height = findViewById(R.id.climb_height);
        customDrive = findViewById(R.id.customDrive);
        //https://stackoverflow.com/questions/18708955/invisible-components-still-take-up-space
        customDrive.setVisibility(View.GONE);
        customWheel = findViewById(R.id.customWheel);
        customWheel.setVisibility(View.GONE);


        start_w_cube = findViewById(R.id.start_w_cube);
        move_past_line = findViewById(R.id.move_past_line);
        general_score_auto = findViewById(R.id.general_score_auto);
        auto_scale = findViewById(R.id.auto_scale);
        auto_switch = findViewById(R.id.auto_switch);
        acquire_floor = findViewById(R.id.acquire_floor);
        get_from_portal = findViewById(R.id.get_from_portal);
        deposit_vault = findViewById(R.id.deposit_vault);
        score_switch = findViewById(R.id.score_switch);
        score_scale = findViewById(R.id.score_scale);
        climb_rung = findViewById(R.id.climb_rung);
        has_rungs = findViewById(R.id.has_rungs);
        attach_robot = findViewById(R.id.attach_robot);
        pref_left = findViewById(R.id.pref_left);
        pref_center = findViewById(R.id.pref_center);
        pref_right = findViewById(R.id.pref_right);

        place_switch = findViewById(R.id.place_switch);
        toss_switch = findViewById(R.id.toss_switch);
        place_scale = findViewById(R.id.place_scale);
        toss_scale = findViewById(R.id.toss_scale);
        pref_floor = findViewById(R.id.pref_floor);
        pref_portal = findViewById(R.id.pref_portal);
        rankChoices = findViewById(R.id.rankStartTextView);
        driveTypeSpinner = findViewById(R.id.drive_type_spinner);
        wheelTypeSpinner = findViewById(R.id.wheel_type_spinner);
        submit_button = findViewById(R.id.submit_button);
        chooseAgainButton = findViewById(R.id.chooseAgainButton);
        prefGroup = findViewById(R.id.prefStartGroup);


        //resets the choices
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
                rankChoices.setText("1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);

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
                rankChoices.setText("1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);
            }
        });



        prefGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.pref_left:
                            if(prefStart1.equals("none")) {
                                prefStart1 = "Left";
                            } else if(prefStart2.equals("none")) {
                                prefStart2 = "Left";
                            } else {
                                prefStart3 = "Left";
                            }
                            prefGroup.removeView(findViewById(R.id.pref_left));
                        break;
                    case R.id.pref_center:
                            if(prefStart1.equals("none")) {
                                prefStart1 = "Center";
                            } else if(prefStart2.equals("none")) {
                                prefStart2 = "Center";
                            } else {
                                prefStart3 = "Center";
                            }
                            prefGroup.removeView(findViewById(R.id.pref_center));
                        break;
                    case R.id.pref_right:
                            if(prefStart1.equals("none")) {
                                prefStart1 = "Right";
                            } else if(prefStart2.equals("none")) {
                                prefStart2 = "Right";
                            } else {
                                prefStart3 = "Right";
                            }
                            prefGroup.removeView(findViewById(R.id.pref_right));
                        break;
                }
//                if (numTimesRegex(rankChoices.getText().toString(), "none") == 3) {
//                    rankChoices.setText("1. " + prefStart1 + " 2. none 3. none");
//                } else if(numTimesRegex(rankChoices.getText().toString(), "none") == 2) {
//                    rankChoices.setText("1. " + prefStart1 + " 2. " + prefStart2 + " 3. none");
//                } else if(numTimesRegex(rankChoices.getText().toString(), "none") == 1) {
//                    rankChoices.setText("1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);
//                }
                rankChoices.setText("1. " + prefStart1 + " 2. " + prefStart2 + " 3. " + prefStart3);
            }});



        //for drive type
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        final String[] driveTypeArraySpinner = new String[] {
                "Select Drive Type", "Tank", "Mecanum", "Swerve", "H-Drive / Slide", "Other"
        };

        ArrayAdapter<String> driveAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, driveTypeArraySpinner);
        driveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driveTypeSpinner.setAdapter(driveAdapter);

        //https://stackoverflow.com/questions/3928071/setting-a-spinner-onclicklistener-in-android
        driveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driveType = driveTypeArraySpinner[position];
                if(driveType.equals("Other")) {
                    customDrive.setVisibility(View.VISIBLE);
                } else {
                    customDrive.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //for wheel type
        final String[] wheelTypeArraySpinner = new String[] {
                "Select Wheel Type", "Traction Narrow", "Traction Wide",
                "Omni", "Tank Treads", "Combination Traction And Omni", "Other"
        };

        ArrayAdapter<String> wheelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, wheelTypeArraySpinner);
        wheelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wheelTypeSpinner.setAdapter(wheelAdapter);

        wheelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wheelType = wheelTypeArraySpinner[position];
                if(wheelType.equals("Other")) {
                    customWheel.setVisibility(View.VISIBLE);
                } else {
                    customWheel.setVisibility(View.GONE);
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
                BenchmarkData benchmarkData = new BenchmarkData();
                if(!team_number_input.getText().toString().isEmpty()){
                    benchmarkData.setTeamnumber(Integer.parseInt(team_number_input.getText().toString()));
                }




            }
        });
    }
    //https://ubuntuforums.org/showthread.php?t=752729
    public int numTimesRegex(String str, String findStr) {
        int lastIndex = 0;
        int count =0;

        while(lastIndex != -1){

            lastIndex = str.indexOf(findStr,lastIndex);

            if( lastIndex != -1){
                count ++;
            }
        }
        return count;
    }
}






// Swerve, Mecanum, Tank Drive, H-Drive