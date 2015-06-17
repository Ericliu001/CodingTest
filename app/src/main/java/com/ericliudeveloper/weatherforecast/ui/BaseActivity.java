package com.ericliudeveloper.weatherforecast.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.dialog.MessageDialog;

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
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        Bundle args = new Bundle();
        String title = getResources()
                .getString(R.string.network_error);
        args.putString(MessageDialog.TITLE, title);

        String must_fill_fields_bill = getResources()
                .getString(R.string.network_unavailable);
        args.putString(MessageDialog.MESSAGE, must_fill_fields_bill);
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
