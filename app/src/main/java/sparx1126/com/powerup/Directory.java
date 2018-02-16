package sparx1126.com.powerup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;

/**
 * Created by Hiram on 2/10/2018.
 */



public class Directory extends AppCompatActivity {


    private Button view;
    private Button scouting;
    private Button benchmarking;
    private Button admin;
    private Button checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);
        view = findViewById(R.id.view);
        scouting = findViewById(R.id.scouting);
        benchmarking = findViewById(R.id.benchmark);
        admin =findViewById(R.id.admin);
        checklist = findViewById(R.id.checklist);

        checklist.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, CheckList.class);
                startActivity(intent);
            }
        });
        view.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                        Intent intent = new Intent(Directory.this, View.class);
                        startActivity(intent);
            }
        });

        scouting.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Scouting.class);
                startActivity(intent);
            }
        });

        benchmarking.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Benchmarking.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Directory.this, Admin.class);
                startActivity(intent);
            }
        });


    }
}
