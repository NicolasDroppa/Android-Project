package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

/**
 * Created by Rasekgala on 2016/11/07.
 */

public class FcmMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        notificationNews(title, "Howzit man... i missed you");
        // Extract JSON array from the response
       /* try {

            //instantiate database object
            MyDatabase db = new MyDatabase(getApplicationContext());

            // Get JSON object
            JSONObject obj = new JSONObject(body);
            System.out.println(obj.get("type"));
            System.out.println(obj.get("respond"));

            if(String.valueOf(obj.get("type")).equals("news"))
            {

                News news =  new News();


                String date = CommonMethod.getCurrentDate();
                news.setBody(String.valueOf(obj.get("body")));
                news.setDate(date);
                news.setReporter(String.valueOf(obj.get("reporter")));
                news.setTitle(title);


                db.insertNews(news);

                notificationNews(title, String.valueOf(obj.get("body")));

            }else {

                //intantiate the random object
                Random random = new Random();

                String user1 = "owner";
                String user2 = "doctor";

                //create the ChatMessage object and set values
                final ChatMessage chatMessage = new ChatMessage(user2, user1 , String.valueOf(obj.get("body")), "" + random.nextInt(1000), true);

                //set values to Chat Message object
                chatMessage.setMsgID();
                chatMessage.Date = CommonMethod.getCurrentDate();
                chatMessage.Time = CommonMethod.getCurrentTime();

                db.storeChatMessage(chatMessage);
                notificationNews("New Message", String.valueOf(obj.get("body")));
            }


        }catch (Exception e)
        {

        }*/

        super.onMessageReceived(remoteMessage);
    }

    public void notificationNews(String title, String body)
    {
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setSmallIcon(R.drawable.background);
        notificationBuilder.setAutoCancel(true);
        //notificationBuilder.setStyle(new Notification.BigTextStyle().bigText(""));
        notificationBuilder.setContentIntent(pendingIntent);

        // Sets an ID for the notification, so it can be updated
        int notifyID = 9001;

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        notificationBuilder.setDefaults(defaults);

        notificationBuilder.setContentText(body);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyID, notificationBuilder.build());
    }

    public void notificationMessage(String title, String body)
    {
        Intent intent = new Intent(this, AllMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        notificationBuilder.setDefaults(defaults);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
