package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Map;

import sparx1126.com.powerup.blue_alliance.BlueAllianceNetworking;
import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;

public class MainActivity extends AppCompatActivity {
    private static final String[] studentList = {"Felix", "Huang"};
    private static BlueAllianceNetworking blueAlliance;
    private AutoCompleteTextView studentNameAutoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueAlliance = BlueAllianceNetworking.getInstance();

        // student selection
        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(1);
        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedStudent = (String) parent.getItemAtPosition(pos);
                Log.d("onItemClick", selectedStudent);
            }
        });

        blueAlliance.getEventsSparxsIsIn(new BlueAllianceNetworking.CallbackEvents() {
            @Override
            public void onFailure(String _reason) {
                Log.e("getEventsSparxsIsIn", _reason);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceEvent> _result) {
            }
        });
    }
}