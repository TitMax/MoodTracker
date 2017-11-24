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
    private ImageButton mImageButtonPieChart;
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

        // Wire widgets
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mImageButtonAddNote = findViewById(R.id.activity_main_note_add_btn);
        mImageButtonHistory = findViewById(R.id.activity_main_history_btn);
        mImageButtonPieChart = findViewById(R.id.activity_main_pie_chart_btn);

        // Open the alert dialog when click on the button
        mImageButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNoteDialog();
            }
        });

        // Open the History activity when click on the button
        mImageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Open the Pie Chart History activity when click on the button
        mImageButtonPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PieChartHistoryActivity.class);
                startActivity(intent);
            }
        });

        System.out.println("MainActivity::onCreate()");
    }
    // Custom alert dialog
    public void addNoteDialog() {
        // Create the dialogBuilder and wire the custom style on it
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog, null, false);
        dialogBuilder.setView(dialogView);

        // wire the widget
        mEditTextNote = dialogView.findViewById(R.id.alert_dialog_edit_text);
        // Change the editText bottom line color
        mEditTextNote.getBackground().setColorFilter(Color.rgb(0,142,125), PorterDuff.Mode.SRC_IN);
        mEditTextNote.setText(comment); // set the editText with the current comment variable
        mEditTextNote.setSelection(mEditTextNote.length()); // set the cursor to the end of the text

        // When click on the submit button
        dialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mEditTextNote.getText().toString().trim().equals("")) { // If no text or cleared by the user
                    comment = ""; // Clear the comment variable
                }
                else {
                    comment = mEditTextNote.getText().toString().trim(); // Add text to the comment variable
                }
            }
        });
        // When click on the cancel button
        dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do Nothing, just close the Alert Dialog
            }
        });

        AlertDialog alertDialog = dialogBuilder.create(); // Create the alert dialog
        alertDialog.show(); // Show the alert dialog
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Open the database
        mDatabaseManager = new DatabaseManager(this);
        // Get the current mood from the database
        MoodData currentMood = mDatabaseManager.getCurrentMood();
        if(currentMood != null) { // if current mood found in the database
            // find the corresponding id of the mood in the mood table
            currentMoodId = Arrays.asList(mood).indexOf(currentMood.getMood());
            comment = currentMood.getComment(); // get the comment from the database
        } else { // if no current mood
            currentMoodId = 3; // Initialize the current mood to happy
            comment = ""; // Clear comment
        }
        mDatabaseManager.close(); // Close the database
        // set the ViewPagerAdapter to show fragments
        mVerticalViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(currentMoodId); // Set the pager to the current mood fragment
        System.out.println("MainActivity::onResume()");
    }

    // Called every times we leave this activity (phone call or activity change)
    @Override
    protected void onPause() {
        super.onPause();
        int i = mVerticalViewPager.getCurrentItem(); // get the current pager position
        // Insert or replace the current mood, comment and color in the database
        mDatabaseManager = new DatabaseManager(this);
        mDatabaseManager.insertMood(mood[i], comment, color[i]);
        mDatabaseManager.close();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
