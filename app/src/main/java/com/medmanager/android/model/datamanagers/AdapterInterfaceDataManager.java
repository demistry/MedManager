package com.medmanager.android.model.datamanagers;

import android.content.Context;
import android.content.Intent;

import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.adapter.AllMedicationAdapter;
import com.medmanager.android.views.activities.AboutMedicationActivity;

/**
 * Created by ILENWABOR DAVID on 10/04/2018.
 *
 * This class responds to clicks from all Medication Adapters
 */

public class AdapterInterfaceDataManager implements AllMedicationAdapter.MedicationClickedInterface {

    private String medicationName;
    private String medicationDescription;
    private String startDate;//
    private String startTime;//
    private String endDate;//
    private String endTime;//
    private String doseNumber;
    private String medicationType;//
    private int dosageCount;


    /**
     * Medication interval is number of hours that must pass between taking meds
     */
    private int medicationInterval;


    private boolean isMedicationStarted;//

    private String serializedMedifcation;

    private MedInfo medInfo;


    public AdapterInterfaceDataManager(){
    }

    @Override
    public void onMedicationClicked(MedInfo medInfo, Context context) {
        this.medicationName = medInfo.getMedicationName();
        this.medicationDescription = medInfo.getMedicationDescription();
        this.startDate = medInfo.getStartDate();
        this.startTime = medInfo.getStartTime();
        this.endDate = medInfo.getEndDate();
        this.endTime = medInfo.getEndTime();
        this.doseNumber = medInfo.getDoseNumber();
        this.medicationType = medInfo.getMedicationType();
        this.medicationInterval = medInfo.getMedicationInterval();
        this.dosageCount = medInfo.getDosageCount();

        this.isMedicationStarted = medInfo.isMedicationStarted();
        this.serializedMedifcation = medInfo.serialize();
        this.medInfo = medInfo;
        context.startActivity(new Intent(context, AboutMedicationActivity.class));
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getMedicationDescription() {
        return medicationDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDoseNumber() {
        return doseNumber;
    }

    public String getMedicationType() {
        if (medicationType!=null)
        return medicationType;
        else return "";
    }

    public int getMedicationInterval() {
        return medicationInterval;
    }

    public int getDosageCount() {
        return dosageCount;
    }

    public boolean isMedicationStarted() {
        return isMedicationStarted;
    }

    public String getSerializedMedifcation() {
        return serializedMedifcation;
    }

    public MedInfo getMedInfo() {
        return medInfo;
    }
}
