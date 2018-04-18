package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.model.datamanagers.AdapterInterfaceDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.ActiveMedicationHolder;
import com.medmanager.android.presenter.utils.BindViewsUtils;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 07/04/2018.
 */

public class ActiveMedicationsAdapter extends RecyclerView.Adapter<ActiveMedicationHolder> {

    private List<MedInfo> mMedInfos;
    @Inject
    AdapterInterfaceDataManager adapterInterfaceDataManager;
    @Inject
    Context context;


    public ActiveMedicationsAdapter(Context context){
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
    }

    @Override
    public ActiveMedicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_category, parent, false);
        return new ActiveMedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActiveMedicationHolder holder, int position) {
        BindViewsUtils.bindViews(holder, mMedInfos, position);
        holder.relativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapterInterfaceDataManager.onMedicationClicked(mMedInfos.get(holder.getAdapterPosition()),context);
                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        if (mMedInfos!=null)
            return mMedInfos.size();
        return 0;
    }

    public void setActiveMedInfo(List<MedInfo> medInfos){
        this.mMedInfos = medInfos;
    }
}
