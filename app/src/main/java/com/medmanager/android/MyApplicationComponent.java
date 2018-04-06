package com.medmanager.android;

import com.medmanager.android.model.di.MedModule;
import com.medmanager.android.presenter.adapter.MonthCategoryAdapter;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
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
}