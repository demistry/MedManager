package com.medmanager.android.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 02/04/2018.
 */

public class TimePickerFragment extends BaseFragment {



    private TimePicker mTimePicker;
    private TextView mSaveTimeTextView;
    private TimePickedInterface timePickedInterface;
    private RemoveTimeFragmentInterface removeTimeFragmentInterface;

    private int fragmentType;



    public TimePickerFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getActivity().getApplication()).getMyApplicationComponent().inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((DaggerApplication)getActivity().getApplication()).getMyApplicationComponent().inject(this);
        timePickedInterface = interfaceDataManager;
        removeTimeFragmentInterface = interfaceDataManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Date date = StringProcessor.convertStringToTime(interfaceDataManager.getTimeAsString());
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        mTimePicker = view.findViewById(R.id.time_picker);
        mSaveTimeTextView = view.findViewById(R.id.text_time_picked);

        fragmentType = getArguments().getInt("Args");

//        if (date!=null){
//            mTimePicker.setCurrentHour(date.getHours());
//            mTimePicker.setCurrentMinute(date.getMinutes());
//        }


        mSaveTimeTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCalendar =  new GregorianCalendar(0, 0, 0, mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                        if (fragmentType == 0)
                            timePickedInterface.timePicked(simpleDateFormat.format(mCalendar.getTime()),  mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                        else timePickedInterface.endTimePicked(simpleDateFormat.format(mCalendar.getTime()), mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                        Toast.makeText(getContext(), "Time picked is "+ simpleDateFormat.format(mCalendar.getTime()), Toast.LENGTH_SHORT).show();
                        removeTimeFragmentInterface.timeFragmentRemoved();
                    }
                }
        );
        return view;
    }

    //this interface responds to the click event of the done text-view
    public interface TimePickedInterface{
        void timePicked(String timeText, int timeHour, int timeMinute);
        void endTimePicked(String timeText, int timeHour, int timeMinute);
    }
    public interface RemoveTimeFragmentInterface{
        void timeFragmentRemoved();
    }
}
