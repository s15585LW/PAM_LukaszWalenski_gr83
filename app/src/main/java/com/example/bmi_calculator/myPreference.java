package com.example.bmi_calculator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class myPreference extends PreferenceActivity {

    private static final String PREFERENCES_NAME = "Preferences";
    private static final String LIST_FIELD = "pref_numberOfChoices";
    private SharedPreferences preferences;
    private ListPreference listPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        listPreference = (ListPreference) findPreference("pref_numberOfChoices");
    initPreferences();

    }

    private void initPreferences() {
        String listDefaultValue = listPreference.getEntryValues()[1].toString();
        String listValue = preferences.getString(LIST_FIELD, listDefaultValue);
        listPreference.setValue(listValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();

    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_FIELD, listPreference.getValue());
        editor.commit();
    }
}