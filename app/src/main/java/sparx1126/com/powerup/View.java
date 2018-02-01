package sparx1126.com.powerup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class View extends AppCompatActivity{

    private EditText teamnumber;
    //temporaryteamlist
    List<String> teamlistnew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This came from AppCompatActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        teamlistnew.add("1126");
        teamlistnew.add("123");

        teamnumber = findViewById(R.id.teamnumber);
        teamnumber.addTextChangedListener(teamTextEntered);
    }
    private final TextWatcher teamTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println(s.toString());
            if(teamlistnew.contains(s.toString())) {
            System.out.println("Hello");}
        }
    };
}

