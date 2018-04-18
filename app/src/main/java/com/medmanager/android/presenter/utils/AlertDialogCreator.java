package com.medmanager.android.presenter.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.AlarmManagerBroadcast;


/**
 * Created by ILENWABOR DAVID on 18/04/2018.
 * This class is used to create an alert dialog for the medication deleting action
 */

public class AlertDialogCreator {

    /**
     *This method is used to create an alert dialog for warning the user about to delete a medication
     * @param context @tag Pass in context
     * @param deleteMedication @tag Pass in DeleteMedication object
     * @param medInfo @tag Pass in Medication information object
     */
    public static void createDeleteDialog(final Context context, final DeleteMedication deleteMedication, final MedInfo medInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Medication")
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMedication.deleteMedication(context, medInfo);
                        Toast.makeText(context, "Medication Deleted", Toast.LENGTH_SHORT).show();
                        cancelAlarm(context, medInfo);
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method cancels the alarm on a deleted medication
     */
    public static void cancelAlarm(Context context, MedInfo medInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE);
        int originalId = sharedPreferences.getInt(medInfo.getMedicationDescription(), 0);
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        PendingIntent originalIntent = PendingIntent.getBroadcast(context,originalId,intent, PendingIntent.FLAG_NO_CREATE );
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (manager!=null&& originalIntent!=null){
            manager.cancel(originalIntent);
            originalIntent.cancel();
        }


    }
}
