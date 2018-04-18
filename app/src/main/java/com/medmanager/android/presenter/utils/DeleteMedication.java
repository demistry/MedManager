package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.services.NotificationDispatcherService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 13/04/2018.
 */

public class DeleteMedication {

    private final Context mContext;
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

    public DeleteMedication(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
        medInfo = new MedInfo();
        medInfoList = new ArrayList<>();
    }

    public void deleteMedication(Context context, MedInfo medInfo){
        new DeleteAsync().execute(medInfo);
    }

    private static class DeleteAsync extends AsyncTask<MedInfo, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(MedInfo... medInfos) {
            asyncMedDao.deleteMedInfo(medInfos[0]);
            return asyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            MedsSingleton.getInstance().setAllMedArrayList(medInfos);

        }
    }
}
