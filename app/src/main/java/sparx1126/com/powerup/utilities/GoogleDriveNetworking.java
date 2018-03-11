package sparx1126.com.powerup.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleDriveNetworking {
    // some code came from:
    // https://github.com/googledrive/android-demos/tree/master/app/src/main/java/com/google/android/gms/drive/sample/demo
    private static final String TAG = "GoogleDriveNetworking ";

    private DriveResourceClient driveResourceClient;
    private DriveClient driveClient;
    private static GoogleDriveNetworking instance;
    private List<Metadata> metadataList;
    private static FileIO fileIO;
    private static Utility utility;

    // synchronized means that the method cannot be executed by two threads at the same time
    // hence protected so that it always returns the same instance
    public static synchronized GoogleDriveNetworking getInstance() {
        if (instance == null)
            instance = new GoogleDriveNetworking();
        return instance;
    }

    private GoogleDriveNetworking() {
        driveResourceClient = null;
        metadataList = new ArrayList<>();
        fileIO = FileIO.getInstance();
        utility = Utility.getInstance();
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

    public boolean tryInitializeDriveClient(Intent _data, Context _context) {
        Task<GoogleSignInAccount> accountTask =
                GoogleSignIn.getSignedInAccountFromIntent(_data);
        if (accountTask.isSuccessful()) {
            initializeDriveClient(accountTask.getResult(), _context);
            return true;
        }
        return false;
    }

    private void initializeDriveClient(GoogleSignInAccount signInAccount, final Context _context) {
        driveResourceClient = Drive.getDriveResourceClient(_context, signInAccount);
        driveClient = Drive.getDriveClient(_context, signInAccount);
        syncDrive(_context);
    }

    public void uploadContentToGoogleDrive(final String _content, final String _fileName, final Context _context) {
        if (driveResourceClient != null) {
            List<String> fileList = getListOfFiles();

            if (fileList.contains(_fileName)) {
                updateFile(_content, _fileName, _context);
            } else {
                createFile(_content, _fileName, _context);
            }
        } else {
            Dialog dialog = utility.getPositiveButtonDialog(_context, TAG,
                    "Not Connected to Google Drive!",
                    "Okay");
            dialog.show();
        }
    }

    private List<String> getListOfFiles() {
        List<String> list = new ArrayList<>();
        for (Metadata data : metadataList) {
            list.add(data.getOriginalFilename());
        }
        return list;
    }

    private Metadata getMetadata(String _fileName) {
        Metadata rtndata = null;
        for (Metadata data : metadataList) {
            if (data.getOriginalFilename().equals(_fileName)) {
                rtndata = data;
            }
        }
        return rtndata;
    }

    private void downloadMetadatas(final Context _context) {
        metadataList.clear();
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
                .build();

        Task<MetadataBuffer> queryTask = driveResourceClient.query(query);
        queryTask
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                                for (int index = 0; index < metadataBuffer.getCount(); index++) {
                                    Metadata metadata = metadataBuffer.get(index);
                                    // for some strange reason sometimes the sync returns success but,
                                    // the metadata is not ready
                                    if(metadata.getOriginalFilename() == null) {
                                        // try again
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d(TAG, "Trying Again!");
                                                downloadMetadatas(_context);
                                            }
                                        }, 2000);
                                    } else {
                                        String msg = "Found in google drive: " + metadata.getOriginalFilename();
                                        metadataList.add(metadata);
                                    }
                                }
                                metadataBuffer.release();
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Error retrieving files!";
                        Log.e(TAG, msg, e);
                        Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void downloadContents(final Context _context) {
        if (driveResourceClient != null) {
            final Handler handler = new Handler();
            AlertDialog.Builder builder = new AlertDialog.Builder(_context);
            builder.setTitle(TAG);
            builder.setMessage("Error syncing google drive,trying Again. Please Wait...");
            builder.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    handler.removeCallbacksAndMessages(null);
                    metadataList.clear();
                    dialogInterface.dismiss();
                    String msg = "Google Drive Out of sync!";
                    Log.e(TAG, msg);
                    Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                }
            });
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            downloadContents(_context, dialog, handler);
        } else {
            Dialog dialog = utility.getPositiveButtonDialog(_context, TAG,
                    "Not Connected to Google Drive!",
                    "Okay");
            dialog.show();
        }
    }

    private void downloadContents(final Context _context, final Dialog failureDialog, final Handler handler) {
        Task<Void> syncTask = driveClient.requestSync();
        syncTask
                .addOnSuccessListener((Activity) _context, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        failureDialog.dismiss();
                        Log.d(TAG, "Synced");
                        downloadMetadatasForContent(_context);
                    }
                })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "downloadContents: Error syncing!";
                        Log.e(TAG, msg, e);
                        failureDialog.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                downloadContents(_context, failureDialog, handler);
                            }
                        }, 5000);
                    }
                });
    }

    private void downloadMetadatasForContent(final Context _context) {
        metadataList.clear();
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
                .build();

        Task<MetadataBuffer> queryTask = driveResourceClient.query(query);
        queryTask
                .addOnSuccessListener((Activity) _context,
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                                Map<String, DriveFile> filesToRestore = new HashMap<>();
                                boolean success = true;
                                for (int index = 0; index < metadataBuffer.getCount(); index++) {
                                    Metadata metadata = metadataBuffer.get(index);
                                    // for some strange reason sometimes the sync returnd success but,
                                    // the metadata is not ready
                                    if (metadata.getOriginalFilename() == null) {
                                        // try again in the background
                                        success = false;
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d(TAG, "Trying Again!");
                                                downloadMetadatasForContent(_context);
                                            }
                                        }, 2000);
                                    } else {
                                        String fileName = metadata.getOriginalFilename();
                                        if (fileName.contains(FileIO.SCOUTING_DATA_HEADER) ||
                                                fileName.contains(FileIO.BENCHMARK_DATA_HEADER)) {
                                            String msg = "Found in google drive: " + metadata.getOriginalFilename();
                                            metadataList.add(metadata);
                                            filesToRestore.put(fileName, metadata.getDriveId().asDriveFile());
                                        }
                                    }
                                }
                                metadataBuffer.release();
                                if(success) {
                                    restoreContents(_context, filesToRestore);
                                }
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Error retrieving files!";
                        Log.e(TAG, msg, e);
                        Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void restoreContents (final Context _context, final Map<String, DriveFile> filesToRestore) {
        Iterator it = filesToRestore.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            it.remove(); // avoids a ConcurrentModificationException
            restoreContent(_context, (String)pair.getKey(), (DriveFile)pair.getValue(), !it.hasNext());
        }
    }

    private void restoreContent (final Context _context, final String fileName, DriveFile file, final boolean lastFile) {
        Task<DriveContents> openFileTask =
                driveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY);
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
                        if (fileName.contains(FileIO.SCOUTING_DATA_HEADER)) {
                            String[] fileNameParts = fileName.split("[_.]");
                            String team = fileNameParts[1].replace(FileIO.TEAM, "");
                            String match = fileNameParts[2].replace(FileIO.MATCH, "");

                            fileIO.storeScoutingData(data, team, match);
                        } else if (fileName.contains(FileIO.BENCHMARK_DATA_HEADER)) {
                            String[] fileNameParts = fileName.split("[_.]");
                            String team = fileNameParts[1].replace(FileIO.TEAM, "");

                            fileIO.storeBenchmarkData(data, team);
                        }

                        Task<Void> discardTask = driveResourceClient.discardContents(contents);
                        if (lastFile) {
                            utility.restoreFromTablet();
                        }
                        return discardTask;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to read coontents!";
                        Log.e(TAG, msg, e);
                        Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void createFile(final String _content, final String _fileName, final Context _context) {
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
                                String msg = "File created: " + _fileName;
                                Log.d(TAG, msg);
                                Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                                syncDrive(_context);
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to create file: " + _fileName;
                        Log.e(TAG, msg, e);
                        Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateFile(final String _content, final String _fileName, final Context _context) {
        final Metadata metadata = getMetadata(_fileName);
        DriveFile file = metadata.getDriveId().asDriveFile();
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
                        .setTitle(metadata.getOriginalFilename())
                        .setMimeType(metadata.getMimeType())
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
                                String msg = "File updated: " + _fileName;
                                Log.d(TAG, msg);
                                Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                                syncDrive(_context);
                            }
                        })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "Unable to update file: " + _fileName;
                        Log.e(TAG, msg, e);
                        Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void syncDrive(final Context _context) {
        final Handler handler = new Handler();
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setTitle(TAG);
        builder.setMessage("Error syncing google drive,trying Again. Please Wait...");
        builder.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handler.removeCallbacksAndMessages(null);
                metadataList.clear();
                dialogInterface.dismiss();
                String msg = "Google Drive Out of sync!";
                Log.e(TAG, msg);
                Toast.makeText(_context, TAG + msg, Toast.LENGTH_LONG).show();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        syncDrive(_context, dialog, handler);
    }

    private void syncDrive(final Context _context, final Dialog failureDialog, final Handler handler) {
        Task<Void> syncTask = driveClient.requestSync();
        syncTask
                .addOnSuccessListener((Activity) _context, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        failureDialog.dismiss();
                        Log.d(TAG, "Synced");
                        downloadMetadatas(_context);
                    }
                })
                .addOnFailureListener((Activity) _context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String msg = "syncDrive: Error syncing!";
                        Log.e(TAG, msg, e);
                        failureDialog.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                syncDrive(_context, failureDialog, handler);
                            }
                        }, 5000);
                    }
                });
    }
}
