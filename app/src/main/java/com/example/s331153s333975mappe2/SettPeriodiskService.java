package com.example.s331153s333975mappe2;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.util.Calendar;

public class SettPeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MinVarselService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
/*      cal.set(Calendar.HOUR_OF_DAY, 8); //dette var for å teste med tilfeldig tid som ikke ble hentet fra brukeren men hardkodet
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);*/
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //må byttes til 1000 * 60 * 60 * 24 senere
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);
        return super.onStartCommand(intent, flags, startId);
    }
}
