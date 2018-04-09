package com.medmanager.android.model.datamanagers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.utils.MedsSingleton;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public class ActiveMedicationsDataManager extends DataManagerClass {
    private static MedicationDAO sMedDao;

    public ActiveMedicationsDataManager(Context context) {
        super(context);
        sMedDao = medicationDAO;
    }
    public void requeryActiveMedications(){
        new QueryRoom().execute(sMedDao);
    }

    private static class QueryRoom extends AsyncTask<MedicationDAO, Void, List<MedInfo>> {

        @Override
        protected List<MedInfo> doInBackground(MedicationDAO... medicationDAOS) {
            return medicationDAOS[0].getActiveMedications(true);
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfo) {
            super.onPostExecute(medInfo);
            //medInfos = medInfo;
            MedsSingleton.getInstance().setActiveMedInfo(medInfo);
            //Log.v("TAG", "Active Med Array is " + medInfo.toString());
        }
    }
}
