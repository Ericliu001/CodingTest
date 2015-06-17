package com.ericliudeveloper.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.ericliudeveloper.weatherforecast.model.Currently;
import com.ericliudeveloper.weatherforecast.model.WeatherInfo;
import com.ericliudeveloper.weatherforecast.model.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;
import com.ericliudeveloper.weatherforecast.provider.WeatherProvider;

/**
 * Created by liu on 17/06/15.
 */
public class ProviderTests extends ProviderTestCase2<WeatherProvider> {
    /**
     * Constructor.
     */
    public ProviderTests() {
        super(WeatherProvider.class, ProviderContract.CONTENT_AUTHORITY);
    }


    private Context mContext;
    private MockContentResolver mResolver;
    // Contains an SQLite database, used as test data
    private SQLiteDatabase mDb;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getMockContext();
        mResolver = getMockContentResolver();
    }



    Uri weatherinfoUri = ProviderContract.WeatherInfos.CONTENT_URI;
    String[] weatherinfoProjection = ProviderContract.WeatherInfos.PROJECTION;

    public void testInsertAndQueryWeatherinfo(){

        Currently currently = new Currently("1 o clock", "We are happy","23", "40%");
        WeatherInfo.Builder builder = new WeatherInfo.Builder();
        builder.latitude("23.436353");
        builder.longitude("34.346354");
        builder.timezone("+10");
        builder.currently(currently);

        WeatherInfo info = builder.build();

        ContentValues values = WeatherinfoDAO.getContentValuesFromWeatherinfoInstance(info);

        Uri uri = mResolver.insert(weatherinfoUri, values);
        assertNotNull("returned null uri when inserting  weatherinfo", uri);


        Cursor cursor =  mResolver.query(weatherinfoUri, weatherinfoProjection, null, null, null);
        cursor.moveToFirst();

        WeatherInfo retrievedInfo = WeatherinfoDAO.getWeatherInfoFromCursor(cursor);

        assertEquals("saved and retrieved weather info not consistent.", retrievedInfo.getLatitude(), info.getLatitude());
    }

}
