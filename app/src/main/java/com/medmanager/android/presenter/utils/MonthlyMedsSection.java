package com.medmanager.android.presenter.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;



/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class MonthlyMedsSection {
    /**
     * Create a stateless Section object based on {@link SectionParameters}.
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
