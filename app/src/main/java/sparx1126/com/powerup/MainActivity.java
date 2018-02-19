package sparx1126.com.powerup;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Map;

import sparx1126.com.powerup.data_components.BlueAllianceMatch;
import sparx1126.com.powerup.utilities.BlueAllianceNetworking;
import sparx1126.com.powerup.data_components.BlueAllianceEvent;
import sparx1126.com.powerup.utilities.DataCollection;
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
    private static DataCollection dataCollection;
    private AutoCompleteTextView studentNameAutoTextView;
    private SharedPreferences settings;
    private RelativeLayout wholeScreen;
    float x1;
    float x2;
    float y1;
    float y2;

    private Button toBenchmarking;
    private Button toScouting;
    private Button toView;
    private Button toChecklist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wholeScreen = findViewById(R.id.wholeView);

        toScouting = findViewById(R.id.toScouting);
        toBenchmarking = findViewById(R.id.toBenchmarking);
        toView = findViewById(R.id.toView);
        toChecklist = findViewById(R.id.toChecklist);




//        //all of this is highlighted but it works so android studio can stick that warning where the sun don't shine
//        //https://stackoverflow.com/questions/35787430/detecting-swipes-click-hold-on-one-view-android
//        wholeScreen.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        x1 = event.getX();
//                        y1 = event.getY();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        x2 = event.getX();
//                        y2 = event.getY();
//                        boolean isVertSwipe = isVertSwipe(x1, x2, y1, y2);
//
//                        if (x1 > x2 && !isVertSwipe) {
//                            //https://stackoverflow.com/questions/5641103/how-to-use-toast-when-i-cant-use-this-as-context
//                            //left swipe
//                            if(isStudentName()) {
//                                Intent intent = new Intent(MainActivity.this, Benchmarking.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                            }
//
//                        } else if (x2 > x1 && !isVertSwipe) {
//                            //right swipe
//                            if(isStudentName()) {
//                                Intent intent = new Intent(MainActivity.this, Scouting.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                            }
//
//                        } else if(y2 > y1 && isVertSwipe) {
//                            //down swipe
//                            if(isStudentName()) {
//                                Intent intent = new Intent(MainActivity.this, CheckList.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
//                            }
//                        } else if(y2 < y1 && isVertSwipe) {
//                            //up swipe
//                            if(isStudentName()) {
//                                Intent intent = new Intent(MainActivity.this, View.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
//                            }
//                        }
//
//
//                        return true;
//                }
//
//                return false;
//            }
//        });


        toScouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudentName()) {
                    Intent intent = new Intent(MainActivity.this, Scouting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });


        toBenchmarking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudentName()) {
                    Intent intent = new Intent(MainActivity.this, Benchmarking.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });


        toView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudentName()) {
                    Intent intent = new Intent(MainActivity.this, View.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                }
            }
        });

        //Goes to checlkist screen
        toChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudentName()) {
                    Intent intent = new Intent(MainActivity.this, CheckList.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                }
            }
        });



        logger = Logger.getInstance();
        blueAlliance = BlueAllianceNetworking.getInstance();
        fileIO = FileIO.getInstance(this);
        googleDrive = GoogleDriveNetworking.getInstance();
        dataCollection = DataCollection.getInstance();
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
                dataCollection.setEventsWeAreIn(_result);
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

    public boolean isStudentName() {
        String studentName = studentNameAutoTextView.getText().toString();
                for (String student : studentList) {
                    if (student.equals(studentName)) {
                        return true;
                    }
                }
                return false;
    }

    public boolean isVertSwipe(float x1, float x2, float y1, float y2) {
        float xDifference = Math.abs(x1-x2);
        float yDifference = Math.abs(y1-y2);

        return yDifference > xDifference;
    }
}