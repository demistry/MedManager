package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.presenter.adapter.MonthCategoryAdapter;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class MonthlyMedicationFragment extends BaseFragment {

    private RecyclerView mMonthlyCategoryRecyclerView;
    private MonthCategoryAdapter monthCategoryAdapter;

    public MonthlyMedicationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_meds, container, false);
        mMonthlyCategoryRecyclerView = view.findViewById(R.id.recycler_view_monthly_category);
        monthCategoryAdapter = new MonthCategoryAdapter(getContext());

        mMonthlyCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mMonthlyCategoryRecyclerView.setAdapter(monthCategoryAdapter);
        return view;
    }
}
