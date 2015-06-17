package com.ericliudeveloper.weatherforecast.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.service.RetrieveWeatherDataService;


public class MainActivity extends BaseActivity {

    private String tag = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment frag = getFragmentManager().findFragmentByTag(tag);
        if (frag == null) {
            frag = new DisplayWeatherInfoFragment();
            getFragmentManager().beginTransaction().add(R.id.container, frag, tag).commit();
        }

        refreshWeatherData();
    }

    public void refreshWeatherData() {
        checkNetworkAndShowErrorMsg();
        checkLocationAvailableAndShowErrorMsg();
        Intent intent = new Intent(this, RetrieveWeatherDataService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
