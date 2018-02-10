package sparx1126.com.powerup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.utilities.DataCollection;


public class Admin extends AppCompatActivity {
    private static final String TAG = "Admin";
    private SharedPreferences settings;
    private Spinner eventSpinner;
    private ArrayAdapter<String> eventAdapter;
    private List<String> eventsWeAreInList;
    private List<String> eventSpinnerList;
    private Map<String, String> eventNameToKeyMap;
    private boolean eventSelected = false;
    private boolean eventFilter = true;
    private ToggleButton blueSelectedToggle;
    private RadioButton teamNumberOneSelectedButton;
    private RadioButton teamNumber2SelectedButton;
    private RadioButton teamNumber3SelectedButton;
    private boolean teamNumberSelected = false;
    private Button stuffSelectedButton;
    private static DataCollection dataCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        dataCollection = DataCollection.getInstance();


        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        Map<String, BlueAllianceEvent> eventsWeAreIn = dataCollection.getEventsWeAreIn();
        eventSpinnerList = new ArrayList<>();
        eventSpinnerList.add("Select Event");
        for (String eventName : eventsWeAreIn.keySet()) {
            eventSpinnerList.add(eventName);
        }

        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventSpinnerList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);



        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!getEventName().isEmpty()) {

                    if(!getEventName().contains("Select Event")) {
                        eventSelected = true;
                    }
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(getResources().getString(R.string.pref_event), getEventName());
                    editor.apply();


                    showButtons();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //eventsNearTodayList = new ArrayList<>();
        eventsWeAreInList = new ArrayList<>();

        eventNameToKeyMap = new HashMap<>();

        blueSelectedToggle = (ToggleButton) findViewById(R.id.blueSelectedToggle);

        teamNumberOneSelectedButton = (RadioButton) findViewById(R.id.team1);
        teamNumberOneSelectedButton.setOnClickListener(teamNumberSelectionFunction);

        teamNumber2SelectedButton = (RadioButton) findViewById(R.id.team2);
        teamNumber2SelectedButton.setOnClickListener(teamNumberSelectionFunction);
        teamNumber2SelectedButton.setVisibility(View.INVISIBLE);

        teamNumber3SelectedButton = (RadioButton) findViewById(R.id.team3);
        teamNumber3SelectedButton.setOnClickListener(teamNumberSelectionFunction);

        stuffSelectedButton = (Button) findViewById(R.id.selectStuff);
        stuffSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean blueSelected = blueSelectedToggle.isChecked();
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(getString(R.string.red_alliance), !blueSelected);
                editor.apply();


                finish();
            }
        });
        showButtons();
        //restorePreferences();
    }

    private final View.OnClickListener teamNumberSelectionFunction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int teamNumberIndex = Integer.MAX_VALUE;
            if (teamNumberOneSelectedButton.isChecked()) {
                teamNumberIndex = 0;
            } else if (teamNumber2SelectedButton.isChecked()) {
                teamNumberIndex = 1;
            } else if (teamNumber3SelectedButton.isChecked()) {
                teamNumberIndex = 2;
            }

            if (teamNumberIndex != Integer.MAX_VALUE) {
                teamNumberSelected = true;
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(getString(R.string.team_selected), teamNumberIndex);
                editor.apply();

            }
            else {
                teamNumberSelected = false;
            }
            showButtons();
        }
    };


    private String getEventName() {
        String eventName = "";
        if (eventSpinner.getSelectedItem() != null) {
            eventName = eventSpinner.getSelectedItem().toString();
        }
        return eventName;
    }

    private void restorePreferences() {
        String eventName = settings.getString(getResources().getString(R.string.pref_event), "");
        if (!eventName.isEmpty() && (eventAdapter != null)) {

            //fillInEvents(dbHelper.createEventsWeAreInNameCursor(), eventsWeAreInList);
           // fillInEvents(dbHelper.createAllEventsCursor(), eventsNearTodayList);

            // MIKE get list
            setupEventSpinner(eventsWeAreInList);
            eventSpinner.setSelection(eventAdapter.getPosition(eventName));
        }

        int teamIndex = settings.getInt(getResources().getString(R.string.team_selected), Integer.MAX_VALUE);
        if (teamIndex == 0) {
            teamNumberOneSelectedButton.setChecked(true);
        } else if (teamIndex == 1) {
            teamNumber2SelectedButton.setChecked(true);
        } else if (teamIndex == 2) {
            teamNumber3SelectedButton.setChecked(true);
        }
    }


    private void setupEventSpinner(List<String> eventList) {
        eventSpinnerList.clear();
        for (String eventName : eventList) {
            eventSpinnerList.add(eventName);
        }

        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventSpinnerList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);
    }



    private void showButtons() {
        if (eventSelected) {
            stuffSelectedButton.setVisibility(View.VISIBLE);
            blueSelectedToggle.setVisibility(View.VISIBLE);
            teamNumberOneSelectedButton.setVisibility(View.VISIBLE);
            teamNumber2SelectedButton.setVisibility(View.VISIBLE);
            teamNumber3SelectedButton.setVisibility(View.VISIBLE);
        } else {
            stuffSelectedButton.setVisibility(View.INVISIBLE);
            blueSelectedToggle.setVisibility(View.INVISIBLE);
            teamNumberOneSelectedButton.setVisibility(View.INVISIBLE);
            teamNumber2SelectedButton.setVisibility(View.INVISIBLE);
            teamNumber3SelectedButton.setVisibility(View.INVISIBLE);
        }
    }
}
