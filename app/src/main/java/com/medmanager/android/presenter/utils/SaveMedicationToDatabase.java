package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.di.MedComponent;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.AlarmSetter;
import com.medmanager.android.presenter.services.NotificationDispatcherService;
import com.medmanager.android.presenter.viewpresenters.NotificationPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class SaveMedicationToDatabase {


//    @Inject
    static MedInfo medInfo;
//    @Inject
    static List<MedInfo> medInfoList;
    @Inject
    MedicationDAO medicationDAO;
    @Inject
    AllMedicationsDataManager dataManager;
    @Inject
    ActiveMedicationsDataManager activeMedicationsDataManager;
    @Inject
    NotificationDispatcherService notificationDispatcherService;

    private static MedicationDAO asyncMedDao;
    private static List<MedInfo> asyncMedInfo;
    private final ShowNotificationForMedInterface medInterface = notificationDispatcherService;
    private Context mContext;


    public SaveMedicationToDatabase(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
        medInfo = new MedInfo();
        medInfoList = new ArrayList<>();
    }
    public void saveMedToRoomDatabase(Context context, String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, String doseNumber, int interval, boolean isMedStarted, String medicationType){
        medInfo.setMedicationName(medName);
        medInfo.setMedicationDescription(medDescription);
        medInfo.setStartDate(startDate);
        medInfo.setStartTime(startTime);
        medInfo.setEndDate(endDate);
        medInfo.setEndTime(endTime);
        medInfo.setMonthType(monthType);
        medInfo.setDoseNumber(doseNumber);
        medInfo.setMedicationInterval(interval);
        medInfo.setMedicationStarted(isMedStarted);
        medInfo.setMedicationType(medicationType);
        medInfo.setDosageCount(0); //start all dosage count from 0

        medInfoList.add(medInfo);
        if (isMedStarted){
//            notificationDispatcherService.dispatchJob(context, medInfo);
//            NotificationPresenter.sendNotification(context, medInfo);
            AlarmSetter.setUpAlarm(context, medInfo);
        }
        new SaveAsync().execute(medInfoList);
        dataManager.getAllMedications();
        activeMedicationsDataManager.requeryActiveMedications();
    }



    private static class SaveAsync extends AsyncTask<List<MedInfo>, Void, List<MedInfo>>{


        @Override
        protected List<MedInfo> doInBackground(List<MedInfo>[] lists) {
            asyncMedDao.insertMedInfo(lists[0].get(lists.length-1));
            return asyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
//            Log.v("TAG", "Med Name is "+medInfos.get(0).getMedicationName());
//            Log.v("TAG", "Med Name at pos 1 is "+medInfos.get(1).getMedicationName());
//            Log.v("TAG", "Med Description is "+medInfos.get(0).getMedicationDescription());
        }
    }

    //This interface sets up the newly added item for a job
    public interface ShowNotificationForMedInterface{
        void dispatchJob(Context context, MedInfo medInfo);
    }

}
