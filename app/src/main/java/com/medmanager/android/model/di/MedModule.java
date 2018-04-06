package com.medmanager.android.model.di;

import android.content.Context;

import com.medmanager.android.MyApplicationModule;
import com.medmanager.android.model.storage.MedInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */
@Module
public class MedModule {
    private List<MedInfo> medInfoArrayList = new ArrayList<>();

    public MedModule(){

    }

//    @Provides
//    MedInfo provideMedInfo(){
//        return new MedInfo();
//    }


    @Provides
    List<MedInfo> provideMedArrayList(MedInfo medInfo){
        medInfoArrayList.add(medInfo);
        return medInfoArrayList;
    }
}
