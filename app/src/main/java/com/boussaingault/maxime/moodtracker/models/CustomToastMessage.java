package com.boussaingault.maxime.moodtracker.models;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boussaingault.maxime.moodtracker.R;

/**
 * Created by Tit Max on 21/11/2017.
 */

public class CustomToastMessage {

    public static void showMessage(Context context, String message) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.custom_toast_text);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
