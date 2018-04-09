package com.medmanager.android;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.datamanagers.DataManagerClass;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.model.storage.MedicationDatabase;
import com.medmanager.android.presenter.adapter.ActiveMedicationsAdapter;
import com.medmanager.android.presenter.adapter.AllMedicationAdapter;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.presenter.viewpresenters.ActiveMedFragmentPresenter;
import com.medmanager.android.presenter.viewpresenters.AllMedFragmentPresenter;
import com.medmanager.android.views.activities.BaseActivity;
import com.medmanager.android.views.fragments.ActiveMedicationFragment;
import com.medmanager.android.views.fragments.DatePickerFragment;
import com.medmanager.android.views.fragments.IntervalSelectorFragment;
import com.medmanager.android.views.fragments.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */
@Module
public class MyApplicationModule {
    private DaggerApplication daggerApplication;
    private List<MedInfo> medInfoArrayList = new ArrayList<>();

    public MyApplicationModule(DaggerApplication daggerApplication){
        this.daggerApplication = daggerApplication;
    }
    public MyApplicationModule(){

    }
    @Provides
    @Singleton
    Context provideApplicationContext(){return daggerApplication;}

    @Provides @Singleton
    FirebaseAuth providesFireBaseAuth(){return FirebaseAuth.getInstance();}

    @Provides @Singleton
    MedicationDatabase providesMedicationDatabase(Context context){return Room.databaseBuilder(context,
            MedicationDatabase.class,
            "database-name")
            .build();
    }

    @Provides @Singleton
    MedicationDAO providesMedicationDAO(MedicationDatabase medicationDatabase){ return medicationDatabase.getDAO();}

    @Provides @Singleton
    SaveMedicationToDatabase providesMedication(){return new SaveMedicationToDatabase(daggerApplication);}

    @Provides @Singleton
    AllMedicationsDataManager providesAllMedsDataManager(){return new AllMedicationsDataManager(daggerApplication);}

    @Provides @Singleton
    ActiveMedicationsDataManager providesActiveMedsDataManager(){return new ActiveMedicationsDataManager(daggerApplication);}

    @Provides @Singleton
    AllMedicationAdapter providesAllMeds(){return new AllMedicationAdapter();}

    @Provides @Singleton
    ActiveMedicationsAdapter providesActiveMedsAdapter(){return new ActiveMedicationsAdapter();}

    @Provides @Singleton
    DataManagerClass provideDataManager(){return new DataManagerClass(daggerApplication);}



    @Provides
    MedInfo provideMedInfo(){
        return new MedInfo();
    }


    @Provides
    List<MedInfo> provideMedArrayList(MedInfo medInfo){
        medInfoArrayList.add(medInfo);
        return medInfoArrayList;
    }

    @Provides
    Calendar provideCalendar(){return Calendar.getInstance();}

    @Provides
    DatePickerFragment provideDatePickerFragment(){return new DatePickerFragment();}

    @Provides
    TimePickerFragment providesTimePickerFragment(){return new TimePickerFragment();}

    @Provides
    ActiveMedicationFragment providesActiveMedFragment(){return new ActiveMedicationFragment();}

    @Provides @Singleton
    IntervalSelectorFragment providesInterval(){return new IntervalSelectorFragment();}

    @Provides @Singleton
    InterfaceDataManager providesInterfaceDataManager(){return new InterfaceDataManager();}

    @Provides @Singleton
    AllMedFragmentPresenter providesAllMedPresenter(){return new AllMedFragmentPresenter();}

    @Provides @Singleton
    ActiveMedFragmentPresenter providesActiveMedPresenter(){return new ActiveMedFragmentPresenter();}

}
