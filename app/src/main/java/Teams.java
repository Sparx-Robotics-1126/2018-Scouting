import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Papa on 1/27/18.
 */

public class Teams {
    public Teams(JSONObject teamObj) {

        String name;
        String startdate;
        String end_date;
        String wk;
        String location;
        String key;
        try {
            name = teamObj.getString(NAME);
            location = teamObj.getString(LOCATION);
            key = teamObj.getString(KEY);
            end_date = teamObj.getString(END_DATE);
            startdate= teamObj.getString(START_DATE);
            wk= teamObj.getString(WEEK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
}
