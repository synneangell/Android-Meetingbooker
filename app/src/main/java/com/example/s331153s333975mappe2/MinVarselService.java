package com.example.s331153s333975mappe2;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

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
        Toast.makeText(this, "I MinVarselService", Toast.LENGTH_SHORT).show();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        //boolean varsel = pref.getBoolean("bytt", false);

        boolean varsel = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("bytt", false);
        Log.d("Er varsel på? ", Boolean.toString(varsel));

        db = new DBHandler(MinVarselService.this);
        final List<Mote> alleMoter = db.finnAlleMoter(); //den klarer ikke å få inn noen møter her! en liten motherfucker
        final List<MoteDeltagelse> alleMoteDeltagelser = db.finnAlleMoteDeltagelser();
        final List<Kontakt> alleKontakter = db.finnAlleKontakter();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Aktivitet_Mote.class);
        PendingIntent pintent = PendingIntent.getActivity(MinVarselService.this, 0, i, 0);

        for(Mote mote : alleMoter){
            if(mote.getDato().equals(currentDate)){
                byggNotifikasjon(pintent, notificationManager);

                if(varsel){ //her er det masse feil, kan sikkert gjøres kortere også.
                    for(MoteDeltagelse md : alleMoteDeltagelser){
                        for(Kontakt k : alleKontakter){
                            if(md.get_KID() == k.get_KID() && md.get_KID()  != null){
                                for(Kontakt kontakt : alleKontakter){
                                    sendSMS(kontakt.getTelefon());
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void sendSMS(String telefonnr){
        String melding = getString(R.string.standardMelding);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefonnr, null, melding, null, null);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Møtebooker har ikke tillatelse til å sende SMS. Gi tillatelse i innstillinger", Toast.LENGTH_SHORT).show();
        }
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
