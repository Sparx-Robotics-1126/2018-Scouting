package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.utilities.Logger;

public class Benchmarking extends AppCompatActivity {

    private EditText team_number_input;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private EditText climb_height;

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
    private CheckBox pref_left;
    private CheckBox pref_center;
    private CheckBox pref_right;
    private CheckBox can_left;
    private CheckBox can_center;
    private CheckBox can_right;


    private RadioButton place_switch;
    private RadioButton toss_switch;
    private RadioButton place_scale;
    private RadioButton toss_scale;
    private RadioButton pref_floor;
    private RadioButton pref_portal;
    private AutoCompleteTextView drive_auto_complete;
    private Button submit_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);


        team_number_input = findViewById(R.id.team_number);
        speed = findViewById(R.id.speed);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        climb_height = findViewById(R.id.climb_height);

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
        can_left = findViewById(R.id.can_left);
        can_center = findViewById(R.id.can_center);
        can_right = findViewById(R.id.can_right);

        place_switch = findViewById(R.id.place_switch);
        toss_switch = findViewById(R.id.toss_switch);
        place_scale = findViewById(R.id.place_scale);
        toss_scale = findViewById(R.id.toss_scale);
        pref_floor = findViewById(R.id.pref_floor);
        pref_portal = findViewById(R.id.pref_portal);

        drive_auto_complete = findViewById(R.id.drive_auto_complete);

        submit_button = findViewById(R.id.submit_button);










// do something tactile when submit button



        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BenchmarkData benchmarkData = new BenchmarkData();
                if(!team_number_input.getText().toString().isEmpty()){
                    benchmarkData.setTeamnumber(Integer.parseInt(team_number_input.getText().toString()));
                }



                if (general_score_auto.isChecked()){

                }





            }
        });
    }
}






// Swerve, Mecanum, Tank Drive, H-Drive



/*
                benchmarkData.set(start_w_cube.isChecked());
                benchmarkData.set(move_past_line.isChecked());
                benchmarkData.set(auto_scale.isChecked());
                benchmarkData.set(auto_switch.isChecked());
                benchmarkData.set(acquire_floor.isChecked());
                benchmarkData.set(get_from_portal.isChecked());
                benchmarkData.set(deposit_vault.isChecked());
                benchmarkData.set(score_switch.isChecked());
                benchmarkData.set(score_scale.isChecked());
                benchmarkData.set(climb_rung.isChecked());
                benchmarkData.set(has_rungs.isChecked());
                benchmarkData.set(attach_robot.isChecked());
                benchmarkData.set(pref_left.isChecked());
                benchmarkData.set(pref_center.isChecked());
                benchmarkData.set(pref_right.isChecked());
                benchmarkData.set(pref_left.isChecked());
                benchmarkData.set(can_left.isChecked());
                benchmarkData.set(can_center.isChecked());
                benchmarkData.set(can_right.isChecked());
 */