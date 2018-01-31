package sparx1126.com.powerup.blue_alliance;

import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import sparx1126.com.powerup.utilities.JSONParser;
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
    private JSONParser jsonParser;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized BlueAllianceNetworking getInstance() {
        if (instance == null)
            instance = new BlueAllianceNetworking();
        return instance;
    }

    private BlueAllianceNetworking() {
        network = Networking.getInstance();
        jsonParser = JSONParser.getInstance();
    }

    public void downloadEventsSparxsIsIn(CallbackEvents _callback) {
        downloadTeamEvents(SPARX_TEAM_KEY, _callback);
    }

    // made this private because why do we care of other team events???
    private void downloadTeamEvents(String _key, final CallbackEvents _callback) {
        String url_tail = (TEAM_EVENTS_URL_TAIL).replace("{team_key}", _key);
        network.downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceEvent> rtnMap = jsonParser.parseTeamEvents(response.body().string());
                    _callback.onSuccess(rtnMap);
                }
                else {
                    _callback.onFailure(response.message());
                }
            }
        });
    }

    public void downloadEventTeams(String _key, final CallbackTeams _callback) {
        String url_tail = (EVENT_TEAMS_URL_TAIL).replace("{event_key}", _key);
        network.downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceTeam> rtnMap = jsonParser.parseEventTeams(response.body().string());
                    _callback.onSuccess(rtnMap);
                }
                else {
                    _callback.onFailure(response.message());
                }
            }
        });
    }
}
