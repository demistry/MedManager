package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ActiveMedicationFragment extends BaseFragment implements ViewPresenterInterface.ActiveMedsInterface {

    private RecyclerView mActiveMedsRecyclerView;

    public ActiveMedicationFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeMedFragmentPresenter.setFragment(this);
        //FragmentSingleton.getInstance().setFragment(this);
        activeMedFragmentPresenter.loadActiveMedications();
        Log.v("TAG", "Active MED Fragmeent OnCreate called");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_meds, container, false);
        mActiveMedsRecyclerView = view.findViewById(R.id.recycler_view_active_category);

        mActiveMedicationsAdapter.setActiveMedInfo(MedsSingleton.getInstance().getActiveMedInfo());
        mActiveMedsRecyclerView.addItemDecoration(new RecyclerViewItemDivider(getContext()));
        mActiveMedsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mActiveMedsRecyclerView.setAdapter(mActiveMedicationsAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activeMedFragmentPresenter.detachFragment();
    }


    @Override
    public void setEmptyActiveMedsView() {

    }

    @Override
    public void showActiveMedications(List<MedInfo> medInfos) {
        mActiveMedicationsAdapter.setActiveMedInfo(medInfos);
        mActiveMedicationsAdapter.notifyDataSetChanged();
        mActiveMedsRecyclerView.setAdapter(mActiveMedicationsAdapter);
    }
}
