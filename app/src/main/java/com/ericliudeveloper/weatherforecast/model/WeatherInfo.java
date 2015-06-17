package com.ericliudeveloper.weatherforecast.model;

/**
 * Created by liu on 17/06/15.
 */
public class WeatherInfo {

    private String latitude;
    private String longitude;
    private String timezone;


    String time;
    String summary;
    String temperature;

    public String getHumidity() {
        return humidity;
    }

    public String getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getTemperature() {
        return temperature;
    }

    String humidity;



    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }



    private WeatherInfo(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.timezone = builder.timezone;

        setValuesFromCurrently(builder.currently);
    }

    private void setValuesFromCurrently(Currently currently) {
        this.time = currently.time;
        this.summary = currently.summary;
        this.temperature = currently.temperature;
        this.humidity = currently.humidity;
    }

    public static class Builder{
        private String latitude;
        private String longitude;
        private String timezone;

        private Currently currently;

        public Builder latitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder currently(Currently currently) {
            this.currently = currently;
            return this;
        }

        public WeatherInfo build() {
            return new WeatherInfo(this);
        }
    }
}
