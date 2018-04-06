package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.presenter.utils.InterfaceDataManager;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 05/04/2018.
 */

public class BaseFragment extends Fragment {

    @Inject
    Calendar mCalendar;
    @Inject
    InterfaceDataManager interfaceDataManager;

    public BaseFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getActivity().getApplication()).getMyApplicationComponent().inject(this);
    }

}
