package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;

public class FileIO {
    private static final String TAG = "FileIO ";
    private static final String FOLDER_NAME ="powerup";
    private static final String TEAM_EVENTS_FILE_NAME ="teamEvents.json";
    private static final String EVENT_MATCHES_FILE_NAME ="eventMatches.json";
    private static final String EVENT_TEAMS_FILE_NAME ="eventTeams.json";
    private static final String SCOUTING_DATA_HEADER ="scoutingData";
    private static FileIO instance;
    private File dir;
    private static DataCollection dataCollection;
    private static JSONParser jsonParser;


    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    private FileIO() {
        dataCollection = DataCollection.getInstance();
        jsonParser = JSONParser.getInstance();
    }

    // To be called once by MainActivity
    public void InitializeStorage(Context _context) {
        dir = new File(_context.getCacheDir(), FOLDER_NAME);
        if(!dir.exists()) {
            if(!dir.mkdir()) throw new AssertionError("Could not make directory!" + this);
        }

        Log.d(TAG, "Storage Path:" + dir.getPath());
        Toast.makeText(_context, TAG + "Storage Path:" + dir.getPath(), Toast.LENGTH_LONG).show();
    }

    public void storeTeamEvents(String _input) {
        storeData(TEAM_EVENTS_FILE_NAME, _input);
    }
    public String fetchTeamEvents() {
        return fetchData(TEAM_EVENTS_FILE_NAME);
    }

    public void storeEventMatches(String _input) {
        storeData(EVENT_MATCHES_FILE_NAME, _input);
    }
    public String fetchEventMatches() {
        return fetchData(EVENT_MATCHES_FILE_NAME);
    }

    public void storeEventTeams(String _input) {
        storeData(EVENT_TEAMS_FILE_NAME, _input);
    }
    public String fetchEventTeams() {
        return fetchData(EVENT_TEAMS_FILE_NAME);
    }

    public void storeScoutingData(String _input, String _match, String _teamNumber) {
        long timeStampInSeconds = System.currentTimeMillis() / 1000;
        String fileName = SCOUTING_DATA_HEADER + "_" + _match + "_" + _teamNumber + "_" + String.valueOf(timeStampInSeconds) + ".json";
        storeData(fileName, _input);
    }
    public String fetchScoutingData() {
        return fetchData(TEAM_EVENTS_FILE_NAME);
    }

    private void storeData(String _fileName, String _input) {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        String filePath = dir.getPath() + "/" + _fileName;

        try {
            BufferedWriter writer = new BufferedWriter( new FileWriter( filePath));
            writer.write( _input);
            writer.close();
            Log.d(TAG, "Stored: " + _fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fetchData(String _fileName) {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        String filePath = dir.getPath() + "/" + _fileName;
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public void restore() {
        String teamEvents = fetchTeamEvents();
        if(!teamEvents.isEmpty()){
            Map<String, BlueAllianceEvent> rtnMap = jsonParser.teamEventsStringIntoMap(teamEvents);
            dataCollection.setTeamEvents(rtnMap);
        }

        String eventTeams = fetchEventTeams();
        if(!eventTeams.isEmpty()){
            Map<String, BlueAllianceTeam> rtnMap = jsonParser.eventTeamsStringIntoMap(eventTeams);
            dataCollection.setEventTeams(rtnMap);
        }

        String eventMatches = fetchEventMatches();
        if(!eventMatches.isEmpty()){
            Map<String, BlueAllianceMatch> rtnMap = jsonParser.eventMatchesStringIntoMap(eventMatches);
            dataCollection.setEventMatches(rtnMap);
        }
    }
}
