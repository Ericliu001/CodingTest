package com.ericliudeveloper.weatherforecast.model;

/**
 * Created by liu on 17/06/15.
 */
public class Currently {

    String time;
    String summary;
    String temperature;
    String humidity;

    public Currently(String time, String summary, String temperature, String humidity) {
        this.time = time;
        this.summary = summary;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
