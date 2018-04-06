package com.medmanager.android.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.medmanager.android.views.fragments.ActiveMedicationFragment;
import com.medmanager.android.views.fragments.AllMedicationFragment;
import com.medmanager.android.views.fragments.MonthlyMedicationFragment;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class MedViewPagerAdapter extends FragmentPagerAdapter {


    private int tabCount;

    public MedViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new AllMedicationFragment();
            case 1: return new ActiveMedicationFragment();
            case 2: return new MonthlyMedicationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
