package com.ericliudeveloper.weatherforecast.model;

/**
 * Created by liu on 17/06/15.
 */
public class WeatherInfo {

    private String latitude;
    private String longitude;
    private String timezone;

    public Currently getCurrently() {
        return currently;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    private Currently currently;


    private WeatherInfo(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.timezone = builder.timezone;
        this.currently = builder.currently;
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
