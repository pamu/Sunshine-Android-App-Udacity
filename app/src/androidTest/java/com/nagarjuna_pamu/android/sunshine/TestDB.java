package com.nagarjuna_pamu.android.sunshine;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.nagarjuna_pamu.android.sunshine.data.LocationContract.LocationEntry;
import com.nagarjuna_pamu.android.sunshine.data.WeatherContract.WeatherEntry;
import com.nagarjuna_pamu.android.sunshine.data.WeatherDBHelper;


/**
 * Created by android on 27/1/15.
 */
public class TestDB extends AndroidTestCase {

    private String LOG_TAG = TestDB.class.getName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(WeatherDBHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDBHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertDB() throws Throwable {

        String testName = "North Pole";
        String testLocationSetting = "Bangalore, In";
        Double testLong = 73.222;
        Double testLat = -22.212;

        WeatherDBHelper helper = new WeatherDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocationEntry.COLUMN_CITY_NAME, testName);
        values.put(LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(LocationEntry.COLUMN_COORD_LAT, testLat);
        values.put(LocationEntry.COLUMN_COORD_LONG, testLong);

        long rowId = db.insert(LocationEntry.TABLE_NAME, null, values);

        int testLocationId = 10;
        String testDate = "1234";
        double testDegrees = 20;
        double testHumidity = 20;
        double testMax = 30;
        double testMin = 10;
        double testPressure = 120;
        double testWindSpeed = 111;
        String testShortDesc = "the day is clear";
        int testWeatherId = 1;

        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherEntry.COLUMN_LOCATION_ID, testLocationId);
        weatherValues.put(WeatherEntry.COLUMN_DATE_TEXT, testDate);
        weatherValues.put(WeatherEntry.COLUMN_DEGREES, testDegrees);
        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, testHumidity);
        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, testMax);
        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, testMin);
        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, testPressure);
        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, testWindSpeed);
        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, testShortDesc);
        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, testWeatherId);

        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);

        assertTrue(weatherRowId != -1 && rowId != -1);

        Log.d(LOG_TAG, "Row id : "+rowId);

        Log.d(LOG_TAG, "Weather Row Id: "+weatherRowId);

        db.close();
    }

}
