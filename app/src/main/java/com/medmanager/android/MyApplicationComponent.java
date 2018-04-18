package com.medmanager.android;

import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.datamanagers.DataManagerClass;
import com.medmanager.android.model.di.MedModule;
import com.medmanager.android.presenter.adapter.ActiveMedicationsAdapter;
import com.medmanager.android.presenter.adapter.AllMedicationAdapter;

import com.medmanager.android.presenter.adapter.MonthlyCategoryAdapter;
import com.medmanager.android.presenter.adapter.SearchQueryAdapter;
import com.medmanager.android.presenter.services.NotificationDispatcherService;
import com.medmanager.android.presenter.utils.DeleteMedication;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.presenter.utils.UpdateMedicationCount;
import com.medmanager.android.presenter.utils.UpdateMedicationToDatabase;
import com.medmanager.android.presenter.viewpresenters.AllMedFragmentPresenter;
import com.medmanager.android.views.activities.BaseActivity;
import com.medmanager.android.views.fragments.BaseFragment;
import com.medmanager.android.views.fragments.DatePickerFragment;
import com.medmanager.android.views.fragments.IntervalSelectorFragment;
import com.medmanager.android.views.fragments.TimePickerFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */
@Singleton
@Component(modules = {MyApplicationModule.class})
public interface MyApplicationComponent {

    void inject(DaggerApplication daggerApplication);
    void inject(BaseActivity baseActivity);
    void inject(SaveMedicationToDatabase saveMedicationToDatabase);
    void inject(DatePickerFragment fragment);
    void inject(TimePickerFragment fragment);
    void inject(IntervalSelectorFragment fragment);
    void inject(BaseFragment baseFragment);
    void inject(AllMedicationAdapter allMedicationAdapter);
    void inject(ActiveMedicationsAdapter activeMedicationsAdapter);
    void inject(DataManagerClass dataManagerClass);
    void inject(AllMedFragmentPresenter fragmentPresenter);
    void inject(NotificationDispatcherService service);

    void inject(MonthlyCategoryAdapter monthlyCategoryAdapter);

    void inject(SearchQueryAdapter searchQueryAdapter);

    void inject(UpdateMedicationToDatabase updateMedicationToDatabase);

    void inject(DeleteMedication deleteMedication);

    void inject(UpdateMedicationCount updateMedicationCount);
}
