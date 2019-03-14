package com.example.hardeep.myproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.hardeep.myproject.admin.display_user_details;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Hardeep on 04-04-2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG="FirebaseMessaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,"FROM :"+remoteMessage.getFrom());

        if(remoteMessage.getData().size() >0)
        {

            Map<String,String> nots=remoteMessage.getData();
            Log.d(TAG,"Message Data"+remoteMessage.getData());
        }

        if(remoteMessage.getNotification()!=null)
        {
            Log.d(TAG,"Message body"+remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String body) {
        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Message")
                .setContentText(body)
                .setAutoCancel(true);


        Intent i=new Intent(this,display_user_details.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(i);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notificationbuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());
    }
}
