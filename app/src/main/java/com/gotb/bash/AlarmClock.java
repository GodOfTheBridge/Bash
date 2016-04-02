package com.gotb.bash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmClock extends BroadcastReceiver {

    private  final long MIN_30 = 60000 * 30;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("myLog", "Alarm");

        Intent iStartService = new Intent(context, ParserService.class);
        context.startService(iStartService);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intentItself = new Intent(context, AlarmClock.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentItself, 0);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + MIN_30, pendingIntent);
    }
}
