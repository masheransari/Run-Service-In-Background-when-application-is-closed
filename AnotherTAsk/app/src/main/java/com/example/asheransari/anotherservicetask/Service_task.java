package com.example.asheransari.anotherservicetask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by asher.ansari on 9/20/2017.
 */

public class Service_task extends Service {
    private static long UPDATE_INTERVAL = 1 * 5 * 1000;  //default
    static Calendar calendar = Calendar.getInstance();
    static int count = 0;
    Context context;
    private static Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        Log.e("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _startService();

    }

    private void _startService() {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();
                    }
                }, 1000, UPDATE_INTERVAL);
        Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
    }

    private void doServiceWork() {


        Log.e("Talentify", "callllllllllllllll" + calendar.getTime());
        //do something wotever you want
        //like reading file or getting data from network
        try {
            Toast.makeText(context, "getted data", Toast.LENGTH_SHORT).show();
            addNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void _shutdownService() {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_menu_search)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        int id = count++;
        PendingIntent contentIntent = PendingIntent.getActivity(this, id, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        _shutdownService();

        // if (MAIN_ACTIVITY != null)  Log.d(getClass().getSimpleName(), "FileScannerService stopped");
    }
}
