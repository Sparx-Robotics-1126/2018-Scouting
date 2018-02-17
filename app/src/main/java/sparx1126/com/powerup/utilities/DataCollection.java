package sparx1126.com.powerup.utilities;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.Admin;
import sparx1126.com.powerup.R;
import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.data_components.ScoutingData;

/**
 * Created by Papa on 2/5/18.
 */

public class DataCollection {
    private static DataCollection theDataCollection;
    private Map<Integer, List<ScoutingData>> scoutingDataMap;
    private Map<String, BlueAllianceEvent > eventsWeAreInMap;
    private Map<String, BlueAllianceTeam> teamsInEventMap;
    private SharedPreferences settings;
    public Admin downloader = new Admin();


    public static synchronized DataCollection getInstance(){
        if(theDataCollection == null ) {
            theDataCollection = new DataCollection();
        }
        return theDataCollection;
    }

    private DataCollection(){
        scoutingDataMap = new HashMap<Integer, List<ScoutingData>>();
    }

    public void addScoutingData(ScoutingData data){
        Integer key = data.getTeamnumber();
        if(scoutingDataMap.containsKey(key)){
            scoutingDataMap.get(key).add(data);
        }
        else {
            List<ScoutingData> newList = new ArrayList<ScoutingData>();
            newList.add(data);
            scoutingDataMap.put(key, newList);
        }
    }

    public void setEventsWeAreIn(Map<String, BlueAllianceEvent> _eventData){
        eventsWeAreInMap = _eventData;
    }

    public Map<String, BlueAllianceEvent> getEventsWeAreIn(){
        return eventsWeAreInMap;
    }

    public Map<Integer, List<ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }

    public Map<String, BlueAllianceTeam> setTeamsInEvent (Map<String, BlueAllianceTeam> _Data){
        teamsInEventMap = _Data;
        return _Data;
    }

    public Map<String, BlueAllianceTeam> getTeamsInEventMap(){
        downloader.downloadTeams();
        return teamsInEventMap;
    }
}
