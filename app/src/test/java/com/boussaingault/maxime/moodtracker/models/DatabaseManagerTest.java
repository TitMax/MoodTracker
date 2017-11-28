package com.boussaingault.maxime.moodtracker.models;

import com.boussaingault.maxime.moodtracker.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Maxime Boussaingault on 26/11/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class DatabaseManagerTest {

    private DatabaseManager mDatabaseManager;

    @Before
    public void setUp() throws Exception {
        mDatabaseManager = new DatabaseManager(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() {
        mDatabaseManager.close();
    }

    @Test
    public void should_return_null() {
        mDatabaseManager.getCurrentMood();
    }

    @Test
    public void should_find_zero_Happy_Mood() {
        String mood = "Sad";
        String comment = "";
        String color = "red_faded";

        // When
        mDatabaseManager.insertMood(mood, color, comment);
        assertEquals(0, mDatabaseManager.countMoods("Happy", 0));
    }

    @Test
    public void should_return_countMoods_two() {
        String mood = "Sad";
        String comment = "";
        String color = "red_faded";

        // When
        mDatabaseManager.insertMood(mood, color, comment, 2);
        mDatabaseManager.insertMood(mood, color, comment, 8);

        // Then
        assertEquals(2, mDatabaseManager.countMoods("Sad", 10));
    }

    @Test
    public void should_insert_in_the_database() {
        // Given
        String mood = "Sad";
        String comment = "Test";
        String color = "red_faded";

        // When
        mDatabaseManager.insertMood(mood, color, comment);

        // Then
        assertEquals(mood, mDatabaseManager.getCurrentMood().getMood());
        assertEquals(comment, mDatabaseManager.getCurrentMood().getComment());
        assertEquals(color, mDatabaseManager.getCurrentMood().getColor());
        assertEquals("Aujourd'hui", mDatabaseManager.getCurrentMood().getDaysAgo());
    }

    // For an insert that is not from current day.
    @Test
    public void should_insert_with_date_in_the_database() {
        // Given
        String mood = "Sad";
        String comment = "Test";
        String color = "red_faded";

        int daysAgo = 3;

        // When
        mDatabaseManager.insertMood(mood, color, comment, daysAgo);

        // Then
        assertEquals(1, mDatabaseManager.isHistory());
        System.out.println(mDatabaseManager.mMoodData());
    }
}