package com.ericliudeveloper.weatherforecast.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.RemoteException;
import android.util.JsonReader;
import android.util.Log;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.model.WeatherInfo;
import com.ericliudeveloper.weatherforecast.model.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;
import com.ericliudeveloper.weatherforecast.util.LastLocationFinder;
import com.ericliudeveloper.weatherforecast.util.NetworkConstants;
import com.ericliudeveloper.weatherforecast.util.Parser;
import com.ericliudeveloper.weatherforecast.util.RestfulUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class RetrieveWeatherDataService extends IntentService implements NetworkConstants, DBConstants.WeatherInfoColumns {


    private static final String TAG = "REST" ;

    public RetrieveWeatherDataService() {
        super("RetrieveWeatherDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Location location = getMyLocation();
        if (location != null) {
            String gpsCoordinates = location.getLatitude() + "," + location.getLongitude();

            retrieveWeatherData(gpsCoordinates);
        }

    }

    private Location getMyLocation() {
        return LastLocationFinder.getLastBestLocation(1000, 5000);
    }

    /**
     * Handle action retrieve weather data in the provided background thread with the provided
     * parameters.
     */
    private void retrieveWeatherData(String gpsCoordinates) {
        // TODO: Handle action


        String urlString = "https://api.forecast.io/forecast/69b4c892f57f9e7cefc65fc4d1fcb941/" + gpsCoordinates;

        Uri uri = Uri.parse(urlString);
        try {
            RestfulUtil.sendRequest(uri, null, new RestfulUtil.ResponseHandler() {
                @Override
                public void handleResponse(BufferedReader in) throws IOException {
                    JsonReader reader = new JsonReader(in);
                    WeatherInfo info = Parser.readWeatherInfo(reader);
                    if (info != null) {

                        try {
                            mergeToLocalDB(info);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (OperationApplicationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeToLocalDB(WeatherInfo info) throws RemoteException, OperationApplicationException {


        ContentResolver resolver = getApplication().getContentResolver();
        Uri weatherinfoUri = ProviderContract.WeatherInfos.CONTENT_URI;
        String[] projection = ProviderContract.WeatherInfos.PROJECTION;




        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        Cursor cursor = resolver.query(weatherinfoUri, projection, null, null, null);
        if (cursor != null ) {
            // delete all existing entries with the time_stamp current
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ROWID));
                String timeStamp = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME_STAMP));
                if (timeStamp.equals(DBConstants.TimeStamps.CURRENT)) {
                    Uri deleteUri = ProviderContract.WeatherInfos.CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
                    Log.i(TAG, "Scheduling delete: " + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                }
            }

            resolver.applyBatch(ProviderContract.CONTENT_AUTHORITY, batch);
        }

        ContentValues insertValues = WeatherinfoDAO.getContentValuesFromWeatherinfoInstance(info);
        // set the time_stamp as current
        insertValues.put(COL_TIME_STAMP, DBConstants.TimeStamps.CURRENT);
        Uri uri = resolver.insert(weatherinfoUri, insertValues);
        Log.i(TAG, "updated : " + uri);
    }


}
