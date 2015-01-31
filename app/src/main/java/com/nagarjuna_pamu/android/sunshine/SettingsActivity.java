package com.nagarjuna_pamu.android.sunshine;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.os.Bundle;


public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.units_label_key)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if(preference instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            int index = listPref.findIndexOfValue(stringValue);
            if(index >= 0) {
                preference.setSummary(listPref.getEntries()[index]);
            }
        }else {
            preference.setSummary(stringValue);
        }

        return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }
}
