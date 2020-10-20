package com.example.s331153s333975mappe2;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
        boolean varsel = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("bytt", true);
        db = new DBHandler(MinVarselService.this);
        final List<Mote> alleMoter = db.finnAlleMoter();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Aktivitet_Mote.class);
        PendingIntent pintent = PendingIntent.getActivity(MinVarselService.this, 0, i, 0);

        byggNotifikasjon(pintent, notificationManager);

        for(Mote mote : alleMoter){
            if(currentDate.equals(mote.getDato())){
                byggNotifikasjon(pintent, notificationManager);
                Long moteid = mote.get_MID();
                if(varsel){
                    List<Kontakt> deltakere = db.finnDeltakere(moteid);
                    for(Kontakt kontakt : deltakere){
                        sendSMS(kontakt.telefon);
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

/*    public void sendSMS(String telefonnr){
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        String egendefinertSMS = pref2.getString("smsMelding", "Husk møtet ditt for i dag!");

        String melding = egendefinertSMS;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefonnr, null, melding, null, null);

            String currentDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Møtebooker har ikke tillatelse til å sende SMS. Gi tillatelse i innstillinger", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void sendSMS() {
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        String egendefinertSMS = pref2.getString("smsMelding", "Husk møtet ditt for i dag!");
        String melding = egendefinertSMS;

        MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED && MY_PHONE_STATE_PERMISSION == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsMan = SmsManager.getDefault();
            smsMan.sendTextMessage(telefonnr, null, melding, null, null);
            Toast.makeText(this, "Har sendt sms", Toast.LENGTH_SHORT).show();
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    private void byggNotifikasjon(PendingIntent pintent, NotificationManager notificationManager){
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Husk møtene for i dag!")
                .setContentText("Husk møtet idag!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pintent)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
}
