package com.medmanager.android.model.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 * Inspiration
 */


@Dao
public interface MedicationDAO {

    @Query("SELECT * FROM MedInfo")
    List<MedInfo> getAllMedications();

    @Query("SELECT * FROM MedInfo WHERE isMedicationStarted LIKE :isMedicationStarted ")
    List<MedInfo> getActiveMedications(boolean isMedicationStarted);

    @Insert(onConflict = REPLACE)
    void insertMedInfo(MedInfo medInfos);
}
