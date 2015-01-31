package com.nagarjuna_pamu.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by android on 28/1/15.
 */
public class WeatherProvider extends ContentProvider {

    private static final int WEATHER = 100;

    private static final int WEATHER_WITH_LOCATION = 101;

    private static final int WEATHER_WITH_LOCATION_AND_DATE = 102;

    private static final int LOCATION = 300;

    private static final int LOCATION_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private WeatherDBHelper helper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTEXT_AUTHORITY;
        matcher.addURI(authority, WeatherContract.PATH_WEATHER, WEATHER);
        matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
        matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/*", WEATHER_WITH_LOCATION_AND_DATE);

        matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
        matcher.addURI(authority, WeatherContract.PATH_LOCATION + "/#", LOCATION_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        helper = new WeatherDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor sCursor = null;
        switch (sUriMatcher.match(uri)) {
            case WEATHER_WITH_LOCATION_AND_DATE: {
                sCursor = null;
                break;
            }
            case WEATHER_WITH_LOCATION: {
                sCursor = null;
                break;
            }
            case WEATHER: {
                sCursor = helper.getReadableDatabase()
                                .query(WeatherContract.WeatherEntry.TABLE_NAME,
                                 projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case LOCATION_ID: {
                sCursor = null;
                break;
            }
        }

        sCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return sCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int   match = sUriMatcher.match(uri);
        switch (match) {
            case WEATHER_WITH_LOCATION_AND_DATE:
                return WeatherContract.WeatherEntry.CONTENT_ITEM_TYPE;
            case WEATHER_WITH_LOCATION:
                return WeatherContract.WeatherEntry.CONTENT_TYPE;
            case WEATHER:
                return WeatherContract.WeatherEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI: "+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
       return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
