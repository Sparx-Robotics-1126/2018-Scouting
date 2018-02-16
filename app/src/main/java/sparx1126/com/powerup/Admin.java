package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.utilities.BlueAllianceNetworking;
import sparx1126.com.powerup.utilities.DataCollection;


public class Admin extends AppCompatActivity {
    private static final String TAG = "Admin ";
    private SharedPreferences settings;
    SharedPreferences.Editor editor;
    private BlueAllianceNetworking blueAlliance;
    private static DataCollection dataCollection;

    Dialog eventsWeAreInDialog;
    Dialog matchesDialog;
    Dialog teamsDialog;
    private Spinner eventSpinner;
    private ToggleButton blueSelectedToggle;
    private RadioButton teamNumber1SelectedButton;
    private RadioButton teamNumber2SelectedButton;
    private RadioButton teamNumber3SelectedButton;
    private Button stuffSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        editor = settings.edit();
        blueAlliance = BlueAllianceNetworking.getInstance();
        dataCollection = DataCollection.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TAG);
        builder.setMessage("Downloading Events We Are In");
        eventsWeAreInDialog = builder.create();

        builder.setMessage("Downloading Matches for event");
        matchesDialog = builder.create();

        builder.setMessage("Downloading Team for event");
        teamsDialog = builder.create();

        eventSpinner = findViewById(R.id.eventSpinner);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.isEmpty() && !selectedItem.contains(getResources().getString(R.string.selectEvent))) {
                    editor.putString(getResources().getString(R.string.pref_event), selectedItem);
                    editor.apply();
                    matchesDialog.show();
                    blueAlliance.downloadEventMatches(selectedItem, Admin.this, new BlueAllianceNetworking.Callback() {
                        @Override
                        public void handleFinishDownload() {
                            matchesDialog.dismiss();
                            String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
                            teamsDialog.show();
                            blueAlliance.downloadEventTeams(pref_SelectedEvent, Admin.this, new BlueAllianceNetworking.Callback() {

                                @Override
                                public void handleFinishDownload() {
                                    teamsDialog.dismiss();
                                    showButtons();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        blueSelectedToggle = (ToggleButton) findViewById(R.id.blueSelectedToggle);
        teamNumber1SelectedButton = (RadioButton) findViewById(R.id.team1);
        teamNumber2SelectedButton = (RadioButton) findViewById(R.id.team2);
        teamNumber3SelectedButton = (RadioButton) findViewById(R.id.team3);

        stuffSelectedButton = (Button) findViewById(R.id.selectStuff);
        stuffSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamNumber1SelectedButton.isChecked() || teamNumber2SelectedButton.isChecked() || teamNumber3SelectedButton.isChecked() ) {
                    editor.putString(getResources().getString(R.string.pref_SelectedEvent), eventSpinner.getSelectedItem().toString());
                    editor.putBoolean(getResources().getString(R.string.pref_BlueAlliance), blueSelectedToggle.isChecked());
                    if (teamNumber1SelectedButton.isChecked()) {
                        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 1);
                    }
                    else if(teamNumber2SelectedButton.isChecked()) {
                        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 2);
                    }
                    else {
                        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 3);
                    }
                    editor.apply();
                    finish();
                }
            }
        });

        restorePreferences();
    }

    private void restorePreferences() {
        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
        if (!pref_SelectedEvent.isEmpty()) {
            Map<String, BlueAllianceEvent> data = dataCollection.getEventsWeAreIn();
            List<String> eventSpinnerList = new ArrayList<>();
            eventSpinnerList.add(pref_SelectedEvent);
            for (String eventName : data.keySet()) {
                if (!eventName.equals(pref_SelectedEvent)) {
                    eventSpinnerList.add(eventName);
                }
            }
            SpinnerAdapter eventAdapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_spinner_item, eventSpinnerList);
            eventSpinner.setAdapter(eventAdapter);
        } else {
            eventsWeAreInDialog.show();
            blueAlliance.downloadEventsSparxsIsIn(this, new BlueAllianceNetworking.Callback() {
                @Override
                public void handleFinishDownload() {
                    Map<String, BlueAllianceEvent> data = dataCollection.getEventsWeAreIn();
                    List<String> eventSpinnerList = new ArrayList<>();
                    eventSpinnerList.add(getResources().getString(R.string.selectEvent));
                    for (String eventName : data.keySet()) {
                        eventSpinnerList.add(eventName);
                    }
                    SpinnerAdapter eventAdapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_spinner_item, eventSpinnerList);
                    eventSpinner.setAdapter(eventAdapter);
                    eventsWeAreInDialog.dismiss();
                }
            });
        }

        boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
        if (pref_BlueAlliance) {
            blueSelectedToggle.setChecked(true);
        } else {
            blueSelectedToggle.setChecked(false);
        }

        int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 1);

        if (pref_TeamPosition == 1) {
            teamNumber1SelectedButton.setChecked(true);
        } else if (pref_TeamPosition == 2) {
            teamNumber2SelectedButton.setChecked(true);
        } else {
            teamNumber3SelectedButton.setChecked(true);
        }
        showButtons();
    }


    private void showButtons() {
        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");

        if (!pref_SelectedEvent.isEmpty()) {
            stuffSelectedButton.setVisibility(View.VISIBLE);
            blueSelectedToggle.setVisibility(View.VISIBLE);
            teamNumber1SelectedButton.setVisibility(View.VISIBLE);
            teamNumber2SelectedButton.setVisibility(View.VISIBLE);
            teamNumber3SelectedButton.setVisibility(View.VISIBLE);
        } else {
            stuffSelectedButton.setVisibility(View.INVISIBLE);
            blueSelectedToggle.setVisibility(View.INVISIBLE);
            teamNumber1SelectedButton.setVisibility(View.INVISIBLE);
            teamNumber2SelectedButton.setVisibility(View.INVISIBLE);
            teamNumber3SelectedButton.setVisibility(View.INVISIBLE);
        }
    }
}
