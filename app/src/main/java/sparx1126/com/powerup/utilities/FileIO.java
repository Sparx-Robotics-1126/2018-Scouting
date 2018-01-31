package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Scanner;

import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;

public class FileIO {
    private static final String FOLDER_NAME ="powerup";
    private static final String TEAM_EVENTS_FILE_NAME ="teamEvents.json";
    private static String TEAM_EVENTS_FILE_PATH;
    private static FileIO instance;
    private JSONParser jsonParser;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized FileIO getInstance(Context _context) {
        if (instance == null) {
            instance = new FileIO(_context);
        }
        return instance;
    }

    private FileIO(Context _context) {
        jsonParser = JSONParser.getInstance();
        File dir = new File(_context.getCacheDir(), FOLDER_NAME);
        if(!dir.exists()) {
            dir.mkdir();
        }
        Log.d("storagePath", dir.getPath());

        TEAM_EVENTS_FILE_PATH = dir.getPath() + "/" + TEAM_EVENTS_FILE_NAME;
    }

    public void storeTeamEvents(Map<String, BlueAllianceEvent> _input) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, BlueAllianceEvent> entry : _input.entrySet())
        {
            BlueAllianceEvent event = entry.getValue();
            JSONObject eventJSONObject = event.getJSONObject(event);
            jsonArray.put(eventJSONObject);
        }

        try {
            FileWriter  outputStream = new FileWriter(TEAM_EVENTS_FILE_PATH);
            outputStream.write(jsonArray.toString());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, BlueAllianceEvent> fetchTeamEvents() {
        File eventsSparxFile = new File(TEAM_EVENTS_FILE_PATH);
        String fileContentInJSONForm = "";
        try {
            Scanner scanner = new Scanner(eventsSparxFile).useDelimiter("\\Z");
            fileContentInJSONForm = scanner.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, BlueAllianceEvent> rtnMap = jsonParser.parseTeamEvents(fileContentInJSONForm);
        return rtnMap;
    }
}
