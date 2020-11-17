package com.example.saneh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Remainder2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Saneh_Channel")
                .setSmallIcon(R.drawable.bill)
                .setContentTitle("Saneh App reminder")
                .setContentText("hey there your booking class "+intent.getStringExtra("classID"+" is tomorrow "))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.InboxStyle()
               // .addLine("hey there!")
                .addLine("your booking class "+intent.getStringExtra("classID")+" is tomorrow " ));


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(201, builder.build());
    }
}
