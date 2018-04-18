package com.medmanager.android.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.model.datamanagers.AdapterInterfaceDataManager;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.holder.AllMedicationHolder;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 10/04/2018.
 */

public class SearchQueryAdapter extends RecyclerView.Adapter<AllMedicationHolder> {

    @Inject
    AdapterInterfaceDataManager adapterInterfaceDataManager;

    private Context mContext;
    private List<MedInfo> mMedInfoList;
    private ArrayList<MedInfo> medInfoArrayList;

    public SearchQueryAdapter(Context context, List<MedInfo> medInfoList){

        mContext = context;
        mMedInfoList = medInfoList;
        medInfoArrayList = new ArrayList<>();
        medInfoArrayList.addAll(medInfoList);
        ((DaggerApplication)context.getApplicationContext()).getMyApplicationComponent().inject(this);
    }

    @Override
    public AllMedicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_category, parent, false);
        return new AllMedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(final AllMedicationHolder holder, final int position) {
        holder.mMedName.setText(mMedInfoList.get(position).getMedicationName());
        holder.mMedAvatar.setText(StringProcessor.extractFirstLetter(mMedInfoList.get(position).getMedicationName()));
        String medType = mMedInfoList.get(position).getMedicationType();
        if (medType.equals("Pills")){
            holder.mMedPillsNumber.setText(mMedInfoList.get(position).getDoseNumber() + " pills per intake");
            holder.mMedTypeImage.setImageResource(R.drawable.ic_pill);
        }
        else if (medType.equals("Syrup")){
            holder.mMedPillsNumber.setText(mMedInfoList.get(position).getDoseNumber() + " spoons per intake");
            holder.mMedTypeImage.setImageResource(R.drawable.ic_syrup);
        } else if (medType.equals("Injection")){
            holder.mMedPillsNumber.setText(mMedInfoList.get(position).getDoseNumber() + " shots per intake");
            holder.mMedTypeImage.setImageResource(R.drawable.ic_injection);
        }

        if (mMedInfoList.get(position).isMedicationStarted()){
            holder.mMedStatusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
            holder.mMedStatus.setText("On going");
        }
        else{
            holder.mMedStatusImage.setImageResource(R.drawable.ic_warning_black_24dp);
            holder.mMedStatus.setText("Not Started");
        }
        holder.mMedInterval.setText(mMedInfoList.get(position).getMedicationInterval() + " hours interval");

        holder.relativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("TAG",  "Clicked text is " + mMedInfoList.get(holder.getAdapterPosition()).getMedicationName());
                        adapterInterfaceDataManager.onMedicationClicked(mMedInfoList.get(holder.getAdapterPosition()), mContext);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return mMedInfoList.size();
    }

    public void filter(String charText){
        String searchedText = charText.toLowerCase(Locale.getDefault());
        Log.v("TAG",  "Searched text is " + searchedText);
        mMedInfoList.clear();
        if (searchedText.length()==0){
            mMedInfoList.addAll(medInfoArrayList);
        }
        else{
            for (MedInfo medInfo : medInfoArrayList){
                if (medInfo.getMedicationName().toLowerCase(Locale.getDefault()).contains(searchedText)){
                    mMedInfoList.add(medInfo);
                }
            }
        }

        notifyDataSetChanged();
    }
}
