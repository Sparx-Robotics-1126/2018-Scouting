package steamworks.sparx1126.com.powerup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.text.TextWatcher;
import android.text.Editable;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String[] studentList = {"Felix", "Huang"};
    AutoCompleteTextView scoutername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoutername = findViewById(R.id.scoutername);
        scoutername.addTextChangedListener(scouterTextEntered);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        scoutername.setAdapter(adapter);
        scoutername.setThreshold(1);
    }



    private final TextWatcher scouterTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable lastInput) {
            String scouterName = lastInput .toString();
            scouterName = scouterName.replace("\\n", "");
            scouterName = scouterName.replace("\\r", "");
            System.out.println(scouterName);
            if (Arrays.asList(studentList).contains(scoutername.getText().toString())) {
                System.out.print("I got a real name");
            }
           /* if (Arrays.asList(studentList).contains(scouterName)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_scouter), scouterName);
                editor.apply();
                Log.e(TAG, "Selected scouter - " + scouterName);
            }
            showButtons();*/
        }
    };
}