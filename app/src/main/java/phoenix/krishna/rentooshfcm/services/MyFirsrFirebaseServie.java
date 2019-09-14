package phoenix.krishna.rentooshfcm.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import phoenix.krishna.rentooshfcm.R;

public class MyFirsrFirebaseServie extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKEN_UPDATE",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){

            final String title = remoteMessage.getNotification().getTitle();
            final String body = remoteMessage.getNotification().getBody();
            String url = "";

            if(remoteMessage.getData()!=null){

                url = remoteMessage.getData().get("image");
            }

            if(TextUtils.isEmpty(url)){

                final String finalUrl = url;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        Picasso.get()
                                .load(finalUrl)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                        showNotification(MyFirsrFirebaseServie.this,title,body,null,bitmap);

                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });
                    }
                });
            }

            else

                showNotification(MyFirsrFirebaseServie.this,title,body,null,null);
        }

        else{

            final String title = remoteMessage.getData().get("title");
            final String body = remoteMessage.getData().get("Body");
            String url = remoteMessage.getData().get("image");

            if(TextUtils.isEmpty(url)){

                final String finalUrl = url;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        Picasso.get()
                                .load(finalUrl)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                        showNotification(MyFirsrFirebaseServie.this,title,body,null,bitmap);

                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });
                    }
                });
            }

            else{

                showNotification(MyFirsrFirebaseServie.this,title,body,null,null);

            }
        }

    }

    private void showNotification(Context context,
                                  String title,
                                  String body,
                                  Intent pendingIntent,
                                  Bitmap bitmap){

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        int notificationId = new Random().nextInt();
        String channelId = "edmtdev-911";
        String channelName = "EDMTDev";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder;

        if(bitmap != null)
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(bitmap)
                    .setContentTitle(title)
                    .setContentText(body);

        else

            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(body);

        if(pendingIntent != null){

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(pendingIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);

        }

        notificationManager.notify(notificationId,builder.build());


    }
}

