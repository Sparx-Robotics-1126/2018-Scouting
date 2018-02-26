package sparx1126.com.powerup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.Arrays;

public class Directory extends AppCompatActivity {
    private static final String TAG = "Directory ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);

        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        Button view = findViewById(R.id.view);
        view.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, View.class);
                startActivity(intent);
            }
        });

        Button scouting = findViewById(R.id.scouting);
        scouting.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Scouting.class);
                startActivity(intent);
            }
        });

        Button benchmarking = findViewById(R.id.benchmark);
        benchmarking.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Benchmarking.class);
                startActivity(intent);
            }
        });

        Button admin =findViewById(R.id.admin);
        admin.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
        });
        admin.setVisibility(android.view.View.INVISIBLE);

        Button checklist = findViewById(R.id.checklist);
        checklist.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, CheckList.class);
                startActivity(intent);
            }
        });

        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        String[] adminList = getResources().getStringArray(R.array.admins);
        boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);

        if(adminNameFound) {
            admin.setVisibility(android.view.View.VISIBLE);
        }

        boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
        if(!isTableConfigured) {
            if(adminNameFound) {
                Log.d(TAG, "Admin");
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
            else {
                String msg = "Have an Admin Setup Tablet!";
                Log.e(TAG, msg);
                AlertDialog.Builder builder = new AlertDialog.Builder(Directory.this);

                builder.setTitle(TAG);
                builder.setMessage(msg);
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
