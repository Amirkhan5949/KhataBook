package com.example.khatabook.utils;

import android.content.Context;
import android.view.Gravity;

import com.example.khatabook.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class Loader {

    DialogPlus dialog;

    public Loader(Context context) {
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.loader))
                .setContentBackgroundResource(R.color.tranperent)
                .setCancelable(false)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }
}
