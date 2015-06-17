package com.ericliudeveloper.weatherforecast.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.database.DBHelper;

import static com.ericliudeveloper.weatherforecast.provider.ProviderContract.*;

public class WeatherProvider extends ContentProvider implements DBConstants.TableAndView, DBConstants.CommonColumns {
    public WeatherProvider() {
    }


    private static final int CODE_WEATHERINFOS = 1;
    private static final int CODE_WEATHERINFO_ID = 10;


    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        //weatherinfo
        URI_MATCHER.addURI(CONTENT_AUTHORITY, PATH_WEATHERINFO, CODE_WEATHERINFOS);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, PATH_WEATHERINFO + "/#", CODE_WEATHERINFO_ID);
    }


    private DBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return (mDBHelper == null) ? false : true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        int uriMatch = URI_MATCHER.match(uri);
        final SelectionBuilder sb = new SelectionBuilder();
        sb.where(selection, selectionArgs);

        switch (uriMatch) {
            case CODE_WEATHERINFOS:
                sb.table(TABLE_WEATHERINFO);
                break;

            case CODE_WEATHERINFO_ID:
                sb.table(TABLE_WEATHERINFO);
                sb.where(COL_ROWID + "=?", uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }

        Cursor cursor = sb.query(db, projection, sortOrder);

        // MUST NOT MISS this line or the CursorLoader won't be able to automatically reload.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long rowID = -1;
        Uri newUri = null;
        int uriMatch = URI_MATCHER.match(uri);

        switch (uriMatch) {
            case CODE_WEATHERINFOS:
                rowID = db.insert(TABLE_WEATHERINFO, null, values);
                newUri = ContentUris.withAppendedId(ProviderContract.WeatherInfos.CONTENT_URI, rowID);
                break;


            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return rowID >= 0 ? newUri : null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        SelectionBuilder sb = new SelectionBuilder();
        sb.where(selection, selectionArgs);


        int count = -1;
        int uriMatch = URI_MATCHER.match(uri);
        String idString = uri.getLastPathSegment();

        switch (uriMatch) {
            case CODE_WEATHERINFO_ID:

                count = sb.table(TABLE_WEATHERINFO).where(COL_ROWID + "=?", idString).update(db, values);
                break;


            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        SelectionBuilder sb = new SelectionBuilder();
        sb.where(selection, selectionArgs);


        int count = -1;
        String idString = null;
        int uriMatch = URI_MATCHER.match(uri);
        switch (uriMatch) {
            case CODE_WEATHERINFO_ID:
                count = sb.table(TABLE_WEATHERINFO).delete(db);
                break;

            case CODE_WEATHERINFOS:
                idString = uri.getLastPathSegment();
                count = sb.table(TABLE_WEATHERINFO).where(COL_ROWID + "=?", idString).delete(db);
                break;

            default:
                throw new IllegalArgumentException(" Unknow URL " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
