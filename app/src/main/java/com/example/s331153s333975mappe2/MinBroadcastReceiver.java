package com.example.s331153s333975mappe2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MinBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, SettPeriodiskService.class);
        context.startService(i);
    }
}
