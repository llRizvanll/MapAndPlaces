package com.co.nightowl.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.co.nightowl.R;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */

public class AppToast {
    public static void show(Activity context, String body){
        // create instance
        Toast toast = new Toast(context);

        // inflate custom view
        View view = context.getLayoutInflater().inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.txtMessage);
        textView.setText(body);
        // set custom view
        toast.setView(view);

        // set duration
        toast.setDuration(Toast.LENGTH_SHORT);

        // set position
        int margin = context.getResources().getDimensionPixelSize(R.dimen.toast_margin);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, margin);

        // show toast
        toast.show();
    }
}
