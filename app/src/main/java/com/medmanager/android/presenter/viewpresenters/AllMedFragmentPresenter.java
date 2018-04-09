package com.medmanager.android.presenter.viewpresenters;


import android.util.Log;

import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.views.fragments.AllMedicationFragment;


import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public class AllMedFragmentPresenter {
    private Disposable mDisposable;
    private Observer<List<MedInfo>> listObserver;
    private AllMedicationFragment mFragment;

    @Inject
    AllMedicationsDataManager dataManager;

    public AllMedFragmentPresenter(){

    }

    public void setFragment(AllMedicationFragment allMedicationFragment){
        this.mFragment = allMedicationFragment;
    }


    public void loadMedications(){
        listObserver = new Observer<List<MedInfo>>(){

            @Override
            public void onSubscribe(Disposable s) {
                mDisposable = s;

            }

            @Override
            public void onNext(List<MedInfo> medInfos) {
                if (medInfos.isEmpty()) mFragment.setEmptyMedicationsView();
                else{
                    mFragment.showMedications(medInfos);
                    //Log.v("TAG", "From observer we have" + medInfos.get(medInfos.size()-1).getMedicationName());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        if (MedsSingleton.getInstance().getAllMedicationsInfo()!=null){
            io.reactivex.Observable.fromArray(MedsSingleton.getInstance().getAllMedicationsInfo())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(listObserver);
            //Log.v("TAG", "Instance returns "+ MedsSingleton.getInstance().getAllMedicationsInfo().toString());
        }
        else{
            new AllMedicationsDataManager(mFragment.getContext()).getAllMedications();
        }

    }
    public void detachFragment(){
        if (mDisposable!=null) mDisposable.dispose();
    }
}
