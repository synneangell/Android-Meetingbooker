package com.example.s331153s333975mappe2;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.core.app.ActivityCompat;

import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public class Aktivitet_Innstillinger extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {

        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    android.preference.SwitchPreference preference = (android.preference.SwitchPreference) findPreference("bytt");
                    SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    //boolean isChecked = sharedPreferences1.getBoolean("bytt", false);

                    boolean varsel = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("bytt", true);


                    if(varsel == true){
                        //Log.d("Varsel er ", Boolean.toString(preference.isChecked()));
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            };
        }



        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(R.color.preferanseFarge));
        }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);

    }

    @Override
    public void onPause(){
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    }
}
