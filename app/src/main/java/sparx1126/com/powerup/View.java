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

import static java.lang.Character.isUpperCase;

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
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            int increment = 0;
            for (String key : booleanValuesMap.keySet()) {
                if (booleanValuesMap.get(key)) {
                    if (booleanValueSumsMap.containsKey(key)) {
                        increment += booleanValueSumsMap.get(key);
                    } else {
                        increment = 1;
                    }
                }
                booleanValueSumsMap.put(key, increment);
            }

            Map<String, Integer> intValuesMap = data.getIntValuesMap();
            for (String key : intValuesMap.keySet()) {
                int total = intValuesMap.get(key);
                if (intValueSumsMap.containsKey(key)) {
                    total += intValueSumsMap.get(key);
                }
                // excluding team number and match number
                if (!key.equals(ScoutingData.TEAM_NUMBER) && !key.equals(ScoutingData.MATCH_NUMBER)) {
                    intValueSumsMap.put(key, total);
                }

            }
        }

        for (Map.Entry<String, Integer> entry : booleanValueSumsMap.entrySet()) {
            rtnList.add("<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + entry.getValue() + " times");
        }
        for (Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
            float average = entry.getValue() / numberOfDatas;
            String averageValue = String.format("%.2f", average);
            rtnList.add("\t<font color=\"yellow\">\t\t" + entry.getKey() + ":  </font>" + averageValue + " average");
        }

        return rtnList;
    }

    private List<String> getBenchmarkData(int _teamNumber) {
        List<String> rtnList = new ArrayList<>();

        BenchmarkData data = dataCollection.getBenchmarkData(_teamNumber);

        if (data == null) {
            rtnList.add("<b>\tHas not been benchmarked!</b>");
        } else {
            Map<String, Object> masterMap = new HashMap<String, Object>();

            Map<String, String> stringValuesMap = data.getStringValuesMap();
            for (Map.Entry<String, String> entry : stringValuesMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                masterMap.put(key, value);
            }


            Map<String, Boolean> booleanValuesMap = data.getBooleanValuesMap();
            for (Map.Entry<String, Boolean> entry : booleanValuesMap.entrySet()) {
                String key = entry.getKey();
                String value = trimBoolean(entry.getValue());
                masterMap.put(key, value);
            }

            Map<String, Float> floatValuesMap = data.getFloatValuesMap();
            for (Map.Entry<String, Float> entry : floatValuesMap.entrySet()) {
                String key = entry.getKey();
                Float value = entry.getValue();
                masterMap.put(key, value);
            }

            Map<String, Integer> intValueSumsMap = data.getIntValuesMap();
            for (Map.Entry<String, Integer> entry : intValueSumsMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                masterMap.put(key, value);
            }

            rtnList.add("\t<font color=\"yellow\">\t\t" + "Team Number" + ":  </font>" + masterMap.get("teamNumber"));
            masterMap.remove("teamNumber");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Benchmarked By" + ":  </font>" + masterMap.get("scouterName"));
            masterMap.remove("scouterName");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Type Of Drive" + ":  </font>" + masterMap.get("typeOfDrive"));
            masterMap.remove("typeOfDrive");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Type Of Wheels" + ":  </font>" + masterMap.get("typeOfWheel"));
            masterMap.remove("typeOfWheel");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Number Of Wheels" + ":  </font>" + masterMap.get("numberOfWheels"));
            masterMap.remove("numberOfWheels");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Ground Clearance" + ":  </font>" + masterMap.get("groundClearance"));
            masterMap.remove("groundClearance");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Height" + ":  </font>" + masterMap.get("height"));
            masterMap.remove("height");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Speed " + ":  </font>" + masterMap.get("speed"));
            masterMap.remove("speed");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Weight " + ":  </font>" + masterMap.get("weight"));
            masterMap.remove("weight");
            rtnList.add("\t<font color=\"yellow\">\t\t" +  "Auto-" + "  </font>");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Prefers To Start " + ":  </font> 1. " + masterMap.get("preferStartOne") + " 2. " + masterMap.get("preferStartTwo") + " 3. " + masterMap.get("preferStartThree"));
            masterMap.remove("preferStartOne");
            masterMap.remove("preferStartTwo");
            masterMap.remove("preferStartThree");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Start With A Cube" + ":  </font>" + masterMap.get("canStartWithCube"));
            masterMap.remove("canStartWithCube");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Cross Auto Line " + ":  </font>" + masterMap.get("auto_LineCrossed"));
            masterMap.remove("auto_LineCrossed");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Acquire From Floor " + ":  </font>" + masterMap.get("auto_CanAcquireFloor"));
            masterMap.remove("auto_CanAcquireFloor");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Acquire From Portal " + ":  </font>" + masterMap.get("auto_CanAcquirePortal"));
            masterMap.remove("auto_CanAcquirePortal");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Place On Switch" + ":  </font>" + masterMap.get("auto_HowManySwitchPlaced") + " time(s)");
            masterMap.remove("auto_HowManySwitchPlaced");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Toss On Switch" + ":  </font>" + masterMap.get("auto_HowManySwitchTossed") + " time(s)");
            masterMap.remove("auto_HowManySwitchTossed");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Place On Scale" + ":  </font>" + masterMap.get("auto_HowManyScalePlaced") + " time(s)");
            masterMap.remove("auto_HowManyScalePlaced");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Toss On Scale" + ":  </font>" + masterMap.get("auto_HowManyScaleTossed") + " time(s)");
            masterMap.remove("auto_HowManyScaleTossed");
            rtnList.add("\t<font color=\"yellow\">\t\t" +  "Teleop-" + "  </font>");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Acquire From Floor" + ":  </font>" + masterMap.get("tele_AcquireFloor"));
            masterMap.remove("tele_AcquireFloor");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Acquire From Portal" + ":  </font>" + masterMap.get("tele_AcquirePortal"));
            masterMap.remove("tele_AcquirePortal");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Deposit To The Vault" + ":  </font>" + masterMap.get("tele_Deposit_vault"));
            masterMap.remove("tele_Deposit_vault");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Place On Switch" + ":  </font>" + masterMap.get("tele_PlaceOnSwitch"));
            masterMap.remove("tele_PlaceOnSwitch");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Toss On Switch" + ":  </font>" + masterMap.get("tele_TossToSwitch"));
            masterMap.remove("tele_TossToSwitch");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Place On Scale" + ":  </font>" + masterMap.get("tele_PlaceOnScale"));
            masterMap.remove("tele_PlaceOnScale");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Toss On Scale" + ":  </font>" + masterMap.get("tele_TossToScale"));
            masterMap.remove("tele_TossToScale");
            rtnList.add("\t<font color=\"yellow\">\t\t" +  "End Game-" + "  </font>");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Climb Without Assistance" + ":  </font>" + masterMap.get("end_ClimbWithoutAssist") );
            masterMap.remove("end_ClimbWithoutAssist");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Climb Assist Type" + ":  </font>" + masterMap.get("end_ClimbAssistType"));
            masterMap.remove("end_ClimbAssistType");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Climb Height" + ":  </font>" + masterMap.get("end_ClimbHeight"));
            masterMap.remove("end_ClimbHeight");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Can Attach To A Robot" + ":  </font>" + masterMap.get("end_AttachToRobot"));
            masterMap.remove("end_AttachToRobot");
            rtnList.add("\t<font color=\"yellow\">\t\t" + "Comments " + ":  </font>" + masterMap.get("comments"));
            masterMap.remove("comments");

            for (Map.Entry<String, Object> entry : masterMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                rtnList.add("\t<font color=\"yellow\">\t\t" + fixKey(key) + ":  </font>" + value);
            }
        }
        return rtnList;
    }

    public String trimBoolean(Boolean b) {
        if(b) {
            return "Yes";
        } else {
            return "No ";
        }
    }

    public String fixKey(String key) {
        String fixedKey = "";
        for(int i = 0; i < key.length(); i++) {
            if(isUpperCase(key.charAt(i))) {
                fixedKey += " ";
                fixedKey += key.charAt(i);
            } else if(key.charAt(i) == '_') {
                fixedKey += " ";
            } else {
                fixedKey += key.charAt(i);
            }
        }
        fixedKey = Character.toUpperCase((fixedKey.charAt(0))) + fixedKey.substring(1);
        return fixedKey;
    }
}


