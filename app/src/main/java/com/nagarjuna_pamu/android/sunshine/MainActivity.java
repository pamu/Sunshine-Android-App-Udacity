package com.nagarjuna_pamu.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView = (ListView) findViewById(R.id.listview_forecast);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        if(id == R.id.refresh) {
            new DataFetcherTask(this, new Task() {
                @Override
                public void run(String str) {
                    super.run(str);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    ArrayList<String> newList = new ArrayList();
                    for(int i = 0; i < 7; i++) {
                        try {
                            newList.add(format.format(getTime(str, i) * 1000L)+", MIN temp:" + getMinTempForDay(str, i).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(newList.size() > 0)
                        mListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, newList));
                }
            }).execute(getUri().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getActivity(), DetailsActivity.class));
                }
            });
            new DataFetcherTask(getActivity(), new Task() {
                @Override
                public void run(String str) {
                    super.run(str);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    ArrayList<String> newList = new ArrayList();
                    for(int i = 0; i < 7; i++) {
                        try {
                            newList.add(format.format(getTime(str, i) * 1000L)+", MIN temp:" + getMinTempForDay(str, i).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(newList.size() > 0)
                    listView.setAdapter(new ArrayAdapter(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, newList));
                }
            }).execute(getUri().toString());

            return rootView;
        }

        public Uri getUri() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String location = prefs.getString(getString(R.string.prefs_location_key), getString(R.string.default_location));
            Uri serverUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily?").buildUpon()
                    .appendQueryParameter("q", location)
                    .appendQueryParameter("mode", "json")
                    .appendQueryParameter("units", "metric")
                    .appendQueryParameter("cnt", "7").build();
            return serverUri;
        }
    }

    public Uri getUri() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = prefs.getString(getString(R.string.prefs_location_key), getString(R.string.default_location));
        Uri serverUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily?").buildUpon()
                .appendQueryParameter("q", location)
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("cnt", "7").build();
        return serverUri;
    }

    public static Double getMinTempForDay(String json, int index) throws JSONException {
        JSONObject days = new JSONObject(json);
        JSONArray daysInfo = days.getJSONArray("list");
        JSONObject item = daysInfo.getJSONObject(index);
        JSONObject temp = item.getJSONObject("temp");
        return temp.getDouble("min");
    }

    public static Double getMaxTempForDay(String json, int index) throws JSONException {
        JSONObject days = new JSONObject(json);
        JSONArray daysInfo = days.getJSONArray("list");
        JSONObject item = daysInfo.getJSONObject(index);
        JSONObject temp = item.getJSONObject("temp");
        return temp.getDouble("max");
    }

    public static Double getTempForDay(String json, int index) throws JSONException {
        JSONObject days = new JSONObject(json);
        JSONArray daysInfo = days.getJSONArray("list");
        JSONObject item = daysInfo.getJSONObject(index);
        JSONObject temp = item.getJSONObject("temp");
        return temp.getDouble("day");
    }

    public static Long getTime(String json, int index) throws JSONException {
        JSONObject days = new JSONObject(json);
        JSONArray daysInfo = days.getJSONArray("list");
        JSONObject item = daysInfo.getJSONObject(index);
        return item.getLong("dt");
    }

}
