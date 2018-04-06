package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.di.MedComponent;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class SaveMedicationToDatabase {


    @Inject
    MedInfo medInfo;
    @Inject
    List<MedInfo> medInfoList;
    @Inject
    MedicationDAO medicationDAO;

    private static MedicationDAO asyncMedDao;
    private static List<MedInfo> asyncMedInfo;


    public SaveMedicationToDatabase(Context context){
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
    }
    public void saveMedToRoomDatabase(String medName, String medDescription, String startDate, String startTime, String endDate, String endTime,
                                      int monthType, int interval, boolean isMedStarted){
        medInfo.setMedicationName(medName);
        medInfo.setMedicationDescription(medDescription);
        medInfo.setStartDate(startDate);
        medInfo.setStartTime(startTime);
        medInfo.setEndDate(endDate);
        medInfo.setEndTime(endTime);
        medInfo.setMonthType(monthType);
        //medInfo.setMedicationFrequency(frequency);
        medInfo.setMedicationInterval(interval);
        medInfo.setMedicationStarted(isMedStarted);
        new SaveAsync().execute();

    }
    private static class SaveAsync extends AsyncTask<Void, Void, List<MedInfo>>{

        @Override
        protected List<MedInfo> doInBackground(Void... voids) {
            asyncMedDao.insertMedInfo(asyncMedInfo);
            return asyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            Log.v("TAG", medInfos.toString());
        }
    }

}
