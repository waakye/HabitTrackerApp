package com.waakye.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.waakye.habittrackerapp.data.HabitContract;
import com.waakye.habittrackerapp.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    /** EditText field to enter habit name */
    private EditText mHabitNameEditText;

    /** EditText field to enter habit location */
    private EditText mHabitLocationEditText;

    /** EditText field to enter habit frequency */
    private EditText mHabitFrequencyEditText;

    /** EditText field to enter habit fee */
    private EditText mHabitFeeEditText;

    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Find all relevant views that we need to read user input from
        mHabitNameEditText = (EditText)findViewById(R.id.edit_habit_name);
        mHabitLocationEditText = (EditText)findViewById(R.id.edit_habit_location);
        mHabitFrequencyEditText = (EditText)findViewById(R.id.edit_habit_frequency);
        mHabitFeeEditText = (EditText)findViewById(R.id.edit_habit_fee);

    }

    @Override
    protected void onStart(){
        super.onStart();
        displayHabits();
    }

    private void displayHabits() {
        readData();
        Cursor abc = readData();
        displayData(abc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }

    // TODO: Create a read method using "Saving Data in SQL Databases" as a guide
    public Cursor readData() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


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
     * Display information in the onscreen TextView about the state of
     * the habits database.
     */
    private void displayData(Cursor mCursor) {

        TextView displayView = (TextView)findViewById(R.id.text_view_habit);

        try {
            // Create a header  in the TextView that looks like this
            // The habits table contains <number of rows in Cursor> habits.
            // Habit Name - Habit Location - Habit Frequency
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order
            displayView.setText("The habits table contains " + mCursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry.COLUMN_HABIT_NAME + " - "
                    + HabitContract.HabitEntry.COLUMN_HABIT_LOCATION + " - "
                    + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY
                    + "\n");

            // Figure out the index of each column
            int idColumnIndex = mCursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = mCursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int locationColumnIndex = mCursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION);
            int frequencyColumnIndex = mCursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY);
            int feeColumnIndex = mCursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_FEE);

            // Iterate through all of the returned rows in the cursor
            while(mCursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on
                int currentID = mCursor.getInt(idColumnIndex);
                String currentName = mCursor.getString(nameColumnIndex);
                String currentLocation = mCursor.getString(locationColumnIndex);
                String currentFrequency = mCursor.getString(frequencyColumnIndex);
                String currentFee = mCursor.getString(feeColumnIndex);

                // Display the values from each column of the current row in the TextView
                displayView.append("\n" + currentName
                        + " - " + currentLocation
                        + " - " + currentFrequency);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            mCursor.close();
        }
    }

    private void insertHabit() {

        // Read from the input fields
        // Use trim to eliminate leading or trailing white space
        String habitNameString = mHabitNameEditText.getText().toString().trim();
        String habitLocationString = mHabitLocationEditText.getText().toString().trim();
        String habitFrequencyString = mHabitFrequencyEditText.getText().toString().trim();
        String habitFeeString = mHabitFeeEditText.getText().toString().trim();

        // Create an instance of the HabitDbHelper object
        HabitDbHelper mDbHelper1 = new HabitDbHelper(this);

        // Get the data repository of the SQLiteDatabasein the write mode
        SQLiteDatabase db = mDbHelper1.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, habitNameString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, habitLocationString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY, habitFrequencyString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FEE, habitFeeString);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if(newRowId == -1) {
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_save:
                // Save habit to database
                insertHabit();
                // Exit activity
                return true;
            case R.id.action_display:
                // Display search results
                displayHabits();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
