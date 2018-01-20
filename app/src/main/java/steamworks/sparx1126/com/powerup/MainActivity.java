package steamworks.sparx1126.com.powerup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.text.TextWatcher;
import android.text.Editable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView scoutername = findViewById(R.id.scoutername);
        scoutername.addTextChangedListener(scouterTextEntered);
    }



    private final TextWatcher scouterTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println(s.toString());
           /* String scouterName = getScouterName();
            if (Arrays.asList(studentList).contains(scouterName)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_scouter), scouterName);
                editor.apply();
                Log.e(TAG, "Selected scouter - " + scouterName);
            }
            showButtons();*/
        }
    };
}