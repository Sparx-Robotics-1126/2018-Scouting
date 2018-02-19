package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Scanner;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;

public class FileIO {
    private static final String TAG = "FileIO ";
    private static final String FOLDER_NAME ="powerup";
    private static final String TEAM_EVENTS_FILE_NAME ="teamEvents.json";
    private static final String EVENT_MATCHES_FILE_NAME ="eventMatches.json";
    private static final String EVENT_TEAMS_FILE_NAME ="eventTeams.json";
    private static final String SCOUTING_FILE_NAME ="scoutingData.json";
    private static final String BENCHMARK_FILE_NAME ="benchmarkData.json";
    private static String TEAM_EVENTS_FILE_PATH;
    private static String EVENT_MATCHES_FILE_PATH;
    private static FileIO instance;
    private static JSONParser jsonParser;
    private boolean initialized;

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
            if(!dir.mkdir()) throw new AssertionError("Could not make directory!" + this);
        }

        Log.d(TAG, "Storage Path:" + dir.getPath());
        Toast.makeText(_context, TAG + "Storage Path:" + dir.getPath(), Toast.LENGTH_LONG).show();

        TEAM_EVENTS_FILE_PATH = dir.getPath() + "/" + TEAM_EVENTS_FILE_NAME;
        EVENT_MATCHES_FILE_PATH = dir.getPath() + "/" + EVENT_MATCHES_FILE_NAME;
        initialized = true;
    }

    public void storeTeamEvents(String _input) {
        if (!initialized) throw new AssertionError("Not Initialize" + this);

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
        if (!initialized) throw new AssertionError("Not Initialize" + this);

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
        if (!initialized) throw new AssertionError("Not Initialize" + this);

        try {
            FileWriter  outputStream = new FileWriter(EVENT_MATCHES_FILE_PATH);
            outputStream.write(_input);
            outputStream.close();
            Log.d(TAG, "Stored Event Matches");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, BlueAllianceEvent> fetchEventMatches() {
        if (!initialized) throw new AssertionError("Not Initialize" + this);

        File fileHandle = new File(EVENT_MATCHES_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Event Matches");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParser.teamEventsStringIntoMap(fileContentInJSONForm);
    }

    public void storeEventTeams(String _input) {
        if (!initialized) throw new AssertionError("Not Initialize" + this);

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
        if (!initialized) throw new AssertionError("Not Initialize" + this);

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
        if (!initialized) throw new AssertionError("Not Initialize" + this);

        File fileHandle = new File(TEAM_EVENTS_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(fileHandle).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();
            Log.d(TAG, "Fetched Team Events");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParser.teamEventsStringIntoMap(fileContentInJSONForm);
    }
}
