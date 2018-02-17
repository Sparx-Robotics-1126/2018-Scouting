package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static FileIO fileIO;
    private static GoogleDriveNetworking googleDrive;

    private String[] studentList;
    ArrayAdapter<String> studentArryAdapter;
    private AutoCompleteTextView studentNameAutoTextView;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        editor = settings.edit();

        fileIO = FileIO.getInstance();
        // This is done only once here in MainActivity
        fileIO.InitializeStorage(this);
        googleDrive = GoogleDriveNetworking.getInstance();

        if(isInternetConnected() && isOnline()) {
            // This is done only once here in MainActivity
            Intent tryAutoSignInIntent = googleDrive.tryAutoSignIn(this);
            // if failed auto sign then googleDrive will return an intent to try to
            // sign in by asking the user to select an account
            if(tryAutoSignInIntent != null) {
                startActivityForResult(tryAutoSignInIntent, GOOGLE_REQUEST_CODE_SIGN_IN);
            }
            else {
                String msg = "Logged into Google Drive!";
                Log.d(TAG, msg);
                Toast.makeText(this, TAG + msg, Toast.LENGTH_LONG).show();
            }
        }
        else {
            String msg = "No Internet: Remember to Connect and Upload later!";
            Log.d(TAG, msg);
            showOkayDialog(msg);
        }

        studentList = getResources().getStringArray(R.array.students);

        studentNameAutoTextView = findViewById(R.id.studentNameAutoText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentNameAutoTextView.setAdapter(adapter);
        studentNameAutoTextView.setThreshold(1);
        studentNameAutoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String studentName = studentNameAutoTextView.getText().toString();
                boolean studentNameFound = Arrays.asList(studentList).contains(studentName);
                if (studentNameFound) {
                    editor.putString(getResources().getString(R.string.pref_scouter), studentName);
                    studentNameAutoTextView.dismissDropDown();
                    Log.d(TAG, getResources().getString(R.string.pref_scouter));
                }
                else {
                    editor.putString(getResources().getString(R.string.pref_scouter), "");
                    Log.d(TAG, "");
                }

                editor.apply();
            }
        });

        loginButton = findViewById(R.id.logInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
                if (!scouterName.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, Directory.class);
                    startActivity(intent);
                }
            }
        });

        restorePreferences();
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
                        String erroMsg = "Sign-in Into Goolgle failed: Trying again later!.";
                        Log.e(TAG, erroMsg);
                        showOkayDialog(erroMsg);
                    }
                }
                else {
                    String erroMsg = "Sign-in Into Goolgle result not OK: Trying again later!.";
                    Log.e(TAG, erroMsg);
                    showOkayDialog(erroMsg);
                }
                break;
        }
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return connected;
    }

    public Boolean isOnline() {
        try {
            Toast.makeText(this, TAG + "Checking Internet conection...", Toast.LENGTH_LONG).show();
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void restorePreferences() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            studentNameAutoTextView.setText(scouterName);
            studentNameAutoTextView.dismissDropDown();
        }
    }

    private void showOkayDialog(String _msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(TAG);
        builder.setMessage(_msg);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog noInternetDialog = builder.create();
        noInternetDialog.show();
    }
}