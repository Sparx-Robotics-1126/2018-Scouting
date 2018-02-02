package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;

import java.util.Map;

import sparx1126.com.powerup.blue_alliance.BlueAllianceNetworking;
import sparx1126.com.powerup.blue_alliance.BlueAllianceEvent;
import sparx1126.com.powerup.utilities.FileIO;

public class MainActivity extends AppCompatActivity {
    private static final String[] studentList = {"Felix", "Huang"};
    private static BlueAllianceNetworking blueAlliance;
    private static FileIO fileIO;
    private AutoCompleteTextView studentNameAutoTextView;

    //google
    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQUEST_CODE_SIGN_IN = 0;
    //AIzaSyD8qCUS1ZGsFI_tMcKOWwh4V6EMJWeROz8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueAlliance = BlueAllianceNetworking.getInstance();
        fileIO = FileIO.getInstance(this);

        // student selection
        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(1);
        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedStudent = (String) parent.getItemAtPosition(pos);
                Log.d("onItemClick", selectedStudent);
            }
        });

        blueAlliance.downloadEventsSparxsIsIn(new BlueAllianceNetworking.CallbackEvents() {
            @Override
            public void onFailure(String _reason) {
                Log.e("dEventsSparxsIsIn", _reason);
            }
            @Override
            public void onSuccess(Map<String, BlueAllianceEvent> _result) {
                fileIO.storeTeamEvents(_result);
                //Map<String, BlueAllianceEvent> rtnMap = fileIO.fetchTeamEvents();
                //Log.d("dEventsSparxsIsIn", rtnMap.toString());
                mGoogleSignInClient = buildGoogleSignInClient();
                startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
            }
        });
    }

    /** Build a Google SignIn client. */
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }
}