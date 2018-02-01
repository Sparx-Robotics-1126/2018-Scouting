package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import sparx1126.com.powerup.data_components.ScoutingData;

public class Scouting extends AppCompatActivity {
    private ScoutingData scoutingData;
    private EditText teamnum;
    private RadioButton blueAlliancecolor;
    private RadioButton redAlliancecolor;
    private EditText matchnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scouting);

         scoutingData = new ScoutingData();
        teamnum = findViewById(R.id.scouteamnuminput);
        teamnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                scoutingData.setTeamnumber(Integer.parseInt(editable.toString()));
                Log.e("afterTextChanged", Integer.toString(scoutingData.getTeamnumber()));
            }
        });
    matchnum=findViewById(R.id.matchnumimput);
    matchnum.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            scoutingData.setTeamnumber(Integer.parseInt(editable.toString()));
            Log.e("afterTextChanged", Integer.toString(scoutingData.getTeamnumber()));

        }
    });
    blueAlliancecolor = findViewById(R.id.blueAlliancebuttton);

}}