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
 * This class queries the database for all medications that are currently active
 */



public class ActiveMedicationsDataManager extends DataManagerClass {
    private static MedicationDAO sMedDao;

    public ActiveMedicationsDataManager(Context context) {
        super(context);
        sMedDao = medicationDAO;
    }

    /**
     * This method refreshes the active medications adapter
     */
    public void requeryActiveMedications(){

        if (medicationDAO!=null)new QueryRoom().execute(medicationDAO);
    }



    private static class QueryRoom extends AsyncTask<MedicationDAO, Void, List<MedInfo>> {

        @Override
        protected List<MedInfo> doInBackground(MedicationDAO... medicationDAOS) {
            return medicationDAOS[0].getActiveMedications(true);
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfo) {
            super.onPostExecute(medInfo);
            MedsSingleton.getInstance().setActiveMedInfo(medInfo);
        }
    }
}
