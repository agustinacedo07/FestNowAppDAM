package com.example.agustin.festnowapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogoModificar extends AlertDialog.Builder {

    public DialogoModificar(Context context) {
        super(context);
    }

    public DialogoModificar(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public AlertDialog.Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return super.setOnCancelListener(onCancelListener);
    }

    @Override
    public AlertDialog.Builder setView(View view) {
        return super.setView(view);
    }
}
