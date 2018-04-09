package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.model.storage.MedicationDAO;
import com.medmanager.android.presenter.holder.AllMedicationHolder;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 06/04/2018.
 */

public class AllMedicationAdapter extends RecyclerView.Adapter<AllMedicationHolder> {



    private List<MedInfo> mMedInfos;

    public AllMedicationAdapter(){
    }

    @Override
    public AllMedicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_category, parent, false);
        return new AllMedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(AllMedicationHolder holder, int position) {
        if (mMedInfos!=null){
            holder.mMedName.setText(mMedInfos.get(position).getMedicationName());
            holder.mMedAvatar.setText(StringProcessor.extractFirstLetter(mMedInfos.get(position).getMedicationName()));
            String medType = mMedInfos.get(position).getMedicationType();
            if (medType.equals("Pills")){
                holder.mMedPillsNumber.setText(mMedInfos.get(position).getPillNumber() + " pills per intake");
                holder.mMedTypeImage.setImageResource(R.drawable.ic_pill);
            }
            else if (medType.equals("Syrup")){
                holder.mMedPillsNumber.setText(mMedInfos.get(position).getPillNumber() + " spoons per intake");
                holder.mMedTypeImage.setImageResource(R.drawable.ic_syrup);
            } else if (medType.equals("Injection")){
                holder.mMedPillsNumber.setText(mMedInfos.get(position).getPillNumber() + " shots per intake");
                holder.mMedTypeImage.setImageResource(R.drawable.ic_steroids);
            }

            if (mMedInfos.get(position).isMedicationStarted()){
                holder.mMedStatusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
                holder.mMedStatus.setText("On going");
            }
            else{
                holder.mMedStatusImage.setImageResource(R.drawable.ic_warning_black_24dp);
                holder.mMedStatus.setText("Not Started");
            }
            holder.mMedInterval.setText(mMedInfos.get(position).getMedicationInterval() + " hours interval");
        }


    }

    @Override
    public int getItemCount() {
        if (mMedInfos!=null)
        return mMedInfos.size();
        return 0;
    }

    public void setMedInfo(List<MedInfo> medInfos){
        this.mMedInfos = medInfos;
    }

//    public interface

}
