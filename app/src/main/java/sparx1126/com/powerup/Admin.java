package sparx1126.com.powerup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import sparx1126.com.powerup.R;

/**
 * Created by profe on 2/3/2018.
 */



public class Admin extends AppCompatActivity{
    Button b;
    String scoutingPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        b = (Button)findViewById(R.id.continueButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO link to other things
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.red1:
                if (checked) {
                    scoutingPreference = "red1";
                }
                    break;
            case R.id.red2:
                if (checked) {
                    scoutingPreference = "red2";
                }
                    break;
            case R.id.red3:
                if (checked) {
                    scoutingPreference = "red3";
                }
                    break;


            case R.id.blue1:
                if (checked) {
                    scoutingPreference = "blue1";
                }
                    break;
            case R.id.blue2:
                if (checked) {
                    scoutingPreference = "blue2";
                }
                    break;
            case R.id.blue3:
                if (checked) {
                    scoutingPreference = "blue3";
                }
                    break;
        }
    }
}
