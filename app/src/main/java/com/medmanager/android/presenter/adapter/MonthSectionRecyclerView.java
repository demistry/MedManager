package com.medmanager.android.presenter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;
import com.medmanager.android.presenter.holder.MonthViewHolder;
import com.medmanager.android.presenter.holder.SectionViewHolder;
import com.medmanager.android.presenter.utils.MonthlyMedsSection;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.List;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 */

public class MonthSectionRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MonthlyMedsSection> mMedInfos;

    public MonthSectionRecyclerView(List<MonthlyMedsSection> medInfos){
        mMedInfos = medInfos;

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
            AllMedicationHolder monthViewHolder = (AllMedicationHolder) holder;
            Log.v("TAG", "row names are "+ mMedInfos.get(position).getRow().getMedicationName());
            monthViewHolder.mMedName.setText(mMedInfos.get(position).getRow().getMedicationName());
            String medType = mMedInfos.get(position).getRow().getMedicationType();
            if (medType.equals("Pills")){
                monthViewHolder.mMedPillsNumber.setText(mMedInfos.get(position).getRow().getPillNumber() + " pills per intake");
                monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_pill);
            }
            else if (medType.equals("Syrup")){
                monthViewHolder.mMedPillsNumber.setText(mMedInfos.get(position).getRow().getPillNumber() + " spoons per intake");
                monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_syrup);
            } else if (medType.equals("Injection")){
                monthViewHolder.mMedPillsNumber.setText(mMedInfos.get(position).getRow().getPillNumber() + " shots per intake");
                monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_steroids);
            }
            monthViewHolder.mMedAvatar.setText(StringProcessor.extractFirstLetter(mMedInfos.get(position).getRow().getMedicationName()));
            if (mMedInfos.get(position).getRow().isMedicationStarted()){
                monthViewHolder.mMedStatusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
                monthViewHolder.mMedStatus.setText("On going");
            }
            else{
                monthViewHolder.mMedStatusImage.setImageResource(R.drawable.ic_warning_black_24dp);
                monthViewHolder.mMedStatus.setText("Not Started");
            }
            monthViewHolder.mMedInterval.setText(mMedInfos.get(position).getRow().getMedicationInterval() + " hours interval");
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
}
