package com.boussaingault.maxime.moodtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;

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
    private ArrayList<MoodData> listMoods = new ArrayList<MoodData>();
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
        List<MoodData> listHistory = mDatabaseManager.mMoodData();
        for (MoodData mood : listHistory) {
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
