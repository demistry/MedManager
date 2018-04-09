package com.medmanager.android.presenter.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaDataSource;
import android.media.SoundPool;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.medmanager.android.R;
import com.medmanager.android.views.activities.AboutMedicationActivity;
import com.medmanager.android.views.activities.MainActivity;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class NotificationDispatcherService extends JobService {

    private NotificationCompat.Builder mBuilder;
    private Notification mNotification;
    private NotificationManager notificationManager;
    private Intent mNotifIntent;

    @Override
    public boolean onStartJob(JobParameters job) {

        setUpNotification();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    private void setUpNotification(){
        mNotifIntent = new Intent(this, AboutMedicationActivity.class);
        mNotifIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, mNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_google_plus)
//                .setContentTitle("Medications Notification")
//                .setContentText("This is my notification")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_pill)
                .setContent(setUpRemoteView())
                .setCustomBigContentView(setUpRemoteView())
                .setContentIntent(pendingIntent);
        mNotification = mBuilder.build();
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);

    }
    private RemoteViews setUpRemoteView(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_notification_layout);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AboutMedicationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_increment_pills, pendingIntent);
        return remoteViews;
    }



}
