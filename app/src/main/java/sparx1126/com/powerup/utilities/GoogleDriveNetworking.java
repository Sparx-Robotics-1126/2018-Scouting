package sparx1126.com.powerup.utilities;

import android.app.Activity;
import android.content.Context;
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

public class GoogleDriveNetworking {
    private static final String TAG = "GoogleDriveNetworking ";

    private DriveResourceClient googleDriveResourceClient;
    private static GoogleDriveNetworking instance;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized GoogleDriveNetworking getInstance() {
        if (instance == null)
            instance = new GoogleDriveNetworking();
        return instance;
    }

    private GoogleDriveNetworking() {
        googleDriveResourceClient = null;
    }

    // To be called once by MainActivity
    public Intent tryAutoSignIn(Context _context) {
        Intent rtn = null;
        Set<Scope> requiredScopes = new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(_context);
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(signInAccount, _context);
        }
        else {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(Drive.SCOPE_FILE)
                    .requestScopes(Drive.SCOPE_APPFOLDER)
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(_context, signInOptions);
            rtn = googleSignInClient.getSignInIntent();
        }
        return rtn;
    }

    public boolean tryInitializeDriveClient(Intent _data, Context _context) {
        Task<GoogleSignInAccount> accountTask =
                GoogleSignIn.getSignedInAccountFromIntent(_data);
        if (accountTask.isSuccessful()) {
            initializeDriveClient(accountTask.getResult(), _context);
            return true;
        }
        return false;
    }

    private void initializeDriveClient(GoogleSignInAccount signInAccount, Context _context) {
        googleDriveResourceClient = Drive.getDriveResourceClient(_context, signInAccount);
    }

    public void uploadContentToGoogleDrive(final String _content, final String _fileName, final Context _context) {
        if(googleDriveResourceClient != null) {
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
                    .addOnSuccessListener((Activity) _context,
                            new OnSuccessListener<DriveFile>() {
                                @Override
                                public void onSuccess(DriveFile driveFile) {
                                    showMessage("Created file " + _fileName, _context);
                                    //finish();
                                }
                            })
                    .addOnFailureListener((Activity) _context, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Unable to create file", e);
                            showMessage("Error while trying to create the file " + _fileName, _context);
                            //finish();
                        }
                    });
        }
        else {
            showMessage("Not Connected to Google Drive", _context);
        }
    }

    private void showMessage(String message, Context _context) {
        Toast.makeText(_context, TAG + message, Toast.LENGTH_LONG).show();
    }
}
