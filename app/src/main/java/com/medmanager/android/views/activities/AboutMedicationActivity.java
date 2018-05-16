package com.medmanager.android.views.activities;

import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.utils.AlertDialogCreator;

import java.util.Locale;

public class AboutMedicationActivity extends BaseActivity {
    private TextView mMedNameTextView, mMedDescriptionTextView, mMedStartDateTextView, mMedStartTimeTextView;
    private TextView mEndDateTextView, mEndTimeTextView, mMedicationTypeTextView, mDosageCountTextView, mDosageIntervalTextView;
    private ImageView mMedStatusImageView, mMedTypeImageView, mIncrementCountImageView, mDecrementCountImageView;
    private Button mEditButton;

    private MedInfo mMedInfo;

    private Bundle mBundle;

    private static int sDosage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_medication);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMedNameTextView = findViewById(R.id.text_about_med_name);
        mMedDescriptionTextView = findViewById(R.id.text_about_med_description);
        mMedStartDateTextView = findViewById(R.id.about_med_start_date);
        mMedStartTimeTextView = findViewById(R.id.about_med_start_time);
        mEndDateTextView = findViewById(R.id.about_med_end_date);
        mEndTimeTextView = findViewById(R.id.about_med_end_time);
        mMedicationTypeTextView = findViewById(R.id.text_about_med_type);
        mDosageCountTextView = findViewById(R.id.text_about_dosage_count);
        mDosageIntervalTextView = findViewById(R.id.text_about_med_interval);
        mMedStatusImageView = findViewById(R.id.image_about_med_status);
        mMedTypeImageView = findViewById(R.id.image_about_med_type);
        mIncrementCountImageView = findViewById(R.id.image_increment_count);
        mDecrementCountImageView = findViewById(R.id.image_decrement_count);
        mEditButton = findViewById(R.id.btn_edit_med);

        mBundle = getIntent().getExtras();

        mEditButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMedInfo ==null){
                            Intent intent = new Intent(AboutMedicationActivity.this, AddMedicationActivity.class);

                            intent.putExtra(ConstantClass.EXTRA_UPDATE_MED, adapterInterfaceDataManager.getSerializedMedifcation());
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(AboutMedicationActivity.this, AddMedicationActivity.class);

                            intent.putExtra(ConstantClass.EXTRA_UPDATE_MED, mMedInfo.serialize());
                            startActivity(intent);
                        }
                    }
                }
        );

        mIncrementCountImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMedInfo ==null){
                            incrementMedicationCount(adapterInterfaceDataManager.getMedInfo());
                        }
                        else{
                            incrementMedicationCount(mMedInfo);
                        }
                    }
                }
        );

        mDecrementCountImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMedInfo ==null){
                            decrementMedicationCount(adapterInterfaceDataManager.getMedInfo());
                        }
                        else{
                            decrementMedicationCount(mMedInfo);
                        }
                    }
                }
        );




    }




    @Override
    protected void onStart() {
        super.onStart();
        if(adapterInterfaceDataManager!=null){
            mMedInfo = adapterInterfaceDataManager.getMedInfo();
            mMedNameTextView.setText(adapterInterfaceDataManager.getMedicationName());
            mMedDescriptionTextView.setText(adapterInterfaceDataManager.getMedicationDescription());
            mMedStartDateTextView.setText(adapterInterfaceDataManager.getStartDate());
            mMedStartTimeTextView.setText(adapterInterfaceDataManager.getStartTime());
            mEndDateTextView.setText(adapterInterfaceDataManager.getEndDate());
            mEndTimeTextView.setText(adapterInterfaceDataManager.getEndTime());
            mMedicationTypeTextView.setText(adapterInterfaceDataManager.getMedicationType());
            String test = adapterInterfaceDataManager.getMedicationType();

                switch (test) {
                    case "Pills":
                        mMedTypeImageView.setImageResource(R.drawable.ic_pill);
                        break;
                    case "Injection":
                        mMedTypeImageView.setImageResource(R.drawable.ic_injection);
                        break;
                    case "Syrup":
                        mMedTypeImageView.setImageResource(R.drawable.ic_syrup);
                        break;
                }


            if (adapterInterfaceDataManager.isMedicationStarted())
                mMedStatusImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
            else
                mMedStatusImageView.setImageResource(R.drawable.ic_warning_black_24dp);

            mDosageCountTextView.setText(String.valueOf(adapterInterfaceDataManager.getDosageCount()));
            int interval = adapterInterfaceDataManager.getMedicationInterval();
            if (interval == 30){
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.minutes)));
            } else
            if (interval == 1){
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.hour)));
            } else
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.hours)));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        handleNotificationClicks(getIntent().getExtras());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
            AlertDialogCreator.createDeleteDialog(this, deleteMedication, mMedInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mBundle = intent.getExtras();
        handleNotificationClicks(mBundle);
    }



    private void incrementMedicationCount(MedInfo medInformation) {
        int medicationAmount = Integer.parseInt(medInformation.getDoseNumber());
        sDosage = medInformation.getDosageCount();
        sDosage += medicationAmount;
        int returnedDosage = updateMedicationCount.updateMedicationCount(this, medInformation.getMedicationName(), sDosage);
        mDosageCountTextView.setText(String.valueOf(returnedDosage));
    }

    private void decrementMedicationCount(MedInfo medInformation) {
        int medicationAmount = Integer.parseInt(medInformation.getDoseNumber());
        sDosage = medInformation.getDosageCount();
        if (sDosage !=0)
            sDosage -= medicationAmount;
        int returnedDosage = updateMedicationCount.updateMedicationCount(this, medInformation.getMedicationName(), sDosage);
        mDosageCountTextView.setText(String.valueOf(returnedDosage));
    }



    private void handleNotificationClicks(Bundle bundle){
        if (bundle!=null){
            String notificationMedInfo = bundle.getString(ConstantClass.EXTRA_NOTIFICATION_ITEM);

            mMedInfo = MedInfo.create(notificationMedInfo);
            mMedNameTextView.setText(mMedInfo.getMedicationName());
            mMedDescriptionTextView.setText(mMedInfo.getMedicationName());
            mMedStartDateTextView.setText(mMedInfo.getStartDate());
            mMedStartTimeTextView.setText(mMedInfo.getStartTime());
            mEndDateTextView.setText(mMedInfo.getEndDate());
            mEndTimeTextView.setText(mMedInfo.getEndTime());
            mMedicationTypeTextView.setText(mMedInfo.getMedicationType());

            switch (mMedInfo.getMedicationType()) {
                case "Pills":
                    mMedTypeImageView.setImageResource(R.drawable.ic_pill);
                    break;
                case "Injection":
                    mMedTypeImageView.setImageResource(R.drawable.ic_injection);
                    break;
                case "Syrup":
                    mMedTypeImageView.setImageResource(R.drawable.ic_syrup);
                    break;
            }
            if (mMedInfo.isMedicationStarted())
                mMedStatusImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
            else
                mMedStatusImageView.setImageResource(R.drawable.ic_warning_black_24dp);

            mDosageCountTextView.setText(String.valueOf(mMedInfo.getDosageCount()));
            int interval = mMedInfo.getMedicationInterval();
            if (interval == 30){
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.minutes)));
            } else
            if (interval == 1){
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.hour)));
            } else
                mDosageIntervalTextView.setText(String.format(Locale.getDefault(),"%d%s", interval, getString(R.string.hours)));

            NotificationManagerCompat.from(this).cancel(bundle.getString(ConstantClass.EXTRA_NOTIFICATION_TAG),
                    bundle.getInt(ConstantClass.EXTRA_NOTIFICATION_ID));
            incrementMedicationCount(mMedInfo);
        }
    }
}
