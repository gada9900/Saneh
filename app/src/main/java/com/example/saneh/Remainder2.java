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
                .setContentText("hey there \n your booking class is tomorrow , if you no longer need it , go to the app and delete it.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
