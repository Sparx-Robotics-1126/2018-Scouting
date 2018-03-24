package sparx1126.com.powerup;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

import sparx1126.com.powerup.data_components.BenchmarkData;
import sparx1126.com.powerup.data_components.BlueAllianceTeam;
import sparx1126.com.powerup.data_components.ScoutingData;
import sparx1126.com.powerup.utilities.DataCollection;

public class CheckList extends AppCompatActivity {
    private static final String TAG = "CheckList ";
    public static final float HEADER_TEXT_SIZE = 20;
    public static final float TEXT_SIZE = 16;
    private static DataCollection dataCollection;

    private TableLayout masterTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);

        dataCollection = DataCollection.getInstance();

        Map<String, BlueAllianceTeam> teamsInEvent = dataCollection.getEventTeams();
        Map<Integer, BenchmarkData> becnhmarkData = dataCollection.getBenchmarkDataMap();
        Map<Integer, Map<Integer, ScoutingData>> scoutingData = dataCollection.getScoutingDataMap();
        Map<Integer, Integer> teamNumberOfPhotos = dataCollection.getTeamNumberOfPhotos();

        masterTable = findViewById(R.id.masterTable);
        Drawable cellBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.check_list_cell, null);

        int index = 0;
        TableRow heading = new TableRow(this);

        TextView teamNumberHeading = new TextView(this);
        teamNumberHeading.setText("TEAM");
        teamNumberHeading.setTextColor(Color.BLACK);
        teamNumberHeading.setTextSize(HEADER_TEXT_SIZE);
        teamNumberHeading.setBackground(cellBackground);
        teamNumberHeading.setTypeface(null, Typeface.BOLD);
        heading.addView(teamNumberHeading);

        TextView benchmarkHeading = new TextView(this);
        benchmarkHeading.setText("BENCHMARKED");
        benchmarkHeading.setTextColor(Color.BLACK);
        benchmarkHeading.setTextSize(HEADER_TEXT_SIZE);
        benchmarkHeading.setBackground(cellBackground);
        benchmarkHeading.setTypeface(null, Typeface.BOLD);
        heading.addView(benchmarkHeading);

        TextView scoutingHeading = new TextView(this);
        scoutingHeading.setText("SCOUTED");
        scoutingHeading.setTextColor(Color.BLACK);
        scoutingHeading.setTextSize(HEADER_TEXT_SIZE);
        scoutingHeading.setBackground(cellBackground);
        scoutingHeading.setTypeface(null, Typeface.BOLD);
        heading.addView(scoutingHeading);

        TextView photosHeading = new TextView(this);
        photosHeading.setText("PHOTOS");
        photosHeading.setTextColor(Color.BLACK);
        photosHeading.setTextSize(HEADER_TEXT_SIZE);
        photosHeading.setBackground(cellBackground);
        photosHeading.setTypeface(null, Typeface.BOLD);
        heading.addView(photosHeading);

        masterTable.addView(heading, index);
        index++;


        for (BlueAllianceTeam blueAllianceTeamData : teamsInEvent.values()) {
            int teamNumber = Integer.valueOf(blueAllianceTeamData.getNumber());
            TableRow child = new TableRow(this);

            TextView teamNumberText = new TextView(this);
            teamNumberText.setText(blueAllianceTeamData.getNumber());
            teamNumberText.setTextColor(Color.BLACK);
            teamNumberText.setTextSize(TEXT_SIZE);
            teamNumberText.setBackground(cellBackground);
            child.addView(teamNumberText);

            TextView benchmark = new TextView(this);
            String benchmarkStr = "No";
            if(becnhmarkData.get(teamNumber) != null) {
                benchmarkStr = "Yes";
                benchmark.setTextColor(Color.GREEN);
            }
            else {
                benchmark.setTextColor(Color.BLACK);
            }
            benchmark.setText(benchmarkStr);
            benchmark.setTextSize(TEXT_SIZE);
            benchmark.setBackground(cellBackground);
            child.addView(benchmark);

            TextView scouting = new TextView(this);
            String scoutingStr = "0";
            if(scoutingData.get(teamNumber) != null) {
                int howMany = scoutingData.get(teamNumber).size();
                scoutingStr = String.valueOf(howMany);
                if(howMany > 0) {
                    scouting.setTextColor(Color.GREEN);
                } else {
                    scouting.setTextColor(Color.BLACK);
                }
            }
            scouting.setText(scoutingStr);
            scouting.setTextSize(TEXT_SIZE);
            scouting.setBackground(cellBackground);
            child.addView(scouting);
//for photos//
            TextView photos = new TextView(this);
            String photoStr = "0";
            if(teamNumberOfPhotos.get(teamNumber) != null) {
                int howMany = teamNumberOfPhotos.get(teamNumber);
                photoStr = String.valueOf(howMany);
                if(howMany > 0) {
                    photos.setTextColor(Color.GREEN);
                } else {
                    scouting.setTextColor(Color.BLACK);
                }
            }
            photos.setText(photoStr);
            photos.setTextSize(TEXT_SIZE);
            photos.setBackground(cellBackground);
            child.addView(photos);


            masterTable.addView(child, index);
            index++;
        }
    }
}

