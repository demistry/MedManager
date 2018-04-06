package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class ActiveMedicationFragment extends BaseFragment {

    //private RecyclerView mMonthlyCategoryRecyclerView;
    //private MonthCategoryAdapter monthCategoryAdapter;

    public ActiveMedicationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_meds, container, false);
        //mMonthlyCategoryRecyclerView = view.findViewById(R.id.recycler_view_monthly_category);
        //monthCategoryAdapter = new MonthCategoryAdapter(getContext());

        //MonthlyCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //mMonthlyCategoryRecyclerView.setAdapter(monthCategoryAdapter);
        return view;
    }
}
