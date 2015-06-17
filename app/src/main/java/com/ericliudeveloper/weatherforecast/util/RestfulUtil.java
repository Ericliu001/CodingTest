package com.ericliudeveloper.weatherforecast.util;

import android.net.Uri;
import android.util.Log;

import com.ericliudeveloper.weatherforecast.BuildConfig;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liu on 17/06/15.
 */
public final class RestfulUtil implements  NetworkConstants{
    private static final String TAG = "REST";

    private RestfulUtil(){}


    public interface ResponseHandler {
        void handleResponse(BufferedReader in) throws IOException;
    }

    public static int sendRequest(
            Uri uri,
            String payload,
            ResponseHandler hdlr)
            throws IOException {
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
        } finally {
            if (null != conn) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                }
            }
        }

        return code;
    }
}
