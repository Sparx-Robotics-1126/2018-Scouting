package sparx1126.com.powerup.utilities;

import android.util.Log;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.data_components.ScoutingData;

public class DataCollection {
    private static DataCollection dataCollection;
    private SparseArray<List<ScoutingData>> scoutingDataMap;
    private SparseArray<BenchmarkData> benchmarkDataMap;
    private Map<String, BlueAllianceEvent > teamEvents;
    private Map<String, BlueAllianceTeam> eventTeams;
    private Map<String, BlueAllianceMatch> eventMatches;
    private static FileIO fileIO;

    public static synchronized DataCollection getInstance(){
        if(dataCollection == null ) {
            dataCollection = new DataCollection();
        }
        return dataCollection;
    }

    private DataCollection(){
        scoutingDataMap = new SparseArray();
        benchmarkDataMap = new SparseArray();
        teamEvents = new HashMap<>();
        eventTeams = new HashMap<>();
        eventMatches = new HashMap<>();
        fileIO = FileIO.getInstance();
    }

    public void addScoutingData(ScoutingData _data){
        Integer key = _data.getTeamNumber();
        if(scoutingDataMap.get(key) != null){
            scoutingDataMap.get(key).add(_data);
        }
        else {
            List<ScoutingData> newList = new ArrayList<>();
            newList.add(_data);
            scoutingDataMap.put(key, newList);
        }
    }
    public List<ScoutingData> getScoutingDatas(int _teamNumber){
        List<ScoutingData> rtnData = new ArrayList<>();
        if(scoutingDataMap.get(_teamNumber) != null){
            rtnData = scoutingDataMap.get(_teamNumber);
        }
        return rtnData;
    }
    public SparseArray<List<ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }

    public void addBenchmarkData(BenchmarkData _data){
        Integer key = _data.getTeamNumber();
        benchmarkDataMap.put(key, _data);
    }

    public BenchmarkData getBenchmarkData(int _teamNumber){
        BenchmarkData rtnData = null;
        if(benchmarkDataMap.get(_teamNumber) != null){
            rtnData = benchmarkDataMap.get(_teamNumber);
        }
        return rtnData;
    }

    public SparseArray<BenchmarkData> getBenchmarkDataMap() {
        return benchmarkDataMap;
    }


    public void setTeamEvents(String _data){
        fileIO.storeTeamEvents(_data);
        teamEvents = teamEventsStringIntoMap(_data);
    }
    public Map<String, BlueAllianceEvent> getTeamEvents(){
        return teamEvents;
    }

    public void setEventTeams(String _data){
        fileIO.storeEventTeams(_data);
        eventTeams = eventTeamsStringIntoMap(_data);
    }
    public Map<String, BlueAllianceTeam> getEventTeams(){
        return eventTeams;
    }

    public Map<String, BlueAllianceMatch> getEventMatches() {
        return eventMatches;
    }
    public void setEventMatches(String _data) {
        fileIO.storeEventMatches(_data);
        eventMatches = eventMatchesStringIntoMap(_data);
    }

    public void restore() {
        String teamEvents = fileIO.fetchTeamEvents();
        if (!teamEvents.isEmpty()) {
            setTeamEvents(teamEvents);
        }

        String eventTeams = fileIO.fetchEventTeams();
        if (!eventTeams.isEmpty()) {
            setEventTeams(eventTeams);
        }

        String eventMatches = fileIO.fetchEventMatches();
        if (!eventMatches.isEmpty()) {
            setEventMatches(eventMatches);
        }

        SparseArray< SparseArray< SparseArray<String>>> scoutingDatasByTeamMatchTimeMap = fileIO.fetchScoutingDatas();
        for(int indexTeam = 0; indexTeam != scoutingDatasByTeamMatchTimeMap.size(); indexTeam++) {
            for(int indexMatch = 0; indexMatch != scoutingDatasByTeamMatchTimeMap.get(indexTeam).size(); indexMatch++) {
                for(int indexTime = 0; indexTime != scoutingDatasByTeamMatchTimeMap.get(indexTeam).get(indexMatch).size(); indexTime++) {
                        ScoutingData scoutingData = new ScoutingData();
                        scoutingData.setJsonString(scoutingDatasByTeamMatchTimeMap.get(indexTeam).get(indexMatch).get(indexTime));
                        addScoutingData(scoutingData);
                }
            }
        }
    }

    private Map<String, BlueAllianceEvent> teamEventsStringIntoMap(String _input) {
        Map<String, BlueAllianceEvent> output = new HashMap<>();

        try {
            JSONArray array = new JSONArray(_input);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceEvent item = new BlueAllianceEvent(obj);
                output.put(item.getKey(), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }

    private Map<String, BlueAllianceMatch> eventMatchesStringIntoMap(String _contentInJSONForm) {
        Map<String, BlueAllianceMatch> rtnMap = new HashMap<>();

        try {
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceMatch item = new BlueAllianceMatch(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("eventMatchesString", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }

    private Map<String, BlueAllianceTeam> eventTeamsStringIntoMap(String _contentInJSONForm) {
        Map<String, BlueAllianceTeam> rtnMap = new HashMap<>();

        try {
            JSONArray array = new JSONArray(_contentInJSONForm);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceTeam item = new BlueAllianceTeam(obj);
                rtnMap.put(item.getKey(), item);
            }
            Log.d("eventTeamsString", rtnMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }
}
