package sparx1126.com.powerup.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import sparx1126.com.powerup.R;

public class HorizontalNumberPicker extends LinearLayout implements View.OnClickListener {

    private Button plus;
    private Button minus;
    private EditText editText;

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.control_horizontal_number_pickers, this);

        if(view != null) {
            editText = (EditText) view.findViewById(R.id.edit_text);

            plus = (Button) view.findViewById(R.id.btn_plus);
            plus.setOnClickListener(this);
            minus = (Button) view.findViewById(R.id.btn_minus);
            minus.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(plus == view){
            int value = Integer.parseInt(editText.getText().toString());
            editText.setText(Integer.toString(value + 1));
        }else if(minus == view){
            int value = Integer.parseInt(editText.getText().toString());
            if(value > 0) {
                editText.setText(Integer.toString(value - 1));
            }
        }

    }

    public void setValue(int value){editText.setText(Integer.toString(value));}
    public int getValue(){return Integer.parseInt(editText.getText().toString());}
}
