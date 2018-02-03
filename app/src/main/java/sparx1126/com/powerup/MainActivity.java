package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import sparx1126.com.powerup.blue_alliance.BlueAllianceMatch;
import sparx1126.com.powerup.blue_alliance.BlueAllianceNetworking;
import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;
import sparx1126.com.powerup.blue_alliance.BlueAllianceTeam;

public class MainActivity extends AppCompatActivity {
    private static final String[] studentList = {"Aaron Mayernik", "Adam Gesner", "Adam Daniels", "Aidan Cheeseman", "Andrew D'Ambrosio", "Andrew Ophardt",
                                                 "Andrew Thompson", "Andrew Bixler", "Brandon Connor", "Brian Jimenez", "Chris Voigt", "Colin Thompson",
                                                 "Darwinch Pray", "David Tomer", "Drew Moulton", "Emory Towne", "Ethan Nguyen", "Evan Hart", "Felix Huang",
                                                 "Giovanni Mannino", "Gus Pellett", "Helena Robinson", "Jack Griebel", "James Hart", "Jaren Cascino",
                                                 "JD Thomann", "Jensen Li", "Joshua Ramph", "JT Mongeon", "Justin Fici", "Justin Rogers", "Karina Bryant",
                                                 "Kevin Bates", "Logan Ogden", "Mason Seibert", "Matthew Connor", "Michael Geraci", "Michael Anderson",
                                                 "Nathan Hunt", "Nick Castronova", "Nolan White", "Olivia Markovitz", "Owen Aser", "Peyton Roemer", "Rebekah Mayernik",
                                                 "Ruoyan Li", "Ryan Mcveigh", "Skyler Ferrante", "Spencer Griebel", "Spenser Dewey", "Thomas Shannon", "Tyler Armstrong",
                                                 "Tyler Mosher", "Tyler Baker", "Zach Rogers", "Zachary Charlebois"};
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

        blueAlliance.getEventMatches("2016nytr", new BlueAllianceNetworking.CallbackMatches() {
            @Override
            public void onFailure(String _reason) {
                Log.e("getEventMatches Error", _reason);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceMatch> _result) {
                for(Map.Entry<String, BlueAllianceMatch> entry : _result.entrySet()) {
                    Log.e(entry.getKey(), entry.getValue().toString());
                }
            }
        });
    }
}