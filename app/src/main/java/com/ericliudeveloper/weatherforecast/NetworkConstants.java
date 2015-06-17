package com.ericliudeveloper.weatherforecast;

/**
 * Created by liu on 17/06/15.
 */
public interface NetworkConstants {

    public static final String HEADER_ENCODING = "Accept-Encoding";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String MIME_ANY = "*/*";
    public static final String MIME_JSON = "application/json;charset=UTF-8";
    public static final String ENCODING_NONE = "identity";

    public static final int HTTP_READ_TIMEOUT = 30 * 1000; // ms
    public static final int HTTP_CONN_TIMEOUT = 30 * 1000; // ms
}
