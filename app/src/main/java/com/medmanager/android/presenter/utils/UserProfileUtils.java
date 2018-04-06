package com.medmanager.android.presenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.medmanager.android.R;
import com.medmanager.android.views.activities.MainActivity;
import com.medmanager.android.views.activities.SignInActivity;

/**
 * Created by ILENWABOR DAVID on 23/03/2018.
 * This class handles everything that has to do with user sign in, sign up and forgotten password.
 * It is linked to the Firebase server using firebase authentication SDK
 */

public class UserProfileUtils {

    //This method handles user sign up
    public static GoogleApiClient handleSignUp(Context context, GoogleApiClient.OnConnectionFailedListener listener){
        GoogleSignInOptions googleSignInOptions = buildGoogleSignIn(context);
        return new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, listener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    //This method handles user sign in
    public static void handleSignIn(final Context context, String emailText, String passWord, FirebaseAuth firebaseAuth){
        firebaseAuth.signInWithEmailAndPassword(emailText, passWord)
        .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(context, "completed custom sign-in", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
                ((Activity) context).finish();

                if (!task.isSuccessful()){
                    Toast.makeText(context, "Login failed, check details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void handleEmailSignUp(final SignInActivity signInActivity, String emailText, String verifiedPassWord, FirebaseAuth mFireBaseAuthInstance) {
        mFireBaseAuthInstance.createUserWithEmailAndPassword(emailText, verifiedPassWord)
                .addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(signInActivity, "Account created, please sign in", Toast.LENGTH_SHORT).show();
                        signInActivity.startActivity(new Intent(signInActivity, MainActivity.class ));
                        signInActivity.finish();
                    }
                });
    }

    //This method handles user password recovery
    public static void handleForgottenPassword(Context context){

    }

    //This method handles linking of obtained google account to fire-base
    public static void linkToFireBase(final Context context, GoogleSignInAccount account, FirebaseAuth firebaseAuth){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Google Sign-In Successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "FireBase authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static GoogleSignInOptions buildGoogleSignIn(Context context){
        return new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
    }



}
