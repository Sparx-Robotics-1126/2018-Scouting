package sparx1126.com.powerup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private List<Integer> teamsInEvent;

    private AutoCompleteTextView teamnumber;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        dataCollection = DataCollection.getInstance();
        teamsInEvent = dataCollection.getTeamsInEvent();

        teamnumber = findViewById(R.id.team_number);
        teamnumber.setTransformationMethod(null);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.custom_list_item, teamsInEvent);
        teamnumber.setAdapter(adapter);
        teamnumber.setThreshold(1);
        teamnumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String teamNumberStringg = teamnumber.getText().toString();
                    int teamNumber = Integer.valueOf(teamNumberStringg);
                    boolean teamNumberFound = teamsInEvent.contains(teamNumber);
                    HashMap<String, List<String>> expandableListDetail = new HashMap<>();
                    if (teamNumberFound) {
                        Log.d(TAG, teamNumberStringg);
                        expandableListDetail = getData(teamNumber);
                        android.view.View view = findViewById(android.R.id.content).getRootView();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                    List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(View.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);

                } catch (Exception e) {
                    Log.e("Problem w/ team # txt", e.toString());
                }
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

        float numberOfDatas = datas.size();

        rtnList.add("<b>\tMatches scouted: </b>" + numberOfDatas);
        // Notice no String Map. It would look wrong to show ALL strings for ALL scouting in one field
        Map<String, Integer> booleanValueSumsMap = new HashMap<>();
        Map<String, Integer> intValueSumsMap = new HashMap<>();
        for (ScoutingData data : datas.values()) {
            Map<String, Boolean> booleanValuesMap = data.getBooleanValuesMap();
            for(String key: booleanValuesMap.keySet()) {
                if(booleanValuesMap.get(key)) {
                    int increment = 1;
                    if(booleanValueSumsMap.containsKey(key)) {
                        increment += booleanValueSumsMap.get(key);
                    }
                    booleanValueSumsMap.put(key, increment);
                }
            }

            Map<String, Integer> intValuesMap = data.getIntValuesMap();
            for(String key: intValuesMap.keySet()) {
                int total = intValuesMap.get(key);
                if(intValueSumsMap.containsKey(key)) {
                    total += intValueSumsMap.get(key);
                }
                // excluding team number
                if(!key.equals(ScoutingData.TEAM_NUMBER) && !key.equals(ScoutingData.MATCH_NUMBER)) {
                    intValueSumsMap.put(key, total);
                }

            }
        }

        if (numberOfDatas != 0) {
            for(Map.Entry<String, Integer> entry : booleanValueSumsMap.entrySet()) {
                rtnList.add("<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + entry.getValue() + " times");
            }
            for(Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
                float average = entry.getValue() / numberOfDatas;
                String averageValue = String.format("%.2f", average);
                Log.e("roundedNumber", averageValue);
                rtnList.add("\t<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + averageValue + " average");
            }
        }

        return rtnList;
    }

    private List<String> getBenchmarkData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        BenchmarkData data = dataCollection.getBenchmarkData(_teamNumber);

        if(data == null) {
            rtnList.add("<b>\tHas not been benchmarked!</b>");
        }
        else {
            Map<String, String> stringValuesMap = data.getStringValuesMap();
            for(Map.Entry<String, String> entry : stringValuesMap.entrySet()) {
                rtnList.add("\t<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + entry.getValue());
            }
            Map<String, Boolean> booleanValuesMap = data.getBooleanValuesMap();
            for(Map.Entry<String, Boolean> entry : booleanValuesMap.entrySet()) {
                String result = "False";
                if(entry.getValue()){
                    result = "True";
                }
                rtnList.add("\t<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + result);
            }
            Map<String, Integer> intValueSumsMap = data.getIntValuesMap();
            for(Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
                rtnList.add("\t<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + entry.getValue());
            }
        }

        return rtnList;
    }
}


