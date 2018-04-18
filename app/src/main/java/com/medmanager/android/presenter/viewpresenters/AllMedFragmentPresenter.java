package com.medmanager.android.presenter.viewpresenters;


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
 * Preseter for All Medications Fragment
 */

public class AllMedFragmentPresenter {
    private Disposable mDisposable;
    private Observer<List<MedInfo>> mListObserver;
    private AllMedicationFragment mFragment;

    @Inject
    AllMedicationsDataManager dataManager;

    public AllMedFragmentPresenter(){

    }

    /**
     * Method to set the fragment
     * @param allMedicationFragment AllMedicationFragment
     */
    public void setFragment(AllMedicationFragment allMedicationFragment){
        this.mFragment = allMedicationFragment;
    }

    /**
     * Method to reload all medications
     */
    public void loadMedications(){
        mListObserver = new Observer<List<MedInfo>>(){

            @Override
            public void onSubscribe(Disposable s) {
                mDisposable = s;

            }

            @Override
            public void onNext(List<MedInfo> medInfos) {
                if (medInfos.isEmpty()) mFragment.setEmptyMedicationsView();
                else{
                    mFragment.showMedications(medInfos);
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
                    .subscribe(mListObserver);
        }
        else{
            new AllMedicationsDataManager(mFragment.getContext()).getAllMedications();
        }

    }

    /**
     * Method to remove resources
      */
    public void detachFragment(){
        if (mDisposable!=null) mDisposable.dispose();
    }
}
