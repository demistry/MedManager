package com.medmanager.android.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.AlarmClock;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.model.storage.MedInfo;

import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 * Class for setting up alarm to a medication
 */

public class AlarmSetter {


    private static int REMINDER_INTERVAL_SECONDS;
    private static AlarmManager sAlarmManagerElapsed;
    private static PendingIntent sAlarmIntentElapsed;

    private static Date mDate;


    /**
     * This method instantiates the alarm for a stored medication
     * @param context Context
     * @param medInfo MedInfo
     */
    public static void setUpAlarm(Context context, MedInfo medInfo){

        if(medInfo.getMedicationInterval() == 30){
            //1000 is to convert to milliseconds
            //60 is to convert to seconds
            int secs =  medInfo.getMedicationInterval()*60*1000;
            mDate = new Date(0,0,0,0,2,0);
        }
        else{
            int secs =  medInfo.getMedicationInterval()*60*60*1000;
//            AlarmManager.
            mDate = new Date(0,0,0,0,2,0);
        }

        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        intent.setAction(AlarmClock.ACTION_SET_ALARM);

        ComponentName receiver = new ComponentName(context, AlarmManagerBroadcast.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        String serialized = medInfo.serialize();

        //send current med name along with extra
        intent.putExtra(ConstantClass.EXTRA_SERIAL, medInfo.getMedicationName());

        //save serialized info of current med
        int alarmIntentId = (int) System.currentTimeMillis();
        SharedPreferences.Editor editor = context.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE).edit();
        editor.putString(medInfo.getMedicationName(), serialized);
        editor.putInt(medInfo.getMedicationDescription(), alarmIntentId);

        sAlarmIntentElapsed = PendingIntent.getBroadcast(context, alarmIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        editor.apply();


        //getting instance of AlarmManager service
        sAlarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //We're setting alarm to fire notification after 5seconds, and every med interval there on
        if (sAlarmManagerElapsed !=null)
            sAlarmManagerElapsed.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    50000,
                    REMINDER_INTERVAL_SECONDS, sAlarmIntentElapsed);
    }

    public static void restartAlarm(Context context, MedInfo medInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE);
        int originalId = sharedPreferences.getInt(medInfo.getMedicationDescription(), 0);
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        PendingIntent originalIntent = PendingIntent.getBroadcast(context,originalId,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (manager!=null&& originalIntent!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,600,originalIntent);
            }
        }


    }
}
