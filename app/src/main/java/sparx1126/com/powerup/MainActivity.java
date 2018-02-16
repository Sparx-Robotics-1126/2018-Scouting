package sparx1126.com.powerup;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import sparx1126.com.powerup.utilities.BlueAllianceNetworking;
import sparx1126.com.powerup.utilities.DataCollection;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.FileIO;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private SharedPreferences settings;
    private String[] studentList;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;
    private Button loginButton;
    private AutoCompleteTextView studentNameAutoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        fileIO = FileIO.getInstance();
        // This is done only once here in MainActivity
        fileIO.InitializeStorage(this);
        googleDrive = GoogleDriveNetworking.getInstance();
        // if failed auto sign then googleDrive will return an intent to try to
        // sign in by asking the user to select an account
        // This is done only once here in MainActivity
        Intent tryAutoSignInIntent = googleDrive.tryAutoSignIn(this);
        if(tryAutoSignInIntent != null) {
            startActivityForResult(tryAutoSignInIntent, GOOGLE_REQUEST_CODE_SIGN_IN);
        }
        else {
            String msg = "Logged into Google Drive!";
            Log.d(TAG, msg);
            Toast.makeText(this, TAG + msg, Toast.LENGTH_LONG).show();
        }


        loginButton = findViewById(R.id.logInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = studentNameAutoTextView.getText().toString();
                for (String student : studentList) {
                    if (student.equals(studentName)) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(getResources().getString(R.string.pref_scouter), studentName);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, Directory.class);
                        startActivity(intent);
                    }
                }
            }
        });

        // student selection
        studentList = getResources().getStringArray(R.array.students);
        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(1);

        restorePreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(settings.getInt(getResources().getString(R.string.team_selected), Integer.MAX_VALUE) == Integer.MAX_VALUE){
            Intent intent = new Intent(MainActivity.this, Admin.class);
            startActivity(intent);
        }
        else {
            setupTeamList();
            setupMatchMap();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_REQUEST_CODE_SIGN_IN:
                String msg = "Signed In!";
                if (resultCode != RESULT_OK) {
                    String erroMsg = "Sign-in failed result not OK.";
                    Log.d(TAG, erroMsg);
                    Toast.makeText(this, TAG + erroMsg, Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    if(!googleDrive.tryInitializeDriveClient(data, this)) {
                        String erroMsg = "Sign-in failed.";
                        Log.d(TAG, erroMsg);
                        Toast.makeText(this, TAG + erroMsg, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                break;
        }
    }

    private void restorePreferences() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            studentNameAutoTextView.setText(scouterName);
            studentNameAutoTextView.dismissDropDown();
        }

        List<BenchmarkingData> benchmarkingDatas = dbHelper.getAllBenchmarkingData();
        for(BenchmarkingData benchmarkingData : benchmarkingDatas) {
            TeamData.setTeamData(benchmarkingData.getTeamNumber(), benchmarkingData.getEventName());
            TeamData.getCurrentTeam().setBenchmarkingData(benchmarkingData);
        }

        List<ScoutingData> scoutingDatas = dbHelper.getAllScoutingDatas();
        for(ScoutingData scoutingData : scoutingDatas) {
            TeamData.setTeamData(scoutingData.getTeamNumber(), scoutingData.getEventName());
            TeamData.getCurrentTeam().addScoutingData(scoutingData);
        }
    }
}