package sparx1126.com.powerup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.utilities.DataCollection;

public class Benchmarking extends AppCompatActivity {
    private static final String TAG = "Benchmarking ";

    private static DataCollection dataCollection;
    private List<Integer> teamsInEvent;

    private AutoCompleteTextView team_number_input;
    private View benchmark_main_layout;
    private AutoCompleteTextView drive_auto_complete;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private CheckBox pref_left;
    private CheckBox pref_center;
    private CheckBox pref_right;
    private CheckBox start_w_cube;
    private CheckBox move_past_line;
    private CheckBox auto_switch;
    private CheckBox auto_scale;
    private CheckBox acquire_floor;
    private CheckBox get_from_portal;
    private CheckBox deposit_vault;
    private CheckBox score_switch;
    private RadioButton place_switch;
    private RadioButton toss_switch;
    private CheckBox score_scale;
    private RadioButton place_scale;
    private RadioButton toss_scale;
    private RadioButton pref_floor;
    private RadioButton pref_portal;
    private CheckBox climb_rung;
    private CheckBox has_rungs;
    private EditText climb_height;
    private CheckBox attach_robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);

        dataCollection = DataCollection.getInstance();
        teamsInEvent =  dataCollection.getTeamsInEvent();

        team_number_input = findViewById(R.id.team_number);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamsInEvent);
        team_number_input.setAdapter(adapter);
        team_number_input.setThreshold(1);

        Button teamButton = findViewById(R.id.teamButton);
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamNumberStrg = team_number_input.getText().toString();
                int teamNumber = Integer.valueOf(teamNumberStrg);
                boolean teamNumberFound = teamsInEvent.contains(teamNumber);
                if (teamNumberFound) {
                    Log.d(TAG, teamNumberStrg);
                    dismissKeyboard();
                    benchmark_main_layout.setVisibility(View.VISIBLE);
                    BenchmarkData data = dataCollection.getBenchmarkData(teamNumber);
                    if(data != null) {
                        String msg = "Found Benchmark for " + teamNumber;
                        Log.e(TAG, msg);
                        Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                        restorePreferences(data);
                    }
                }
                else {
                    String msg = "Team number " + teamNumber + " not found!";
                    Log.e(TAG, msg);
                    Toast.makeText(Benchmarking.this, TAG + msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        benchmark_main_layout = findViewById(R.id.benchmark_main_layout);
        benchmark_main_layout.setVisibility(View.INVISIBLE);
        drive_auto_complete = findViewById(R.id.drive_auto_complete);
        speed = findViewById(R.id.speed);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        pref_left = findViewById(R.id.pref_left);
        pref_center = findViewById(R.id.pref_center);
        pref_right = findViewById(R.id.pref_right);
        start_w_cube = findViewById(R.id.start_w_cube);
        move_past_line = findViewById(R.id.move_past_line);
        auto_switch = findViewById(R.id.auto_switch);
        auto_scale = findViewById(R.id.auto_scale);
        acquire_floor = findViewById(R.id.acquire_floor);
        get_from_portal = findViewById(R.id.get_from_portal);
        deposit_vault = findViewById(R.id.deposit_vault);
        score_switch = findViewById(R.id.score_switch);
        place_switch = findViewById(R.id.place_switch);
        toss_switch = findViewById(R.id.toss_switch);
        score_scale = findViewById(R.id.score_scale);
        place_scale = findViewById(R.id.place_scale);
        toss_scale = findViewById(R.id.toss_scale);
        pref_floor = findViewById(R.id.pref_floor);
        pref_portal = findViewById(R.id.pref_portal);
        climb_rung = findViewById(R.id.climb_rung);
        has_rungs = findViewById(R.id.has_rungs);
        climb_height = findViewById(R.id.climb_height);
        attach_robot = findViewById(R.id.attach_robot);
        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BenchmarkData data = new BenchmarkData();
                data.setTeamNumber(Integer.parseInt(team_number_input.getText().toString()));
                data.setTypeOfDrive(drive_auto_complete.getText().toString());
                if(!speed.getText().toString().isEmpty()) {
                    data.setSpeed(Integer.parseInt(speed.getText().toString()));
                }
                if(!height.getText().toString().isEmpty()) {
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
        drive_auto_complete.setText(_data.getTypeOfDrive());
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
        if(_data.isPlaceOnSwitch() || _data.isTossToSwitch()) {
            place_switch.setSelected(_data.isPlaceOnSwitch());
            toss_switch.setSelected(_data.isTossToSwitch());
        }
        if(_data.isPlaceOnScale() || _data.isTossToScale()) {
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
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
