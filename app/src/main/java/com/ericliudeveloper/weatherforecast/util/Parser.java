package com.ericliudeveloper.weatherforecast.util;

import android.util.JsonReader;

import com.ericliudeveloper.weatherforecast.model.Currently;
import com.ericliudeveloper.weatherforecast.model.WeatherInfo;

import java.io.IOException;

/**
 * Created by liu on 17/06/15.
 */
public final class Parser {

    private Parser() {
    }

    public static WeatherInfo readWeatherInfo(JsonReader reader) throws IOException {

        WeatherInfo.Builder builder = new WeatherInfo.Builder();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();


            if (name.equals("latitude")) {
                builder.latitude(reader.nextString());
            } else if (name.equals("longitude")) {
                builder.longitude(reader.nextString());
            } else if (name.equals("timezone")) {
                builder.timezone(reader.nextString());
            } else if (name.equals("currently")) {
                builder.currently(readCurrently(reader));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return builder.build();
    }

    public static Currently readCurrently(JsonReader reader) throws IOException {
        String time = null;
        String summary = null;
        String temperature = null;
        String humidity = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("time")) {
                time = reader.nextString();
            } else if (name.equals("summary")) {
                summary = reader.nextString();
            } else if (name.equals("temperature")) {
                temperature = reader.nextString();
            } else if (name.equals("humidity")) {
                humidity = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return new Currently(time, summary, temperature, humidity);
    }
}
