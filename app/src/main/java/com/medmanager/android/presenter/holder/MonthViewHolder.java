package com.medmanager.android.presenter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.medmanager.android.R;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 * This class returns the viewholder object for the MonthCategoryAdapter
 */

public class MonthViewHolder extends RecyclerView.ViewHolder {
    public TextView monthAvatar, monthType, numberOfMedications;

    public MonthViewHolder(View itemView) {
        super(itemView);
        monthAvatar = itemView.findViewById(R.id.text_month_avatar);
        monthType = itemView.findViewById(R.id.text_month_type);
        numberOfMedications = itemView.findViewById(R.id.text_number_of_meds);
    }
}
