package com.ericliudeveloper.weatherforecast.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;

/**
 * Created by liu on 17/06/15.
 */
public final class WeatherinfoDAO implements DAO, DBConstants.WeatherInfoColumns{
    static  String[] projection = ProviderContract.WeatherInfos.PROJECTION;
    static  Uri weatherInfosUri = ProviderContract.WeatherInfos.CONTENT_URI;


    public static ContentValues getContentValuesFromWeatherinfoInstance(WeatherInfo info) {
        if (info == null) {
            return  null;
        }

        ContentValues values = new ContentValues();
        values.put(COL_LONGITUDE, info.getLongitude());
        values.put(COL_LATITUDE, info.getLatitude());
        values.put(COL_TIMEZONE, info.getTimezone());
        values.put(COL_TIME, info.getTime());
        values.put(COL_SUMMARY, info.getSummary());
        values.put(COL_TEMPERATURE, info.getTemperature());
        values.put(COL_HUMIDITY, info.getHumidity());


        return values;
    }


    public static  WeatherInfo getWeatherInfoFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        WeatherInfo.Builder builder = new WeatherInfo.Builder();
        builder.longitude(cursor.getString(cursor.getColumnIndexOrThrow(COL_LONGITUDE)));
        builder.latitude(cursor.getString(cursor.getColumnIndexOrThrow(COL_LATITUDE)));
        builder.timezone(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIMEZONE)));

        Currently currently = new Currently();
        currently.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)));
        currently.setSummary(cursor.getString(cursor.getColumnIndexOrThrow(COL_SUMMARY)));
        currently.setTemperature(cursor.getString(cursor.getColumnIndexOrThrow(COL_TEMPERATURE)));
        currently.setHumidity(cursor.getString(cursor.getColumnIndexOrThrow(COL_HUMIDITY)));

        builder.currently(currently);

        return builder.build();
    }
}
