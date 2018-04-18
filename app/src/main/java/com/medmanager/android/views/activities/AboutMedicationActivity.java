package com.medmanager.android.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.services.NotificationDispatcherService;
import com.medmanager.android.presenter.utils.AlertDialogCreator;
import com.medmanager.android.presenter.utils.DeleteMedication;
import com.medmanager.android.presenter.utils.MedsSingleton;

public class AboutMedicationActivity extends BaseActivity {
    private TextView mMedNameTextView, mMedDescriptionTextView, mMedStartDateTextView, mMedStartTimeTextView;
    private TextView mEndDateTextView, mEndTimeTextView, mMedicationTypeTextView, mDosageCountTextView, mDosageIntervalTextView;
    private ImageView mMedStatusImageView, mMedTypeImageView, mIncrementCountImageView, mDecrementCountImageView;

    private Button mEditButton;

    private MedInfo medInfo;

    private Bundle bundle;

    private static int dosage;
    int newAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_medication);
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

        bundle = getIntent().getExtras();

        NotificationDispatcherService.setUpPref(getSharedPreferences(ConstantClass.PREF_SHARED, MODE_PRIVATE));

        mEditButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(medInfo==null){
                            Intent intent = new Intent(AboutMedicationActivity.this, AddMedicationActivity.class);

                            intent.putExtra("UpdateMed", adapterInterfaceDataManager.getSerializedMedifcation());
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(AboutMedicationActivity.this, AddMedicationActivity.class);

                            intent.putExtra("UpdateMed", medInfo.serialize());
                            startActivity(intent);
                        }
                    }
                }
        );

        mIncrementCountImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(medInfo==null){
                            incrementMedicationCount(adapterInterfaceDataManager.getMedInfo());
                        }
                        else{
                            incrementMedicationCount(medInfo);
                        }
                    }
                }
        );

        mDecrementCountImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(medInfo==null){
                            decrementMedicationCount(adapterInterfaceDataManager.getMedInfo());
                        }
                        else{
                            decrementMedicationCount(medInfo);
                        }
                    }
                }
        );




    }




    @Override
    protected void onStart() {
        super.onStart();
        if(adapterInterfaceDataManager!=null){
            medInfo = adapterInterfaceDataManager.getMedInfo();
            mMedNameTextView.setText(adapterInterfaceDataManager.getmMedicationName());
            mMedDescriptionTextView.setText(adapterInterfaceDataManager.getmMedicationDescription());
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
                mDosageIntervalTextView.setText(interval + " minutes");
            } else
            if (interval == 1){
                mDosageIntervalTextView.setText(interval + " hour");
            } else
                mDosageIntervalTextView.setText(interval + " hours");
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
            AlertDialogCreator.createDeleteDialog(this, deleteMedication, medInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.bundle = intent.getExtras();
        handleNotificationClicks(bundle);
    }



    private void incrementMedicationCount(MedInfo medInformation) {
        int medicationAmount = Integer.parseInt(medInformation.getDoseNumber());
        dosage = medInformation.getDosageCount();
        dosage += medicationAmount;
        int returnedDosage = updateMedicationCount.updateMedicationCount(this, medInformation.getMedicationName(), dosage);
        mDosageCountTextView.setText(String.valueOf(returnedDosage));
    }

    private void decrementMedicationCount(MedInfo medInformation) {
        int medicationAmount = Integer.parseInt(medInformation.getDoseNumber());
        dosage = medInformation.getDosageCount();
        if (dosage!=0)
            dosage -= medicationAmount;
        int returnedDosage = updateMedicationCount.updateMedicationCount(this, medInformation.getMedicationName(), dosage);
        mDosageCountTextView.setText(String.valueOf(returnedDosage));
    }



    private void handleNotificationClicks(Bundle bundle){
        if (bundle!=null){
            String notificationMedInfo = bundle.getString("notificationItem");

            medInfo = MedInfo.create(notificationMedInfo);
            mMedNameTextView.setText(medInfo.getMedicationName());
            mMedDescriptionTextView.setText(medInfo.getMedicationName());
            mMedStartDateTextView.setText(medInfo.getStartDate());
            mMedStartTimeTextView.setText(medInfo.getStartTime());
            mEndDateTextView.setText(medInfo.getEndDate());
            mEndTimeTextView.setText(medInfo.getEndTime());
            mMedicationTypeTextView.setText(medInfo.getMedicationType());

            switch (medInfo.getMedicationType()) {
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
            if (medInfo.isMedicationStarted())
                mMedStatusImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
            else
                mMedStatusImageView.setImageResource(R.drawable.ic_warning_black_24dp);

            mDosageCountTextView.setText(String.valueOf(medInfo.getDosageCount()));
            int interval = medInfo.getMedicationInterval();
            if (interval == 30){
                mDosageIntervalTextView.setText(interval + " minutes");
            } else
            if (interval == 1){
                mDosageIntervalTextView.setText(interval + " hour");
            } else
                mDosageIntervalTextView.setText(interval + " hours");

            NotificationManagerCompat.from(this).cancel(bundle.getString("notifTag"), bundle.getInt("id"));
            incrementMedicationCount(medInfo);
        }
    }
}
