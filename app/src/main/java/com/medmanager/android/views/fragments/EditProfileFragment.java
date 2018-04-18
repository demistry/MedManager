package com.medmanager.android.views.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.medmanager.android.R;
import com.medmanager.android.views.activities.MainActivity;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class EditProfileFragment extends DialogFragment {

    private Context mContext;
    private ImageView mImageView;
    private EditText mEditText;
    private ImageButton mButton;
    private Button mUpdateButton;

    private ProfileEditingInterface mProfileEditingInterface;
    private Bitmap mBitmap;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_fragment_edit_profile, null);
        dialog.setView(view);
        return dialog.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mProfileEditingInterface = (MainActivity) activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_profile, container, false);
        mImageView = view.findViewById(R.id.imageView_edit_profile);
        mEditText = view.findViewById(R.id.editText_display_name);
        mButton = view.findViewById(R.id.button_upload_pic);
        mUpdateButton = view.findViewById(R.id.button_update_profile);


        mUpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProfileEditingInterface.handleEditProfile(mEditText.getText().toString());
//                        getDialog().dismiss();
                    }
                }
        );

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImageView.setImageBitmap(mProfileEditingInterface.openImageGallery());
                        Toast.makeText(mContext, "Button clicked", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        if (getDialog()!=null){
            getDialog().setTitle("Edit Profile");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBitmap!=null)
        mImageView.setImageBitmap(mBitmap);
    }

    public void setBitmap(Bitmap bitmap){

        mBitmap = bitmap;
    }

    /**\
     * This interface handles user profile editing
     */
    public interface ProfileEditingInterface{
        Bitmap openImageGallery();
        void handleEditProfile(String displayName);
    }

}
