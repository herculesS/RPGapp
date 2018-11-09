package com.rpgapp.devapp.rpgapp.Screens.Auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rpgapp.devapp.rpgapp.R;

public class Login extends AppCompatActivity {

    ImageView mSignInFacebook;
    ImageView mSignInGoogle;
    ImageView mSignInBTN;
    ImageView mKeepLoggedToggle;

    EditText mLoginEmail;
    EditText mLoginPassword;

    TextView mSignUp;
    TextView mForgotPassword;

    Boolean mKeepLogged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linkViewControllers();
        setClickListeners();
    }

    private void setClickListeners() {



        mSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSignInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSignInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mKeepLoggedToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void linkViewControllers() {
        mSignInFacebook = findViewById(R.id.signin_facebook);
        mSignInGoogle = findViewById(R.id.signin_google);
        mSignInBTN = findViewById(R.id.signin_btn);

        mKeepLoggedToggle = findViewById(R.id.keep_logged);

        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);

        mSignUp = findViewById(R.id.signup);
        mForgotPassword = findViewById(R.id.forgotten_password);

    }


}
