package com.medmanager.android.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.model.storage.MedInfo;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 * Class for setting up alarm to a medication
 */

public class AlarmSetter {


    private static long REMINDER_INTERVAL_SECONDS;
    private static AlarmManager sAlarmManagerElapsed;
    private static PendingIntent sAlarmIntentElapsed;


    /**
     * This method instantiates the alarm for a stored medication
     * @param context Context
     * @param medInfo MedInfo
     */
    public static void setUpAlarm(Context context, MedInfo medInfo){

        if(medInfo.getMedicationInterval() == 30){
            //1000 is to convert to milliseconds
            //60 is to convert to seconds
            REMINDER_INTERVAL_SECONDS = (long) medInfo.getMedicationInterval()*60*1000;
        }
        else{
            REMINDER_INTERVAL_SECONDS = (long) medInfo.getMedicationInterval()*60*60*1000;
        }

        Intent intent = new Intent(context, AlarmManagerBroadcast.class);

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
}
