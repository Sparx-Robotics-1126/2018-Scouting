package sparx1126.com.powerup.utilities;

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
    private Map<String, BlueAllianceMatch> eventMatches;
    private Map<Integer, BlueAllianceMatch> qualificationsMatches;
    private Map<Integer, Integer> teamNumberOfPhotos;

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
        eventMatches = new HashMap<>();
        qualificationsMatches = new HashMap<>();
        teamNumberOfPhotos = new HashMap<>();
    }

    public void setEventTeams(String _data){
        eventTeams.clear();
        try {
            JSONArray array = new JSONArray(_data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceTeam item = new BlueAllianceTeam(obj);
                eventTeams.put(item.getKey(), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Map<String, BlueAllianceTeam> getEventTeams(){
        return eventTeams;
    }
    public List<Integer> getTeamsInEvent() {
        List<Integer> teams = new ArrayList<>();
        for(BlueAllianceTeam team: eventTeams.values()) {
            teams.add(Integer.valueOf(team.getNumber()));
        }
        return teams;
    }

    public void setTeamEvents(String _data){
        teamEvents.clear();
        try {
            JSONArray array = new JSONArray(_data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceEvent item = new BlueAllianceEvent(obj);
                teamEvents.put(item.getKey(), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Map<String, BlueAllianceEvent> getTeamEvents(){
        return teamEvents;
    }

    public void setEventMatches(String _data) {
        eventMatches.clear();
        try {
            JSONArray array = new JSONArray(_data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                BlueAllianceMatch item = new BlueAllianceMatch(obj);
                eventMatches.put(item.getKey(), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(BlueAllianceMatch match: eventMatches.values()) {
            // If qualification match
            if(match.getCompLevel().equals("qm")) {
                qualificationsMatches.put(Integer.valueOf(match.getMatchNumber()), match);
            }
        }
    }
    public Map<String, BlueAllianceMatch> getEventMatches() {
        return eventMatches;
    }
    public Map<Integer, BlueAllianceMatch> getQualificationMatches() {
        return qualificationsMatches;
    }

    public void addScoutingData(ScoutingData _data){
        Integer teamNumber = _data.getTeamNumber();
        Integer matchNumber = _data.getMatchNumber();
        Map<Integer, ScoutingData> matchMap;
        if(scoutingDataMap.get(teamNumber) == null){
            matchMap = new HashMap<>();
        }
        else {
            matchMap = scoutingDataMap.get(teamNumber);
        }
        matchMap.put(matchNumber, _data);
        String result = "false";
        if (_data.isAutoScoredScale()){
            result="true";
        }

        ScoutingData storedsd = getScoutingData(teamNumber, matchNumber);
        result ="false";
        if((storedsd != null) && storedsd.isAutoScoredScale()){
            result="true";
        }
        scoutingDataMap.put(teamNumber, matchMap);
    }
    public Map<Integer, Map<Integer, ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }
    public Map<Integer, ScoutingData> getScoutingDatasForTeam(int _teamNumber){
        Map<Integer, ScoutingData> rtnData = new HashMap<>();
        if(scoutingDataMap.get(_teamNumber) != null){
            rtnData = scoutingDataMap.get(_teamNumber);
        }
        return rtnData;
    }
    public ScoutingData getScoutingData(int _teamNumber, int _match) {
        ScoutingData rtnData = null;
        if(scoutingDataMap.get(_teamNumber) != null){
            Map<Integer, ScoutingData> matchDatas = scoutingDataMap.get(_teamNumber);
            if(matchDatas.get(_match) != null){
                rtnData = matchDatas.get(_match);
            }
        }
        return rtnData;
    }

    public void addBenchmarkData(BenchmarkData _data){
        Integer teamNumber = _data.getTeamNumber();
        benchmarkDataMap.put(teamNumber, _data);
    }
    public Map<Integer, BenchmarkData> getBenchmarkDataMap() {
        return benchmarkDataMap;
    }
    public BenchmarkData getBenchmarkData(int _teamNumber){
        BenchmarkData rtnData = null;
        if(benchmarkDataMap.get(_teamNumber) != null){
            rtnData = benchmarkDataMap.get(_teamNumber);
        }
        return rtnData;
    }

    public void pictureTaken(int teamNumber){
        if (teamNumberOfPhotos.containsKey(teamNumber)){
            int numberOfPhotos = teamNumberOfPhotos.get(teamNumber) +1;
            teamNumberOfPhotos.put(teamNumber, numberOfPhotos);
        }
        else {
            teamNumberOfPhotos.put(teamNumber, 1);
        }
    }
    public Map<Integer, Integer> getTeamNumberOfPhotos(){
        return teamNumberOfPhotos;
    }
}
