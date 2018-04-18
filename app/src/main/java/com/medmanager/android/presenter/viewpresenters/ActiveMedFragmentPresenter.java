package com.medmanager.android.presenter.viewpresenters;


import android.content.Context;
import android.util.Log;

import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.views.fragments.ActiveMedicationFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 * Presenter for the Active Medications Fragment
 */

public class ActiveMedFragmentPresenter {
    private Disposable mDisposable;
    private Observer<List<MedInfo>> mListObserver;

    private ActiveMedicationFragment mFragment;

    @Inject
    AllMedicationsDataManager dataManager;

    private Context mContext;

    public ActiveMedFragmentPresenter(){

    }
    public ActiveMedFragmentPresenter(Context context){

        this.mContext = context;
    }


    /**
     * Method to set the fragment
     * @param activeMedicationFragment ActiveMedicationFragment
     */
    public void setFragment(ActiveMedicationFragment activeMedicationFragment){
        this.mFragment = activeMedicationFragment;
    }


    /**
     * Method to reload all active medications
     */
    public void loadActiveMedications(){
        mListObserver = new Observer<List<MedInfo>>(){


            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(List<MedInfo> medInfos) {
                if(mFragment!=null){
                    if (medInfos.isEmpty()) mFragment.setEmptyActiveMedsView();
                    else if (mFragment!=null){
                        mFragment.showActiveMedications(medInfos);
                    }
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        if (MedsSingleton.getInstance().getActiveMedInfo()!=null){
            Observable.fromArray(MedsSingleton.getInstance().getActiveMedInfo())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(mListObserver);
        }
        else{
            new ActiveMedicationsDataManager(mContext).requeryActiveMedications();
        }

    }

    /**
     * Method to detach fragment
     */
    public void detachFragment(){
    }
}
