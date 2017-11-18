package com.boussaingault.maxime.moodtracker.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.boussaingault.maxime.moodtracker.R;

import java.util.ArrayList;

/**
 * Created by Tit Max on 15/11/2017.
 */

public class HistoryListAdapter extends ArrayAdapter<MoodData> {

    private ArrayList<MoodData> listMood = new ArrayList<MoodData>();
    private Context context;
    private String[] backgroundColor = {"faded_red", "warm_grey", "cornflower_blue_65", "light_sage", "banana_yellow"};

    public HistoryListAdapter(Context context, int layoutResourceId, ArrayList<MoodData> listMood) {
        super(context, layoutResourceId, listMood);
        this.context = context;
        this.listMood = listMood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textViewDate;
        ImageButton imageButtonComment;

        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_mood, parent, false);
        }

        textViewDate = (TextView) convertView.findViewById(R.id.row_mood_date_text_view);
        imageButtonComment = (ImageButton) convertView.findViewById(R.id.row_mood_comment_image_btn);

        final MoodData moodData = listMood.get(position);
        textViewDate.setText(moodData.getDaysAgo());
        if (!moodData.getComment().equals(""))
            imageButtonComment.setVisibility(View.VISIBLE);

        imageButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), moodData.getComment(), Toast.LENGTH_SHORT).show();
            }
        });

        String color = moodData.getColor();
        switch (color) {
            case "faded_red":
                setMoodBackground(convertView, backgroundColor[0], 1);
                break;
            case "warm_grey":
                setMoodBackground(convertView, backgroundColor[1], 2);
                break;
            case "cornflower_blue_65":
                setMoodBackground(convertView, backgroundColor[2], 3);
                break;
            case "light_sage":
                setMoodBackground(convertView, backgroundColor[3], 4);
                break;
            case "banana_yellow":
                setMoodBackground(convertView, backgroundColor[4], 5);
                break;
            default:
                break;
        }
        return convertView;
    }
    private void setMoodBackground(View convertView, String color, double indice) {
        FrameLayout frameLayout;
        LayoutParams params;

        int background = convertView.getResources().getIdentifier(color, "color", context.getPackageName());
        convertView.setBackgroundColor(convertView.getResources().getColor(background));
        Point size = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        frameLayout = (FrameLayout) convertView.findViewById(R.id.row_mood_frame_layout);
        Double reductionX = size.x * (indice / 5);
        Double reductionY = size.y / 7d - 10;
        params = frameLayout.getLayoutParams();
        params.width = reductionX.intValue();
        params.height = reductionY.intValue();
        frameLayout.setLayoutParams(params);
    }
}
