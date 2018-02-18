package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import sparx1126.com.powerup.utilities.FileIO;
import sparx1126.com.powerup.utilities.GoogleDriveNetworking;
import sparx1126.com.powerup.utilities.NetworkStatus;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private static final int GOOGLE_REQUEST_CODE_SIGN_IN = 0;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static GoogleDriveNetworking googleDrive;
    private static NetworkStatus networkStatus;

    private String[] studentList;
    private AutoCompleteTextView studentNameAutoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        editor = settings.edit();

        FileIO fileIO = FileIO.getInstance();
        // This is done only once here in MainActivity
        fileIO.InitializeStorage(this);
        googleDrive = GoogleDriveNetworking.getInstance();
        networkStatus = NetworkStatus.getInstance();
        // This is done only once here in MainActivity
        networkStatus.SetConnectivityManager((ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE));

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
                    Log.d(TAG, getResources().getString(R.string.pref_scouter));
                    dismissKeyboard();
                }
                else {
                    editor.putString(getResources().getString(R.string.pref_scouter), "");
                    Log.d(TAG, "");
                }

                editor.apply();
            }
        });

        Button loginButton = findViewById(R.id.logInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
                if (!scouterName.isEmpty()) {
                    boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
                    if(isTableConfigured) {
                        Intent intent = new Intent(MainActivity.this, Directory.class);
                        startActivity(intent);
                    }
                    else {
                        String[] adminList = getResources().getStringArray(R.array.admins);
                        boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);

                        if(adminNameFound) {
                            Intent intent = new Intent(MainActivity.this, Admin.class);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            builder.setTitle(TAG);
                            builder.setMessage("Have an Admin Setup Tablet");
                            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                }
                            });

                            Dialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }
            }
        });
        tryConnectToGoogleDrive();
        restorePreferences();
    }

    private void tryConnectToGoogleDrive() {
        if(networkStatus.isInternetConnected() && networkStatus.isOnline()) {
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
    }

    private void restorePreferences() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            studentNameAutoTextView.setText(scouterName);
            studentNameAutoTextView.dismissDropDown();
            dismissKeyboard();
        }
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
                        String errorMsg = "Sign-in Into Google failed: Trying again later!.";
                        Log.e(TAG, errorMsg);
                        showOkayDialog(errorMsg);
                    }
                }
                else {
                    String errorMsg = "Sign-in Into Google result not OK: Trying again later!.";
                    Log.e(TAG, errorMsg);
                    showOkayDialog(errorMsg);
                }
                break;
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

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dismissKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}