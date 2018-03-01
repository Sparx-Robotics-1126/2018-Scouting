package sparx1126.com.powerup.utilities;

import android.util.Log;

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
    private Map<Integer, Map<Integer, ScoutingData>> scoutingDataMap;
    private Map<Integer, BenchmarkData> benchmarkDataMap;
    private Map<String, BlueAllianceEvent > teamEvents;
    private Map<String, BlueAllianceTeam> eventTeams;
    private Map<String, BlueAllianceMatch> eventMatchesByKey;
    private Map<Integer, BlueAllianceMatch> eventMatchesByMatchNumber;
    private static FileIO fileIO;

    public static synchronized DataCollection getInstance(){
        if(dataCollection == null ) {
            dataCollection = new DataCollection();
        }
        return dataCollection;
    }

    private DataCollection(){
        scoutingDataMap = new HashMap<>();
        benchmarkDataMap = new HashMap<>();
        teamEvents = new HashMap<>();
        eventTeams = new HashMap<>();
        eventMatchesByKey = new HashMap<>();
        eventMatchesByMatchNumber = new HashMap<>();
        fileIO = FileIO.getInstance();
    }

    public List<Integer> getTeamsInEvent() {
        List<Integer> teams = new ArrayList<>();
        for(BlueAllianceTeam team: eventTeams.values()) {
            teams.add(Integer.valueOf(team.getNumber()));
        }
        return teams;
    }

    public void addScoutingData(ScoutingData _data){
        Integer teamKey = _data.getTeamNumber();
        Integer matchKey = _data.getMatchNumber();
        Map<Integer, ScoutingData> matchMap;
        if(scoutingDataMap.get(teamKey) == null){
            matchMap = new HashMap<>();
        }
        else {
            matchMap = scoutingDataMap.get(teamKey);
        }
        matchMap.put(matchKey, _data);
        scoutingDataMap.put(teamKey, matchMap);
        fileIO.storeScoutingData(_data.toString(), String.valueOf(_data.getTeamNumber()), String.valueOf(_data.getMatchNumber()));
    }
    public Map<Integer, ScoutingData> getScoutingDatas(int _teamNumber){
        Map<Integer, ScoutingData> rtnData = new HashMap<>();
        if(scoutingDataMap.get(_teamNumber) != null){
            rtnData = scoutingDataMap.get(_teamNumber);
        }
        return rtnData;
    }
    public Map<Integer, Map<Integer, ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }

    public void addBenchmarkData(BenchmarkData _data){
        Integer key = _data.getTeamNumber();
        benchmarkDataMap.put(key, _data);
        fileIO.storeBenchmarkData(_data.toString(), String.valueOf(_data.getTeamNumber()));
    }

    public BenchmarkData getBenchmarkData(int _teamNumber){
        BenchmarkData rtnData = null;
        if(benchmarkDataMap.get(_teamNumber) != null){
            rtnData = benchmarkDataMap.get(_teamNumber);
        }
        return rtnData;
    }

    public Map<Integer, BenchmarkData> getBenchmarkDataMap() {
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

    public Map<String, BlueAllianceMatch> getEventMatchesByKey() {
        return eventMatchesByKey;
    }
    public void setEventMatchesByKey(String _data) {
        fileIO.storeEventMatches(_data);
        eventMatchesByKey = eventMatchesStringIntoMap(_data);
        for(BlueAllianceMatch match: eventMatchesByKey.values()) {
            // If qualification match
            if(match.getCompLevel().equals("qm")) {
                eventMatchesByMatchNumber.put(Integer.valueOf(match.getMatchNumber()), match);
            }
        }
    }

    public Map<Integer, BlueAllianceMatch> getEventMatchesByMatchNumber() {
        return eventMatchesByMatchNumber;
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
            setEventMatchesByKey(eventMatches);
        }

        Map<Integer, Map<Integer, Map<Integer, String>>> scoutingDatasByTeamMatchTimeMap = fileIO.fetchScoutingDatas();
        for(Map<Integer, Map<Integer, String>> match: scoutingDatasByTeamMatchTimeMap.values()) {
            for(Map<Integer, String> time: match.values()) {
                for(String data: time.values()) {
                        ScoutingData scoutingData = new ScoutingData();
                        scoutingData.setJsonString(data);
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
