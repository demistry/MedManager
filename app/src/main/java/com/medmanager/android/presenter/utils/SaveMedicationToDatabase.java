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
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class SaveMedicationToDatabase {

    private static MedInfo sMedInfo;
    private static List<MedInfo> sMedInfoList;
    @Inject
    MedicationDAO medicationDAO;
    @Inject
    AllMedicationsDataManager dataManager;
    @Inject
    ActiveMedicationsDataManager activeMedicationsDataManager;

    private static MedicationDAO asyncMedDao;
    private static List<MedInfo> asyncMedInfo;
    private Context mContext;


    public SaveMedicationToDatabase(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = sMedInfoList;
        sMedInfo = new MedInfo();
        sMedInfoList = new ArrayList<>();
    }

    /*
    Method to save medication to database
     */
    public void saveMedToRoomDatabase(Context context, String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, String doseNumber, int interval, boolean isMedStarted, String medicationType){
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
        sMedInfo.setDosageCount(0); //start all dosage count from 0

        sMedInfoList.add(sMedInfo);
        if (isMedStarted){
            AlarmSetter.setUpAlarm(context, sMedInfo);
        }
        new SaveAsync().execute(sMedInfoList);
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
        }
    }


}
