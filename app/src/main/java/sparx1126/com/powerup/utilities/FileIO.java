package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.data_components.ScoutingData;

public class FileIO {
    private static final String TAG = "FileIO ";
    private static final String FOLDER_NAME ="powerup";
    private static final String TEAM_EVENTS_FILE_NAME ="teamEvents.json";
    private static final String EVENT_MATCHES_FILE_NAME ="eventMatches.json";
    private static final String EVENT_TEAMS_FILE_NAME ="eventTeams.json";
    private static final String SCOUTING_FILE_NAME ="scoutingData.json";
    private static final String BENCHMArK_FILE_NAME ="benchmarkData.json";
    private static String TEAM_EVENTS_FILE_PATH;
    private static String EVENT_MATCHES_FILE_PATH;
    private static FileIO instance;
    private JSONParser jsonParser;
    private boolean intialized;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    private FileIO() {
        jsonParser = JSONParser.getInstance();
    }

    // To be called once by MainActivity
    public void InitializeStorage(Context _context) {
        File dir = new File(_context.getCacheDir(), FOLDER_NAME);
        if(!dir.exists()) {
            dir.mkdir();
        }

        Log.d(TAG, "Storage Path:" + dir.getPath());
        Toast.makeText(_context, TAG + "Storage Path:" + dir.getPath(), Toast.LENGTH_LONG).show();

        TEAM_EVENTS_FILE_PATH = dir.getPath() + "/" + TEAM_EVENTS_FILE_NAME;
        EVENT_MATCHES_FILE_PATH = dir.getPath() + "/" + EVENT_MATCHES_FILE_NAME;
        intialized = true;
    }

    public void storeTeamEvents(String _input) {
        assert !intialized;

        try {
            FileWriter  outputStream = new FileWriter(TEAM_EVENTS_FILE_PATH);
            outputStream.write(_input);
            outputStream.close();
            Log.d(TAG, "Stored Team Events");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fetchTeamEvents() {
        assert !intialized;

        File fileHandle = new File(TEAM_EVENTS_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Team Events");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContentInJSONForm;
    }

    public void storeEventMatches(String _input) {
        assert !intialized;

        try {
            FileWriter  outputStream = new FileWriter(EVENT_MATCHES_FILE_PATH);
            outputStream.write(_input);
            outputStream.close();
            Log.d(TAG, "Stored Evemt Matches");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, BlueAllianceEvent> fetchEventMatches() {
        assert !intialized;

        File fileHandle = new File(EVENT_MATCHES_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Event Matches");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, BlueAllianceEvent> rtnMap = jsonParser.teamEventsStringIntoMap(fileContentInJSONForm);
        return rtnMap;
    }

    public void storeEventTeams(String _input) {
        assert !intialized;

        try {
            FileWriter  outputStream = new FileWriter(EVENT_TEAMS_FILE_NAME);
            outputStream.write(_input);
            outputStream.close();
            Log.d(TAG, "Stored Event Teams");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fetchEventTeams() {
        assert !intialized;

        File fileHandle = new File(EVENT_TEAMS_FILE_NAME);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Event Teams");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContentInJSONForm;
    }

    public Map<String, BlueAllianceEvent> fetchScouting() {
        assert !intialized;

        File fileHandle = new File(TEAM_EVENTS_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Team Events");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, BlueAllianceEvent> rtnMap = jsonParser.teamEventsStringIntoMap(fileContentInJSONForm);
        return rtnMap;
    }
}
