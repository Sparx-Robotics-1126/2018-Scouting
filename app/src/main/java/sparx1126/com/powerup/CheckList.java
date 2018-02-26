package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;

public class CheckList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);

        DataCollection data = DataCollection.getInstance();


        Map<String, BlueAllianceTeam> teamsInEvent = data.getEventTeams();
        SparseArray<BenchmarkData> becnhmarkData = data.getBenchmarkDataMap();
        SparseArray<List<ScoutingData>> scoutingData = data.getScoutingDataMap();

        TableLayout masterTable = findViewById(R.id.masterTable);

        for (BlueAllianceTeam blueAllianceTeamData : teamsInEvent.values()) {
            int teamNumber = Integer.valueOf(blueAllianceTeamData.getNumber());
            TableRow child = new TableRow(this);

            TextView teamNumberText = new TextView(this);
            teamNumberText.setText(blueAllianceTeamData.getNumber());
            child.addView(teamNumberText);

            TextView benchmark = new TextView(this);
            String benchmarkStr = "No";
            if(becnhmarkData.get(teamNumber) != null) {
                benchmarkStr = "Yes";
            }
            benchmark.setText(benchmarkStr);
            child.addView(benchmark);

            TextView scouting = new TextView(this);
            String scoutingStr = "0";
            if(scoutingData.get(teamNumber) != null) {
                int howMany = scoutingData.get(teamNumber).size();
                scoutingStr = String.valueOf(howMany);
            }
            scouting.setText(scoutingStr);
            child.addView(scouting);

            masterTable.addView(child);
        }
    }
}

