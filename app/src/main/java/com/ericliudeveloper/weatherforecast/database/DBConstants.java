package com.ericliudeveloper.weatherforecast.database;

import android.provider.BaseColumns;

/**
 * Created by liu on 17/06/15.
 */
public final class DBConstants {
    private DBConstants(){}



    public interface TableAndView{
        String TABLE_WEATHERINFO = "weatherinfo";
        String VIEW_CURRENT_WEATHERINFO = "view_current_weatherinfo";
    }

    public interface TimeStamps{
        String CURRENT = "current";
        String PAST = "past";
    }

    public interface CommonColumns extends BaseColumns{
        String COL_ROWID = _ID;
        String COL_TIME_STAMP = "time_stamp";
    }

    public interface WeatherInfoColumns extends  CommonColumns{
        String COL_LONGITUDE = "longitude";
        String COL_LATITUDE = "latitude";
        String COL_TIMEZONE = "timezone";
        String COL_TIME = "time";
        String COL_SUMMARY = "summary";
        String COL_TEMPERATURE = "temperature";
        String COL_HUMIDITY = "humidity";
    }

}
