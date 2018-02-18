package sparx1126.com.powerup.utilities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
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

    public Boolean isOnline() {
        boolean reachable = false;

        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            reachable = (returnVal==0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reachable;
    }
}
