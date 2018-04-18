package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.model.datamanagers.AllMedicationsDataManager;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.MonthlyMedsSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class MonthlyMedicationFragment extends BaseFragment {

    private RecyclerView mMonthlyCategoryRecyclerView;
    List<MonthlyMedsSection> mMedsSections;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;


    public MonthlyMedicationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_meds, container, false);
        mEmptyView = view.findViewById(R.id.empty_root);
        mMonthlyCategoryRecyclerView = view.findViewById(R.id.recycler_view_monthly_category);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_monthly_meds);
        mMedsSections = new ArrayList<>();

        for (int i = 0; i<MedsSingleton.getInstance().getAllMedicationsInfo().size(); i++){
            MonthlyMedsSection row = MonthlyMedsSection.createRow(MedsSingleton.getInstance().getAllMedicationsInfo().get(i));
            MonthlyMedsSection section = MonthlyMedsSection.createSection(MedsSingleton.getInstance().getAllMedicationsInfo().get(i).getMonthType());
            mMedsSections.add(section);
            mMedsSections.add(row);
        }
        monthCategoryAdapter.addSections(mMedsSections);

        mMonthlyCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMonthlyCategoryRecyclerView.setAdapter(monthCategoryAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        allMedFragmentPresenter.loadMedications();
                        new AllMedicationsDataManager(getContext()).getAllMedications();
                        monthCategoryAdapter.notifyDataSetChanged();
                        mMonthlyCategoryRecyclerView.setAdapter(monthCategoryAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (monthCategoryAdapter.getItemCount()==0){
            if(mEmptyView !=null)
            mEmptyView.setVisibility(View.VISIBLE);
            mMonthlyCategoryRecyclerView.setVisibility(View.GONE);
        }
        else{
            if(mEmptyView !=null)
            mEmptyView.setVisibility(View.GONE);
            mMonthlyCategoryRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
