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
import com.medmanager.android.presenter.holder.AllMedicationHolder;
import com.medmanager.android.presenter.utils.BindViewsUtils;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 06/04/2018.
 * This class holds the adapter for displaying all medications
 */

public class AllMedicationAdapter extends RecyclerView.Adapter<AllMedicationHolder> {
    @Inject
    AdapterInterfaceDataManager adapterInterfaceDataManager;
    @Inject
    Context context;

    private List<MedInfo> mMedInfos;

    public AllMedicationAdapter(Context context){
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
    }

    @Override
    public AllMedicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_category, parent, false);
        return new AllMedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nonnull final AllMedicationHolder holder, final int position) {
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

    /**
     * This method is used to set the list source of all medications
     *
     */
    public void setMedInfo(List<MedInfo> medInfos){
        this.mMedInfos = medInfos;
    }


    /**
     * This interface callback is used to respond to clicks on the medication recycler view
     */

    public interface MedicationClickedInterface{
        void onMedicationClicked(MedInfo medInfo, Context context);
    }

}
