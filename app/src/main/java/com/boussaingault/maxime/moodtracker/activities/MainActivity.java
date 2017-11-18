package com.boussaingault.maxime.moodtracker.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.boussaingault.maxime.moodtracker.models.MoodData;
import com.boussaingault.maxime.moodtracker.models.VerticalViewPager;
import com.boussaingault.maxime.moodtracker.models.ViewPagerAdapter;

import java.util.Arrays;

/**
 * Created by Maxime Boussaingault on 14/11/2017.
 */

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;
    private ImageButton mImageButtonAddNote;
    private ImageButton mImageButtonHistory;
    private EditText mEditTextNote;
    private String comment = "";
    private String[] mood = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
    private String[] color = {"faded_red", "warm_grey", "cornflower_blue_65", "light_sage", "banana_yellow"};
    private int currentMoodId = 3;
    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mImageButtonAddNote = (ImageButton) findViewById(R.id.activity_main_note_add_btn);
        mImageButtonHistory = (ImageButton) findViewById(R.id.activity_main_history_btn);

        mImageButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteDialog();
            }
        });

        mImageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddNoteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog, null, false);
        dialogBuilder.setView(dialogView);

        mEditTextNote = (EditText) dialogView.findViewById(R.id.alert_dialog_edit_text);
        mEditTextNote.getBackground().setColorFilter(Color.rgb(0,142,125), PorterDuff.Mode.SRC_IN);
        mEditTextNote.setText(comment);
        mEditTextNote.setSelection(mEditTextNote.length());

        dialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mEditTextNote.getText().toString().equals("")) {
                    comment = mEditTextNote.getText().toString();
                }
                else {
                    comment = mEditTextNote.getText().toString();
                }
            }
        });
        dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do Nothing, just close the Alert Dialog
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseManager = new DatabaseManager(this);
        MoodData currentMood = mDatabaseManager.getCurrentMood();
        if(currentMood != null) {
            currentMoodId = Arrays.asList(mood).indexOf(currentMood.getMood());
            comment = currentMood.getComment();
        } else {
            currentMoodId = 3;
            comment = "";
        }
        mVerticalViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(currentMoodId);
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        int i = mVerticalViewPager.getCurrentItem();
        mDatabaseManager.insertMood(mood[i], comment, color[i]);
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseManager.close();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
