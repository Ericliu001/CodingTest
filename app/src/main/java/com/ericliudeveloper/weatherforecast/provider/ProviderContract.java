package com.ericliudeveloper.weatherforecast.provider;

import android.net.Uri;

import com.ericliudeveloper.weatherforecast.database.DBConstants;

/**
 * Created by liu on 17/06/15.
 */
public class ProviderContract {



    public static final String CONTENT_AUTHORITY = "com.ericliudeveloper.weatherforecast.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_WEATHERINFO = "weatherinfos";


    public static class WeatherInfos implements DBConstants.WeatherInfoColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHERINFO).build();

        public static Uri buildWeatherInfoUri(String weatherInfoId) {
            return CONTENT_URI.buildUpon().appendPath(weatherInfoId).build();
        }

        public static String[] PROJECTION ={
                COL_ROWID
                ,COL_TIME_STAMP
                ,COL_LONGITUDE
                ,COL_LATITUDE
                ,COL_TIMEZONE
                ,COL_TIME
                ,COL_SUMMARY
                ,COL_TEMPERATURE
                ,COL_HUMIDITY
        };
    }
}
