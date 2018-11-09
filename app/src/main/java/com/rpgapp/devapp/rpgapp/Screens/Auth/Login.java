package com.rpgapp.devapp.rpgapp.Screens.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rpgapp.devapp.rpgapp.MainActivity;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText _inputEmail, _inputPassword;
    private Button _btnSignup, _btnLogin, _btnForgotPassword;
    private CollectionReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    checkIfEmailVerified(user);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        _inputEmail = (EditText) findViewById(R.id.email);
        _inputPassword = (EditText) findViewById(R.id.password);
        _btnSignup = (Button) findViewById(R.id.btn_signup);
        _btnLogin = (Button) findViewById(R.id.btn_login);
        _btnForgotPassword = (Button) findViewById(R.id.btn_reset_password);

        _btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Login.this, SignUp.class), REQUEST_SIGNUP);
            }
        });

        _btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail()) {
                    String email = _inputEmail.getText().toString();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Email enviado!")
                                        .setContentText("Verifique seu email para redefinir sua senha")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            }else{
                                new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Ops!")
                                        .setContentText("Email não encontrado")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    });
                }
            }
        });

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _btnLogin.setEnabled(false);

        final String email = _inputEmail.getText().toString();
        final String password = _inputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    onLoginFailed();
                }

            }
        });

    }

    private void checkIfEmailVerified(FirebaseUser user) {

        if (user.isEmailVerified()) {
            // user is verified, so you can finish this activity or send user to activity which you want.
            onLoginSuccess(user);
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this,"Email não verificado! Verifique seu email",Toast.LENGTH_LONG).show();
            onLoginFailed();
            //restart this activity

        }
    }

    public boolean validate() {
        boolean valid;
        String password = _inputPassword.getText().toString();

        valid = validateEmail();

        if (password.isEmpty() || password.length() < 6 ) {
            _inputPassword.setError("Mínimo 6 caracteres");
            valid = false;
        } else {
            _inputPassword.setError(null);
        }

        return valid;
    }

    private boolean validateEmail(){
        String email = _inputEmail.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _inputEmail.setError("Email inválido");
            return false;
        } else {
            _inputEmail.setError(null);
            return true;
        }
    }

    public void onLoginSuccess(FirebaseUser user) {
        Log.d("what","here");
        userDatabaseReference = FirebaseFirestore.getInstance().collection("Users");
        userDatabaseReference.document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    User user = document.toObject(User.class);
                    if (user != null) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("userProfile", user);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    onLoginFailed();
                }
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(this, "O Login falhou", Toast.LENGTH_LONG).show();
        _btnLogin.setEnabled(true);
    }

}