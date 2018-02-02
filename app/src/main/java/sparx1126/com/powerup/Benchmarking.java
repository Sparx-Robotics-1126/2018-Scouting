package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import sparx1126.com.powerup.data_components.BenchmarkData;

public class Benchmarking extends AppCompatActivity {
    private BenchmarkData currentData;
    private EditText teamnumber;


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
                //Log.d("afterTextChanged", Integer.toString(currentData.getTeamnumber()));
            }
        });
    }
}