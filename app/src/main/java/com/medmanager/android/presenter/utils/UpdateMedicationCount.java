package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.database.Observable;
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

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ILENWABOR DAVID on 18/04/2018.
 */

public class UpdateMedicationCount {
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

    int returnedDosageCount;

    public UpdateMedicationCount(Context context){
        mContext = context;
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
        asyncMedDao = medicationDAO;
        asyncMedInfo = medInfoList;
        medInfo = new MedInfo();
        medInfoList = new ArrayList<>();
    }

    public int updateMedicationCount(Context context, String medName, int dosageCount){
        new UpdateAsync().execute(medName, dosageCount);
        io.reactivex.Observable<Integer> observable = io.reactivex.Observable.just(dosageCount);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                returnedDosageCount = integer;
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return returnedDosageCount;
    }

    private static class UpdateAsync extends AsyncTask<Object, Void, List<MedInfo>> {


        @Override
        protected List<MedInfo> doInBackground(Object... object) {
            asyncMedDao.updateDosageCount(String.valueOf(object[0]), (int) object[1]);
            return asyncMedDao.getAllMedications();
        }

        @Override
        protected void onPostExecute(List<MedInfo> medInfos) {
            super.onPostExecute(medInfos);
            MedsSingleton.getInstance().setAllMedArrayList(medInfos);

        }
    }
}
