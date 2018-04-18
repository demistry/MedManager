package com.medmanager.android;

import android.app.Application;



/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 * Root of application for initial injection by dagger
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

}
