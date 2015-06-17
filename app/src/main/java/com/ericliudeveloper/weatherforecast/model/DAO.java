package com.ericliudeveloper.weatherforecast.model;

import android.content.ContentResolver;

import com.ericliudeveloper.weatherforecast.MyApplication;

/**
 * Created by liu on 17/06/15.
 */
public interface DAO {


    ContentResolver mContentResolver = MyApplication.getAppContentResolver();
}
