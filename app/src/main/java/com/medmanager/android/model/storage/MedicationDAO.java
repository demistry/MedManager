package com.medmanager.android.model.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 * Database CRUD methods for Medications
 */


@Dao
public interface MedicationDAO {

    /*
    Get All Medications from Database
     */
    @Query("SELECT * FROM MedInfo")
    List<MedInfo> getAllMedications();

    /*
    Get Only Active Medications from Database
     */
    @Query("SELECT * FROM MedInfo WHERE isMedicationStarted LIKE :isMedicationStarted ")
    List<MedInfo> getActiveMedications(boolean isMedicationStarted);


    /*
    Add New Medications to Database
     */
    @Insert(onConflict = REPLACE)
    void insertMedInfo(MedInfo medInfos);

    /*
    Update Dosage count using medication name
     */
    @Query("UPDATE MedInfo SET dosageCount = :dosageCount WHERE medname = :medName")
    void updateDosageCount(String medName, int dosageCount);

    /*
    Delete a medication
     */
    @Delete
    void deleteMedInfo(MedInfo medInfo);
}
