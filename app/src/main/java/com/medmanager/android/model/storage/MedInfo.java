package com.medmanager.android.model.storage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 * This class holds all the information about the medication as stored in the database columns
 */

@Entity(tableName = "MedInfo")
public class MedInfo {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "medName")
    private String medicationName;//
    private String medicationDescription;//
    private int monthType;//
    private String startDate;//
    private String startTime;//
    private String endDate;//
    private String endTime;//


    private String doseNumber;
    private String medicationType;//
    private int medicationInterval;

    @ColumnInfo(name = "dosageCount")
    private int dosageCount;

    private boolean isMedicationStarted;//




    public MedInfo(){

    }

    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static MedInfo create(String serializedData){
        Gson gson = new Gson();
        return gson.fromJson(serializedData, MedInfo.class);
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

    public String getDoseNumber() {
        return doseNumber;
    }

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

    public void setDoseNumber(String doseNumber) {
        this.doseNumber = doseNumber;
    }

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

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public int getDosageCount() {
        return dosageCount;
    }

    public void setDosageCount(int dosageCount) {
        this.dosageCount = dosageCount;
    }
}
