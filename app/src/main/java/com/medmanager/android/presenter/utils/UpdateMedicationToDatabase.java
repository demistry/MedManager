package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.AlarmSetter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 * Class for updating medication to database
 */

public class UpdateMedicationToDatabase {
    @Inject
    MedicationDAO medicationDAO;
    @Inject
    AllMedicationsDataManager dataManager;
    @Inject
    ActiveMedicationsDataManager activeMedicationsDataManager;

    private static MedInfo sMedInfo;
    private static List<MedInfo> sMedInfoList;
    private static MedicationDAO sAsyncMedDao;
    private static List<MedInfo> sAsyncMedInfo;
    private Context mContext;


    public UpdateMedicationToDatabase(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        sAsyncMedDao = medicationDAO;
        sAsyncMedInfo = sMedInfoList;
        sMedInfo = new MedInfo();
        sMedInfoList = new ArrayList<>();
    }

    /**
     * This method deletes a previous medication information and inserts a new one in its place**/
    public void updateMedToRoomDatabase(Context context, String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, String doseNumber, int interval, boolean isMedStarted, String medicationType, MedInfo prevMedInfo){
        sMedInfo.setMedicationName(medName);
        sMedInfo.setMedicationDescription(medDescription);
        sMedInfo.setStartDate(startDate);
        sMedInfo.setStartTime(startTime);
        sMedInfo.setEndDate(endDate);
        sMedInfo.setEndTime(endTime);
        sMedInfo.setMonthType(monthType);
        sMedInfo.setDoseNumber(doseNumber);
        sMedInfo.setMedicationInterval(interval);
        sMedInfo.setMedicationStarted(isMedStarted);
        sMedInfo.setMedicationType(medicationType);
        AlertDialogCreator.cancelAlarm(context, prevMedInfo);
        //sMedInfo.setDosageCount(0); //start all dosage count from 0

        sMedInfoList.add(sMedInfo);
        if (isMedStarted){
//            notificationDispatcherService.dispatchJob(context, sMedInfo);
//            NotificationPresenter.sendNotification(context, sMedInfo);
            AlarmSetter.setUpAlarm(context, sMedInfo);
        }

        new UpdateAsync().execute(prevMedInfo, sMedInfo);
        dataManager.getAllMedications();
        activeMedicationsDataManager.requeryActiveMedications();
    }



    private static class UpdateAsync extends AsyncTask<MedInfo, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(MedInfo... medInfos) {
            sAsyncMedDao.deleteMedInfo(medInfos[0]);
            sAsyncMedDao.insertMedInfo(medInfos[1]);
            return sAsyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            MedsSingleton.getInstance().setAllMedArrayList(medInfos);

        }
    }

}
