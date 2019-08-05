package com.example.hardeep.myproject;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

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
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();


            NotificationCompat.Builder builder =new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body);

            int notificationid= (int) System.currentTimeMillis();

            NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(notificationid,builder.build());
        }
    }
}

