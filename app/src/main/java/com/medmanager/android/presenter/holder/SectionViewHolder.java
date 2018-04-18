package com.medmanager.android.presenter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.medmanager.android.R;

/**
 * Created by ILENWABOR DAVID on 09/04/2018.
 * View Holder class for all month category
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {
    public TextView monthTextView;
    public SectionViewHolder(View itemView) {
        super(itemView);
        monthTextView = itemView.findViewById(R.id.text_month);
    }
}
