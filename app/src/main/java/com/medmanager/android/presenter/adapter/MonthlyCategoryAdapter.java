package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.model.datamanagers.AdapterInterfaceDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;
import com.medmanager.android.presenter.holder.SectionViewHolder;
import com.medmanager.android.presenter.utils.BindViewsUtils;
import com.medmanager.android.presenter.utils.MonthlyMedsSection;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 * This class holds the adapter for displaying all medications by month
 */

public class MonthlyCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @Inject
    AdapterInterfaceDataManager adapterInterfaceDataManager;
    @Inject
    Context context;

    private List<MonthlyMedsSection> mMedInfos;

    public MonthlyCategoryAdapter(Context context){
        ((DaggerApplication)context).getMyApplicationComponent().inject(this);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.v("TAG", "View type is " + viewType);
        if (viewType == 0){
            View view = inflater.inflate(R.layout.item_medication_category, parent, false);
            return new AllMedicationHolder(view);
        }
        if (viewType == 1){
            View view = inflater.inflate(R.layout.item_month_section, parent, false);
            return new SectionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MonthlyMedsSection section = mMedInfos.get(position);

        if (!section.isRow()){
            SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
            Log.v("TAG", "section names are "+ StringProcessor.convertIntToMonthString(mMedInfos.get(position).getSection()));
            sectionViewHolder.monthTextView.setText(StringProcessor.convertIntToMonthString(mMedInfos.get(position).getSection()));

        }
        else{
            final AllMedicationHolder monthViewHolder = (AllMedicationHolder) holder;
            BindViewsUtils.bindMonthCategory(monthViewHolder,mMedInfos, position);
            monthViewHolder.relativeLayout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapterInterfaceDataManager.onMedicationClicked(mMedInfos.get(monthViewHolder.getAdapterPosition()).getRow(),
                                    context);
                        }
                    }
            );
        }

    }

    @Override
    public int getItemCount() {
        if (mMedInfos!=null) return mMedInfos.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        MonthlyMedsSection item = mMedInfos.get(position);
        if (item.isRow())return 0;
        else return 1;
    }

    /**
     This method is used to supply the list of medication information by section and by row
     *
     */
    public void addSections(List<MonthlyMedsSection> medInfo) {
        this.mMedInfos = medInfo;
    }
}
