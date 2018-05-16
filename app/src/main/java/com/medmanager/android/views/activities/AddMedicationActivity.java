package com.medmanager.android.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.StringProcessor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ILENWABOR DAVID on 01/04/2018.
 */

public class AddMedicationActivity extends BaseActivity{
    private Button mAddMedicationButton;
    private EditText mMedNameEditText, mMedDescriptionEditText, mFrequencyEditText;
    private Switch mMedicationStartedSwitch;
    private Spinner mSpinner;
    private TextView mStartDatePickerTextView, mStartTimePickerTextView, mEndDatePickerTextView, mEndTimePickerTextView, mIntervalTextView;

    private Bundle mBundle;

    private boolean isSwitchChecked;
    private String mSpinnerMedicationType;

    private MedInfo mMedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mAddMedicationButton = findViewById(R.id.button_add_medication);
        mMedNameEditText =  findViewById(R.id.edit_text_medication_name);
        mMedDescriptionEditText = findViewById(R.id.edit_text_medication_description);
        mFrequencyEditText = findViewById(R.id.edit_text_frequency);
        mMedicationStartedSwitch = findViewById(R.id.switch_medication_started);
        mStartDatePickerTextView = findViewById(R.id.text_start_date_picker);
        mStartTimePickerTextView = findViewById(R.id.text_start_time_picker);
        mEndDatePickerTextView = findViewById(R.id.text_end_date_picker);
        mEndTimePickerTextView = findViewById(R.id.text_end_time_picker);
        mIntervalTextView = findViewById(R.id.text_interval_picker);
        mSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);



        mStartDatePickerTextView.setText(StringProcessor.convertDateToString());
        mEndDatePickerTextView.setText(StringProcessor.convertDateToString());

        mStartTimePickerTextView.setText(StringProcessor.convertTimeToString());
        mEndTimePickerTextView.setText(StringProcessor.convertTimeToString());

        isSwitchChecked = mMedicationStartedSwitch.isChecked();

        mBundle = getIntent().getExtras();
        if (mBundle!=null && mBundle.getString(ConstantClass.EXTRA_UPDATE_MED)!=null){
            updateViews(mBundle.getString(ConstantClass.EXTRA_UPDATE_MED));
        }



        mAddMedicationButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBundle!=null && mBundle.getString(ConstantClass.EXTRA_UPDATE_MED)!=null){

                            updateMedicationToRoom();
                        }else{
                            saveMedicationToRoom();
                        }
                        startActivity(new Intent(AddMedicationActivity.this, MainActivity.class));
                        finish();
                    }
                }
        );

        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mSpinnerMedicationType = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        mStartDatePickerTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //pickDate();
                        interfaceDataManager.instantiateDateFragment(AddMedicationActivity.this, datePickerFragment, 0);
                        interfaceDataManager.setStartDateTextView(mStartDatePickerTextView);
                    }
                }
        );
        mStartTimePickerTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //pick Time
                        interfaceDataManager.instantiateTimeFragment(AddMedicationActivity.this, timePickerFragment, 0);
                        interfaceDataManager.setStartTimeTextView(mStartTimePickerTextView);
                    }
                }
        );

        mEndDatePickerTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interfaceDataManager.instantiateDateFragment(AddMedicationActivity.this, datePickerFragment, 1);
                        interfaceDataManager.setStartDateTextView(mEndDatePickerTextView);
                    }
                }
        );

        mEndTimePickerTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interfaceDataManager.instantiateTimeFragment(AddMedicationActivity.this, timePickerFragment, 1);
                        interfaceDataManager.setStartTimeTextView(mEndTimePickerTextView);
                    }
                }
        );

        mIntervalTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //pick medication interval
                        interfaceDataManager.instantiateIntervalFragment(AddMedicationActivity.this, intervalSelectorFragment);
                        interfaceDataManager.setIntervalTextView(mIntervalTextView);
                    }
                }
        );
        mMedicationStartedSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isSwitchChecked = isChecked;
                    }
                }
        );

    }




    /**
     *This method is used to update the views with values from the AboutMedicationActivity
     **/
    private void updateViews(String update) {
        mMedInfo = MedInfo.create(update);
        mAddMedicationButton.setText("Update");
        mMedNameEditText.setText(mMedInfo.getMedicationName());
        mMedDescriptionEditText.setText(mMedInfo.getMedicationDescription());
        mMedicationStartedSwitch.setChecked(mMedInfo.isMedicationStarted());
        mStartDatePickerTextView.setText(mMedInfo.getStartDate());
        mEndDatePickerTextView.setText(mMedInfo.getEndDate());
        mStartTimePickerTextView.setText(mMedInfo.getStartTime());
        mEndTimePickerTextView.setText(mMedInfo.getEndTime());
        mFrequencyEditText.setText(mMedInfo.getDoseNumber());
        mIntervalTextView.setText(String.valueOf(mMedInfo.getMedicationInterval()));
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, getResources().getStringArray(R.array.spinner_array));
        mSpinner.setSelection(arrayList.indexOf(mMedInfo.getMedicationType()));
        mMedicationStartedSwitch.setChecked(mMedInfo.isMedicationStarted());
    }

    //this method updates the medication information in the database
    private void updateMedicationToRoom() {
        updateMedicationToDatabase.updateMedToRoomDatabase(this, mMedNameEditText.getText().toString(),
                mMedDescriptionEditText.getText().toString(),
                mStartDatePickerTextView.getText().toString(),
                mStartTimePickerTextView.getText().toString(),
                mEndDatePickerTextView.getText().toString(),
                mEndTimePickerTextView.getText().toString(),
                interfaceDataManager.getStartMonth(),
                mFrequencyEditText.getText().toString(),
                interfaceDataManager.getMedInterval(),
                isSwitchChecked,
                mSpinnerMedicationType,
                mMedInfo);
    }

    //This method saves the medication information to the database
    private void saveMedicationToRoom() {
        saveMedicationToDatabase.saveMedToRoomDatabase(this, mMedNameEditText.getText().toString(),
                mMedDescriptionEditText.getText().toString(),
                mStartDatePickerTextView.getText().toString(),
                mStartTimePickerTextView.getText().toString(),
                mEndDatePickerTextView.getText().toString(),
                mEndTimePickerTextView.getText().toString(),
                interfaceDataManager.getStartMonth(),
                mFrequencyEditText.getText().toString(),
                interfaceDataManager.getMedInterval(),
                isSwitchChecked,
                mSpinnerMedicationType
        );

    }



}
