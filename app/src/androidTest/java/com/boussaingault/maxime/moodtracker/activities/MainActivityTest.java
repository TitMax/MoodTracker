package com.boussaingault.maxime.moodtracker.activities;


import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.models.VerticalViewPager;
import com.boussaingault.maxime.moodtracker.models.ViewPagerAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Maxime Boussaingault on 24/11/2017.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensure_verticalViewPager_is_present() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.activity_main_view_pager);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(VerticalViewPager.class));
        VerticalViewPager verticalViewPager = (VerticalViewPager) viewById;
        PagerAdapter adapter = verticalViewPager.getAdapter();
        assertThat(adapter, instanceOf(ViewPagerAdapter.class));
        assertThat(adapter.getCount(), equalTo(5));
    }

    @Test
    public void ensure_dialog_displayed_when_clicked() throws Exception {
        // Click to show the alert dialog
        onView(withId(R.id.activity_main_note_add_btn)).perform(click());

        // To check if the alert dialog title text is displayed
        onView(withText(R.string.commentaire)).check(matches(isDisplayed()));
    }
}
