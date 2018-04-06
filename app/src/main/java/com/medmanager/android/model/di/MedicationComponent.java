package com.medmanager.android.model.di;

import com.medmanager.android.MyApplicationModule;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

@Singleton
@Component(modules = {MedModule.class})
public interface MedicationComponent {
    //void inject(AddMedicationActivity medicationActivity);
   // void inject(SaveMedicationToDatabase saveMedicationToDatabase);
}
