package com.ericliudeveloper.weatherforecast.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liu on 17/06/15.
 */
public class TableWeatherInfo implements DBConstants.TableAndView, DBConstants.WeatherInfoColumns, DBConstants.TimeStamps{

    //table creation sql string
    private static final String  TABLE_CREATE =
        " create table " + TABLE_WEATHERINFO + "  ( "
                + COL_ROWID + " integer primary key autoincrement, "
                + COL_TIME_STAMP + " text, "
                + COL_LONGITUDE + " text, "
                + COL_LATITUDE + " text, "
                + COL_TIMEZONE + " text, "
                + COL_TIME + " text, "
                + COL_SUMMARY + " text, "
                + COL_TEMPERATURE + " text, "
                + COL_HUMIDITY + " text "
                + " ); "
                ;



    private static final String VIEW_CURRENT_WEATHERINFO_CREATE =
            " create view " + VIEW_CURRENT_WEATHERINFO + " as select "
                    + COL_ROWID + " , "
                    + COL_TIME_STAMP + " , "
                    + COL_LONGITUDE + " , "
                    + COL_LATITUDE + " , "
                    + COL_TIMEZONE + " , "
                    + COL_TIME + " , "
                    + COL_SUMMARY + " , "
                    + COL_TEMPERATURE + " , "
                    + COL_HUMIDITY + "  "
                    + " from "
                    + TABLE_WEATHERINFO
                    + " where " + COL_TIME_STAMP + " = " + CURRENT
                    + ";"
            ;


    public static void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
        db.execSQL(VIEW_CURRENT_WEATHERINFO_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_WEATHERINFO);
        onCreate(db);
    }

}
