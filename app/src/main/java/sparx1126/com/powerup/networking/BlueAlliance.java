package sparx1126.com.powerup.networking;


import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sparx1126.com.powerup.utilities.Event;
import sparx1126.com.powerup.utilities.Team;

/**
 * Created by Papa on 1/27/18.
 */

public class BlueAlliance implements okhttp3.Callback {
    OkHttpClient client = new OkHttpClient();
    String KEY = "0i1rgva3Y8G14rZS4dWDHcPNaw6EVMb9uSI9jW7diochnHpH8Y4nIhT0iHwj0hCq";
    String BASE_URL ="http://www.thebluealliance.com/api/v3/";
    String STATUS = "status";
    String EVENTS = "events";
    String TEAMS = "teams";
    String YEAR = "2018";
    String EVENT = "event";
    String TEAM = "team";
    private static BlueAlliance blueAlliance;
    ArrayList<String> currentTeamEvents = new ArrayList<>();
    Map<String, Event> eventsByKeyMap = new HashMap<>();
    Map<String, Team> teamsByKeyMap = new HashMap<>();
    public static synchronized BlueAlliance getInstance() {
        if (blueAlliance == null)
            blueAlliance = new BlueAlliance();
        return blueAlliance;
    }

    private BlueAlliance() {
    }

    public void fetchEventData() {
        Request requestEvents = new Request.Builder()
                .addHeader("X-TBA-Auth-Key", KEY)
                .url(BASE_URL+EVENTS+"/"+YEAR)
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    public void fetchTeamData(String eventKey) {
        Request requestEvents = new Request.Builder()
                .addHeader("X-TBA-Auth-Key", KEY)
                .url(BASE_URL+EVENT+"/"+eventKey+"/"+TEAMS)
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    public void fetchTeamEvents(String teamCode) {
        Log.e("Fetched Team Events", "");
        Request requestEvents = new Request.Builder()
                .addHeader("X-TBA-Auth-Key", KEY)
                .url(BASE_URL+TEAM+"/"+teamCode+"/"+EVENTS+"2018"+"/"+"simple")
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    public ArrayList<String> getTeamEvents(int teamNumber) {
        System.out.println("Method called by main");
        String teamCode = "frc" + Integer.toString(teamNumber);
        fetchTeamEvents(teamCode);
        return currentTeamEvents;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            //System.out.println(response.body().string());
            try {
                if (response.request().url().toString().contains(EVENTS)) {
                    JSONArray eventsArray = new JSONArray(response.body().string());

                    //checks if it is just for a specific team or multiple
                    if(eventsArray.length() > 5) {
                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject eventObj = eventsArray.getJSONObject(i);
                        String valueOfKey = eventObj.getString(Event.KEY);
                        Event event = new Event(eventObj);
                        eventsByKeyMap.put(valueOfKey, event);

//                        for(String key: eventsByKeyMap.keySet()) {
//                            fetchTeamData(key);
//                        }
                        fetchTeamData("2018ohcl");


                         }
                    } else {
                        currentTeamEvents = new ArrayList<>();
                        for (int i = 0; i < eventsArray.length(); i++) {
                            Log.e("Number of events", Integer.toString(eventsArray.length()));
                            JSONObject eventObj = eventsArray.getJSONObject(i);
                            Event event = new Event(eventObj);
                            currentTeamEvents.add(event.getName());
                        }
                    }
                    //System.out.println(eventsByKeyMap);
                }
                else if(response.request().url().toString().contains(TEAMS)) {
                    JSONArray teamsArray = new JSONArray(response.body().string());
                    for (int i = 0; i < teamsArray.length(); i++) {
                        JSONObject teamObj = teamsArray.getJSONObject(i);
                        String valueOfKey = teamObj.getString(Team.KEY);
                        Team team = new Team(teamObj);
                        String url = response.request().url().toString();
                        String[] urlParts = url.split("/");
//                        String eventKey = urlParts[6];
//                        if(team.getNumber().equals(Integer.toString(1126))) {
//                            Log.i("Found 1126",eventKey);
//                        }

//                        teamsByKeyMap.put(valueOfKey, team);
                    }
            }
             }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
