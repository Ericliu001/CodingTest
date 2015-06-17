package com.ericliudeveloper.weatherforecast.model;

/**
 * Created by liu on 17/06/15.
 */
public class Currently {

    String time;
    String summary;
    String temperature;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    String humidity;

    public Currently(){}

    public Currently(String time, String summary, String temperature, String humidity) {
        this.time = time;
        this.summary = summary;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
