package sparx1126.com.powerup.custom_layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import sparx1126.com.powerup.R;

public class PlusMinusEditTextLinearLayout extends LinearLayout implements View.OnClickListener {
    private Button plus;
    private Button minus;
    private EditText editText;

    public PlusMinusEditTextLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.plus_minus_edittext_linearlayout, this);

        if(view != null) {
            editText = view.findViewById(R.id.edit_text);

            plus = view.findViewById(R.id.btn_plus);
            plus.setOnClickListener(this);
            minus = view.findViewById(R.id.btn_minus);
            minus.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int value = Integer.parseInt(editText.getText().toString());
        if(plus == view){
            editText.setText(Integer.toString(value + 1));
        }else if(minus == view && (value > 0)){
            editText.setText(Integer.toString(value - 1));
        }

    }

    public void setValue(int value){editText.setText(Integer.toString(value));}
    public int getValue(){return Integer.parseInt(editText.getText().toString());}
}
