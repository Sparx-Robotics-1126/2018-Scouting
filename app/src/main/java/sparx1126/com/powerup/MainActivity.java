package sparx1126.com.powerup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.utilities.BlueAllianceNetworking;
import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.Logger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private String[] studentList;
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private static Logger logger;
    private static BlueAllianceNetworking blueAlliance;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;
    private Button loginButton;
    private AutoCompleteTextView studentNameAutoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger = Logger.getInstance();
        blueAlliance = BlueAllianceNetworking.getInstance();
        fileIO = FileIO.getInstance(this);
        googleDrive = GoogleDriveNetworking.getInstance();

        // if failed auto sign then googleDrive will return an internt to try to
        // sign in by asking the user to select an account
        // This is done only once here in MainActivity
        Intent tryAutoSignInIntent = googleDrive.tryAutoSignIn(this);
        if(tryAutoSignInIntent != null) {
            startActivityForResult(tryAutoSignInIntent, GOOGLE_REQUEST_CODE_SIGN_IN);
        }
        else {
            logger.Log(TAG, "Logged into Google Drive!", Logger.MSG_TYPE.NORMAL, this);
        }
loginButton = findViewById(R.id.logInButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = studentNameAutoTextView.getText().toString();
                        for(String student: studentList){
                            if(student.equals(studentName)){
                                Intent intent = new Intent(MainActivity.this, Scouting.class);
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
//        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
//                String selectedStudent = (String) parent.getItemAtPosition(pos);
//            }
//        });

        blueAlliance.downloadEventsSparxsIsIn(new BlueAllianceNetworking.CallbackEvents() {
            @Override
            public void onFailure(String _msg) {
                logger.Log(TAG, _msg, Logger.MSG_TYPE.ERROR, null);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceEvent> _result) {
                logger.Log(TAG, "Got Events!", Logger.MSG_TYPE.NORMAL, null);
                fileIO.storeTeamEvents(_result);
            }
        }, this);

        blueAlliance.downloadEventMatches("2018ohcl",new BlueAllianceNetworking.CallbackMatches() {
            @Override
            public void onFailure(String _msg) {
                logger.Log(TAG, _msg, Logger.MSG_TYPE.ERROR, null);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceMatch> _result) {
                logger.Log(TAG, "Got Matches!", Logger.MSG_TYPE.NORMAL, null);
                _result.toString();
            }
        }, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_REQUEST_CODE_SIGN_IN:
                String msg = "Signed In!";
                if (resultCode != RESULT_OK) {
                    logger.Log(TAG, "Sign-in failed result not OK.", Logger.MSG_TYPE.ERROR, this);
                    finish();
                }
                else {
                    if(!googleDrive.tryInitializeDriveClient(data, this)) {
                        logger.Log(TAG, "Sign-in failed.", Logger.MSG_TYPE.ERROR, this);
                        finish();
                    }
                }
                break;
        }
    }
}