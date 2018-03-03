package sparx1126.com.powerup.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utility {
    private static final String TAG = "Utility ";
    private static Utility instance;

    public static synchronized Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public Dialog getNoButtonDialog(Context _this, String _title, String _msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public Dialog getPositiveButtonDialog(Context _this, String _title, String _msg, String _buttonMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        builder.setPositiveButton(_buttonMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public Dialog getNegativeButtonDialog(final Activity _this, String _title, String _msg, String _buttonMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setTitle(_title);
        builder.setMessage(_msg);
        builder.setPositiveButton(_buttonMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                _this.finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}

