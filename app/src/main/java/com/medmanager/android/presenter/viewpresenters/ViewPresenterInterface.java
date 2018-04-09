package com.medmanager.android.presenter.viewpresenters;

import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.views.activities.BaseActivity;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public interface ViewPresenterInterface {
    void setEmptyMedicationsView();
    void showMedications(List<MedInfo> medInfos);

     interface ActiveMedsInterface{
        void setEmptyActiveMedsView();
        void showActiveMedications(List<MedInfo> medInfos);
    }

}
