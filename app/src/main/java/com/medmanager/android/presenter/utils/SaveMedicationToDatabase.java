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

    private static MedicationDAO asyncMedDao;
    private static List<MedInfo> asyncMedInfo;


    public SaveMedicationToDatabase(Context context){
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
        medInfo = new MedInfo();
        medInfoList = new ArrayList<>();
    }
    public void saveMedToRoomDatabase(String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, String pillNumber, int interval, boolean isMedStarted, String medicationType){
        medInfo.setMedicationName(medName);
        medInfo.setMedicationDescription(medDescription);
        medInfo.setStartDate(startDate);
        medInfo.setStartTime(startTime);
        medInfo.setEndDate(endDate);
        medInfo.setEndTime(endTime);
        medInfo.setMonthType(monthType);
        medInfo.setPillNumber(pillNumber);
        medInfo.setMedicationInterval(interval);
        medInfo.setMedicationStarted(isMedStarted);
        medInfo.setMedicationType(medicationType);
        medInfoList.add(medInfo);
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

}
