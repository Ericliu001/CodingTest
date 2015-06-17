package com.ericliudeveloper.weatherforecast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eric.liu on 9/06/15.
 */
public final class MyDateUtils {

    private static final String DATE_WRONG = "date wrong";
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.US);
    ;

    private MyDateUtils() {
        // to prevent this class to be instantiated
    }


    public static String getStringFromDate(String timeString) throws ParseException {

        Date date = dateFormat
                .parse(timeString);

        return dateFormat.format(date);
    }


}
