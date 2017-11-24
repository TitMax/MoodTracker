package com.boussaingault.maxime.moodtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.CustomToastMessage;
import com.boussaingault.maxime.moodtracker.models.DatabaseManager;
import com.boussaingault.maxime.moodtracker.models.HistoryListAdapter;
import com.boussaingault.maxime.moodtracker.models.MoodData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime Boussaingault on 15/11/2017.
 */

public class HistoryActivity extends AppCompatActivity {

    private ListView mListView;
    private DatabaseManager mDatabaseManager;
    private ArrayList<MoodData> listMoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Wire widget
        mListView = findViewById(R.id.activity_history_list_view);

        System.out.println("HistoryActivity::onCreate()");
    }
    // Disable scroll touch events
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Detect  a movement during a press gesture and consume it
        return event.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("HistoryActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseManager = new DatabaseManager(this); // Open database
        if (mDatabaseManager.isHistory() != 0) {    // Check if there is an history
            createListHistory(); // call the createListHistory() method
        } else { // if no history
            // Send a toast
            CustomToastMessage customToastMessage = new CustomToastMessage();
            customToastMessage.showMessage(this, "Pas encore d'historique? Revenez demain!");
            /*      UNCOMMENT BELOW TO POPULATE THE SQLITE DATABASE       */
//            populateDatabase();
        }
        mDatabaseManager.close(); // Close database
        mListView.setAdapter(new HistoryListAdapter(this, R.layout.row_mood, listMoods));
        System.out.println("HistoryActivity::onResume()");
    }
    // Method to populate the database for the demo
    private void populateDatabase() {
        String[] moods = {"Sad", "Disappointed", "Normal", "Happy", "Super Happy"};
        String[] color = {"faded_red", "warm_grey", "cornflower_blue_65", "light_sage", "banana_yellow"};
        mDatabaseManager.insertMood(moods[3],"",color[3], 1);
        mDatabaseManager.insertMood(moods[0],"Je suis fatigué...",color[0], 2);
        mDatabaseManager.insertMood(moods[1],"",color[1], 3);
        mDatabaseManager.insertMood(moods[4],"Ma pizza était définitivement trop bonne, et une bière en terrasse, c'était top !",color[4], 4);
        mDatabaseManager.insertMood(moods[3],"",color[3], 5);
        mDatabaseManager.insertMood(moods[2],"No comment...",color[2], 6);
        mDatabaseManager.insertMood(moods[4],"",color[4], 7);
        for (int i = 8; i <= 30; i++) {
            int num = new java.util.Random().nextInt(5);
            mDatabaseManager.insertMood(moods[num],"",color[num], i);
        }
        createListHistory(); // call the createListHistory() method
    }
    // Method to create the history list
    private void createListHistory() {
        List<MoodData> listHistory = mDatabaseManager.mMoodData(); // Create the history list
        listMoods.addAll(listHistory); // Add rows from the database into the history list
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("HistoryActivity::onPause()");
        listMoods.clear(); // Clear the list
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("HistoryActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("HistoryActivity::onDestroy()");
    }
}
