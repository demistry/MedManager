package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ILENWABOR DAVID on 10/04/2018.
 */

public class SearchListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<MedInfo> medInfos;

    private ArrayList<MedInfo> mMedInfoArrayList;
    private View view;

    public SearchListAdapter(Context context, List<MedInfo> medInfos){
        this.mContext = context;
        this.medInfos = medInfos;
        inflater = LayoutInflater.from(context);
        this.mMedInfoArrayList = new ArrayList<>();
        this.mMedInfoArrayList.addAll(medInfos);
    }
    @Override
    public int getCount() {
        return medInfos.size();
    }

    @Override
    public MedInfo getItem(int position) {
        return medInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AllMedicationHolder holder;

        if (convertView == null){
            view =inflater.inflate(R.layout.item_medication_category, null);
            holder = new AllMedicationHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (AllMedicationHolder) convertView.getTag();
        }
        holder.mMedName.setText(medInfos.get(position).getMedicationName());
        return view;
    }

    public void filter(String charText){
        String searchedText = charText.toLowerCase(Locale.getDefault());
        medInfos.clear();
        if (searchedText.length()==0){
            medInfos.addAll(mMedInfoArrayList);
        }
        else{
            for (MedInfo medInfo : mMedInfoArrayList){
                if (medInfo.getMedicationName().toLowerCase(Locale.getDefault()).contains(searchedText)){
                    medInfos.add(medInfo);
                }
            }
        }

        notifyDataSetChanged();
    }
}
