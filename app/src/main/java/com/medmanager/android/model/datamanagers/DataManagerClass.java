package com.medmanager.android.model.datamanagers;

import android.content.Context;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.model.storage.MedicationDAO;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public class DataManagerClass {
    @Inject
    MedicationDAO medicationDAO;

    public DataManagerClass(Context context){
        ((DaggerApplication)context.getApplicationContext()).getMyApplicationComponent().inject(this);
    }
    public DataManagerClass(){

    }



}
