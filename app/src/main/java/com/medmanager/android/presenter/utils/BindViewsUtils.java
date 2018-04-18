package com.medmanager.android.presenter.utils;

import android.support.v7.widget.RecyclerView;

import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by ILENWABOR DAVID on 11/04/2018.
 */

public class BindViewsUtils {

    public static void bindViews(AllMedicationHolder holder, List<MedInfo> mMedInfos, int position){
        if (mMedInfos!=null){
            holder.mMedName.setText(mMedInfos.get(position).getMedicationName());
            holder.mMedAvatar.setText(StringProcessor.extractFirstLetter(mMedInfos.get(position).getMedicationName()));
            String medType = mMedInfos.get(position).getMedicationType();
            if (medType.equals("Pills")){
                String doseNumber = mMedInfos.get(position).getDoseNumber();
                if(doseNumber.equals("1"))
                    holder.mMedPillsNumber.setText( doseNumber + " pill per intake");
                else
                    holder.mMedPillsNumber.setText( doseNumber + " pills per intake");
                holder.mMedTypeImage.setImageResource(R.drawable.ic_pill);
            }
            else if (medType.equals("Syrup")){
                String doseNumber = mMedInfos.get(position).getDoseNumber();
                if(doseNumber.equals("1"))
                    holder.mMedPillsNumber.setText(doseNumber + " spoon per intake");
                else
                    holder.mMedPillsNumber.setText(doseNumber + " spoons per intake");

                holder.mMedTypeImage.setImageResource(R.drawable.ic_syrup);
            } else if (medType.equals("Injection")){
                String doseNumber = mMedInfos.get(position).getDoseNumber();
                if(doseNumber.equals("1"))
                    holder.mMedPillsNumber.setText(doseNumber + " shot per intake");
                else
                    holder.mMedPillsNumber.setText(doseNumber + " shots per intake");
                holder.mMedTypeImage.setImageResource(R.drawable.ic_injection);
            }

            if (mMedInfos.get(position).isMedicationStarted()){
                holder.mMedStatusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
                holder.mMedStatus.setText("On going");
            }
            else{
                holder.mMedStatusImage.setImageResource(R.drawable.ic_warning_black_24dp);
                holder.mMedStatus.setText("Not Started");
            }
            int interval = mMedInfos.get(position).getMedicationInterval();
            if (interval == 30){
                holder.mMedInterval.setText(interval + " minutes interval");
            } else
            if (interval == 1){
                holder.mMedInterval.setText(interval + " hour interval");
            } else
                holder.mMedInterval.setText(interval + " hours interval");
        }

    }
    //This method is used to bind rows of month category
    public static void bindMonthCategory(AllMedicationHolder monthViewHolder, List<MonthlyMedsSection> monthlyMedsSections, int position){
        //Log.v("TAG", "row names are "+ mMedInfos.get(position).getRow().getMedicationName());
        monthViewHolder.mMedName.setText(monthlyMedsSections.get(position).getRow().getMedicationName());
        String medType = monthlyMedsSections.get(position).getRow().getMedicationType();
        if (medType.equals("Pills")){
            String doseNumber = monthlyMedsSections.get(position).getRow().getDoseNumber();
            if(doseNumber.equals("1"))
                monthViewHolder.mMedPillsNumber.setText( doseNumber + " pill per intake");
            else
                monthViewHolder.mMedPillsNumber.setText( doseNumber + " pills per intake");
            monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_pill);
        }
        else if (medType.equals("Syrup")){
            String doseNumber = monthlyMedsSections.get(position).getRow().getDoseNumber();
            if(doseNumber.equals("1"))
                monthViewHolder.mMedPillsNumber.setText(doseNumber + " spoon per intake");
            else
                monthViewHolder.mMedPillsNumber.setText(doseNumber + " spoons per intake");

            monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_syrup);
        } else if (medType.equals("Injection")){
            String doseNumber = monthlyMedsSections.get(position).getRow().getDoseNumber();
            if(doseNumber.equals("1"))
                monthViewHolder.mMedPillsNumber.setText(doseNumber + " shot per intake");
            else
                monthViewHolder.mMedPillsNumber.setText(doseNumber + " shots per intake");
            monthViewHolder.mMedTypeImage.setImageResource(R.drawable.ic_injection);
        }
        monthViewHolder.mMedAvatar.setText(StringProcessor.extractFirstLetter(monthlyMedsSections.get(position).getRow().getMedicationName()));
        if (monthlyMedsSections.get(position).getRow().isMedicationStarted()){
            monthViewHolder.mMedStatusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
            monthViewHolder.mMedStatus.setText("On going");
        }
        else{
            monthViewHolder.mMedStatusImage.setImageResource(R.drawable.ic_warning_black_24dp);
            monthViewHolder.mMedStatus.setText("Not Started");
        }
        int interval = monthlyMedsSections.get(position).getRow().getMedicationInterval();
        if (interval == 30){
            monthViewHolder.mMedInterval.setText(interval + " minutes interval");
        } else
        if (interval == 1){
            monthViewHolder.mMedInterval.setText(interval + " hour interval");
        } else
            monthViewHolder.mMedInterval.setText(interval + " hours interval");
    }
}
