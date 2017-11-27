package com.boussaingault.maxime.moodtracker.activities;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.HistoryListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Maxime Boussaingault on 25/11/2017.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class HistoryActivityTest {

    @Rule
    public ActivityTestRule<HistoryActivity> rule = new ActivityTestRule<>(HistoryActivity.class);

    @Test
    public void ensure_listView_is_present() throws Exception {
        HistoryActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.activity_history_list_view);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(ListView.class));
        ListView listView = (ListView) viewById;
        ListAdapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(HistoryListAdapter.class));
    }
}