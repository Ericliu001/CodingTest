package com.ericliudeveloper.weatherforecast.ui;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ericliudeveloper.weatherforecast.MyApplication;
import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.dialog.MessageDialog;

import java.util.List;

/**
 * Created by liu on 17/06/15.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNetworkAndShowErrorMsg();
    }

    protected void checkNetworkAndShowErrorMsg(){
        if (!isOnline()) {
            String title = getResources()
                    .getString(R.string.network_error);

            String message = getResources()
                    .getString(R.string.network_unavailable);

            showErrorDialog(title, message);
        }
    }

    protected void checkLocationAvailableAndShowErrorMsg() {
        if (!isLocationAvailable()) {
            String title = getResources().getString(R.string.location_error);
            String message = getResources().getString(R.string.cant_retrieve_location);

            showErrorDialog(title, message);
        }
    }

    public boolean isLocationAvailable(){
        LocationManager locationManager = (LocationManager) MyApplication
                .getApplication().getSystemService(Context.LOCATION_SERVICE);
        List<String> locationProviders = locationManager.getAllProviders();

        boolean locationEnabled = false;
        for (String provider : locationProviders) {
            if (locationManager.isProviderEnabled(provider)) {
                locationEnabled = true;
            }
        }

        return  locationEnabled;

    }

    private void showErrorDialog(String title, String message) {
        Bundle args = new Bundle();

        args.putString(MessageDialog.TITLE, title);


        args.putString(MessageDialog.MESSAGE, message);
        MessageDialog messageDialog = MessageDialog
                .newInstance(args);
        messageDialog.show(getFragmentManager(), "message");
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
