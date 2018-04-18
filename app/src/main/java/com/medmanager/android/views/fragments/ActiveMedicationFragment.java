package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.model.datamanagers.ActiveMedicationsDataManager;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;

    public ActiveMedicationFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeMedFragmentPresenter.setFragment(this);
        activeMedFragmentPresenter.loadActiveMedications();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_meds, container, false);
        mActiveMedsRecyclerView = view.findViewById(R.id.recycler_view_active_category);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_active_meds);
        mEmptyView = view.findViewById(R.id.empty_root);

        mActiveMedicationsAdapter.setActiveMedInfo(MedsSingleton.getInstance().getActiveMedInfo());
        mActiveMedsRecyclerView.addItemDecoration(new RecyclerViewItemDivider(getContext()));
        mActiveMedsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mActiveMedsRecyclerView.setAdapter(mActiveMedicationsAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        activeMedFragmentPresenter.loadActiveMedications();
                        new ActiveMedicationsDataManager(getContext()).requeryActiveMedications();
                        mActiveMedicationsAdapter.notifyDataSetChanged();
                        mActiveMedsRecyclerView.setAdapter(mActiveMedicationsAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActiveMedicationsAdapter.getItemCount()==0){
            mEmptyView.setVisibility(View.VISIBLE);
            mActiveMedsRecyclerView.setVisibility(View.GONE);
        }
        else{
            mEmptyView.setVisibility(View.GONE);
            mActiveMedsRecyclerView.setVisibility(View.VISIBLE);
        }
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
