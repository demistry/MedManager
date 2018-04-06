package com.medmanager.android.presenter.utils;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.medmanager.android.R;
import com.medmanager.android.views.fragments.DatePickerFragment;

import com.medmanager.android.views.fragments.DatePickerFragment.DatePickedInterface;
import com.medmanager.android.views.fragments.DatePickerFragment.RemoveFragmentInterface;
import com.medmanager.android.views.fragments.IntervalSelectorFragment;
import com.medmanager.android.views.fragments.IntervalSelectorFragment.IntervalSelectorInterface;
import com.medmanager.android.views.fragments.TimePickerFragment;

import javax.inject.Inject;

import static com.medmanager.android.views.fragments.TimePickerFragment.*;

/**
 * Created by ILENWABOR DAVID on 04/04/2018.
 * This class handles all interface implementations from fragments to activities
 */

public class InterfaceDataManager implements DatePickedInterface, RemoveFragmentInterface, TimePickedInterface, RemoveTimeFragmentInterface, IntervalSelectorInterface {

    private FragmentTransaction mFragmentTransaction;
    private DatePickerFragment mDatePickerFragment;
    private TimePickerFragment mTimePickerFragment;
    private AppCompatActivity appCompatActivity;
    private IntervalSelectorFragment intervalSelectorFragment;

    private String dateText;
    private int startMonth;
    private TextView startDateTextView, startTimeTextView,intervalTextView;
    private String timeText;
    private int timeHour;
    private int timeMinute;
    private int medInterval;
    private String intervalText;



    public void instantiateDateFragment(AppCompatActivity appCompatActivity, DatePickerFragment datePickerFragment){
        this.mDatePickerFragment = datePickerFragment;
        this.appCompatActivity = appCompatActivity;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, datePickerFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void instantiateTimeFragment(AppCompatActivity appCompatActivity, TimePickerFragment timePickerFragment){
        this.appCompatActivity = appCompatActivity;
        this.mTimePickerFragment = timePickerFragment;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, timePickerFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void instantiateIntervalFragment(AppCompatActivity appCompatActivity, IntervalSelectorFragment intervalSelectorFragment){
        this.appCompatActivity = appCompatActivity;
        this.intervalSelectorFragment = intervalSelectorFragment;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, intervalSelectorFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }


    @Override
    public void datePicked(String dateText, int startMonth) {
        this.dateText = dateText;
        this.startMonth = startMonth;
    }

    @Override
    public void fragmentRemoved() {
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.remove(mDatePickerFragment).commit();
        startDateTextView.setText(dateText);
    }

    @Override
    public void timePicked(String timeText, int timeHour, int timeMinute) {

        this.timeText = timeText;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
    }

    @Override
    public void timeFragmentRemoved() {
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.remove(mTimePickerFragment).commit();
        startTimeTextView.setText(timeText);
    }

    @Override
    public void intervalSelected(int medInterval, String intervalText) {
        this.medInterval = medInterval;
        this.intervalText = intervalText;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.remove(intervalSelectorFragment).commit();
        intervalTextView.setText(intervalText);
    }
    public void setStartDateTextView(TextView textView){
        startDateTextView = textView;
    }
    public void setStartTimeTextView(TextView textView){
        startTimeTextView = textView;
    }
    public void setIntervalTextView(TextView textView){
        intervalTextView = textView;
    }

    public int getStartMonth() {
        return startMonth;
    }


    public int getMedInterval() {
        return medInterval;
    }

    public String getDateAsString(){
        return startDateTextView.getText().toString();
    }

    public String getTimeAsString(){
        return startTimeTextView.getText().toString();
    }
}
