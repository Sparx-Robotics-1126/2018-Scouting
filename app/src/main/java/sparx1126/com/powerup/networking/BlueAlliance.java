package sparx1126.com.powerup.networking;


import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Papa on 1/27/18.
 */

public class BlueAlliance implements okhttp3.Callback {
    OkHttpClient client = new OkHttpClient();
    // Hiram's key (expires in 30 days)
    String KEY = "0i1rgva3Y8G14rZS4dWDHcPNaw6EVMb9uSI9jW7diochnHpH8Y4nIhT0iHwj0hCq";
    String URL_HEADER = "X-TBA-Auth-Key";
    String BASE_URL ="http://www.thebluealliance.com/api/v3/";
    String EVENTS_URL = BASE_URL + "events/2018";
    String TEAMS_URL = BASE_URL + "event/{event_key}/teams";
    String TEAM_EVENTS_URL = BASE_URL + "team/{team_key}/events/2018";

    private static BlueAlliance blueAlliance;

    Map<String, EventNet> eventsByKeyMap;
    Map<String, TeamNet> teamsByKeyMap;
    Map<String, EventNet> eventsWeAreInByKeyMap;

    public static synchronized BlueAlliance getInstance() {
        if (blueAlliance == null)
            blueAlliance = new BlueAlliance();
        return blueAlliance;
    }

    private BlueAlliance() {
    }

    public void fetchEvents() {
        Request requestEvents = new Request.Builder()
                .addHeader(URL_HEADER, KEY)
                .url(EVENTS_URL)
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    public void fetchTeams(String eventKey) {
        String finalURL = (TEAMS_URL).replace("{event_key}", eventKey);
        Request requestEvents = new Request.Builder()
                .addHeader(URL_HEADER, KEY)
                .url(finalURL)
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    public void fetchTeamEvents(String teamKey) {
        String finalURL = (TEAM_EVENTS_URL).replace("{team_key}", teamKey);
        Request requestEvents = new Request.Builder()
                .addHeader(URL_HEADER, KEY)
                .url(finalURL)
                .build();

        client.newCall(requestEvents).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            //System.out.println(response.body().string());
            if (response.request().url().toString() == (EVENTS_URL)) {
                eventsByKeyMap = FillEvents(response.body().string());
                //System.out.println(eventsByKeyMap);
            }
            else if(response.request().url().toString().contains("teams")) {
                teamsByKeyMap = FillTeams(response.body().string());
                //System.out.println(teamsByKeyMap);
            }
            else if(response.request().url().toString().contains("team")) {
                eventsWeAreInByKeyMap = FillEvents(response.body().string());
                //System.out.println(eventsWeAreInByKeyMap);
            }
        }
    }

    Map<String, EventNet> FillEvents(String _input) {
        Map<String, EventNet> rtnMap = new HashMap<>();

        try {
            JSONArray eventsArray = new JSONArray(_input);
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventObj = eventsArray.getJSONObject(i);
                String valueOfKey = eventObj.getString(EventNet.KEY);
                EventNet event = new EventNet(eventObj);
                rtnMap.put(valueOfKey, event);
                //System.out.println(eventsWeAreInByKeyMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }

    Map<String, TeamNet> FillTeams(String _input) {
        Map<String, TeamNet> rtnMap = new HashMap<>();

        try {
            JSONArray teamsArray = new JSONArray(_input);
            for (int i = 0; i < teamsArray.length(); i++) {
                JSONObject teamObj = teamsArray.getJSONObject(i);
                String valueOfKey = teamObj.getString(TeamNet.KEY);
                TeamNet team = new TeamNet(teamObj);
                rtnMap.put(valueOfKey, team);
                //System.out.println(teamsByKeyMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnMap;
    }
}
