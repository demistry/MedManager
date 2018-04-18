package com.medmanager.android.presenter.utils;

import com.medmanager.android.model.storage.MedInfo;




/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class MonthlyMedsSection {
    /**
     * Create a stateless Section object
     *
     *
     */
    private MedInfo row;
    private int monthType;
    private boolean isRow;

    public static MonthlyMedsSection createRow(MedInfo row){
        MonthlyMedsSection section = new MonthlyMedsSection();
        section.row = row;
        section.isRow = true;
        return section;
    }
    public static MonthlyMedsSection createSection(int monthType) {
        MonthlyMedsSection section = new MonthlyMedsSection();
        section.monthType = monthType;
        section.isRow = false;
        return section;
    }

    public MedInfo getRow() {
        return row;
    }

    public int getSection() {
        return monthType;
    }

    public boolean isRow() {
        return isRow;
    }



}
