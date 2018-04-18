package com.medmanager.android.views.fragments;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.UserProfileUtils;

/**
 * Created by ILENWABOR DAVID on 25/03/2018.
 */

public class SignInDialogFragment extends DialogFragment {

    private EditText mEmailEditText;
    private EditText mPassWordEditText;
    private Button mSignInButton;
    private TextView mForgottenPassWordTextView;

    private FirebaseAuth mFirebaseAuth;

    public SignInDialogFragment(){
        //Default Empty Constructor
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_fragment_sign_in, null);
        dialog.setView(view);
        return dialog.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_sign_in, container, false);
        mEmailEditText = view.findViewById(R.id.edit_text_dialog_email);
        mPassWordEditText = view.findViewById(R.id.edit_text_dialog_password);
        mForgottenPassWordTextView = view.findViewById(R.id.textview_dialog_sign_in);
        mSignInButton = view.findViewById(R.id.button_dialog_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mSignInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emailText = mEmailEditText.getText().toString();
                        String passWord = mPassWordEditText.getText().toString();
                        if (!emailText.isEmpty() && !passWord.isEmpty()){
                            UserProfileUtils.handleSignIn(getContext(), emailText, passWord, mFirebaseAuth);
                        }
                    }
                }
        );
        return view;
    }
}
