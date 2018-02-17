package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import sparx1126.com.powerup.custom_layouts.CustomExpandableListAdapter;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;


public class View extends AppCompatActivity {
    private static final String TAG = "View";

    //temporaryteamlist
    List<String> teamlistnew;
    private DataCollection dataCollection;

    private EditText teamnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        teamlistnew = new ArrayList<>();
        teamlistnew.add("1126");
        teamlistnew.add("123");

        dataCollection = DataCollection.getInstance();

        teamnumber = findViewById(R.id.teamnumber);
        teamnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s.toString());
                if (teamlistnew.contains(s.toString())) {
                    ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
                    HashMap<String, List<String>> expandableListDetail = getData();
                    List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(View.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                }
            }
        });
    }

    private HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
    //Scouting Data for testing
        ScoutingData tempdata = new ScoutingData();
        tempdata.setClimbunder15secs(true);
        tempdata.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata);
        ScoutingData tempdata2 = new ScoutingData();
        tempdata2.setClimbunder15secs(false);
        tempdata2.setNumRobotsHeld(2);
        tempdata2.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata2);
        ScoutingData tempdata3 = new ScoutingData();
        tempdata3.setClimbRung(true);
        tempdata3.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata3);
        ScoutingData tempdata4 = new ScoutingData();
        tempdata4.setClimbOnRobot(true);
        tempdata4.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata4);
        ScoutingData tempdata5 = new ScoutingData();
        tempdata5.setPlayeddefense(true);
        tempdata5.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata5);
        ScoutingData tempdata6 = new ScoutingData();
        tempdata6.setTimesPickedfromfloor(3);
        tempdata6.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata6);
        ScoutingData tempdata7 = new ScoutingData();
        tempdata7.setCubesfromplayers(1);
        tempdata7.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata7);
        ScoutingData tempdata8 = new ScoutingData();
        tempdata8.setTimesplacedexchange(3);
        tempdata8.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata8);
        ScoutingData tempdata9 = new ScoutingData();
        tempdata9.setTimesscoredswitch(3);
        tempdata9.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata8);
        ScoutingData tempdata11 = new ScoutingData();
        tempdata11.setAutoStartedLeft(true);
        tempdata11.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata11);
        ScoutingData tempdata12 = new ScoutingData();
        tempdata12.setAutoStartedCenter(false);
        tempdata12.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata12);
        ScoutingData tempdata13 = new ScoutingData();
        tempdata13.setAutoStartedRight(false);
        tempdata13.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata13);
        ScoutingData tempdata14 = new ScoutingData();
        tempdata14.setAutoScoredSwitch(true);
        tempdata14.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata14);
        ScoutingData tempdata15 = new ScoutingData();
        tempdata15.setAutoScoredScale(false);
        tempdata15.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata15);
        ScoutingData tempdata16 = new ScoutingData();
        tempdata16.setAutoPickedUpCube(true);
        tempdata16.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata16);
        ScoutingData tempdata17 = new ScoutingData();
        tempdata17.setAutoCubeExchange(true);
        tempdata17.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata17);
        ScoutingData tempdata18 = new ScoutingData();
        tempdata18.setAutolinecheck(true);
        tempdata18.setTeamnumber(1126);
        dataCollection.addScoutingData(tempdata18);







        Log.e("Felix Was Here" ,  dataCollection.getScoutingDataMap().toString());
        Map<Integer, List<ScoutingData>> teamsScouted = dataCollection.getScoutingDataMap();

        // for testing
        if (teamsScouted.containsKey(1126)) {
            List<ScoutingData> teamDatas = teamsScouted.get(1126);
            float climbunder15secs = 0;
            float numRobotsHeld = 0;
            float climbRung = 0;
            float climbOnRobot = 0;
            float playeddefense = 0;
            float timesPickedfromfloor = 0;
            float cubesfromplayers = 0;
            float timesplacedexchange = 0;
            float timescoredscale = 0;
            float timesscoredswitch = 0;
            float autoStartedLeft = 0;
            float autoStartedCenter = 0;
            float autoStartedRight = 0;
            float autoScoredSwitch = 0;
            float autoScoredScale = 0;
            float autoPickedUpCube = 0;
            float autoCubeExchange = 0;
            float autolinecheck = 0;


            List<String> scouting = new ArrayList<>();
            scouting.add("<font color=\"black\"><b>Matches scouted: </b></font>" + teamDatas.size());
            for (ScoutingData sd : teamDatas) {
                if (sd.isClimbunder15secs()) {
                    climbunder15secs++;
                    Log.e("Felix was here", Float.toHexString(climbunder15secs));
                }

                numRobotsHeld += sd.getNumRobotsHeld();
                if (sd.getClimbRung()) {
                    climbRung++;
                }
                if (sd.getClimbOnRobot()) {
                    climbOnRobot++;
                }
                if (sd.isPlayeddefense()) {
                    playeddefense++;
                }
                timesPickedfromfloor += timesPickedfromfloor;
                cubesfromplayers += cubesfromplayers;
                timesplacedexchange += timesplacedexchange;
                timescoredscale += timescoredscale;
                timesscoredswitch += timesscoredswitch;
                if (sd.getAutoStartedLeft()) {
                    autoStartedLeft++;
                }

                if (sd.getAutoStartedCenter()) {
                    autoStartedCenter++;
                }
                if (sd.getAutoStartedRight()) {
                    autoStartedRight++;
                }
                if (sd.isAutoScoredSwitch()) {
                    autoScoredSwitch++;
                }
                if (sd.isAutoScoredScale()) {
                    autoScoredScale++;
                }
                if (sd.isAutoPickedUpCube()) {
                    autoPickedUpCube++;
                }
                if (sd.isAutoCubeExchange()) {
                    autoCubeExchange++;
                }
                if (sd.isAutolinecheck()) {
                    autolinecheck++;

                }

            }
            if (teamDatas.size() != 0) {
                climbunder15secs = climbunder15secs / teamDatas.size();
                numRobotsHeld = numRobotsHeld / teamDatas.size();
                climbRung = climbRung / teamDatas.size();
                climbOnRobot = climbOnRobot / teamDatas.size();
                playeddefense = playeddefense / teamDatas.size();
                timesPickedfromfloor = timesPickedfromfloor / teamDatas.size();
                cubesfromplayers = cubesfromplayers / teamDatas.size();
                timesplacedexchange = timesplacedexchange / teamDatas.size();
                timescoredscale = timescoredscale / teamDatas.size();
                timesscoredswitch = timesscoredswitch / teamDatas.size();
                autoStartedLeft = autoStartedLeft / teamDatas.size();
                autoStartedCenter = autoStartedCenter / teamDatas.size();
                autoStartedRight = autoStartedRight / teamDatas.size();
                autoScoredSwitch = autoScoredSwitch / teamDatas.size();
                autoScoredScale = autoScoredScale / teamDatas.size();
                autoPickedUpCube = autoPickedUpCube / teamDatas.size();
                autoCubeExchange = autoCubeExchange / teamDatas.size();
                autolinecheck = autolinecheck / teamDatas.size();
            }
            scouting.add("<font color=\"black\"><b>AUTO</b></font>");
            scouting.add("<font color=\"black\"><b></b></font>");
            scouting.add("<font color=\"black\"><b> Number of times climbed under 15 seconds. ; </b></font>" + climbunder15secs);

            expandableListDetail.put("Scouting", scouting);

        }
        else{
                Log.e(TAG, "team not found");
            }


            return expandableListDetail;
        }
    }


