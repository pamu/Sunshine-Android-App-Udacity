package com.nagarjuna_pamu.android.sunshine.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by android on 27/1/15.
 */
public class LocationContract {

    public static final class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = WeatherContract.BASE_CONTENT_URI.buildUpon()
                .appendPath(WeatherContract.PATH_LOCATION)
                .build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + WeatherContract.CONTEXT_AUTHORITY + "/" +
                        WeatherContract.PATH_LOCATION;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + WeatherContract.CONTEXT_AUTHORITY + "/" +
                        WeatherContract.PATH_LOCATION;

        public static final String TABLE_NAME = "location_entry";
        public static final String COLUMN_LOCATION_SETTING = "location_setting";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
