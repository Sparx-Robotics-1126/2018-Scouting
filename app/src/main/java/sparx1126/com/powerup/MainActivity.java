package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String[] studentList = {"Felix", "Huang"};
    AutoCompleteTextView studenName;
    BlueAlliance blueAlliance = BlueAlliance.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // student selection
        studenName = findViewById(R.id.studentNameText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studenName.setAdapter(adapter);
        studenName.setThreshold(1);
        studenName.setOnItemClickListener(studentSelectedFunction);

        blueAlliance.refreshData();
    }

    private final AdapterView.OnItemClickListener studentSelectedFunction = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
            //String selectedStudent = (String) parent.getItemAtPosition(pos);
            //System.out.println("onItemClick " + selectedStudent);
        }
    };
}