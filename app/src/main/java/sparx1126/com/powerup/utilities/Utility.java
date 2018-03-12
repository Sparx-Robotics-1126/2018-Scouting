package sparx1126.com.powerup.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.util.Map;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.ScoutingData;

public class Utility {
    private static final String TAG = "Utility ";
    private static Utility instance;
    private static DataCollection dataCollection;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;

    public static synchronized Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    private Utility() {
        dataCollection = DataCollection.getInstance();
        fileIO = FileIO.getInstance();
        googleDrive = GoogleDriveNetworking.getInstance();
    }

    public Dialog getNoButtonDialog(Context _this, String _title, String _msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public Dialog getPositiveButtonDialog(Context _this, String _title, String _msg, String _buttonMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        builder.setPositiveButton(_buttonMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public Dialog getNegativeButtonDialog(final Activity _this, String _title, String _msg, String _buttonMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        builder.setPositiveButton(_buttonMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                _this.finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void restoreFromTablet() {
        String teamEvents = fileIO.fetchTeamEvents();
        if (!teamEvents.isEmpty()) {
            dataCollection.setTeamEvents(teamEvents);
        }

        String eventTeams = fileIO.fetchEventTeams();
        if (!eventTeams.isEmpty()) {
            dataCollection.setEventTeams(eventTeams);
        }

        String eventMatches = fileIO.fetchEventMatches();
        if (!eventMatches.isEmpty()) {
            dataCollection.setEventMatches(eventMatches);
        }

        Map<Integer, Map<Integer, String>> scoutingDatasByTeamMatchMap = fileIO.fetchScoutingDatas();
        for(Map<Integer, String> match: scoutingDatasByTeamMatchMap.values()) {
            for(String data: match.values()) {
                ScoutingData scoutingData = new ScoutingData();
                scoutingData.restoreFromJsonString(data);
                dataCollection.addScoutingData(scoutingData);
            }
        }

        Map<Integer, String> benchmarkDatasByTeamMap = fileIO.fetchBenchmarkDatas();
        for (String data : benchmarkDatasByTeamMap.values()) {
            BenchmarkData benchmarkData = new BenchmarkData();
            benchmarkData.restoreFromJsonString(data);
            dataCollection.addBenchmarkData(benchmarkData);
        }
    }

    public void restoreFromGoogleDrive() {
        Map<String, String> contentData = googleDrive.getContentMap();
        for(Map.Entry<String, String> entry: contentData.entrySet()) {
            fileIO.storeGoogleData(entry.getKey(), entry.getValue());
        }
        restoreFromTablet();
    }
}

