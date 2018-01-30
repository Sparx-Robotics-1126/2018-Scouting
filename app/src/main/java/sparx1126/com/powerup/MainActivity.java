package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

import sparx1126.com.powerup.networking.BlueAlliance;

public class MainActivity extends AppCompatActivity {
    String[] studentList = {"Felix", "Huang"};
    AutoCompleteTextView studenName;
    BlueAlliance blueAlliance = BlueAlliance.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // student selection
        studenName = findViewById(R.id.studentNameText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studenName.setAdapter(adapter);
        studenName.setThreshold(1);
        studenName.setOnItemClickListener(studentSelectedFunction);

        blueAlliance.fetchEventData();;

        //code to get what meets our team is in
        ArrayList<String> sparxEvents = blueAlliance.getTeamEvents(1126);
        Log.e("NUMBER SPARX EVENTS" , Integer.toString(sparxEvents.size()));
        for(int i = 0; i < sparxEvents.size(); i++) {
            Log.e("SPARX Event 1" , sparxEvents.get(i));

        }
    }

    private final AdapterView.OnItemClickListener studentSelectedFunction = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
            //String selectedStudent = (String) parent.getItemAtPosition(pos);
            //System.out.println("onItemClick " + selectedStudent);
        }
    };
}