package sparx1126.com.powerup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import java.util.Map;

import sparx1126.com.powerup.blue_alliance.BlueAllianceNetworking;
import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;
import sparx1126.com.powerup.google_drive.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.FileIO;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String[] studentList = {"Felix", "Huang"};
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private static BlueAllianceNetworking blueAlliance;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;
    private AutoCompleteTextView studentNameAutoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueAlliance = BlueAllianceNetworking.getInstance();
        fileIO = FileIO.getInstance(this);
        googleDrive = GoogleDriveNetworking.getInstance(this);
        // if failed auto sign in then ask user to select account
        // This is done only once here in MainActivity
        if(!googleDrive.autoSignIn()) {
            startActivityForResult(googleDrive.getSignInIntent(), GOOGLE_REQUEST_CODE_SIGN_IN);
        }

        // student selection
        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(1);
        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedStudent = (String) parent.getItemAtPosition(pos);
            }
        });

        blueAlliance.downloadEventsSparxsIsIn(new BlueAllianceNetworking.CallbackEvents() {
            @Override
            public void onFailure(String _reason) {
                Log.e(TAG, _reason);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceEvent> _result) {
                fileIO.storeTeamEvents(_result);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_REQUEST_CODE_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    Log.e(TAG, "Sign-in failed result not OK.");
                    finish();
                }
                else {
                    if(!googleDrive.tryInitializeDriveClient(data)) {
                        Log.e(TAG, "Sign-in failed.");
                        finish();
                    }
                }
                break;
        }
    }
}