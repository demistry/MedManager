package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.services.NotificationDispatcherService;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.RecyclerViewItemDivider;
import com.medmanager.android.presenter.viewpresenters.ViewPresenterInterface;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class AllMedicationFragment extends BaseFragment implements ViewPresenterInterface {

    private RecyclerView mAllCategoryRecyclerView;
    private TextView emptyTextView;


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
        emptyTextView = view.findViewById(R.id.text_empty_view);

        mAllCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAllCategoryRecyclerView.addItemDecoration(new RecyclerViewItemDivider(getContext()));
        allMedicationAdapter.setMedInfo(MedsSingleton.getInstance().getAllMedicationsInfo());
        //NotificationDispatcherService.showMedInfoOnNotification(getContext(), MedsSingleton.getInstance().getAllMedicationsInfo());
        mAllCategoryRecyclerView.setAdapter(allMedicationAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allMedFragmentPresenter.detachFragment();
    }

    @Override
    public void setEmptyMedicationsView() {
        emptyTextView.setVisibility(View.VISIBLE);
        mAllCategoryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMedications(List<MedInfo> medInfos) {
        emptyTextView.setVisibility(View.GONE);
        mAllCategoryRecyclerView.setVisibility(View.VISIBLE);
        allMedicationAdapter.setMedInfo(medInfos);
        allMedicationAdapter.notifyDataSetChanged();
        mAllCategoryRecyclerView.setAdapter(allMedicationAdapter);
    }
}
