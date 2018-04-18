package com.medmanager.android.presenter.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.medmanager.android.ConstantClass;
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
    private int fragmentBundleArgument;
    private DatePickerFragment mDatePickerFragment;
    private TimePickerFragment mTimePickerFragment;
    private int timeFragType;
    private AppCompatActivity appCompatActivity;
    private IntervalSelectorFragment intervalSelectorFragment;

    private String startDateText;
    private String endDateText;
    private int startMonth;
    private int endMonth;
    private TextView startDateTextView, startTimeTextView,intervalTextView;
    private String startTimeText;
    private int startTimeHour;
    private int startTimeMinute;
    private int medInterval;
    private String intervalText;
    private String endTimeText;
    private int endTimeHour;
    private int endTimeMinute;


    /**
     * Method to instantiate Date Fragment
     * @param appCompatActivity  /
     * @param datePickerFragment /
     * @param fragType /
     */
    public void instantiateDateFragment(AppCompatActivity appCompatActivity, DatePickerFragment datePickerFragment, int fragType){
        this.mDatePickerFragment = datePickerFragment;
        this.appCompatActivity = appCompatActivity;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        fragmentBundleArgument = fragType;
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantClass.ARGUMENT_DATE, fragType);
        datePickerFragment.setArguments(bundle);
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, datePickerFragment);
        mFragmentTransaction.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_FADE, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    /**
     * Method to instantiate Time Fragment
     * @param appCompatActivity /
     * @param timePickerFragment /
     * @param fragType /
     */
    public void instantiateTimeFragment(AppCompatActivity appCompatActivity, TimePickerFragment timePickerFragment, int fragType){
        this.appCompatActivity = appCompatActivity;
        this.mTimePickerFragment = timePickerFragment;
        timeFragType = fragType;
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantClass.ARGUMENT_TIME, fragType);
        timePickerFragment.setArguments(bundle);
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_FADE, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, timePickerFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }


    /**
     * Method to instantiate Interval Selector Fragment
     * @param appCompatActivity /
     * @param intervalSelectorFragment /
     */
    public void instantiateIntervalFragment(AppCompatActivity appCompatActivity, IntervalSelectorFragment intervalSelectorFragment){
        this.appCompatActivity = appCompatActivity;
        this.intervalSelectorFragment = intervalSelectorFragment;
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_FADE, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        mFragmentTransaction.add(R.id.layout_add_medication_root_layout, intervalSelectorFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }


    @Override
    public void datePicked(String dateText, int startMonth) {
        this.startDateText = dateText;
        this.startMonth = startMonth;
    }

    @Override
    public void EndDatePicked(String dateText, int endMonth) {
        this.endDateText = dateText;
        this.endMonth = endMonth;
    }

    @Override
    public void fragmentRemoved() {
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.remove(mDatePickerFragment).commit();
        if (fragmentBundleArgument == 0) startDateTextView.setText(startDateText);
        else startDateTextView.setText(endDateText);
    }

    @Override
    public void timePicked(String timeText, int timeHour, int timeMinute) {

        this.startTimeText = timeText;
        this.startTimeHour = timeHour;
        this.startTimeMinute = timeMinute;
    }

    @Override
    public void endTimePicked(String timeText, int timeHour, int timeMinute) {

        endTimeText = timeText;
        endTimeHour = timeHour;
        endTimeMinute = timeMinute;
    }

    @Override
    public void timeFragmentRemoved() {
        mFragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.remove(mTimePickerFragment).commit();
        if (timeFragType == 0)
        startTimeTextView.setText(startTimeText);
        else
            startTimeTextView.setText(endTimeText);
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
