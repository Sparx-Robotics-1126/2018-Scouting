package sparx1126.com.powerup.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BlueAllianceNetworking {
    public interface Callback {
        void handleFinishDownload(String _data);
    }

    private static final String TAG = "BlueAllianceNetworking ";
    private static final String BLUE_ALLIANCE_BASE_URL = "http://www.thebluealliance.com/api/v3/";
    // header for authentication
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

    private final OkHttpClient regularHttpClient;
    private static BlueAllianceNetworking instance;
    private static FileIO fileIO;
    private static DataCollection dataCollection;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized BlueAllianceNetworking getInstance() {
        if (instance == null)
            instance = new BlueAllianceNetworking();
        return instance;
    }

    private BlueAllianceNetworking() {
        regularHttpClient = new OkHttpClient();
        fileIO = FileIO.getInstance();
        dataCollection = DataCollection.getInstance();
    }

    public void downloadEventsSparxsIsIn(Callback _callback) {
        downloadTeamEvents(SPARX_TEAM_KEY, _callback);
    }

    public void downloadTeamEvents(String _key, final Callback _callback) {
        String url_tail = (TEAM_EVENTS_URL_TAIL).replace("{team_key}", _key);
        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new AssertionError(e.getMessage() + this);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    _callback.handleFinishDownload(response.body().string());
                } else {
                    throw new AssertionError(response.message() + this);
                }
            }
        });
    }

    public void downloadEventTeams(String _key, final Callback _callback) {
        String url_tail = (EVENT_TEAMS_URL_TAIL).replace("{event_key}", _key);
        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new AssertionError(e.getMessage() + this);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    _callback.handleFinishDownload(response.body().string());
                } else {
                    throw new AssertionError(response.message() + this);
                }
            }
        });
    }

    public void downloadEventMatches(String _eventKey, final Callback _callback) {
        String url_tail = (EVENT_MATCHES_URL_TAIL).replace("{event_key}", _eventKey);

        downloadBlueAllianceData(url_tail, new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new AssertionError(e.getMessage() + this);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    _callback.handleFinishDownload(response.body().string());
                } else {
                    throw new AssertionError(response.message() + this);
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
}
