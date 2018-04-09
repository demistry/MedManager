package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.presenter.adapter.MonthCategoryAdapter;
import com.medmanager.android.presenter.adapter.MonthSectionRecyclerView;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.MonthlyMedsSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class MonthlyMedicationFragment extends BaseFragment {

    private RecyclerView mMonthlyCategoryRecyclerView;
    private MonthSectionRecyclerView monthCategoryAdapter;
    List<MonthlyMedsSection> medsSections;


    public MonthlyMedicationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_meds, container, false);
        mMonthlyCategoryRecyclerView = view.findViewById(R.id.recycler_view_monthly_category);
        medsSections = new ArrayList<>();
        for (int i = 0; i<MedsSingleton.getInstance().getAllMedicationsInfo().size(); i++){
            MonthlyMedsSection row = MonthlyMedsSection.createRow(MedsSingleton.getInstance().getAllMedicationsInfo().get(i));
            MonthlyMedsSection section = MonthlyMedsSection.createSection(MedsSingleton.getInstance().getAllMedicationsInfo().get(i).getMonthType());
            medsSections.add(section);
            medsSections.add(row);
        }
        Log.v("TAG", "Med Sectioning contains "+ medsSections.toString());
        monthCategoryAdapter = new MonthSectionRecyclerView(medsSections);

        mMonthlyCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMonthlyCategoryRecyclerView.setAdapter(monthCategoryAdapter);
        return view;
    }
}
