package com.daniel.alarmclock.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.daniel.alarmclock.models.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmsDBAdapter {
    AlarmsDBHelper alarmsDBHelper;

    public AlarmsDBAdapter(Context context) {
        alarmsDBHelper = new AlarmsDBHelper(context);
    }

    public long insert(ContentValues cv) {
        SQLiteDatabase dbb = alarmsDBHelper.getWritableDatabase();
        long id = dbb.insert(AlarmsDBHelper.TABLE_NAME, null, cv);
        return id;
    }

    public List<Alarm> getAlarmsList() {
        SQLiteDatabase dbb = alarmsDBHelper.getWritableDatabase();
        String[] columns = {
                AlarmsDBHelper.UID,
                AlarmsDBHelper.HOUR,
                AlarmsDBHelper.MINUTE,
                AlarmsDBHelper.TITLE,
                AlarmsDBHelper.ACTIVE,
                AlarmsDBHelper.REPEAT,
                AlarmsDBHelper.SUNDAY,
                AlarmsDBHelper.MONDAY,
                AlarmsDBHelper.TUESDAY,
                AlarmsDBHelper.WEDNESDAY,
                AlarmsDBHelper.THURSDAY,
                AlarmsDBHelper.FRIDAY,
                AlarmsDBHelper.SATURDAY};
        Cursor cursor = dbb.query(AlarmsDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        List<Alarm> res = new ArrayList<Alarm>();
        while (cursor.moveToNext()) {
            Alarm a = new Alarm(
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.HOUR)),
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.MINUTE)),
                    cursor.getString(cursor.getColumnIndex(AlarmsDBHelper.TITLE)),
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.REPEAT)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.SUNDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.MONDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.TUESDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.WEDNESDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.THURSDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.FRIDAY)) > 0,
                    cursor.getInt(cursor.getColumnIndex(AlarmsDBHelper.SATURDAY)) > 0
            );
            res.add(a);
        }

        return res;

    }

    static class AlarmsDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "ALARMS_DB";
        private static final String TABLE_NAME = "ALARMS";
        private static final int DATABASE_Version = 4;
        private static final String UID = "alarm_id";
        private static final String HOUR = "HOUR";
        private static final String MINUTE = "MINUTE";
        private static final String TITLE = "TITLE";
        private static final String ACTIVE = "ACTIVE";
        private static final String REPEAT = "REPEAT";
        private static final String SUNDAY = "SUNDAY";
        private static final String MONDAY = "MONDAY";
        private static final String TUESDAY = "TUESDAY";
        private static final String WEDNESDAY = "WEDNESDAY";
        private static final String THURSDAY = "THURSDAY";
        private static final String FRIDAY = "FRIDAY";
        private static final String SATURDAY = "SATURDAY";

        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        HOUR + " NUMERIC, " +
                        MINUTE + " NUMERIC, " +
                        TITLE + " VARCHAR(255), " +
                        ACTIVE + " NUMERIC, " +
                        REPEAT + " NUMERIC, " +
                        SUNDAY + " NUMERIC, " +
                        MONDAY + " NUMERIC, " +
                        TUESDAY + " NUMERIC, " +
                        WEDNESDAY + " NUMERIC, " +
                        THURSDAY + " NUMERIC, " +
                        FRIDAY + " NUMERIC, " +
                        SATURDAY + " NUMERIC)";

        private static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        private final Context context;

        public AlarmsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Log.e("ALARM_CLOCK_ONCREATE", e.getLocalizedMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Log.e("ALARM_CLOCK_ONUPGRADE", e.getLocalizedMessage());
            }
        }
    }
}
