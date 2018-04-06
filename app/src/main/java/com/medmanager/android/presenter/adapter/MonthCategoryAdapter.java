package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.R;
import com.medmanager.android.presenter.holder.MonthViewHolder;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 */

public class MonthCategoryAdapter extends RecyclerView.Adapter<MonthViewHolder> {
    private Context context;
    String dummyMonth[] = {"January", "February", "March", "November", "June", "December", "January", "February", "March", "November", "June", "December"};
    String [] dummyNumber = {"1", "43", "555", "23", "33", "0", "1", "43", "555", "23", "33", "0"};


    public MonthCategoryAdapter(Context context){
        this.context = context;
    }
    @Override
    public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_month_category, parent, false);
        return new MonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonthViewHolder holder, int position) {
        StringBuilder builder = new StringBuilder(dummyMonth[position]);
        holder.monthAvatar.setText(builder.substring(0,1).toUpperCase());
        holder.monthType.setText(dummyMonth[position]);
        holder.numberOfMedications.setText(dummyNumber[position]);
    }

    @Override
    public int getItemCount() {
        return dummyMonth.length;
    }
}
