package com.medmanager.android.presenter.utils;

import com.medmanager.android.model.storage.MedInfo;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public class MedsSingleton {
    private static final MedsSingleton ourInstance = new MedsSingleton();
    private List<MedInfo> mInfos;
    private List<MedInfo> activeMedInfo;

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
}
