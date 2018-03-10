package sparx1126.com.powerup;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.NetworkStatus;
import sparx1126.com.powerup.utilities.Utility;

public class Admin extends AppCompatActivity {
    private static final String TAG = "Admin ";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static BlueAllianceNetworking blueAlliance;
    private static DataCollection dataCollection;
    private static FileIO fileIO;
    private static NetworkStatus networkStatus;
    private static Utility utility;

    private Dialog eventsWeAreInDialog;
    private Dialog matchesDialog;
    private Dialog teamsDialog;
    private Dialog testingInternetDialog;

    private Spinner eventSpinner;
    private View adminSelectionLayout;
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
        fileIO = FileIO.getInstance();
        networkStatus = NetworkStatus.getInstance();
        utility = Utility.getInstance();

        eventsWeAreInDialog = utility.getNoButtonDialog(this, TAG, "Wait a moment. Downloading Events...");
        matchesDialog = utility.getNoButtonDialog(this, TAG, "Wait a moment. Downloading Matches...");
        teamsDialog = utility.getNoButtonDialog(this, TAG, "Wait a moment. Downloading Teams...");
        testingInternetDialog = utility.getNoButtonDialog(this, TAG, getResources().getString(R.string.testing_internet));

        eventSpinner = findViewById(R.id.eventSpinner);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final String selectedItem = eventSpinner.getSelectedItem().toString();
                if (!selectedItem.isEmpty() && !selectedItem.contains(getResources().getString(R.string.selectEvent))) {
                    String previousSelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
                    if(!previousSelectedEvent.equals(selectedItem)) {
                        reset();
                    }
                    editor.putString(getResources().getString(R.string.pref_SelectedEvent), selectedItem);
                    editor.apply();

                    testingInternetDialog.show();
                    networkStatus.isOnline(new NetworkStatus.Callback() {
                        @Override
                        public void handleConnected(final boolean _success) {
                            // this needs to run on the ui thread because of ui components in it
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testingInternetDialog.dismiss();
                                    if (_success) {
                                        matchesDialog.show();
                                        blueAlliance.downloadEventMatches(selectedItem, new BlueAllianceNetworking.Callback() {
                                            @Override
                                            public void handleFinishDownload(String _data) {
                                                dataCollection.setEventMatches(_data);
                                                fileIO.storeEventMatches(_data);
                                                // this needs to run on the ui thread because of ui components in it
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
                                                        matchesDialog.dismiss();
                                                        teamsDialog.show();
                                                        blueAlliance.downloadEventTeams(pref_SelectedEvent, new BlueAllianceNetworking.Callback() {

                                                            @Override
                                                            public void handleFinishDownload(String _data) {
                                                                dataCollection.setEventTeams(_data);
                                                                fileIO.storeEventTeams(_data);
                                                                // this needs to run on the ui thread because of ui components in it
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        teamsDialog.dismiss();
                                                                        showButtons();
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        showConnectToInternetDialog();
                                    }
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

        stuffSelectedButton = findViewById(R.id.selectStuff);
        stuffSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teamNumber1SelectedButton.isChecked() || teamNumber2SelectedButton.isChecked() || teamNumber3SelectedButton.isChecked()) {
                    editor.putBoolean(getResources().getString(R.string.pref_BlueAlliance), blueSelectedToggle.isChecked());
                    if (teamNumber1SelectedButton.isChecked()) {
                        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 1);
                    } else if (teamNumber2SelectedButton.isChecked()) {
                        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 2);
                    } else if (teamNumber3SelectedButton.isChecked()) {
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
        Map<String, BlueAllianceEvent> data = dataCollection.getTeamEvents();
        if (data.isEmpty()) {
            testingInternetDialog.show();
            networkStatus.isOnline(new NetworkStatus.Callback() {
                @Override
                public void handleConnected(final boolean _success) {
                    // this needs to run on the ui thread because of ui components in it
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testingInternetDialog.dismiss();
                            if (_success) {
                                eventsWeAreInDialog.show();
                                blueAlliance.downloadEventsSparxsIsIn(new BlueAllianceNetworking.Callback() {
                                    @Override
                                    public void handleFinishDownload(String _data) {
                                        dataCollection.setTeamEvents(_data);
                                        fileIO.storeTeamEvents(_data);
                                        // this needs to run on the ui thread because of ui components in it
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                eventsWeAreInDialog.dismiss();
                                                setEventSpinner();
                                            }
                                        });
                                    }
                                });
                            } else {
                                showConnectToInternetDialog();
                            }
                        }
                    });
                }
            });
        } else {
            Log.d(TAG, "Team Events Found");
            setEventSpinner();
        }

        boolean pref_BlueAlliance = settings.getBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
        if (pref_BlueAlliance) {
            blueSelectedToggle.setChecked(true);
        } else {
            blueSelectedToggle.setChecked(false);
        }

        int pref_TeamPosition = settings.getInt(getResources().getString(R.string.pref_TeamPosition), 0);

        if (pref_TeamPosition == 1) {
            teamNumber1SelectedButton.setChecked(true);
        } else if (pref_TeamPosition == 2) {
            teamNumber2SelectedButton.setChecked(true);
        } else if (pref_TeamPosition == 3) {
            teamNumber3SelectedButton.setChecked(true);
        }
    }

    private void setEventSpinner() {
        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");
        Map<String, BlueAllianceEvent> data = dataCollection.getTeamEvents();
        List<String> eventSpinnerList = new ArrayList<>();
        if (pref_SelectedEvent.isEmpty()) {
            eventSpinnerList.add(getResources().getString(R.string.selectEvent));
            eventSpinnerList.addAll(data.keySet());
        } else {
            eventSpinnerList.add(pref_SelectedEvent);
            for (String eventName : data.keySet()) {
                if (!eventName.equals(pref_SelectedEvent)) {
                    eventSpinnerList.add(eventName);
                }
            }
        }
        SpinnerAdapter eventAdapter = new ArrayAdapter<>(Admin.this, R.layout.custom_spinner_item, eventSpinnerList);
        eventSpinner.setAdapter(eventAdapter);
    }

    private void showConnectToInternetDialog() {
        Dialog dialog = utility.getNegativeButtonDialog(this, TAG,
                "You NEED to connect to the internet!",
                "Go Back");
        dialog.show();
    }

    private void showButtons() {
        String pref_SelectedEvent = settings.getString(getResources().getString(R.string.pref_SelectedEvent), "");

        if (pref_SelectedEvent.isEmpty()) {
            adminSelectionLayout.setVisibility(View.INVISIBLE);
        } else {
            adminSelectionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void reset() {
        editor.putBoolean(getResources().getString(R.string.pref_BlueAlliance), false);
        editor.putInt(getResources().getString(R.string.pref_TeamPosition), 0);
        editor.putBoolean(getResources().getString(R.string.tablet_Configured), false);
        editor.apply();
        blueSelectedToggle.setChecked(false);
        teamNumber1SelectedButton.setChecked(false);
        teamNumber2SelectedButton.setChecked(false);
        teamNumber3SelectedButton.setChecked(false);
    }
}
