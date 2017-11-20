package com.boussaingault.maxime.moodtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.boussaingault.maxime.moodtracker.R;
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
    private HistoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mListView = (ListView) findViewById(R.id.activity_history_list_view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_MOVE)
            return true;
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("HistoryActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseManager = new DatabaseManager(this);
        if (mDatabaseManager.isHistory() != 0) {
            List<MoodData> listHistory = mDatabaseManager.mMoodData();
            for (MoodData mood : listHistory)
                listMoods.add(mood);
        } else {
            Toast.makeText(this, "Pas encore d'historique? Revenez demain!", Toast.LENGTH_LONG).show();
            /*      UNCOMMENT BELOW TO POPULATE THE SQLITE DATABASE       */
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
            List<MoodData> listHistory = mDatabaseManager.mMoodData();
            for (MoodData mood : listHistory)
                listMoods.add(mood);
        }
        mDatabaseManager.close();
        adapter = new HistoryListAdapter(this, R.layout.row_mood, listMoods);
        mListView.setAdapter(adapter);
        System.out.println("HistoryActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("HistoryActivity::onPause()");
        listMoods.clear();
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
