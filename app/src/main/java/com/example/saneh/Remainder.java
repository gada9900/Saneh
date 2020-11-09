package com.example.saneh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Remainder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Saneh_Channel")
                .setSmallIcon(R.drawable.bill)
                .setContentTitle("Saneh App reminder")
                .setContentText("hey there prepare for your booking class, your reservation time")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.InboxStyle()
               // .addLine("hey there")
                .addLine("prepare for your booking class, your reservation time will")
                .addLine("start soon"));


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
    }
}
