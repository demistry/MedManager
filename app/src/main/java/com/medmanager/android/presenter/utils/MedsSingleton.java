package com.medmanager.android.presenter.utils;

import android.os.Bundle;

import com.medmanager.android.model.storage.MedInfo;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 *
 * Helps to retain instances of medication information for use in various views
 */

public class MedsSingleton {
    private static final MedsSingleton ourInstance = new MedsSingleton();
    private List<MedInfo> mInfos;
    private List<MedInfo> activeMedInfo;
    private int prevCount;


    public static MedsSingleton getInstance() {
        return ourInstance;
    }

    private MedsSingleton() {
    }

    public void setAllMedArrayList(List<MedInfo> infos){

        mInfos = infos;
    }

    public List<MedInfo> getAllMedicationsInfo() {
        return mInfos;
    }

    public List<MedInfo> getActiveMedInfo() {
        return activeMedInfo;
    }

    public void setActiveMedInfo(List<MedInfo> activeMedInfo) {
        this.activeMedInfo = activeMedInfo;
    }

    public void setPrevCount(int prevCount) {
        this.prevCount = prevCount;
    }

    public int getPrevCount() {
        return prevCount;
    }
}
