package sparx1126.com.powerup.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logger {
    private static Logger instance;
    public enum MSG_TYPE {
        NORMAL, WARNING, ERROR;
    }

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    private Logger() {
    }

    public void Log(String _tag, String _msg, MSG_TYPE _msgType, Context _context) {
        switch(_msgType) {
            case NORMAL:
                Log.d(_tag, _msg);
                break;
            case WARNING:
                Log.w(_tag, _msg);
                break;
            case ERROR:
                Log.e(_tag, _msg);
                break;
        }

        if(_context != null) {
            Toast.makeText(_context, _tag + _msg, Toast.LENGTH_LONG).show();
        }
    }
}
