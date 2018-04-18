package com.medmanager.android.views.fragments;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class DatePickerFragment extends BaseFragment {

    private DatePicker mDatePicker;
    private TextView mSaveDateTextView;
    private DatePickedInterface mDatePickedInterface;
    private RemoveFragmentInterface mRemoveFragmentInterface;

    private int mFragmentType;


    public DatePickerFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatePickedInterface = interfaceDataManager;
        mRemoveFragmentInterface = interfaceDataManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        mDatePicker = view.findViewById(R.id.date_picker);
        mSaveDateTextView = view.findViewById(R.id.text_date_picked);

        if(getArguments()!=null)
        mFragmentType = getArguments().getInt(ConstantClass.ARGUMENT_DATE);

        mSaveDateTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCalendar =  new GregorianCalendar(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
                        if (mFragmentType ==0) mDatePickedInterface.datePicked(simpleDateFormat.format(mCalendar.getTime()),  mDatePicker.getMonth());
                        else mDatePickedInterface.EndDatePicked(simpleDateFormat.format(mCalendar.getTime()), mDatePicker.getMonth());
                        mRemoveFragmentInterface.fragmentRemoved();
                    }
                }
        );
        return view;
    }

    /**
     * This interface is used to dismiss the fragment and pick dates
     */
    public interface DatePickedInterface{
        void datePicked(String dateText, int startMonth);
        void EndDatePicked(String dateText, int endMonth);
    }
    public interface RemoveFragmentInterface{
        void fragmentRemoved();
    }
}
