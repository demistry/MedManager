package com.medmanager.android.presenter.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.firebase.jobdispatcher.JobParameters;
//import com.firebase.jobdispatcher.JobService;
import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.presenter.utils.StringProcessor;
import com.medmanager.android.views.activities.AboutMedicationActivity;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class NotificationDispatcherService extends JobService implements SaveMedicationToDatabase.ShowNotificationForMedInterface {

    private Context mContext;
    private static MedInfo mMedInfos;
    private NotificationCompat.Builder mBuilder;
    private Notification mNotification;
    private NotificationManager notificationManager;
    private Intent mNotifIntent;


    static SharedPreferences sharedPreferences;
    static int notificationId = 0;


    public NotificationDispatcherService(Context context){
        ((DaggerApplication)context.getApplicationContext()).getMyApplicationComponent().inject(this);
    }
    public NotificationDispatcherService(){

    }

    @Override
    public boolean onStartJob(android.app.job.JobParameters params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpNotification(params.getExtras().getString("Tag"), 1);
            jobFinished(params, true);
        }
//        PowerManager.

        return false;
    }

    @Override
    public boolean onStopJob(android.app.job.JobParameters params) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void setUpPref(SharedPreferences sharedPreference){
        sharedPreferences = sharedPreference;
    }
//    @Override
//    public boolean onStartJob(JobParameters job) {
//        Log.v("TAG", "Job Tag is " + job.getTag());
//
//        setUpNotification(job.getTag(), 1);
//        return false;
//    }

//    @Override
//    public boolean onStopJob(JobParameters job) {
//        return false;
//    }



    private void setUpNotification(String tag, int id){

        mNotifIntent = new Intent(this, AboutMedicationActivity.class);
        String serializedDataFromPref = sharedPreferences.getString(tag, null);
        mNotifIntent.putExtra("notificationItem",serializedDataFromPref);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this,0, mNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_google_plus)
//                .setContentTitle("Medications Notification")
//                .setContentText("This is my notification")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_pill)
                .setContentTitle("Med Manager")
                //.setContent(setUpRemoteView())
                .setCustomBigContentView(setUpRemoteView(tag));
                //.setContentIntent(pendingIntent);
        mNotification = mBuilder.build();

        mNotification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification.priority = Notification.PRIORITY_HIGH;
        notificationManager.notify(MedInfo.create(sharedPreferences.getString(tag, null)).getMedicationName(),
                notificationId, mNotification);


    }
    private RemoteViews setUpRemoteView(String tag){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_notification_layout);
        String serializedDataFromPref =  sharedPreferences.getString(tag, null);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AboutMedicationActivity.class).putExtra("notificationItem",serializedDataFromPref)
                        .putExtra("notifTag", tag)
                        .putExtra("id", mNotification)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);

        MedInfo restoredInfo = MedInfo.create(serializedDataFromPref);
        remoteViews.setTextViewText(R.id.text_notif_med_name, restoredInfo.getMedicationName());
        remoteViews.setTextViewText(R.id.text_notify_med_info, StringProcessor.displayNotifDescription(restoredInfo.getMedicationName(),
                restoredInfo.getDoseNumber(),
                restoredInfo.getMedicationType()));

        switch (restoredInfo.getMedicationType()) {
            case "Pills":
                remoteViews.setImageViewResource(R.id.image_notif, R.drawable.ic_pill);
                break;
            case "Injection":
                remoteViews.setImageViewResource(R.id.image_notif, R.drawable.ic_injection);
                break;
            case "Syrup":
                remoteViews.setImageViewResource(R.id.image_notif, R.drawable.ic_syrup);
                break;
        }
        remoteViews.setOnClickPendingIntent(R.id.btn_increment_pills, pendingIntent);
        return remoteViews;
    }



    @Override
    public void dispatchJob(Context context, MedInfo medInfo) {
        mMedInfos = medInfo;
        mContext = context;
        String serializedData = mMedInfos.serialize();
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(mMedInfos.getMedicationName(), serializedData);
        editor.apply();

    }
}
