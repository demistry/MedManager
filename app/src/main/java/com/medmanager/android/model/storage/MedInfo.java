package com.medmanager.android.model.storage;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */

@Entity
public class MedInfo {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String medicationName;
    private String medicationDescription;
    private int monthType;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    @Ignore
    private int medicationFrequency;
    private int medicationInterval;

    private boolean isMedicationStarted;


    public MedInfo(){

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

    public String getEndDate() {
        return endDate;
    }

//    public int getMedicationFrequency() {
//        return medicationFrequency;
//    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setMedicationDescription(String medicationDescription) {
        this.medicationDescription = medicationDescription;
    }

    public int getMonthType() {
        return monthType;
    }

    public void setMonthType(int monthType) {
        this.monthType = monthType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

//    public void setMedicationFrequency(int medicationFrequency) {
//        this.medicationFrequency = medicationFrequency;
//    }

    public int getMedicationInterval() {
        return medicationInterval;
    }

    public void setMedicationInterval(int medicationInterval) {
        this.medicationInterval = medicationInterval;
    }

    public boolean isMedicationStarted() {
        return isMedicationStarted;
    }

    public void setMedicationStarted(boolean medicationStarted) {
        isMedicationStarted = medicationStarted;
    }

    public int get_id() {
        return _id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
