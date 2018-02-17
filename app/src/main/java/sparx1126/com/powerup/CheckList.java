package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.utilities.DataCollection;

/**
 * Created by Hiram on 2/14/2018.
 */


public class CheckList extends AppCompatActivity {

    private TableLayout masterTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);

        DataCollection data = DataCollection.getInstance();
        Map scoutingData = data.getScoutingDataMap();
        Map<String, BlueAllianceTeam> teamsInEvent = data.getTeamsInEventMap();
        masterTable = findViewById(R.id.masterTable);
    if(teamsInEvent != null) {
        for (String key : teamsInEvent.keySet()) {
            TableRow child = new TableRow(this);
            TextView teamNumber = new TextView(this);
            teamNumber.setText(key.substring(3));
            teamNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            child.addView(teamNumber);
            masterTable.addView(child);
        }
    }

    }


}

