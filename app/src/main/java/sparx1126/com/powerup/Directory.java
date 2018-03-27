package sparx1126.com.powerup;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.NetworkStatus;
import sparx1126.com.powerup.utilities.Utility;

public class Directory extends AppCompatActivity {
    private static final String TAG = "Directory ";
    private SharedPreferences settings;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;
    private static NetworkStatus networkStatus;
    private static Utility utility;

    private Dialog testingInternetDialog;
    private Dialog downloadInternetDialog;
    private Dialog uploadInternetDialog;

    private Button admin;
    private LinearLayout adminButton;
    private LinearLayout normalButtons;
    private Button benchmarking;
    private Button scouting;
    private Button view;
    private Button checklist;
    private Button upload;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        fileIO = FileIO.getInstance();
        googleDrive = GoogleDriveNetworking.getInstance();
        networkStatus = NetworkStatus.getInstance();
        utility = Utility.getInstance();

        testingInternetDialog = utility.getNoButtonDialog(this, TAG, getResources().getString(R.string.testing_internet));
        downloadInternetDialog = utility.getNoButtonDialog(this, TAG, getResources().getString(R.string.downloading_internet));
        uploadInternetDialog = utility.getNoButtonDialog(this, TAG, getResources().getString(R.string.uploading_internet));

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
        });

        adminButton = findViewById(R.id.admin_button_layout);
        adminButton.setVisibility(android.view.View.GONE);
        normalButtons = findViewById(R.id.normal_buttons_layout);

        benchmarking = findViewById(R.id.benchmark);
        benchmarking.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Benchmarking.class);
                startActivity(intent);
            }
        });

        scouting = findViewById(R.id.scouting);
        scouting.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Scouting.class);
                startActivity(intent);
            }
        });

        view = findViewById(R.id.view);
        view.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, View.class);
                startActivity(intent);
            }
        });

        checklist = findViewById(R.id.checklist);
        checklist.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, CheckList.class);
                startActivity(intent);
            }
        });

        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                testingInternetDialog.show();
                networkStatus.isOnline(new NetworkStatus.NetworkCallback() {
                    @Override
                    public void handleConnected(final boolean _success) {
                        // this needs to run on the ui thread because of ui components in it
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                testingInternetDialog.dismiss();
                                if (_success) {
                                    Map<String, String> stringData = new HashMap<>();

                                    Map<Integer, Map<Integer, String>> scoutingDatas = fileIO.fetchScoutingDatas();
                                    for (Map.Entry<Integer, Map<Integer, String>> entryTeam : scoutingDatas.entrySet()) {
                                        for (Map.Entry<Integer, String> entryMatch : entryTeam.getValue().entrySet()) {
                                            String fileName = FileIO.SCOUTING_DATA_HEADER + "_" + FileIO.TEAM
                                                    + String.valueOf(entryTeam.getKey()) + "_" + FileIO.MATCH
                                                    + String.valueOf(entryMatch.getKey()) + ".json";
                                            stringData.put(fileName, entryMatch.getValue());
                                        }
                                    }

                                    Map<Integer, String> benchmarkingDatas = fileIO.fetchBenchmarkDatas();
                                    for (Map.Entry<Integer, String> entryTeam : benchmarkingDatas.entrySet()) {
                                        String fileName = FileIO.BENCHMARK_DATA_HEADER + "_" + FileIO.TEAM
                                                + String.valueOf(entryTeam.getKey()) + ".json";
                                        stringData.put(fileName, entryTeam.getValue());
                                    }

                                    Map<String, File> photoData = new HashMap<>();
                                    File storageDir = Directory.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                    if(storageDir != null) {
                                        String path = storageDir.getAbsolutePath();
                                        File directory = new File(path);
                                        File[] files = directory.listFiles();
                                        for (int i = 0; i < files.length; i++) {
                                            String fileName = files[i].getName();
                                            if(fileName.contains(getResources().getString(R.string.pictureHeader))) {
                                                photoData.put(fileName, files[i]);
                                            }
                                        }
                                    } else {
                                        String msg = "Could not access: " + storageDir.getName();
                                        Log.e(TAG, msg);
                                        Toast.makeText(Directory.this, TAG + msg, Toast.LENGTH_LONG).show();
                                    }


                                    uploadInternetDialog.show();
                                    googleDrive.uploadContentToGoogleDrive(Directory.this, stringData, photoData,
                                            new GoogleDriveNetworking.GoogleCompletedCallback() {
                                                @Override
                                                public void handleOnSuccess() {
                                                    uploadInternetDialog.dismiss();
                                                    String msg = "Uploaded to Internet";
                                                    Log.d(TAG, msg);
                                                    Toast.makeText(Directory.this, TAG + msg, Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void handleOnFailure(String _reason) {
                                                    uploadInternetDialog.dismiss();
                                                    Dialog dialog = utility.getNegativeButtonDialog(Directory.this, TAG,
                                                            _reason,
                                                            "Okay");
                                                    dialog.show();
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
        });

        download = findViewById(R.id.download);
        download.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                testingInternetDialog.show();
                networkStatus.isOnline(new NetworkStatus.NetworkCallback() {
                    @Override
                    public void handleConnected(final boolean _success) {
                        // this needs to run on the ui thread because of ui components in it
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                testingInternetDialog.dismiss();
                                if (_success) {
                                    downloadInternetDialog.show();
                                    googleDrive.downloadContentsFromGoogleDrive(Directory.this,
                                            new GoogleDriveNetworking.GoogleCompletedCallback() {
                                                @Override
                                                public void handleOnSuccess() {
                                                    downloadInternetDialog.dismiss();
                                                    String msg = "Downloaded from Internet";
                                                    Log.d(TAG, msg);
                                                    Toast.makeText(Directory.this, TAG + msg, Toast.LENGTH_LONG).show();
                                                    utility.restoreFromGoogleDrive();
                                                }

                                                @Override
                                                public void handleOnFailure(String _reason) {
                                                    downloadInternetDialog.dismiss();
                                                    Dialog dialog = utility.getNegativeButtonDialog(Directory.this, TAG,
                                                            _reason,
                                                            "Okay");
                                                    dialog.show();
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
        });

        boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
        if (!isTableConfigured) {
            String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
            String[] adminList = getResources().getStringArray(R.array.admins);
            boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);
            if (adminNameFound) {
                Log.d(TAG, "Admin");
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            } else {
                Dialog dialog = utility.getNegativeButtonDialog(this, TAG,
                        "Have an Admin Setup Tablet!",
                        "Go Back");
                dialog.show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        showButtons();
    }

    private void showButtons() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        String[] adminList = getResources().getStringArray(R.array.admins);
        boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);

        if (adminNameFound) {
            adminButton.setVisibility(android.view.View.VISIBLE);
        }

        boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
        if (isTableConfigured) {
            normalButtons.setVisibility(android.view.View.VISIBLE);
        } else {
            normalButtons.setVisibility(android.view.View.INVISIBLE);
        }
    }

    private void showConnectToInternetDialog() {
        Dialog dialog = utility.getPositiveButtonDialog(this, TAG,
                "You NEED to connect to the internet!",
                "Okay");
        dialog.show();
    }
}
