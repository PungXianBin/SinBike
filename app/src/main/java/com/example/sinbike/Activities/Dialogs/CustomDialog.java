package com.example.sinbike.Activities.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sinbike.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Overriden onCreate function of Dialog.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_title);

        TextView textView = findViewById(R.id.tvClose);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
