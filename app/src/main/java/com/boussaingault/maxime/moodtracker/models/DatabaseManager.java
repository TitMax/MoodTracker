package com.boussaingault.maxime.moodtracker.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime Boussaingault on 15/11/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "History.db";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_ID = "ID";
    public static final String COL_MOOD = "MOOD";
    public static final String COL_COMMENT = "COMMENT";
    public static final String COL_COLOR = "COLOR";
    public static final String COL_DATE = "DATE";
    public static final String TABLE_NAME = "MOOD_HISTORY";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_MOOD + " TEXT NOT NULL, " +
                    COL_COMMENT + " TEXT, " +
                    COL_COLOR + " TEXT, " +
                    COL_DATE + " TEXT UNIQUE)";

    public static final String TABLE_DROP = "DROP TABLE " + TABLE_NAME;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    public void insertMood(String mood, String comment, String color) {
        mood = mood.replace("'", "''");
        comment = comment.replace("'", "''");
        color = color.replace("'", "''");

        SQLiteDatabase db = this.getWritableDatabase();

        // Replace if there is already an entry the current day, else Insert a new row
        final String ROW_INSERT = "REPLACE INTO " + TABLE_NAME +
                " (" + COL_ID + ", " + COL_MOOD + ", " + COL_COMMENT + ", " + COL_COLOR + ", " + COL_DATE + ") " +
                "VALUES ((SELECT " + COL_ID +
                " FROM " + TABLE_NAME +
                " WHERE " + COL_DATE + " = DATE ('NOW', 'LOCALTIME')), '"
                + mood + "', '" + comment + "', '" + color + "', DATE('NOW', 'LOCALTIME'))";
        db.execSQL(ROW_INSERT);
    }

    public MoodData getCurrentMood() {
        SQLiteDatabase db = this.getReadableDatabase();
        MoodData currentMood = new MoodData();
        String selectCurrent = "SELECT * " +
                "FROM " + TABLE_NAME + " " +
                "WHERE DATE('NOW', 'LOCALTIME', 'START OF DAY')";
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_ID, COL_MOOD, COL_COMMENT, COL_COLOR, COL_DATE},
                COL_DATE + " LIKE DATE('NOW', 'LOCALTIME', 'START OF DAY')",
                null, null, null, null);
        return cursorToMood(cursor);
    }

    private MoodData cursorToMood(Cursor cursor) {
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        MoodData currentMood = new MoodData();
        currentMood.setMood(cursor.getString(1));
        currentMood.setComment(cursor.getString(2));
        cursor.close();
        return currentMood;
    }
    public List<MoodData> mMoodData() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<MoodData> listMoods = new ArrayList<>();
        String selectMood = "SELECT * " +
                "FROM " + TABLE_NAME + " " +
                "WHERE DATE BETWEEN DATE('NOW', 'LOCALTIME', 'START OF DAY', '-7 DAY') " +
                "AND DATE('NOW', 'LOCALTIME', 'START OF DAY', '-1 DAY')";
        Cursor cursor = db.rawQuery(selectMood, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            MoodData moodData = new MoodData(cursor.getString(4),
                                             cursor.getString(1),
                                             cursor.getString(2),
                                             cursor.getString(3));
            listMoods.add(moodData);
            cursor.moveToNext();
        }
        db.close();
        return listMoods;
    }
}
