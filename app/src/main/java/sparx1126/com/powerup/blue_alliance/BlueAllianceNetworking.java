package sparx1126.com.powerup.blue_alliance;

import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import sparx1126.com.powerup.utilities.Networking;

public class BlueAllianceNetworking {

    public interface CallbackEvents {
        void onFailure(String _reason);
        void onSuccess(Map<String, BlueAllianceEvent> _result);
    }

    public interface CallbackTeams {
        void onFailure(String _reason);
        void onSuccess(Map<String, BlueAllianceTeam> _result);
    }

    private static final String YEAR = "2018";
    private static final String SPARX_TEAM_KEY = "frc1126";
    // intention is for {event_key} to be substituted
    private static String EVENT_TEAMS_URL_TAIL = "event/{event_key}/teams";
    // intention is for {team_key} to be substituted
    private static String TEAM_EVENTS_URL_TAIL = "team/{team_key}/events/" + YEAR;

    private static Networking network;
    private static BlueAllianceNetworking instance;

    public static synchronized BlueAllianceNetworking getInstance() {
        if (instance == null)
            instance = new BlueAllianceNetworking();
        return instance;
    }

    private BlueAllianceNetworking() {
        network = Networking.getInstance();
    }

    public void getEventsSparxsIsIn(CallbackEvents _callback) {
        getTeamEvents(SPARX_TEAM_KEY, _callback);
    }

    // made this private because why do we care of other team events???
    private void getTeamEvents(String _key, final CallbackEvents _callback) {
        String url_tail = (TEAM_EVENTS_URL_TAIL).replace("{team_key}", _key);
        network.getBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceEvent> rtnMap = new HashMap<>();

                    try {
                        //Log.d("getTeamEvents", response.body().string());
                        JSONArray eventsArray = new JSONArray(response.body().string());
                        for (int i = 0; i < eventsArray.length(); i++) {
                            JSONObject eventObj = eventsArray.getJSONObject(i);
                            BlueAllianceEvent event = new BlueAllianceEvent(eventObj);
                            rtnMap.put(event.getKey(), event);
                        }
                        Log.d("getTeamEvents", rtnMap.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    _callback.onSuccess(rtnMap);
                }
                else {
                    _callback.onFailure(response.message());
                }
            }
        });
    }

    public void getEventTeams(String _key, final CallbackTeams _callback) {
        String url_tail = (EVENT_TEAMS_URL_TAIL).replace("{event_key}", _key);
        network.getBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceTeam> rtnMap = new HashMap<>();

                    try {
                        //Log.d("getEventTeams", response.body().string());
                        JSONArray teamsArray = new JSONArray(response.body().string());
                        for (int i = 0; i < teamsArray.length(); i++) {
                            JSONObject teamObj = teamsArray.getJSONObject(i);
                            BlueAllianceTeam team = new BlueAllianceTeam(teamObj);
                            rtnMap.put(team.getKey(), team);
                        }
                        Log.d("getEventTeams", rtnMap.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    _callback.onSuccess(rtnMap);
                }
                else {
                    _callback.onFailure(response.message());
                }
            }
        });
    }
}
