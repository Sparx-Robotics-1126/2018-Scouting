package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    private static final String TAG = "FileIO ";

    private static final String FOLDER_NAME = "powerup";
    private static final String TEAM_EVENTS_FILE_NAME = "teamEvents.json";
    private static final String EVENT_MATCHES_FILE_NAME = "eventMatches.json";
    private static final String EVENT_TEAMS_FILE_NAME = "eventTeams.json";
    private static final String SCOUTING_DATA_HEADER = "scoutingData";
    private static final String BENCHMARK_DATA_HEADER = "benchmarkData";
    private static final String TEAM_SEPARATOR = "_Team";
    private static final String MATCH_SEPARATOR = "_Match";
    private static final String TIME_SEPARATOR = "_Time";

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
        long timeStampInSeconds = System.currentTimeMillis() / 1000;
        String fileName = SCOUTING_DATA_HEADER + TEAM_SEPARATOR + _teamNumber + MATCH_SEPARATOR + _match + TIME_SEPARATOR + String.valueOf(timeStampInSeconds) + ".json";
        storeData(fileName, _input);
    }

    public SparseArray< SparseArray< SparseArray<String>>> fetchScoutingDatas() {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        SparseArray< SparseArray< SparseArray<String>>> rtnObj = new SparseArray<>();
        File[] listOfFiles = dir.listFiles();

        for (File listOfFile : listOfFiles) {
            String filePath = listOfFile.getPath();
            String fileName = listOfFile.getName();
            if (listOfFile.isFile() && fileName.contains(SCOUTING_DATA_HEADER)) {
                String[] fileNameParts = fileName.split("_.");
                Integer team = Integer.parseInt(fileNameParts[1].replace(TEAM_SEPARATOR, ""));
                Integer match = Integer.parseInt(fileNameParts[2].replace(MATCH_SEPARATOR, ""));
                Integer time = Integer.parseInt(fileNameParts[3].replace(TIME_SEPARATOR, ""));

                SparseArray<SparseArray<String>> matchMap;
                if (rtnObj.get(team) != null) {
                    matchMap = rtnObj.get(team);
                } else {
                    matchMap = new SparseArray<>();
                }

                SparseArray<String> timeMap;
                if (matchMap.get(match) != null) {
                    timeMap = matchMap.get(match);
                } else {
                    timeMap = new SparseArray<>();
                }

                Log.d(TAG, fileName);
                String data = fetchData(filePath);
                timeMap.put(time, data);
                matchMap.put(match, timeMap);
                rtnObj.put(team, matchMap);
            }
        }
        return rtnObj;
    }

    public void storeBenchmarkData(String _input, String _teamNumber) {
        long timeStampInSeconds = System.currentTimeMillis() / 1000;
        String fileName = BENCHMARK_DATA_HEADER + TEAM_SEPARATOR + _teamNumber + TIME_SEPARATOR + String.valueOf(timeStampInSeconds) + ".json";
        storeData(fileName, _input);
    }

    public SparseArray< SparseArray<String>> fetchBenchmarkDatas() {
        if (dir == null) throw new AssertionError("Not Initialize" + this);

        SparseArray< SparseArray<String>> rtnObj = new SparseArray<>();
        File[] listOfFiles = dir.listFiles();

        for (File listOfFile : listOfFiles) {
            String filePath = listOfFile.getPath();
            String fileName = listOfFile.getName();
            if (listOfFile.isFile() && fileName.contains(BENCHMARK_DATA_HEADER)) {
                String[] fileNameParts = fileName.split("_.");
                Integer team = Integer.parseInt(fileNameParts[1].replace(TEAM_SEPARATOR, ""));
                Integer time = Integer.parseInt(fileNameParts[2].replace(TIME_SEPARATOR, ""));

                SparseArray<String> timeMap;
                if (rtnObj.get(team) != null) {
                    timeMap = rtnObj.get(team);
                } else {
                    timeMap = new SparseArray<>();
                }

                Log.d(TAG, fileName);
                String data = fetchData(filePath);
                timeMap.put(time, data);
                rtnObj.put(team, timeMap);
            }
        }
        return rtnObj;
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
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
