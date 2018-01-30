package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import sparx1126.com.powerup.components.BenchmarkData;

/**
 * Created by Hiram on 1/20/2018.
 */

public class Benchmarking extends AppCompatActivity {
    EditText teamnumber;
    BenchmarkData currentData = new BenchmarkData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarking);

        teamnumber = findViewById(R.id.teamnumberinput);
        teamnumber.addTextChangedListener(teaminput);

    }

    private final TextWatcher teaminput = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            currentData.setTeamnumber(Integer.getInteger(s.toString()));
            System.out.println(currentData);

        }
    };
}