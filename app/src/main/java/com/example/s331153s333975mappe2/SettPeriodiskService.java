package com.example.s331153s333975mappe2;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
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
        Toast.makeText(this, "I Periodisk service", Toast.LENGTH_SHORT).show();

        String klokkeSlett = PreferenceManager.getDefaultSharedPreferences(this).getString("klokkeslett", null);
        final int[] brukerTid = delTid(klokkeSlett);
        Log.d("Klokkeslett ", klokkeSlett);

        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MinVarselService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        cal.set(Calendar.HOUR_OF_DAY, brukerTid[0]);
        cal.set(Calendar.MINUTE, brukerTid[1]);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //må byttes til 1000 * 60 * 60 * 24 senere
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);
        return super.onStartCommand(intent, flags, startId);
    }

    private int[] delTid(String time) {
        String[] userSplit = time.split(":");
        return new int[] {Integer.parseInt(userSplit[0]), Integer.parseInt(userSplit[1])};
    }
}
