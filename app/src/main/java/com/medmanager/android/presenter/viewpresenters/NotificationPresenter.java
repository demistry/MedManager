package com.medmanager.android.presenter.viewpresenters;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.services.NotificationDispatcherService;

import java.util.concurrent.TimeUnit;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class NotificationPresenter {

    private static int REMINDER_INTERVAL_SECONDS;
    private static int SYNC_FLEX_TIME;

    private static boolean sInitialized;

    public static void sendNotification(Context context, MedInfo medInfo){
        //if (sInitialized) return;
        if(medInfo.getMedicationInterval() == 30){
            REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(medInfo.getMedicationInterval()));
        }
        else{
            REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(medInfo.getMedicationInterval()))*60;
        }
        SYNC_FLEX_TIME = REMINDER_INTERVAL_SECONDS/2;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);


//        ComponentName componentName = new ComponentName(context, NotificationDispatcherService.class);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            PersistableBundle bundle = new PersistableBundle();
//            bundle.putString("Tag", medInfo.getMedicationName());
//            JobInfo.Builder builder = new JobInfo.Builder(0, componentName)
//                    .setMinimumLatency(5000)
//                    .setExtras(bundle);
//                   // .setBackoffCriteria(5000, JobInfo.BACKOFF_POLICY_EXPONENTIAL);
//            JobScheduler jobScheduler = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                jobScheduler = context.getSystemService(JobScheduler.class);
//            }
//            if (jobScheduler!=null)
//            jobScheduler.schedule(builder.build());
//        }


//        Job constraintReminderJob = firebaseJobDispatcher.newJobBuilder()
//                .setService(NotificationDispatcherService.class)
//                .setTag(medInfo.getMedicationName())
//
//                //.setConstraints(Constraint.DEVICE_CHARGING)
//
//                .setLifetime(Lifetime.FOREVER)
//                .setRecurring(true)
//                .setTrigger(Trigger.executionWindow(
//                        REMINDER_INTERVAL_SECONDS/2,
//                        REMINDER_INTERVAL_SECONDS/2 + SYNC_FLEX_TIME
//                ))
//                .setReplaceCurrent(true)
//                .build();
//
//        firebaseJobDispatcher.mustSchedule(constraintReminderJob);
    }
}
