package com.ericliudeveloper.weatherforecast.ui;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.weatherforecast.MyApplication;
import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.model.WeatherInfo;
import com.ericliudeveloper.weatherforecast.model.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayWeatherInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    TextView tvLatitude, tvLongitude, tvTimezone, tvSummary, tvTemperature;

    private WeatherInfo mWeatherInfo = null;

    public DisplayWeatherInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_display_weather_info, container, false);
        setupViews(root);
        return root;
    }

    private void setupViews(View root) {
        tvLatitude = (TextView) root.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) root.findViewById(R.id.tvLongitude);
        tvTimezone = (TextView) root.findViewById(R.id.tvTimezone);
        tvSummary = (TextView) root.findViewById(R.id.tvSummary);
        tvTemperature = (TextView) root.findViewById(R.id.tvTemperature);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    private void refreshDisplay(WeatherInfo info) {
        String lat = info.getLatitude();
        if (!TextUtils.isEmpty(lat)) {
            tvLatitude.setText(lat);
        }

        String lon = info.getLongitude();
        if (!TextUtils.isEmpty(lon)) {
            tvLongitude.setText(lon);
        }

        String timezone = info.getTimezone();
        if (!TextUtils.isEmpty(timezone)) {
            tvTimezone.setText(timezone);
        }



        String summary = info.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            tvSummary.setText(summary);
        }

        String temperature = info.getTemperature();
        if (!TextUtils.isEmpty(temperature)) {
            tvTemperature.setText(temperature);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        Uri uri = ProviderContract.WeatherInfos.CONTENT_URI;
        String[] projection = ProviderContract.WeatherInfos.PROJECTION;
        loader = new CursorLoader(MyApplication.getApplication(), uri, projection, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            WeatherInfo info = WeatherinfoDAO.getWeatherInfoFromCursor(data);
            if (info != null) {
                refreshDisplay(info);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
