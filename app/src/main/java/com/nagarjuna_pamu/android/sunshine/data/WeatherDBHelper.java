package com.nagarjuna_pamu.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nagarjuna_pamu.android.sunshine.data.WeatherContract.WeatherEntry;
import com.nagarjuna_pamu.android.sunshine.data.LocationContract.LocationEntry;

/**
 * Created by android on 27/1/15.
 */
public class WeatherDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    String WEATHER_TABLE_CREATE = "CREATE TABLE " + WeatherEntry.TABLE_NAME + " ( " +

            WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WeatherEntry.COLUMN_LOCATION_ID + " INTEGER NOT NULL, " +
            WeatherEntry.COLUMN_DATE_TEXT + " TEXT NOT NULL, " +

            WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
            WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL, " +

            WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
            WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
            WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
            WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
            WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
            WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
            " FOREIGN KEY ( " + WeatherEntry.COLUMN_LOCATION_ID + " ) REFERENCES " +
            LocationEntry.TABLE_NAME + " ( " + LocationEntry._ID + " ), " +
            " UNIQUE ( " + WeatherEntry.COLUMN_DATE_TEXT + ", " +
            WeatherEntry.COLUMN_LOCATION_ID + ") ON CONFLICT REPLACE); ";

    String LOCATION_TABLE_CREATE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " ( " +
            LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LocationEntry.COLUMN_LOCATION_SETTING + " TEXT NOT NULL, " +
            LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
            LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
            LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL, " +
            " UNIQUE ( " + LocationEntry.COLUMN_LOCATION_SETTING + " ) ON CONFLICT IGNORE ); ";

    private Context mContext;

    public WeatherDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOCATION_TABLE_CREATE);
        db.execSQL(WEATHER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WEATHER_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_CREATE);
        onCreate(db);
    }
}
