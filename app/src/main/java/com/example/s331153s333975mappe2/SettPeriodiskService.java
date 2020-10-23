package com.example.s331153s333975mappe2;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.regex.Pattern;

public class SettPeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String klokkeSlett = pref.getString("klokkeslett", "12:00");

        Pattern TID = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        if(!TID.matcher(klokkeSlett).matches()){
            klokkeSlett = "12:00";

        }
        final int[] brukerTid = delTid(klokkeSlett);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, brukerTid[0]);
        cal.set(Calendar.MINUTE, brukerTid[1]);
        cal.set(Calendar.SECOND,0);
        Log.d("Tid", cal.getTime().toString());
        Intent i = new Intent(this, MinVarselService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
        return super.onStartCommand(intent, flags, startId);
    }

    private int[] delTid(String tid) {
        String[] del = tid.split(":");
        return new int[] {Integer.parseInt(del[0]), Integer.parseInt(del[1])};
    }

}
