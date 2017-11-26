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
    public void should_insert_in_the_database() {

        // Given
        String mood = "Sad";
        String comment = "Test";
        String color = "red_faded";

        // When
        mDatabaseManager.insertMood(mood, color, comment);

        // Then
        assertEquals("Sad", mDatabaseManager.getCurrentMood().getMood());
        assertEquals("Test", mDatabaseManager.getCurrentMood().getComment());
        assertEquals("red_faded", mDatabaseManager.getCurrentMood().getColor());
        assertEquals("Aujourd'hui", mDatabaseManager.getCurrentMood().getDaysAgo());
    }
}