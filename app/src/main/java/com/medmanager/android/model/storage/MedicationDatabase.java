package com.medmanager.android.model.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 * This class is the SQL Layer database for storing medication information
 */
@Database(entities = MedInfo.class, exportSchema = false, version = 1)
public abstract class MedicationDatabase extends RoomDatabase {
    public abstract MedicationDAO getDAO();
}

