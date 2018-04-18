package com.medmanager.android;

import android.app.Application;

import com.medmanager.android.model.di.MedComponent;
import com.medmanager.android.model.di.MedModule;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */

public class DaggerApplication extends Application {
    MyApplicationComponent myApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplicationComponent = DaggerMyApplicationComponent.builder().myApplicationModule(new MyApplicationModule(this)).build();
        myApplicationComponent.inject(this);
    }
    public MyApplicationComponent getMyApplicationComponent(){
        return myApplicationComponent;
    }
    //public static MedComponent getMedComponent(){return medComponent;}
}
