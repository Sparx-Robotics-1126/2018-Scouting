package sparx1126.com.powerup;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;

import sparx1126.com.powerup.utilities.Utility;

public class Directory extends AppCompatActivity {
    private static final String TAG = "Directory ";
    private SharedPreferences settings;
    private static Utility utility;

    private LinearLayout normalButtons;
    private Button view;
    private Button scouting;
    private Button benchmarking;
    private Button admin;
    private Button checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        utility = Utility.getInstance();

        normalButtons = findViewById(R.id.normal_buttons);

        view = findViewById(R.id.view);
        view.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, View.class);
                startActivity(intent);
            }
        });

        scouting = findViewById(R.id.scouting);
        scouting.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Scouting.class);
                startActivity(intent);
            }
        });

        benchmarking = findViewById(R.id.benchmark);
        benchmarking.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Benchmarking.class);
                startActivity(intent);
            }
        });

        admin =findViewById(R.id.admin);
        admin.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
        });
        admin.setVisibility(android.view.View.INVISIBLE);

        checklist = findViewById(R.id.checklist);
        checklist.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, CheckList.class);
                startActivity(intent);
            }
        });

        boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
        if(!isTableConfigured) {
            String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
            String[] adminList = getResources().getStringArray(R.array.admins);
            boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);
            if(adminNameFound) {
                Log.d(TAG, "Admin");
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
            else {
                Dialog dialog = utility.getNegativeButtonDialog(this, TAG,
                        "Have an Admin Setup Tablet!",
                        "Go Back");
                dialog.show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        showButtons();
    }

    private void showButtons() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        String[] adminList = getResources().getStringArray(R.array.admins);
        boolean adminNameFound = Arrays.asList(adminList).contains(scouterName);

        if(adminNameFound) {
            admin.setVisibility(android.view.View.VISIBLE);
        }

        boolean isTableConfigured = settings.getBoolean(getResources().getString(R.string.tablet_Configured), false);
        if(isTableConfigured) {
            normalButtons.setVisibility(android.view.View.VISIBLE);
        }
        else {
            normalButtons.setVisibility(android.view.View.INVISIBLE);
        }
    }
}
