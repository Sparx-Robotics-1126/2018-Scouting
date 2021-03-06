package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.Directory;
import sparx1126.com.powerup.R;

public class FileIO {
    private static final String TAG = "FileIO ";

    private static final String FOLDER_NAME = "powerup";
    private static final String TEAM_EVENTS_FILE_NAME = "teamEvents.json";
    private static final String EVENT_MATCHES_FILE_NAME = "eventMatches.json";
    private static final String EVENT_TEAMS_FILE_NAME = "eventTeams.json";
    public static final String SCOUTING_DATA_HEADER = "scoutingData";
    public static final String BENCHMARK_DATA_HEADER = "benchmarkData";
    public static final String TEAM = "Team";
    public static final String MATCH = "Match";
    public static final String ROBOT_PIC_HEADER = "Robot_";

    private static FileIO instance;
    private File dir;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    // To be called once by MainActivity
    public void InitializeStorage(Context _context) {
        dir = new File(_context.getCacheDir(), FOLDER_NAME);
        if (!dir.exists()) {
            if (!dir.mkdir()) throw new AssertionError("Could not make directory!" + this);
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

    public void storeScoutingData(String _input, String _teamNumber, String _match) {
        String fileName = SCOUTING_DATA_HEADER + "_" + TEAM + _teamNumber + "_" + MATCH + _match + ".json";
        storeData(fileName, _input);
    }
    public Map<Integer, Map<Integer, String>> fetchScoutingDatas() {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        Map<Integer, Map<Integer, String>> rtnObj = new HashMap<>();
        File[] listOfFiles = dir.listFiles();

        for (File listOfFile : listOfFiles) {
            String fileName = listOfFile.getName();
            if (listOfFile.isFile() && fileName.contains(SCOUTING_DATA_HEADER)) {
                String[] fileNameParts = fileName.split("[_.]");
                Integer team = Integer.parseInt(fileNameParts[1].replace(TEAM, ""));
                Integer match = Integer.parseInt(fileNameParts[2].replace(MATCH, ""));

                Map<Integer, String> matchMap;
                if (rtnObj.get(team) != null) {
                    matchMap = rtnObj.get(team);
                } else {
                    matchMap = new HashMap<>();
                }

                String data = fetchData(fileName);
                matchMap.put(match, data);
                rtnObj.put(team, matchMap);
            }
        }
        return rtnObj;
    }

    public void storeBenchmarkData(String _input, String _teamNumber) {
        String fileName = BENCHMARK_DATA_HEADER + "_" + TEAM + _teamNumber + ".json";
        storeData(fileName, _input);
    }

    public Map<Integer, String> fetchBenchmarkDatas() {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        Map<Integer, String> rtnObj = new HashMap<>();
        File[] listOfFiles = dir.listFiles();

        for (File listOfFile : listOfFiles) {
            String fileName = listOfFile.getName();
            if (listOfFile.isFile() && fileName.contains(BENCHMARK_DATA_HEADER)) {
                String[] fileNameParts = fileName.split("[_.]");
                Integer team = Integer.parseInt(fileNameParts[1].replace(TEAM, ""));

                String data = fetchData(fileName);
                rtnObj.put(team, data);
            }
        }
        return rtnObj;
    }

    public Map<String, File> fetchRobotPicturesTaken(File _storageDir) {
        Map<String, File> photoDatas = new HashMap<>();
        if(_storageDir != null) {
            String path = _storageDir.getAbsolutePath();
            File directory = new File(path);
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if(fileName.contains(ROBOT_PIC_HEADER)) {
                    photoDatas.put(fileName, files[i]);
                }
            }
        } else {
            String msg = "Could not access: " + _storageDir.getName();
            Log.e(TAG, msg);
        }
        return photoDatas;
    }

    public void storeGoogleData(String _fileName, String _content) {
        if(_fileName.contains(SCOUTING_DATA_HEADER)) {
            String[] fileNameParts = _fileName.split("[_.]");
            String team = fileNameParts[1].replace(TEAM, "");
            String match = fileNameParts[2].replace(MATCH, "");
            storeScoutingData(_content, team, match);
        } else if (_fileName.contains(BENCHMARK_DATA_HEADER)) {
            String[] fileNameParts = _fileName.split("[_.]");
            String team = fileNameParts[1].replace(TEAM, "");
            storeBenchmarkData(_content, team);
        } else {
            Log.e(TAG, "Don't know what u talking about Willi's: " + _fileName);
        }
    }

    private void storeData(String _fileName, String _input) {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        String filePath = dir.getPath() + "/" + _fileName;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(_input);
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
        File tmpFileHandle = new File(filePath);
        if(tmpFileHandle.exists()) {

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.e(TAG, "File does not exist: " + _fileName);
        }
        Log.d(TAG, "Fetched: " + _fileName);
        return contentBuilder.toString();
    }
}
