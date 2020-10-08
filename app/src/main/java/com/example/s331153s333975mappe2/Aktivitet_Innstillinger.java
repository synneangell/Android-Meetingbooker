package com.example.s331153s333975mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Aktivitet_Innstillinger extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }

/*    public void startService(View v){
        Intent intent = new Intent();
        intent.setAction("serviceBroadcast");
        sendBroadcast(intent);
    }*/

    //denne stopper service som gir varsel om møter en gang i døgnet
/*    public void stoppPeriodisk(View v){
            Intent i = new Intent(Aktivitet_Innstillinger.this, MinVarselService.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if(alarm != null){
                alarm.cancel(pintent);
            }
    }*/

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(R.color.preferanseFarge));
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_innstillinger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mote:
                Intent i = new Intent(this, Aktivitet_Mote.class);
                startActivity(i);
                break;
            case R.id.kontakter:
                Intent i2 = new Intent(this, Aktivitet_Kontakt.class);
                startActivity(i2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
