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
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.RecyclerViewItemDivider;
import com.medmanager.android.presenter.viewpresenters.ViewPresenterInterface;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class AllMedicationFragment extends BaseFragment implements ViewPresenterInterface {

    private RecyclerView mAllCategoryRecyclerView;
    private View mEmptyView;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public AllMedicationFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allMedFragmentPresenter.setFragment(this);
        allMedFragmentPresenter.loadMedications();
        activeMedFragmentPresenter.loadActiveMedications();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_meds, container, false);
        mAllCategoryRecyclerView = view.findViewById(R.id.recycler_view_all_category);
        mEmptyView = view.findViewById(R.id.empty_root);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_all_meds);

        mAllCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAllCategoryRecyclerView.addItemDecoration(new RecyclerViewItemDivider(getContext()));
        allMedicationAdapter.setMedInfo(MedsSingleton.getInstance().getAllMedicationsInfo());
        mAllCategoryRecyclerView.setAdapter(allMedicationAdapter);



        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        allMedFragmentPresenter.loadMedications();
                        activeMedFragmentPresenter.loadActiveMedications();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allMedicationAdapter.getItemCount()==0){
            mEmptyView.setVisibility(View.VISIBLE);
            mAllCategoryRecyclerView.setVisibility(View.GONE);
        }
        else{
            mEmptyView.setVisibility(View.GONE);
            mAllCategoryRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allMedFragmentPresenter.detachFragment();
    }

    @Override
    public void setEmptyMedicationsView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mAllCategoryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMedications(List<MedInfo> medInfos) {
        mEmptyView.setVisibility(View.GONE);
        mAllCategoryRecyclerView.setVisibility(View.VISIBLE);
        allMedicationAdapter.setMedInfo(medInfos);
        allMedicationAdapter.notifyDataSetChanged();
        mAllCategoryRecyclerView.setAdapter(allMedicationAdapter);
    }
}
