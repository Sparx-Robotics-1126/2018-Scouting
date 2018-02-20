package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import sparx1126.com.powerup.utilities.NetworkStatus;

public class Admin extends AppCompatActivity {
    private static final String TAG = "Admin ";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private BlueAllianceNetworking blueAlliance;
    private static DataCollection dataCollection;

    private Dialog eventsWeAreInDialog;
    private Dialog matchesDialog;
    private Dialog teamsDialog;
    private Spinner eventSpinner;
    private LinearLayout adminSelectionLayout;
    private ToggleButton blueSelectedToggle;
    private RadioButton teamNumber1SelectedButton;
    private RadioButton teamNumber2SelectedButton;
    private RadioButton teamNumber3SelectedButton;
    private static NetworkStatus networkStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        editor = settings.edit();
        blueAlliance = BlueAllianceNetworking.getInstance();
        dataCollection = DataCollection.getInstance();
        networkStatus = NetworkStatus.getInstance();

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
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = eventSpinner.getSelectedItem().toString();
                if (!selectedItem.isEmpty() && !selectedItem.contains(getResources().getString(R.string.selectEvent))) {
                    editor.putString(getResources().getString(R.string.pref_SelectedEvent), selectedItem);
                    editor.apply();
                    matchesDialog.show();
                    blueAlliance.downloadEventMatches(selectedItem, new BlueAllianceNetworking.Callback() {
                        @Override
                        public void handleFinishDownload() {
                            // this needs to run on the ui thread because of ui components in it
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
                                    matchesDialog.dismiss();
                                    teamsDialog.show();
                                    blueAlliance.downloadEventTeams(pref_SelectedEvent, new BlueAllianceNetworking.Callback() {

                                        @Override
                                        public void handleFinishDownload() {
                                            // this needs to run on the ui thread because of ui components in it
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    teamsDialog.dismiss();
                                                    editor.putBoolean(getResources().getString(R.string.tablet_Configured), false);
                                                    showButtons();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        adminSelectionLayout = findViewById(R.id.adminSelectionLayout);

        blueSelectedToggle = findViewById(R.id.blueSelectedToggle);
        teamNumber1SelectedButton = findViewById(R.id.team1);
        teamNumber2SelectedButton = findViewById(R.id.team2);
        teamNumber3SelectedButton = findViewById(R.id.team3);

        Button stuffSelectedButton = findViewById(R.id.selectStuff);
        stuffSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamNumber1SelectedButton.isChecked() || teamNumber2SelectedButton.isChecked() || teamNumber3SelectedButton.isChecked() ) {
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
                    editor.putBoolean(getResources().getString(R.string.tablet_Configured), true);
                    editor.apply();
                    finish();
                }
            }
        });

        restorePreferences();
        showButtons();
    }

    private void restorePreferences() {
        eventsWeAreInDialog.show();
        Map<String, BlueAllianceEvent> data = dataCollection.getTeamEvents();
        if(data.isEmpty()) {
            if(networkStatus.isInternetConnected() && networkStatus.isOnline()) {
                blueAlliance.downloadEventsSparxsIsIn(this, new BlueAllianceNetworking.Callback() {
                    @Override
                    public void handleFinishDownload() {
                        // this needs to run on the ui thread because of ui components in it
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
                                Map<String, BlueAllianceEvent> data = dataCollection.getTeamEvents();
                                List<String> eventSpinnerList = new ArrayList<>();
                                if (!pref_SelectedEvent.isEmpty()) {
                                    eventSpinnerList.add(pref_SelectedEvent);
                                    for (String eventName : data.keySet()) {
                                        if (!eventName.equals(pref_SelectedEvent)) {
                                            eventSpinnerList.add(eventName);
                                        }
                                    }
                                } else {
                                    eventSpinnerList.add(getResources().getString(R.string.selectEvent));
                                    eventSpinnerList.addAll(data.keySet());
                                }
                                SpinnerAdapter eventAdapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_spinner_item, eventSpinnerList);
                                eventSpinner.setAdapter(eventAdapter);
                                eventsWeAreInDialog.dismiss();
                            }
                        });
                    }
                });
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);

                builder.setTitle(TAG);
                builder.setMessage("NEED to connect to internet");
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();
            }
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
    }


    private void showButtons() {
        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");

        if (!pref_SelectedEvent.isEmpty()) {
            adminSelectionLayout.setVisibility(View.VISIBLE);
        } else {
            adminSelectionLayout.setVisibility(View.INVISIBLE);
        }
    }
}
