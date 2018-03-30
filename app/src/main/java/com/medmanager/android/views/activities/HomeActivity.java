package com.medmanager.android.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medmanager.android.R;

public class HomeActivity extends AppCompatActivity {


    private Button mSignOutButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSignOutButton = findViewById(R.id.btn_sign_out);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mSignOutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (firebaseUser!=null){
                            firebaseAuth.signOut();
                            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                            finish();
                        }
                    }
                }
        );
    }
}
