package com.medmanager.android.model.datamanagers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.views.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 * This class queries the database for all medications
 */

public class AllMedicationsDataManager extends  DataManagerClass {

    private static List<MedInfo> sMedInfos;
    private static MedicationDAO sMedDao;


    public AllMedicationsDataManager(Context context){
        super(context);
        sMedDao = medicationDAO;
        new QueryRoom().execute(sMedDao);
    }

    /**
     * Used to get all medications from database
     */
    public void getAllMedications(){
        new QueryRoom().execute(sMedDao);
    }

    private static class QueryRoom extends AsyncTask<MedicationDAO, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(MedicationDAO... medicationDAOS) {
            return medicationDAOS[0].getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfo) {
            super.onPostExecute(medInfo);
            sMedInfos = medInfo;
            MedsSingleton.getInstance().setAllMedArrayList(medInfo);
            //Log.v("TAG", "Array is " + medInfos.toString());
        }
    }

}
