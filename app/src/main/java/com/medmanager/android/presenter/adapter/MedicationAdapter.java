package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.medmanager.android.model.di.MedComponent;
import com.medmanager.android.model.storage.MedInfo;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 31/03/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter {
    @Inject
    MedInfo medInfo;

    private MedComponent medComponent;

    public MedicationAdapter(Context context){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
