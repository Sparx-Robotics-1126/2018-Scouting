package sparx1126.com.powerup.utilities;

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
    private Map<Integer, List<ScoutingData>> scoutingDataMap;
    private Map<Integer, BenchmarkData> benchmarkDataMap;
    private Map<String, BlueAllianceEvent > eventsWeAreInMap;
    private Map<String, BlueAllianceTeam> teamsInEventMap;
    private Map<String, BlueAllianceMatch> eventMatchesMap;

    public static synchronized DataCollection getInstance(){
        if(dataCollection == null ) {
            dataCollection = new DataCollection();
        }
        return dataCollection;
    }

    private DataCollection(){
        scoutingDataMap = new HashMap<>();
        benchmarkDataMap = new HashMap<>();
        eventsWeAreInMap = new HashMap<>();
        teamsInEventMap = new HashMap<>();
        eventMatchesMap = new HashMap<>();
    }

    public void addScoutingData(ScoutingData _data){
        Integer key = _data.getTeamNumber();
        if(scoutingDataMap.containsKey(key)){
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
        if(scoutingDataMap.containsKey(_teamNumber)){
            rtnData = scoutingDataMap.get(_teamNumber);
        }
        return rtnData;
    }
    public Map<Integer, List<ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }

    public void addBenchmarkData(BenchmarkData _data){
        Integer key = _data.getTeamnumber();
        benchmarkDataMap.put(key, _data);
    }
    public Map<Integer, BenchmarkData> getBenchmarkDataMap() {
        return benchmarkDataMap;
    }


    public void setTeamEvents(Map<String, BlueAllianceEvent> _eventData){
        eventsWeAreInMap = _eventData;
    }
    public Map<String, BlueAllianceEvent> getTeamEvents(){
        return eventsWeAreInMap;
    }

    public void setEventTeams(Map<String, BlueAllianceTeam> _Data){
        teamsInEventMap = _Data;
    }
    public Map<String, BlueAllianceTeam> getTeamsInEventMap(){
        return teamsInEventMap;
    }

    public Map<String, BlueAllianceMatch> getEventMatches() {
        return eventMatchesMap;
    }
    public void setEventMatches(Map<String, BlueAllianceMatch> matchesInEventMap) {
        this.eventMatchesMap = matchesInEventMap;
    }
}
