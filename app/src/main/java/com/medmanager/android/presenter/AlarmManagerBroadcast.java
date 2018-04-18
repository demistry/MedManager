package com.medmanager.android.presenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.StringProcessor;
import com.medmanager.android.views.activities.AboutMedicationActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 */

public class AlarmManagerBroadcast extends BroadcastReceiver {
    private Context mContext;
    private static MedInfo mMedInfos;
    private NotificationCompat.Builder mBuilder;
    private Notification mNotification;
    private NotificationManager notificationManager;
    private Intent mNotifIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mNotifIntent = new Intent(context, AboutMedicationActivity.class);

        if (intent.getAction()!=null && context!=null && MedsSingleton.getInstance().getAllMedicationsInfo()!=null){
            if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
                List<MedInfo> medInfoArrayList = MedsSingleton.getInstance().getAllMedicationsInfo();
                for (int i =0; i<medInfoArrayList.size();i++){
                    AlarmSetter.setUpAlarm(context, medInfoArrayList.get(i));
                }
            }
        }
        if (context!=null){
            String medName = intent.getStringExtra("serial");
            SharedPreferences preferences = context.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE);
            String tag = preferences.getString(medName, null);
            MedInfo medInfo = MedInfo.create(tag);
            String notificationTag = medInfo.getMedicationName();

            mBuilder = new NotificationCompat.Builder(context)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.ic_pill)
                    .setContentTitle("Med Manager")
                    .setCustomBigContentView(setUpRemoteView(tag, notificationTag));
            mNotification = mBuilder.build();

            mNotification.flags = Notification.FLAG_AUTO_CANCEL;

            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotification.priority = Notification.PRIORITY_HIGH;
            if (notificationManager!=null)
                notificationManager.notify(notificationTag,
                        0, mNotification);
        }



    }

    private RemoteViews setUpRemoteView(String tag, String notifTag){
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_notification_layout);
        //String serializedDataFromPref =  sharedPreferences.getString(tag, null);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(),
                new Intent(mContext, AboutMedicationActivity.class).putExtra("notificationItem",tag)
                        .putExtra("notifTag", notifTag)
                        .putExtra("id", 0)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);

        MedInfo restoredInfo = MedInfo.create(tag);
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
}
