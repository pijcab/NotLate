package com.projet.e4fi.notlate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kiros on 02/03/2017.
 */

public class AlarmDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarm_database.db";
    private static final String NOM_TABLE = "ALARM_DTB";

    private static final String ALARM_DATABASE =
            "CREATE TABLE " + NOM_TABLE + " (" +
                    "HOUR" + " INTEGER, " +
                    "MINUTES" + " INTEGER, " +
                    "DATE" + " INTEGER, " +
                    "ID" + " INTEGER, " +
                    "MUSIQUE_ID" + " INTEGER);";

    private static final String ALARM_DATABASE_DELETE =
            "DROP TABLE IF EXISTS " + NOM_TABLE;


    public AlarmDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALARM_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ALARM_DATABASE_DELETE);
        onCreate(db);
    }

    public long createAlarm(AlarmModel AlarmM) {
        ContentValues values = fillContent(AlarmM);
        return getWritableDatabase().insert("ALARM_DTB", null, values);
    }

    private Calendar fillCalendar(Cursor c) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, c.getInt(c.getColumnIndex("HOUR")));
        calendar.set(Calendar.MINUTE, c.getInt(c.getColumnIndex("MINUTES")));
        calendar.set(Calendar.MINUTE, c.getInt(c.getColumnIndex("DATE")));
        return calendar;
    }

    public AlarmModel fillAlarmModel(Cursor c) {
        AlarmModel alarm = new AlarmModel();
        alarm.Hour = c.getInt(c.getColumnIndex("HOUR"));
        alarm.Minutes = c.getInt(c.getColumnIndex("MINUTES"));
        alarm.Date = c.getInt(c.getColumnIndex("DATE"));
        alarm.id = c.getInt(c.getColumnIndex("ID"));
        alarm.MusiqueId = c.getInt(c.getColumnIndex("MUSIQUE_ID"));

        return alarm;
    }

    private ContentValues fillContent(AlarmModel alarmM) {
        ContentValues values = new ContentValues();
        values.put("HOUR", alarmM.Hour);
        values.put("MINUTES", alarmM.Minutes);
        values.put("DATE", alarmM.Date);
        values.put("ID", alarmM.id);
        values.put("MUSIQUE_ID", alarmM.MusiqueId);

        return values;
    }

    public ArrayList<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + "ALARM_DTB";

        Cursor c = db.rawQuery(select, null);

        ArrayList<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            alarmList.add(fillAlarmModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(NOM_TABLE, "ID" + " = ?", new String[]{String.valueOf(id)});
    }

    /*private ContentValues fillDatabase(Calendar calendar)
    {
        ContentValues values = new ContentValues();
        values.put("HOUR",calendar.get(Calendar.HOUR));
        values.put("MINUTES",calendar.get(Calendar.MINUTE));
        values.put("DATE",calendar.get(Calendar.DATE));

        return values;
    }*/
}
