package com.medmanager.android.views.activities;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medmanager.android.R;
import com.medmanager.android.presenter.utils.UserProfileUtils;
import com.medmanager.android.views.fragments.SignInDialogFragment;
/*
* This activity is responsible for sign-in and sign up by the user*/

public class SignInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth.AuthStateListener mFireBaseAuthListener;

    private static final int RQ_SIGNIN_CODE = 100;

    private GoogleApiClient mGoogleApiClient;

    private Button mSignInButton;
    private Button mSignUpButton;
    private EditText mEmailEditText;
    private EditText mPassWordEditText;
    private EditText mVerifyPasswordEditText;
    private TextView mSignInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInButton = findViewById(R.id.button_sign_in);
        mSignUpButton = findViewById(R.id.button_sign_up);
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPassWordEditText = findViewById(R.id.edit_text_password);
        mVerifyPasswordEditText = findViewById(R.id.edit_text_password_verify);
        mSignInTextView = findViewById(R.id.textview_sign_in);


        if (firebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        mFireBaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               if (firebaseAuth.getCurrentUser()!=null){


               }
            }
        };


        mSignInTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);//used to underline the text of the forgotten password text view

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_SIGNIN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                UserProfileUtils.linkToFireBase(this, result.getSignInAccount(), firebaseAuth);
                startActivity(new Intent(this, MainActivity.class));
            }
            else{
                Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Sign Up Failed", Toast.LENGTH_SHORT).show();
    }

    public void handleGoogleSignUp(View view) {
        mGoogleApiClient = UserProfileUtils.handleSignUp(this, this);
        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(googleSignInIntent,RQ_SIGNIN_CODE);
    }

    public void handleEmailSignUp(View view) {
        {
            String emailText = mEmailEditText.getText().toString();
            String passWord = mPassWordEditText.getText().toString();
            String verifiedPassWord = mVerifyPasswordEditText.getText().toString();
            if (!emailText.isEmpty() && passWord.equals(verifiedPassWord)){
                UserProfileUtils.handleEmailSignUp(this, emailText, verifiedPassWord, firebaseAuth);
            }
            else {
                if (!passWord.equals(verifiedPassWord)){
                    mVerifyPasswordEditText.setError("Passwords do not match");
                }
            }

        }
    }

    public void handleEmailSignIn(View view) {
        SignInDialogFragment dialogFragment = new SignInDialogFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.sign_up_layout, dialogFragment,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
