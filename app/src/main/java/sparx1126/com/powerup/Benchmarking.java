package sparx1126.com.powerup;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import sparx1126.com.powerup.data_components.BenchmarkData;

public class Benchmarking extends AppCompatActivity {
    private BenchmarkData currentData;
    private EditText teamnumber;
    private EditText speed;
    private EditText height;
    private EditText weight;
    private EditText climbheight;
    private CheckBox start_w_cube;
    private CheckBox move_past_line;
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
    private RadioButton place_switch;
    private RadioButton toss_switch;
    private RadioButton place_scale;
    private RadioButton toss_scale;
    private RadioButton pref_floor;
    private RadioButton pref_portal;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.benchmarking);

            currentData = new BenchmarkData();

        teamnumber = findViewById(R.id.teamnumberinput);
        teamnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentData.setTeamnumber(Integer.parseInt(s.toString()));
            }
        });
    }
}