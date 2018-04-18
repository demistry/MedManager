package com.medmanager.android.presenter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medmanager.android.R;

/**
 * Created by ILENWABOR DAVID on 06/04/2018.
 */

public class ActiveMedicationHolder extends AllMedicationHolder {
    public TextView mMedName, mMedInterval, mMedPillsNumber, mMedStatus, mMedAvatar;
    public ImageView mMedStatusImage, mMedTypeImage;
    public RelativeLayout relativeLayout;

    public ActiveMedicationHolder(View itemView) {
        super(itemView);
        mMedAvatar = itemView.findViewById(R.id.text_med_category_avatar);
        mMedName = itemView.findViewById(R.id.text_med_name);
        mMedInterval = itemView.findViewById(R.id.text_interval_display);
        mMedPillsNumber = itemView.findViewById(R.id.text_pill_intake_freq);
        mMedStatus = itemView.findViewById(R.id.text_med_status);
        mMedStatusImage = itemView.findViewById(R.id.image_med_status);
        mMedTypeImage = itemView.findViewById(R.id.image_med_type);
        relativeLayout = itemView.findViewById(R.id.relative_layout_med_category);
    }
}
