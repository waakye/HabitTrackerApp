package com.waakye.habittrackerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

/**
 * Created by lesterlie on 3/24/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weeklyhabits.db";

    /** EditText field to enter habit name */
    private EditText mHabitNameEditText;

    /** EditText field to enter habit location */
    private EditText mHabitLocationEditText;

    /** EditText field to enter habit frequency */
    private EditText mHabitFrequencyEditText;

    /** EditText field to enter habit fee */
    private EditText mHabitFeeEditText;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL DEFAULT 0, "
                + HabitContract.HabitEntry.COLUMN_HABIT_FEE + " INTEGER NOT NULL DEFAULT 0, "
                + HabitContract.HabitEntry.COLUMN_HABIT_LOCATION + " TEXT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_SOCIAL + " INTEGER);";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * A method that reads the database
     * @return Cursor
     */
    public Cursor readData() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_LOCATION,
                HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitContract.HabitEntry.COLUMN_HABIT_FEE
        };

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }


    /**
     * Method takes entries from the UX and enters it into the database
     * @param habitName
     * @param habitLocation
     * @param habitFrequency
     * @param habitFee
     * @return anyErrors describes whether there was any problem in entering the data
     */
    public String insertData(String habitName, String habitLocation, String habitFrequency,
                           String habitFee) {

        String anyErrors = "";

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, habitName);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, habitLocation);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY, habitFrequency);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FEE, habitFee);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if(newRowId == -1) {
            return anyErrors = "Error with saving habit";
        } else {
            return anyErrors = "Habit saved with row id: " + newRowId;
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
