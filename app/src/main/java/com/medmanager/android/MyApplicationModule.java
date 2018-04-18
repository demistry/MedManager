package com.medmanager.android;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
import com.medmanager.android.model.datamanagers.AdapterInterfaceDataManager;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.model.datamanagers.DataManagerClass;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.model.storage.MedicationDatabase;
import com.medmanager.android.presenter.adapter.ActiveMedicationsAdapter;
import com.medmanager.android.presenter.adapter.AllMedicationAdapter;
import com.medmanager.android.presenter.adapter.MonthlyCategoryAdapter;
import com.medmanager.android.presenter.utils.DeleteMedication;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.presenter.utils.UpdateMedicationCount;
import com.medmanager.android.presenter.utils.UpdateMedicationToDatabase;
import com.medmanager.android.presenter.viewpresenters.ActiveMedFragmentPresenter;
import com.medmanager.android.presenter.viewpresenters.AllMedFragmentPresenter;
import com.medmanager.android.views.fragments.ActiveMedicationFragment;
import com.medmanager.android.views.fragments.DatePickerFragment;
import com.medmanager.android.views.fragments.EditProfileFragment;
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
 * Application module that provides all dependencies throughout the app
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
    DeleteMedication providesDeleteMed(){return new DeleteMedication(daggerApplication);}

    @Provides @Singleton
    UpdateMedicationToDatabase providesUpdateMedication(){return new UpdateMedicationToDatabase(daggerApplication);}

    @Provides @Singleton
    UpdateMedicationCount providesCount(){return new UpdateMedicationCount(daggerApplication);}


    @Provides @Singleton
    AllMedicationsDataManager providesAllMedsDataManager(){return new AllMedicationsDataManager(daggerApplication);}

    @Provides @Singleton
    AdapterInterfaceDataManager providesAdapterInterfaceDataManager(){return new AdapterInterfaceDataManager();}

    @Provides @Singleton
    ActiveMedicationsDataManager providesActiveMedsDataManager(){return new ActiveMedicationsDataManager(daggerApplication);}

    @Provides @Singleton
    AllMedicationAdapter providesAllMeds(){return new AllMedicationAdapter(daggerApplication);}

    @Provides @Singleton
    ActiveMedicationsAdapter providesActiveMedsAdapter(){return new ActiveMedicationsAdapter(daggerApplication);}

    @Provides @Singleton
    MonthlyCategoryAdapter providesMonthlyCategory(){return new MonthlyCategoryAdapter(daggerApplication);}

    @Provides @Singleton
    DataManagerClass provideDataManager(){return new DataManagerClass(daggerApplication);}


    @Provides @Singleton
    SharedPreferences providesSharedPreferences(){ return daggerApplication.getSharedPreferences(ConstantClass.PREF_SHARED, Context.MODE_PRIVATE);}



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
    EditProfileFragment providesEditProfileFragment(){return new EditProfileFragment();}

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
