package com.example.s331153s333975mappe2;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import java.util.regex.Pattern;

public class Aktivitet_Innstillinger extends PreferenceActivity {
    public static final Pattern TID = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");

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

            EditTextPreference edit_Pref = (EditTextPreference)
                    getPreferenceScreen().findPreference("klokkeslett");
            edit_Pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                    //String klokkeSlett = pref.getString("klokkeslett", "12:00");
                    if(TID.matcher(newValue).matches()){
                        return true;
                    }else{
                        Toast.makeText(getActivity(), "Tid må være i format TT:MM" , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });

            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        stoppPaminnelse();
                        startPaminnelse();

                }
            };
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        public void startPaminnelse(){
            Intent intent = new Intent();
            intent.setAction(".serviceBroadcast");
            getActivity().sendBroadcast(intent);
        }
        public void stoppPaminnelse(){
            Intent i = new Intent(getActivity(),MinVarselService.class);
            PendingIntent pi = PendingIntent.getService(getActivity(), 0,i,0);
            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

            if(am!=null){
                am.cancel(pi);
            }
        }
        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this.preferenceChangeListener);

        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this.preferenceChangeListener);
            super.onPause();
        }

    }

}
