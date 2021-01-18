package com.gaurashtra.app.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.gaurashtra.app.R;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.NotificationActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("OnMsgRec","Notification fired!");
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Data: " + remoteMessage.getData());
        Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
        Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        Map<String, String> data = remoteMessage.getData();
        Log.e(TAG, "RemoteMsg: " + data.toString());

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_small)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        try {
            Map<String, String> data = remoteMessage.getData();
            Log.e("DataFromNotif",":"+data);

//            strFunction = data.get(Constants.Keys.NOT_CAT_ID).toString();
//            strRideId = data.get(Constants.Keys.RIDE_ID).toString();
//            Log.e(TAG,"notCatId: "+strFunction);
//            if (strFunction.equals("1")) {
////            mediaPlayer.start();
//                newRideRequest(strRideId,"");
//            } else if (strFunction.equals("2")) {
//                PreferenceUtil.setDriverStatusLoggedIn(getApplicationContext(),"ON");
//                newRideRequest(strRideId,remoteMessage.getNotification().getTitle());
//            } else {
//                //Default open page
////            goToHomePage("");
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.e(TAG, "Refreshed token: " + s);
        super.onNewToken(s);
    }

}
