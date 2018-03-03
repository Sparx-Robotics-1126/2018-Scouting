package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.custom_layouts.CustomExpandableListAdapter;
import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;

public class View extends AppCompatActivity {
    private static final String TAG = "View ";
    private static DataCollection dataCollection;

    EditText teamnumber;
    private Button teamNumberButton;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        dataCollection = DataCollection.getInstance();

        teamnumber = findViewById(R.id.teamNumber);
        teamNumberButton = findViewById(R.id.teamNumberButton);
        teamNumberButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                int teamNumber = Integer.valueOf(teamnumber.getText().toString());

                HashMap<String, List<String>> expandableListDetail = getData(teamNumber);
                List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(View.this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

            }
        });
        expandableListView = findViewById(R.id.expandableListView);
    }

    private HashMap<String, List<String>> getData(int _teamNumber) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        expandableListDetail.put("Benchmark", getBenchmarkData(_teamNumber));
        expandableListDetail.put("Scouting", getScoutingData(_teamNumber));

        return expandableListDetail;
    }

    private List<String> getScoutingData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        Map<Integer, ScoutingData> datas = dataCollection.getScoutingDatasForTeam(_teamNumber);

        int numberOfDatas = datas.size();

        rtnList.add("<font color=\"black\"><b>Matches scouted: </b></font>" + numberOfDatas);
        Map<String, Integer> booleanValueSumsMap = new HashMap<>();
        Map<String, Integer> intValueSumsMap = new HashMap<>();
        for (ScoutingData data : datas.values()) {
            Map<String, Boolean> booleanValuesMap = data.getBooleanValuesMap();
            for(String key: booleanValuesMap.keySet()) {
                if(booleanValuesMap.get(key)) {
                    booleanValueSumsMap.put(key, booleanValueSumsMap.get(key) + 1);
                }
            }

            Map<String, Integer> intValuesMap = data.getIntValuesMap();
            for(String key: intValuesMap.keySet()) {
                intValueSumsMap.put(key, intValueSumsMap.get(key) + intValuesMap.get(key));
            }
        }

        if (numberOfDatas != 0) {
            for(Map.Entry<String, Integer> entry : booleanValueSumsMap.entrySet()) {
                rtnList.add("<font color=\"black\"><b> " + entry.getKey() + ": </b></font>" + entry.getValue() + " times");
            }
            for(Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
                float average = entry.getValue() / numberOfDatas;
                rtnList.add("<font color=\"black\"><b> \" + entry.getKey() + \": </b></font>" + average + " average");
            }
        }

        return rtnList;
    }

    private List<String> getBenchmarkData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        BenchmarkData data = dataCollection.getBenchmarkData(_teamNumber);

        if(data == null) {
            rtnList.add("<font color=\"black\"><b>NOT Benchmark!</b></font>");
        }
        else {
            Map<String, String> stringValuesMap = data.getStringValuesMap();
            for(Map.Entry<String, String> entry : stringValuesMap.entrySet()) {
                rtnList.add("<font color=\"black\"><b> " + entry.getKey() + ". </b></font>");
            }
            Map<String, Boolean> booleanValuesMap = data.getBooleanValuesMap();
            for(Map.Entry<String, Boolean> entry : booleanValuesMap.entrySet()) {
                String result = "False";
                if(entry.getValue()){
                    result = "True";
                }
                rtnList.add("<font color=\"black\"><b> " + entry.getKey() + ": </b></font>" + result);
            }
            Map<String, Integer> intValueSumsMap = new HashMap<>();
            for(Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
                rtnList.add("<font color=\"black\"><b> \" + entry.getKey() + \": </b></font>" + entry.getValue());
            }
        }

        return rtnList;
    }
}


