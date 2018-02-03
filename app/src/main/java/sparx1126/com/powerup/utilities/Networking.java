package sparx1126.com.powerup.utilities;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Networking {
    private static final String BLUE_ALLIANCE_BASE_URL ="http://www.thebluealliance.com/api/v3/";
    // header for authetication
    private static final String BLUE_ALLIANCE_AUTH_HEADER = "X-TBA-Auth-Key";
    // Key generated in thebluealliance.com for access
    // This key is Hiram's key (expires in 30 days)
    private static final String BLUE_ALLIANCE_KEY = "0i1rgva3Y8G14rZS4dWDHcPNaw6EVMb9uSI9jW7diochnHpH8Y4nIhT0iHwj0hCq";

    private OkHttpClient client;
    private static Networking instance;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized Networking getInstance() {
        if (instance == null)
            instance = new Networking();
        return instance;
    }

    private Networking() {
        client = new OkHttpClient();
    }

    public void getBlueAllianceData(String _url_tail, okhttp3.Callback _callback) {
        String url = BLUE_ALLIANCE_BASE_URL + _url_tail;
        Log.e("URL: ", url);
        Request requestEvents = new Request.Builder()
                .addHeader(BLUE_ALLIANCE_AUTH_HEADER, BLUE_ALLIANCE_KEY)
                .url(url)
                .build();

        client.newCall(requestEvents).enqueue(_callback);
    }
}
