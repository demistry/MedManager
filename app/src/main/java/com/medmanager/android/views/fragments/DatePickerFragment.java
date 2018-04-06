package com.medmanager.android.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.MyApplicationComponent;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class DatePickerFragment extends BaseFragment {

    private DatePicker mDatePicker;
    private TextView mSaveDateTextView;
    private DatePickedInterface datePickedInterface;
    private RemoveFragmentInterface removeFragmentInterface;



    public DatePickerFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datePickedInterface = interfaceDataManager;
        removeFragmentInterface = interfaceDataManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Date date = StringProcessor.convertStringToDate(interfaceDataManager.getDateAsString());
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        mDatePicker = view.findViewById(R.id.date_picker);
        mSaveDateTextView = view.findViewById(R.id.text_date_picked);

//        if (date!=null) mDatePicker.updateDate(date.getYear(), date.getMonth(), date.getDay());

        mSaveDateTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCalendar =  new GregorianCalendar(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
                        datePickedInterface.datePicked(simpleDateFormat.format(mCalendar.getTime()),  mDatePicker.getMonth());
                        Toast.makeText(getContext(), "Date picked is "+ simpleDateFormat.format(mCalendar.getTime()), Toast.LENGTH_SHORT).show();
                        //onDestroy();
                        removeFragmentInterface.fragmentRemoved();
                    }
                }
        );
        return view;
    }

    //this interface responds to the click event of the done text-view
    public interface DatePickedInterface{
        void datePicked(String dateText, int startMonth);
    }
    public interface RemoveFragmentInterface{
        void fragmentRemoved();
    }
}
