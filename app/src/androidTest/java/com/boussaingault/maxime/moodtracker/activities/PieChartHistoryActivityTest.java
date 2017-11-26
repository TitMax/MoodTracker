package com.boussaingault.maxime.moodtracker.activities;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.SeekBar;

import com.boussaingault.maxime.moodtracker.R;
import com.github.mikephil.charting.charts.PieChart;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.MessageFormat;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Maxime Boussaingault on 25/11/2017.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class PieChartHistoryActivityTest {

    @Rule
    public ActivityTestRule<PieChartHistoryActivity> rule = new ActivityTestRule<>(PieChartHistoryActivity.class);

    @Test
    public void ensure_pieChart_is_present() throws Exception {
        PieChartHistoryActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.activity_piechart_history_pie_chart);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(PieChart.class));
    }

    @Test
    public void ensure_seekBar_current_progression_display_is_correct() throws Exception {
        int MIN = 7;
        int progress = 15;
        int daysNumber = progress + MIN;
        assertEquals(22, daysNumber);

        onView(withId(R.id.activity_piechart_history_seek_bar)).perform(setProgress(progress));
        onView(withText(MessageFormat.format("{0} jours", daysNumber))).check(matches(isDisplayed()));
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
        };
    }
}