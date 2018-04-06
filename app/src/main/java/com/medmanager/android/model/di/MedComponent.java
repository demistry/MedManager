package com.medmanager.android.model.di;

import com.medmanager.android.MyApplicationModule;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.views.activities.AddMedicationActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */
@Singleton
@Component(modules = {MedModule.class})
public interface MedComponent {
    //void inject(AddMedicationActivity medicationActivity);
    //void inject(SaveMedicationToDatabase saveMedicationToDatabase);
}
