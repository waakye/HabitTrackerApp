package com.waakye.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by lesterlie on 3/24/17.
 */

public final class HabitContract {

    private HabitContract() {}

    /** Inner class that defines table contents of the habits table */
    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;

        /** Name of the habit */
        public static final String COLUMN_HABIT_NAME = "name";

        /** Frequency habit conducted per week */
        public static final String COLUMN_HABIT_FREQUENCY = "frequency";

        /** Cost of participating in each habit per week */
        public static final String COLUMN_HABIT_FEE = "fee";

        /** Location of the habit */
        public static final String COLUMN_HABIT_LOCATION = "location";

        /** Is the habit a social activity? */
        public static final String COLUMN_HABIT_SOCIAL = "social activity";

    }

}
