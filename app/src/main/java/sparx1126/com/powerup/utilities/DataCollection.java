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
    private static DataCollection theDataCollection;
    private final Map<Integer, List<ScoutingData>> scoutingDataMap;
    private final Map<Integer, BenchmarkData> benchmarkDataMap;
    private Map<String, BlueAllianceEvent > eventsWeAreInMap;
    private Map<String, BlueAllianceTeam> teamsInEventMap;
    private FileIO fileIO;

    public Map<String, BlueAllianceMatch> getMatchesInEventMap() {
        return matchesInEventMap;
    }

    public void setMatchesInEventMap(Map<String, BlueAllianceMatch> matchesInEventMap) {
        this.matchesInEventMap = matchesInEventMap;
    }

    private Map<String, BlueAllianceMatch> matchesInEventMap;


    public static synchronized DataCollection getInstance(){
        if(theDataCollection == null ) {
            theDataCollection = new DataCollection();
        }
        return theDataCollection;
    }

    private DataCollection(){
        scoutingDataMap = new HashMap<>();
        benchmarkDataMap = new HashMap<>();
        fileIO = FileIO.getInstance();
    }

    public void addScoutingData(ScoutingData _data){
        Integer key = _data.getTeamnumber();
        if(scoutingDataMap.containsKey(key)){
            scoutingDataMap.get(key).add(_data);
        }
        else {
            List<ScoutingData> newList = new ArrayList<>();
            newList.add(_data);
            scoutingDataMap.put(key, newList);
        }
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


    public void setEventsWeAreIn(Map<String, BlueAllianceEvent> _eventData){
        eventsWeAreInMap = _eventData;
    }

    public Map<String, BlueAllianceEvent> getEventsWeAreIn(){
        return eventsWeAreInMap;
    }

    public void setTeamsInEvent (Map<String, BlueAllianceTeam> _Data){
        teamsInEventMap = _Data;
    }

    public Map<String, BlueAllianceTeam> getTeamsInEventMap(){
        return teamsInEventMap;
    }
}
