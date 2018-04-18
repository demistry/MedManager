package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.AlarmSetter;
import com.medmanager.android.presenter.services.NotificationDispatcherService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 */

public class UpdateMedicationToDatabase {
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
    private final SaveMedicationToDatabase.ShowNotificationForMedInterface medInterface = notificationDispatcherService;
    private Context mContext;


    public UpdateMedicationToDatabase(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
        medInfo = new MedInfo();
        medInfoList = new ArrayList<>();
    }

    /**
     * This method deletes a previous medication information and inserts a new one in its place**/
    public void updateMedToRoomDatabase(Context context, String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, String doseNumber, int interval, boolean isMedStarted, String medicationType, MedInfo prevMedInfo){
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
        AlertDialogCreator.cancelAlarm(context, prevMedInfo);
        //medInfo.setDosageCount(0); //start all dosage count from 0

        medInfoList.add(medInfo);
        if (isMedStarted){
//            notificationDispatcherService.dispatchJob(context, medInfo);
//            NotificationPresenter.sendNotification(context, medInfo);
            AlarmSetter.setUpAlarm(context, medInfo);
        }

        new UpdateAsync().execute(prevMedInfo, medInfo);
        dataManager.getAllMedications();
        activeMedicationsDataManager.requeryActiveMedications();
    }



    private static class UpdateAsync extends AsyncTask<MedInfo, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(MedInfo... medInfos) {
            asyncMedDao.deleteMedInfo(medInfos[0]);
            asyncMedDao.insertMedInfo(medInfos[1]);
            return asyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            MedsSingleton.getInstance().setAllMedArrayList(medInfos);

        }
    }

}
