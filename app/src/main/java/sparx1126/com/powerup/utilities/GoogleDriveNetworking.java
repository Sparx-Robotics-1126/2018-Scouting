package sparx1126.com.powerup.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

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
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sparx1126.com.powerup.Benchmarking;

public class GoogleDriveNetworking {
    public interface GoogleCompletedCallback {
        void handleOnSuccess();
        void handleOnFailure(String _reason);
    }
    public interface GoogleContentCallback {
        void handleOnSuccess(String _fileName, String _content);
        void handleOnFailure(String _reason);
    }
    // some code came from:
    // https://github.com/googledrive/android-demos/tree/master/app/src/main/java/com/google/android/gms/drive/sample/demo
    private static final String TAG = "GoogleDriveNetworking ";

    private DriveResourceClient driveResourceClient;
    private DriveClient driveClient;
    private static GoogleDriveNetworking instance;
    private Map<String, Metadata> metadataMap;
    private Map<String, String> contentMap;
    private int metaDatasToProcess;
    private String failedReason;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized GoogleDriveNetworking getInstance() {
        if (instance == null)
            instance = new GoogleDriveNetworking();
        return instance;
    }

    private GoogleDriveNetworking() {
        driveResourceClient = null;
        metadataMap = new HashMap<>();
        contentMap = new HashMap<>();
        metaDatasToProcess = 0;
        failedReason = "";
    }

    // To be called once by MainActivity
    public Intent tryAutoSignIn(Context _context) {
        Intent rtn = null;
        Set<Scope> requiredScopes = new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(_context);
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(_context, signInAccount);
        } else {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(Drive.SCOPE_FILE)
                    .requestScopes(Drive.SCOPE_APPFOLDER)
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(_context, signInOptions);
            rtn = googleSignInClient.getSignInIntent();
        }
        return rtn;
    }

    public boolean tryInitializeDriveClient(Context _context, Intent _data) {
        Task<GoogleSignInAccount> accountTask =
                GoogleSignIn.getSignedInAccountFromIntent(_data);
        if (accountTask.isSuccessful()) {
            initializeDriveClient(_context, accountTask.getResult());
            return true;
        }
        return false;
    }

    private void initializeDriveClient(Context _context, GoogleSignInAccount signInAccount) {
        driveResourceClient = Drive.getDriveResourceClient(_context, signInAccount);
        driveClient = Drive.getDriveClient(_context, signInAccount);
    }

    public void uploadContentToGoogleDrive(final Context _context, final Map<String, String> _stringData,
                                           final Map<String, File> _photoData, final GoogleCompletedCallback _callback) {
        if (driveResourceClient != null) {
            syncDrive(_context, 10, new GoogleCompletedCallback() {
                @Override
                public void handleOnSuccess() {
                    downloadMetadatas(_context, 10, new GoogleCompletedCallback() {
                        @Override
                        public void handleOnSuccess() {
                            metaDatasToProcess = _stringData.size() + _photoData.size();
                            failedReason = "";
                            if(metaDatasToProcess == 0) {
                                Log.d(TAG, "Nothing to do, no data in tablet!");
                                _callback.handleOnSuccess();
                            } else {
                                for (Map.Entry<String, String> entry : _stringData.entrySet()) {
                                    if (metadataMap.containsKey(entry.getKey())) {
                                        Metadata metadata = metadataMap.get(entry.getKey());
                                        updateStringData(_context, entry.getKey(), metadata, entry.getValue(),
                                                new GoogleCompletedCallback() {
                                                    @Override
                                                    public void handleOnSuccess() {
                                                        metaDatasToProcess--;
                                                        testIsFinishedWithOperation(_callback);
                                                    }

                                                    @Override
                                                    public void handleOnFailure(String _reason) {
                                                        metaDatasToProcess--;
                                                        failedReason = failedReason + " " + _reason;
                                                        testIsFinishedWithOperation(_callback);
                                                    }
                                                });
                                    } else {
                                        createStringData(_context, entry.getKey(), entry.getValue(),
                                                new GoogleCompletedCallback() {
                                                    @Override
                                                    public void handleOnSuccess() {
                                                        metaDatasToProcess--;
                                                        testIsFinishedWithOperation(_callback);
                                                    }

                                                    @Override
                                                    public void handleOnFailure(String _reason) {
                                                        metaDatasToProcess--;
                                                        failedReason = failedReason + " " + _reason;
                                                        testIsFinishedWithOperation(_callback);
                                                    }
                                                });
                                    }
                                }

                                for (Map.Entry<String, File> entry : _photoData.entrySet()) {
                                    if (!metadataMap.containsKey(entry.getKey())) {
                                        createFileData(_context, entry.getKey(), entry.getValue(),
                                                new GoogleCompletedCallback() {
                                                    @Override
                                                    public void handleOnSuccess() {
                                                        metaDatasToProcess--;
                                                        testIsFinishedWithOperation(_callback);
                                                    }

                                                    @Override
                                                    public void handleOnFailure(String _reason) {
                                                        metaDatasToProcess--;
                                                        failedReason = failedReason + " " + _reason;
                                                        testIsFinishedWithOperation(_callback);
                                                    }
                                                });
                                    } else {
                                        metaDatasToProcess--;
                                        testIsFinishedWithOperation(_callback);
                                    }
                                }
                            }
                        }

                        @Override
                        public void handleOnFailure(String _reason) {
                            _callback.handleOnFailure(_reason);
                        }
                    });
                }

                @Override
                public void handleOnFailure(String _reason) {
                    _callback.handleOnFailure(_reason);
                }
            });
        } else {
            _callback.handleOnFailure("Not Connected to Google Drive!");
        }
    }

    public void downloadContentsFromGoogleDrive(final Context _context,
                                 final GoogleCompletedCallback _callback) {
        if (driveResourceClient != null) {
            syncDrive(_context, 10, new GoogleCompletedCallback() {
                @Override
                public void handleOnSuccess() {
                    downloadMetadatas(_context, 10, new GoogleCompletedCallback() {
                        @Override
                        public void handleOnSuccess() {
                            contentMap.clear();
                            metaDatasToProcess = metadataMap.size();
                            failedReason = "";
                            if(metadataMap.size() == 0) {
                                Log.d(TAG, "Nothing to do, no data in Google!");
                                _callback.handleOnSuccess();
                            } else {
                                for (Map.Entry<String, Metadata> entry : metadataMap.entrySet()) {
                                    String fileName = entry.getKey();
                                    if(fileName.contains(FileIO.BENCHMARK_DATA_HEADER) ||
                                            fileName.contains(FileIO.SCOUTING_DATA_HEADER)) {
                                        restoreStringContent(_context, fileName, entry.getValue().getDriveId().asDriveFile(),
                                                new GoogleContentCallback() {
                                                    @Override
                                                    public void handleOnSuccess(String _fileName, String _content) {
                                                        metaDatasToProcess--;
                                                        contentMap.put(_fileName, _content);
                                                        testIsFinishedWithOperation(_callback);
                                                    }

                                                    @Override
                                                    public void handleOnFailure(String _reason) {
                                                        metaDatasToProcess--;
                                                        failedReason = failedReason + " " + _reason;
                                                        testIsFinishedWithOperation(_callback);
                                                    }
                                                });
                                    }
                                }
                            }
                        }

                        @Override
                        public void handleOnFailure(String _reason) {
                            _callback.handleOnFailure(_reason);
                        }
                    });
                }

                @Override
                public void handleOnFailure(String _reason) {
                    _callback.handleOnFailure(_reason);
                }
            });
        } else {
            String msg = "Not Connected to Google Drive!";
            Log.e(TAG, msg);
            _callback.handleOnFailure(msg);
        }
    }

    private void testIsFinishedWithOperation(GoogleCompletedCallback _callback) {
        if (metaDatasToProcess == 0) {
            if (failedReason.isEmpty()) {
                _callback.handleOnSuccess();
            } else {
                _callback.handleOnFailure(failedReason);
            }
        }
    }

    public Map<String, String> getContentMap() {
        return contentMap;
    }

    private void syncDrive(final Context _context, final int _attempts,
                           final GoogleCompletedCallback _callback) {
        Task<Void> syncTask = driveClient.requestSync();
        syncTask
                .addOnSuccessListener((Activity) _context, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Synced");
                        _callback.handleOnSuccess();
                    }
                })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // for some reason sometimes the sync fails but,
                        // if you try again it succeeds
                        String msg = "Error syncing: ";
                        Log.e(TAG, msg, e);
                        if(_attempts > 0) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    syncDrive(_context, _attempts - 1, _callback);
                                }
                            }, 3000);
                        } else {
                            _callback.handleOnFailure(msg + e.getMessage());
                        }
                    }
                });
    }

    private void downloadMetadatas(final Context _context, final int _attempts,
                                   final GoogleCompletedCallback _callback) {
        metadataMap.clear();
        Query query = new Query.Builder()
                .build();

        Task<MetadataBuffer> queryTask = driveResourceClient.query(query);
        queryTask
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                                boolean success = true;
                                for (int index = 0; index < metadataBuffer.getCount(); index++) {
                                    Metadata metadata = metadataBuffer.get(index);
                                    Log.e("Hiram", metadata.getMimeType());
                                    // for some strange reason sometimes the sync returns success but,
                                    // the metadata is not ready
                                    if(metadata.getOriginalFilename() == null && !metadata.getMimeType().contentEquals("application/vnd.google-apps.folder")) {
                                        success = false;
                                        String msg = "Error with metadata";
                                        Log.e(TAG, msg);
                                        if(_attempts > 0) {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    downloadMetadatas(_context, _attempts - 1, _callback);
                                                }
                                            }, 3000);
                                        } else {
                                            _callback.handleOnFailure(msg);
                                        }
                                        break;
                                    } else if (metadata.getMimeType().contentEquals("application/vnd.google-apps.folder")) {
                                        Log.e("Hiram", "skipped");
                                    }
                                    else {
                                        String msg = "Found in google drive: " + metadata.getOriginalFilename();
                                        Log.d(TAG, msg);
                                        metadataMap.put(metadata.getOriginalFilename(), metadata);
                                    }
                                }
                                if(success) {
                                    _callback.handleOnSuccess();
                                }
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Error retrieving files!";
                        Log.e(TAG, msg, e);
                        _callback.handleOnFailure(msg + e.getMessage());
                    }
                });
    }

    private void updateStringData(final Context _context, final String _fileName, final Metadata _metadata,
                                  final String _content, final GoogleCompletedCallback _callback) {
        DriveFile file = _metadata.getDriveId().asDriveFile();
        Task<DriveContents> openTask =
                driveResourceClient.openFile(file, DriveFile.MODE_WRITE_ONLY);
        openTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                DriveContents contents = task.getResult();
                OutputStream outputStream = contents.getOutputStream();
                try (Writer writer = new OutputStreamWriter(outputStream)) {
                    writer.write(_content);
                }

                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle(_metadata.getOriginalFilename())
                        .setMimeType(_metadata.getMimeType())
                        .setStarred(true)
                        .build();

                Task<Void> commitTask =
                        driveResourceClient.commitContents(contents, changeSet);

                return commitTask;
            }
        })
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                _callback.handleOnSuccess();
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to update file: " + _fileName;
                        Log.e(TAG, msg, e);
                        _callback.handleOnFailure(msg + ": " + e.getMessage());
                    }
                });
    }

    private void createStringData(final Context _context, final String _fileName, final String _content,
                                  final GoogleCompletedCallback _callback) {
        final Task<DriveFolder> rootFolderTask = driveResourceClient.getRootFolder();
        final Task<DriveContents> createContentsTask = driveResourceClient.createContents();
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

                        return driveResourceClient.createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                _callback.handleOnSuccess();
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to create file: " + _fileName;
                        Log.e(TAG, msg, e);
                        _callback.handleOnFailure(msg + ": " + e.getMessage());
                    }
                });
    }

    private void createFileData(final Context _context, final String _fileName, final File _content,
                                  final GoogleCompletedCallback _callback) {
        final Task<DriveFolder> rootFolderTask = driveResourceClient.getRootFolder();
        final Task<DriveContents> createContentsTask = driveResourceClient.createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();
                        OutputStream outputStream = contents.getOutputStream();
                        try
                        {
                            InputStream dbInputStream = new FileInputStream(_content);

                            byte[] buffer = new byte[1024];
                            int length;
                            int counter = 0;
                            while((length = dbInputStream.read(buffer)) > 0)
                            {
                                ++counter;
                                outputStream.write(buffer, 0, length);
                            }

                            dbInputStream.close();
                            outputStream.flush();
                            outputStream.close();

                        } catch (IOException e) {
                            String msg = "Unable to create file: " + _fileName;
                            Log.e(TAG, msg, e);
                            _callback.handleOnFailure(msg + ": " + e.getMessage());
                        }

                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(_fileName)
                                .setMimeType("image/jpeg")
                                .setStarred(true)
                                .build();

                        return driveResourceClient.createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                _callback.handleOnSuccess();
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to create file: " + _fileName;
                        Log.e(TAG, msg, e);
                        _callback.handleOnFailure(msg + ": " + e.getMessage());
                    }
                });
    }

    private void restoreStringContent(final Context _context, final String _fileName, DriveFile _file,
                                      final GoogleContentCallback _callback) {
        Task<DriveContents> openFileTask =
                driveResourceClient.openFile(_file, DriveFile.MODE_READ_ONLY);
        openFileTask
                .continueWithTask(new Continuation<DriveContents, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                        DriveContents contents = task.getResult();
                        StringBuilder contentBuilder = new StringBuilder();
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(contents.getInputStream()))) {
                            String sCurrentLine;
                            while ((sCurrentLine = br.readLine()) != null) {
                                contentBuilder.append(sCurrentLine).append("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String data = contentBuilder.toString();

                        _callback.handleOnSuccess(_fileName, data);

                        Task<Void> discardTask = driveResourceClient.discardContents(contents);
                        return discardTask;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to read coontents: " + _fileName;
                        Log.e(TAG, msg, e);
                        _callback.handleOnFailure(msg + ": " + e.getMessage());
                    }
                });
    }
}
