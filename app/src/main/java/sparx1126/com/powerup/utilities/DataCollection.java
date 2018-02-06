package sparx1126.com.powerup.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.ScoutingData;

/**
 * Created by Papa on 2/5/18.
 */

public class DataCollection {
    private static DataCollection theDataCollection;

    public Map<Integer, List<ScoutingData>> getScoutingDataMap() {
        return scoutingDataMap;
    }

    private Map<Integer, List<ScoutingData>> scoutingDataMap;

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
}
