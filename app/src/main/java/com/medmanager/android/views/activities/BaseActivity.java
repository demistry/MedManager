package com.medmanager.android.views.activities;

import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medmanager.android.DaggerApplication;
import com.medmanager.android.MyApplicationComponent;
import com.medmanager.android.model.datamanagers.AdapterInterfaceDataManager;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.model.storage.MedicationDatabase;
import com.medmanager.android.presenter.utils.DeleteMedication;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.SaveMedicationToDatabase;
import com.medmanager.android.presenter.utils.UpdateMedicationCount;
import com.medmanager.android.presenter.utils.UpdateMedicationToDatabase;
import com.medmanager.android.views.fragments.DatePickerFragment;
import com.medmanager.android.views.fragments.EditProfileFragment;
import com.medmanager.android.views.fragments.IntervalSelectorFragment;
import com.medmanager.android.views.fragments.TimePickerFragment;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 * This class provides a base for injection of dependencies to activities via dagger
 * It is subclassed by every activity in the application
 */

public class BaseActivity extends AppCompatActivity {
    @Inject
    public FirebaseAuth firebaseAuth;

    @Inject
    public MedicationDAO medicationDAO;

    @Inject
    public SaveMedicationToDatabase saveMedicationToDatabase;

    @Inject
    public UpdateMedicationToDatabase updateMedicationToDatabase;

    @Inject
    public DatePickerFragment datePickerFragment;

    @Inject
    public TimePickerFragment timePickerFragment;

    @Inject
    public EditProfileFragment editProfileFragment;

    @Inject
    public IntervalSelectorFragment intervalSelectorFragment;

    @Inject
    public InterfaceDataManager interfaceDataManager;

    @Inject
    AdapterInterfaceDataManager adapterInterfaceDataManager;

    @Inject
    DeleteMedication deleteMedication;

    @Inject
    UpdateMedicationCount updateMedicationCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getApplication()).getMyApplicationComponent().inject(this);
    }
}
