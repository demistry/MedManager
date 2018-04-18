package com.medmanager.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.medmanager.android.DaggerApplication;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.InterfaceDataManager;
import com.medmanager.android.presenter.utils.StringProcessor;

import javax.inject.Inject;

/**
 * Created by ILENWABOR DAVID on 04/04/2018.
 */

public class IntervalSelectorFragment extends BaseFragment {

    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;

    private IntervalSelectorInterface selectorInterface;

    public IntervalSelectorFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication)getActivity().getApplication()).getMyApplicationComponent().inject(this);
        selectorInterface = interfaceDataManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interval_selector, container, false);
        listView = view.findViewById(R.id.list_interval);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getContext().getResources().getStringArray(R.array.interval_array));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String intervalText = getContext().getResources().getStringArray(R.array.interval_array)[position];
                        int medInterval;
                        if(intervalText.equals("30 minutes")){
                            medInterval = 30;
                        }else{
                            medInterval = StringProcessor.processString(intervalText);
                        }
                        selectorInterface.intervalSelected(medInterval, intervalText);
                    }
                }
        );
        return view;
    }

    public interface IntervalSelectorInterface{
        void intervalSelected(int medInterval, String intervalText);
    }
}
