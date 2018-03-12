package sparx1126.com.powerup.utilities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
    public interface NetworkCallback {
        void handleConnected(boolean _success);
    }
    private static final String TAG = "NetworkStatus ";

    private static NetworkStatus instance;
    private ConnectivityManager cm;

    public static synchronized NetworkStatus getInstance(){
        if(instance == null ) {
            instance = new NetworkStatus();
        }
        return instance;
    }

    private NetworkStatus(){
    }

    // To be called once by MainActivity
    public void SetConnectivityManager(ConnectivityManager _cm){
        cm = _cm;
    }

    public boolean isInternetConnected() {
        if(cm == null) throw new AssertionError("No Connectivity Manager!" + this);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return ((activeNetwork != null) && activeNetwork.isConnectedOrConnecting());
    }

    public void isOnline(final NetworkCallback _connected) {

        if(isInternetConnected()) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
                        int returnVal = p1.waitFor();
                        _connected.handleConnected(returnVal==0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            t.start();
        }
        else {
            _connected.handleConnected(false);
        }
    }
}
