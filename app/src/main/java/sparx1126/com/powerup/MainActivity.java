package sparx1126.com.powerup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

import sparx1126.com.powerup.utilities.DataCollection;
import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.NetworkStatus;
import sparx1126.com.powerup.utilities.Utility;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static DataCollection dataCollection;
    private static GoogleDriveNetworking googleDrive;
    private static NetworkStatus networkStatus;
    private static Utility utility;

    private String[] studentList;
    private Dialog testingInternetDialog;
    private AutoCompleteTextView studentNameAutoTextView;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        editor = settings.edit();

        dataCollection = DataCollection.getInstance();
        FileIO fileIO = FileIO.getInstance();
        // This is done only once here in MainActivity
        fileIO.InitializeStorage(this);
        googleDrive = GoogleDriveNetworking.getInstance();
        networkStatus = NetworkStatus.getInstance();
        // This is done only once here in MainActivity
        networkStatus.SetConnectivityManager((ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE));
        utility = Utility.getInstance();

        studentList = getResources().getStringArray(R.array.students);
        testingInternetDialog = utility.getNoButtonDialog(this, TAG, getResources().getString(R.string.testing_internet));

        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(2);
        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View currentFocussedView = MainActivity.this.getCurrentFocus();
                if (currentFocussedView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(currentFocussedView.getWindowToken(), 0);
                    }
                }
            }
        });

        loginButton = findViewById(R.id.logInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = studentNameAutoTextView.getText().toString();
                boolean studentNameFound = Arrays.asList(studentList).contains(studentName);
                if (studentNameFound) {
                    Log.d(TAG, studentName);
                    editor.putString(getResources().getString(R.string.pref_scouter), studentName);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, Directory.class);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG, "Student name not selected!");
                }
            }
        });

        tryConnectToGoogleDrive();
        restorePreferences();
    }

    private void tryConnectToGoogleDrive() {
        testingInternetDialog.show();

        networkStatus.isOnline(new NetworkStatus.Callback() {
            @Override
            public void handleConnected(final boolean _success) {
                // this needs to run on the ui thread because of ui components in it
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testingInternetDialog.dismiss();
                        if(_success) {
                            // This is done only once here in MainActivity
                            Intent tryAutoSignInIntent = googleDrive.tryAutoSignIn(MainActivity.this);
                            // if failed auto sign then googleDrive will return an intent to try to
                            // sign in by asking the user to select an account
                            if(tryAutoSignInIntent != null) {
                                startActivityForResult(tryAutoSignInIntent, GOOGLE_REQUEST_CODE_SIGN_IN);
                            }
                            else {
                                String msg = "Logged into Google Drive!";
                                Log.d(TAG, msg);
                                Toast.makeText(MainActivity.this, TAG + msg, Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Dialog dialog = utility.getPositiveButtonDialog(MainActivity.this, TAG,
                                    "No Internet: Remember to Connect and Upload later!",
                                    "Okay");
                            dialog.show();
                        }
                    }
                });
            }
        });
    }

    private void restorePreferences() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            Log.d(TAG, scouterName);
            studentNameAutoTextView.setText(scouterName);
            studentNameAutoTextView.dismissDropDown();
        }
        dataCollection.restore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_REQUEST_CODE_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    if(googleDrive.tryInitializeDriveClient(data, this)) {
                        String msg = "Signed Into Google!";
                        Log.d(TAG, msg);
                        Toast.makeText(this, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Dialog dialog = utility.getPositiveButtonDialog(this, TAG,
                                "Sign-in Into Google failed: Try again later!.",
                                "Okay");
                        dialog.show();
                    }
                }
                else {
                    Dialog dialog = utility.getPositiveButtonDialog(this, TAG,
                            "Sign-in Into Google result not OK: Try again later!.",
                            "Okay");
                    dialog.show();
                }
                break;
        }
    }
}