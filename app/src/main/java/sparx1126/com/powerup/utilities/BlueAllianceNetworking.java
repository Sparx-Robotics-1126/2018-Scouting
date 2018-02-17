package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;

public class BlueAllianceNetworking {
    public interface CallbackEvents {
        void onFailure(String _reason);
        void onSuccess(Map<String, BlueAllianceEvent> _result);
    }

    public interface CallbackTeams {
        void onFailure(String _reason);
        void onSuccess(Map<String, BlueAllianceTeam> _result);
    }
    public interface CallbackMatches {
        void onFailure(String _reason);
        void onSuccess(Map<String, BlueAllianceMatch> _result);
    }

    private static final String TAG = "BlueAllianceNetworking ";
    private static final String BLUE_ALLIANCE_BASE_URL ="http://www.thebluealliance.com/api/v3/";
    // header for authetication
    private static final String BLUE_ALLIANCE_AUTH_HEADER = "X-TBA-Auth-Key";
    // Key generated in thebluealliance.com for access
    // This key is Hiram's key (expires in 30 days)
    private static final String BLUE_ALLIANCE_KEY = "0i1rgva3Y8G14rZS4dWDHcPNaw6EVMb9uSI9jW7diochnHpH8Y4nIhT0iHwj0hCq";
    private static final String YEAR = "2017";
    private static final String SPARX_TEAM_KEY = "frc1126";
    // intention is for {event_key} to be substituted
    private static String EVENT_TEAMS_URL_TAIL = "event/{event_key}/teams";
    // intention is for {team_key} to be substituted
    private static String TEAM_EVENTS_URL_TAIL = "team/{team_key}/events/" + YEAR;
     private static String EVENT_MATCHES_URL_TAIL = "event/{event_key}/matches/simple";

    private OkHttpClient regularHttpClient;
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
        regularHttpClient = new OkHttpClient();
        jsonParser = JSONParser.getInstance();
    }

    public void downloadEventsSparxsIsIn(CallbackEvents _callback, Context _context) {
        downloadTeamEvents(SPARX_TEAM_KEY, _callback, _context);
    }

    // made this private because why do we care of other team events???
    private void downloadTeamEvents(String _key, final CallbackEvents _callback, final Context _context) {
        String url_tail = (TEAM_EVENTS_URL_TAIL).replace("{team_key}", _key);
        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMessage(e.getMessage(), _context);
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceEvent> rtnMap = jsonParser.teamEventsStringIntoMap(response.body().string());
                    _callback.onSuccess(rtnMap);
                }
                else {
                    showMessage(response.message(), _context);
                    _callback.onFailure(response.message());
                }
            }
        });
    }

    public void downloadEventTeams(String _key, final CallbackTeams _callback, final Context _context) {
        String url_tail = (EVENT_TEAMS_URL_TAIL).replace("{event_key}", _key);
        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMessage(e.getMessage(), _context);
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, BlueAllianceTeam> rtnMap = jsonParser.eventTeamsStringIntoMap(response.body().string());
                    _callback.onSuccess(rtnMap);
                }
                else {
                    showMessage(response.message(), _context);
                    _callback.onFailure(response.message());
                }
            }
        });
    }

    public void downloadEventMatches(String _eventKey, final CallbackMatches _callback, final Context _context) {
        String url_tail = (EVENT_MATCHES_URL_TAIL).replace("{event_key}", _eventKey);

        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMessage(e.getMessage(), _context);
                _callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //Log.e("JT Mongeon", response.body().string());
                    Map<String, BlueAllianceMatch> rtnMap = jsonParser.eventMatchesStringIntoMap(response.body().string());
                    _callback.onSuccess(rtnMap);
                }
                else {
                    showMessage(response.message(), _context);
                    _callback.onFailure(response.message());
                }
            }
        });
    }

    private void downloadBlueAllianceData(String _url_tail, okhttp3.Callback _callback) {
        String url = BLUE_ALLIANCE_BASE_URL + _url_tail;
        Log.d(TAG, url);
        Request requestEvents = new Request.Builder()
                .addHeader(BLUE_ALLIANCE_AUTH_HEADER, BLUE_ALLIANCE_KEY)
                .url(url)
                .build();

        regularHttpClient.newCall(requestEvents).enqueue(_callback);
    }

    private void showMessage(String message, Context _context) {
        Toast.makeText(_context, TAG + message, Toast.LENGTH_LONG).show();
    }
}
