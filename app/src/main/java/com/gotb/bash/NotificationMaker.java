package com.gotb.bash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

public class NotificationMaker {
    public int NOTIFICATION_ID = 111;
    NotificationManager notificationManager;

    public void showNotification(Context context) {
        notificationManager = (NotificationManager) context.getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setTicker(context.getString(R.string.notification_ticker))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(context.getString(R.string.notification_content_title))
                .setContentText(context.getString(R.string.notification_content_text));
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
