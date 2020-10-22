package com.example.s331153s333975mappe2;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.telephony.SmsManager;
import android.view.View;
import androidx.core.app.ActivityCompat;

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
                    //SwitchPreference preference = (SwitchPreference) findPreference("bytt");
                    //SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    boolean varsel = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("bytt", true);

                    //må skrive noe for å få pushet, tralala
                    if(varsel == true){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
                    } else { //mulig denne koden ikke skal stå her i det hele tatt... vet ikke helt hvordan jeg skal implementere den
                        Intent i = new Intent(this, MinVarselService.class);
                        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
                        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //dette skal vel være smsManager eller??
                        if(alarm != null){
                            alarm.cancel(pintent);
                        }
                    }
                }
            };
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        SwitchPreference preferanse = (SwitchPreference) findPreference("bytt");
        preferanse.setSummaryOff("av");
        preferanse.setSummaryOn("på");
    }

    @Override
    public void onPause(){
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
    }
}
