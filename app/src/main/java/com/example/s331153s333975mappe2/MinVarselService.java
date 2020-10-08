package com.example.s331153s333975mappe2;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MinVarselService extends Service {
    DBHandler db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        db = new DBHandler(MinVarselService.this);
        List<Mote> alleMoter = db.finnAlleMoter(); //den klarer ikke å få inn noen møter her! en liten motherfucker

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Aktivitet_Mote.class);
        PendingIntent pintent = PendingIntent.getActivity(MinVarselService.this, 0, i, 0);

        for(Mote mote : alleMoter){
            if(mote.getDato().equals(currentDate)){
                byggNotifikasjon(pintent, notificationManager);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void byggNotifikasjon(PendingIntent pintent, NotificationManager notificationManager){
        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Du har et møte i dag!")
                .setContentTitle("Husk møtene for i dag!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pintent)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
}
