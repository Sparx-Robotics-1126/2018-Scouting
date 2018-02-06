package sparx1126.com.powerup.custom_layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import sparx1126.com.powerup.data_components.ScoutingData;

public class AutoScoutingDataView {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String> > expandableListDetail = new HashMap<>();
        ScoutingData tempdata = new ScoutingData();
        tempdata.setAutolinecheck(true);
        List<String> auto = new ArrayList<>();

        auto.add("<font color=\"black\"><b>AUTO</b></font>");
        auto.add("<font color=\"black\"><b></b></font>");

        auto.add("<font color=\"black\"><b>Auto Line Crossed:  </b></font>" + tempdata.isAutolinecheck()+ "%");

        expandableListDetail.put("Auto", auto);
        return expandableListDetail;
    }

}