package com.nagarjuna_pamu.android.sunshine.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by android on 27/1/15.
 */
public class WeatherContract {

    public static final String CONTEXT_AUTHORITY = "com.nagarjuna_pamu.android.sunshine";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTEXT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";

    public static final String PATH_LOCATION = "location";


    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                                                .appendPath(PATH_WEATHER)
                                                .build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + WeatherContract.CONTEXT_AUTHORITY + "/" +
                PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + WeatherContract.CONTEXT_AUTHORITY + "/" +
                        PATH_WEATHER;
        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_LOCATION_ID = "location_id";
        public static final String COLUMN_DATE_TEXT = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_SHORT_DESC = "short_desc";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind";
        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buidWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}