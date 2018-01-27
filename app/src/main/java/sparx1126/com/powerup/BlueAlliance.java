package sparx1126.com.powerup;


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
    String KEY = "0i1rgva3Y8G14rZS4dWDHcPNaw6EVMb9uSI9jW7diochnHpH8Y4nIhT0iHwj0hCq";
    String BASE_URL ="http://www.thebluealliance.com/api/v3/";
    String STATUS = "status";
    String EVENTS = "events";
    String TEAMS = "teams";
    String YEAR = "2018";
    private static BlueAlliance blueAlliance;
    Map<String, Event> eventsByKeyMap = new HashMap<>();
    Map<String, Event> teamsByKeyMap = new HashMap<>();
    public static synchronized BlueAlliance getInstance() {
        if (blueAlliance == null)
            blueAlliance = new BlueAlliance();
        return blueAlliance;
    }

    private BlueAlliance() {
    }

    void refreshData() {
        Request requestEvents = new Request.Builder()
                .addHeader("X-TBA-Auth-Key", KEY)
                .url(BASE_URL+EVENTS+"/"+YEAR)
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
            try {
                if(response.request().url().toString().contains(EVENTS)) {
                    JSONArray eventsArray = new JSONArray(response.body().string());
                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject eventObj = eventsArray.getJSONObject(i);
                        String valueOfKey = eventObj.getString(Event.KEY);
                        Event event = new Event(eventObj);
                        eventsByKeyMap.put(valueOfKey, event);
                    }
                    System.out.println(eventsByKeyMap);
                }
                else if(response.request().url().toString().contains(TEAMS)) {
                    JSONArray teamsArray = new JSONArray(response.body().string());
                    for (int i = 0; i < teamsArray.length(); i++) {
                        JSONObject eventObj = teamsArray.getJSONObject(i);
                        String valueOfKey = eventObj.getString(Event.KEY);
                        Event event = new Event(eventObj);
                        eventsByKeyMap.put(valueOfKey, event);
                }
                    System.out.println(teamsByKeyMap);

            } }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
