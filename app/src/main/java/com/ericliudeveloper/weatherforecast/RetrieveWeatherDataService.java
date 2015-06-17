package com.ericliudeveloper.weatherforecast;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;


public class RetrieveWeatherDataService extends IntentService implements  NetworkConstants{





    private static final String TAG = "REST";





    public interface ResponseHandler {
        void handleResponse(BufferedReader in) throws IOException;
    }


    public RetrieveWeatherDataService() {
        super("RetrieveWeatherDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleActionRetrieveWeatherData();
    }

    /**
     * Handle action retrieve weather data in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRetrieveWeatherData() {
        // TODO: Handle action


        String urlString  = "https://api.forecast.io/forecast/69b4c892f57f9e7cefc65fc4d1fcb941/37.8267,-122.423";

        Uri uri = Uri.parse(urlString);
        try {
            sendRequest(uri, null, new ResponseHandler() {
                @Override
                public void handleResponse(BufferedReader in) throws IOException {

                    String text = "";
                    String aux = "";

                    while ((aux = in.readLine()) != null) {
                        text += aux;
                    }

                    Log.d(TAG, text);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // the return code is being ignored, at present
    private int sendRequest(
            Uri uri,
            String payload,
            ResponseHandler hdlr)
            throws IOException
    {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "sending " + " @" + uri + ": " + payload);
        }

        HttpURLConnection conn
                = (HttpURLConnection) new URL(uri.toString()).openConnection();
        int code = HttpURLConnection.HTTP_UNAVAILABLE;
        try {
            conn.setReadTimeout(HTTP_READ_TIMEOUT);
            conn.setConnectTimeout(HTTP_CONN_TIMEOUT);
            conn.setRequestMethod("GET");
//            conn.setRequestProperty(HEADER_USER_AGENT, USER_AGENT);
            conn.setRequestProperty(HEADER_ENCODING, ENCODING_NONE);

            if (null != hdlr) {
                conn.setRequestProperty(HEADER_ACCEPT, MIME_JSON);
                conn.setDoInput(true);
            }

            if (null != payload) {
                conn.setRequestProperty(HEADER_CONTENT_TYPE, MIME_JSON);
                conn.setFixedLengthStreamingMode(payload.length());
                conn.setDoOutput(true);

                conn.connect();
                Writer out = new OutputStreamWriter(
                        new BufferedOutputStream(conn.getOutputStream()),
                        "UTF-8");
                out.write(payload);
                out.flush();
            }

            code = conn.getResponseCode();

            if (null != hdlr) {
                hdlr.handleResponse(new BufferedReader(
                        new InputStreamReader(conn.getInputStream())));
            }
        }
        finally {
            if (null != conn) {
                try { conn.disconnect(); } catch (Exception e) { }
            }
        }

        return code;
    }






}
