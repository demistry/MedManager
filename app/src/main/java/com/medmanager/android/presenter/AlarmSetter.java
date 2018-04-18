package com.medmanager.android.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.model.storage.MedInfo;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 */

public class AlarmSetter {


    private static long REMINDER_INTERVAL_SECONDS;
    static AlarmManager alarmManagerElapsed;
    static PendingIntent alarmIntentElapsed;


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
        intent.putExtra("serial", medInfo.getMedicationName());
        //intent.setAction()

        //save serialized info of current med
        int alarmIntentId = (int) System.currentTimeMillis();
        SharedPreferences.Editor editor = context.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE).edit();
        editor.putString(medInfo.getMedicationName(), serialized);
        editor.putInt(medInfo.getMedicationDescription(), alarmIntentId);



        //Setting pending intent to respond to broadcast sent by AlarmManager everyday at 8am
        //for (int id =0; id<medInfoList.size(); id++)


        //just replace with System.getcurrentmillis and delete shared pref line
        alarmIntentElapsed = PendingIntent.getBroadcast(context, alarmIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        editor.apply();


        //getting instance of AlarmManager service
        alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);


        //Inexact alarm everyday since device is booted up. This is a better choice and
        //scales well when device time settings/locale is changed
        //We're setting alarm to fire notification after med duration, and every med interval there on
        if (alarmManagerElapsed!=null)
        alarmManagerElapsed.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                System.currentTimeMillis() + REMINDER_INTERVAL_SECONDS,
                5000,
                60000, alarmIntentElapsed);

    }
}
