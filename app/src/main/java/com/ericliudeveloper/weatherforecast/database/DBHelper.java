package com.ericliudeveloper.weatherforecast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liu on 17/06/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Weather.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableWeatherInfo.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TableWeatherInfo.onUpgrade(db);
    }
}
