package com.medmanager.android.presenter.viewpresenters;

import android.content.Context;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.medmanager.android.presenter.services.NotificationDispatcherService;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class NotificationPresenter {

    public static void sendNotification(Context context){
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = firebaseJobDispatcher.newJobBuilder()
                .setService(NotificationDispatcherService.class)
                .setTag("Notify")
                //.setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        2,
                        10
                ))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(constraintReminderJob);
    }
}
