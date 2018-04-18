package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 * Class to delete medication from database
 */

public class DeleteMedication {

    private final Context mContext;
    private static MedInfo sMedInfo;
    private static List<MedInfo> sMedInfoList;

    @Inject
    MedicationDAO medicationDAO;
    @Inject
    AllMedicationsDataManager dataManager;
    @Inject
    ActiveMedicationsDataManager activeMedicationsDataManager;

    private static MedicationDAO sAsyncMedDao;
    private static List<MedInfo> sAsyncMedInfo;

    public DeleteMedication(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        sAsyncMedDao = medicationDAO;
        sAsyncMedInfo = sMedInfoList;
        sMedInfo = new MedInfo();
        sMedInfoList = new ArrayList<>();
    }

    public void deleteMedication(Context context, MedInfo medInfo){
        new DeleteAsync().execute(medInfo);
    }

    private static class DeleteAsync extends AsyncTask<MedInfo, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(MedInfo... medInfos) {
            sAsyncMedDao.deleteMedInfo(medInfos[0]);
            return sAsyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            MedsSingleton.getInstance().setAllMedArrayList(medInfos);

        }
    }
}
