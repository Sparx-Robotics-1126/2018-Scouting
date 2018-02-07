package sparx1126.com.powerup.google_drive;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class GoogleDriveNetworking extends Activity {
    private static final String TAG = "GoogleDriveNetworking";
    private static final int REQUEST_CODE_SIGN_IN = 0;

    private DriveResourceClient googleDriveResourceClient;
    private DriveClient googleDriveClient;
    private static GoogleDriveNetworking instance;
    private static Activity parentActivity;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized GoogleDriveNetworking getInstance(Activity _activity) {
        parentActivity = _activity;
        if (instance == null)
            instance = new GoogleDriveNetworking();
        return instance;
    }

    private GoogleDriveNetworking() {
        Set<Scope> requiredScopes = new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(parentActivity);
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(signInAccount);
        } else {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(Drive.SCOPE_FILE)
                            .requestScopes(Drive.SCOPE_APPFOLDER)
                            .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(parentActivity, signInOptions);
            startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode != RESULT_OK) {
                    // Sign-in may fail or be cancelled by the user. For this sample, sign-in is
                    // required and is fatal. For apps where sign-in is optional, handle
                    // appropriately
                    Log.e(TAG, "Sign-in failed result not OK.");
                    finish();
                    return;
                }

                Task<GoogleSignInAccount> getAccountTask =
                        GoogleSignIn.getSignedInAccountFromIntent(data);
                if (getAccountTask.isSuccessful()) {
                    initializeDriveClient(getAccountTask.getResult());
                } else {
                    Log.e(TAG, "Sign-in failed.");
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        googleDriveClient = Drive.getDriveClient(parentActivity, signInAccount);
        googleDriveResourceClient = Drive.getDriveResourceClient(parentActivity, signInAccount);
    }

    private void showMessage(String message) {
        Toast.makeText(parentActivity, message, Toast.LENGTH_LONG).show();
    }

    public void uploadContentToGoogleDrive(final String _content, final String _fileName) {
        // Code here
        final Task<DriveFolder> rootFolderTask = googleDriveResourceClient.getRootFolder();
        final Task<DriveContents> createContentsTask = googleDriveResourceClient.createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();
                        OutputStream outputStream = contents.getOutputStream();
                        try (Writer writer = new OutputStreamWriter(outputStream)) {
                            writer.write(_content);
                        }

                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(_fileName)
                                .setMimeType("text/plain")
                                .setStarred(true)
                                .build();
                        return googleDriveResourceClient.createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(parentActivity,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                showMessage("Created file " + _fileName);
                                finish();
                            }
                        })
                .addOnFailureListener(parentActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        showMessage("Error while trying to create the file " + _fileName);
                        finish();
                    }
                });
    }
}
