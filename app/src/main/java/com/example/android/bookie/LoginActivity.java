package com.example.android.bookie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="Login Activity" ;
    private TextView register,forgotPassword;

    private EditText editTextEmail,editTextPassword;
    private Button login;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView)findViewById(R.id.register);

        register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText)findViewById(R.id.login_email);
        editTextPassword = (EditText)findViewById(R.id.login_password);

        login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_login_Activity);

        forgotPassword = (TextView)findViewById(R.id.forgot_password);

        forgotPassword.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //user stays signed in even if the application closes
        if(user != null) {  // To not ask the user to login again and again
            // User is signed in
            Intent i = new Intent(LoginActivity.this, CatalogActivity.class);// later have to change it to CatalogActivity.class
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.register:
            startActivity(new Intent(this,RegisterActivity.class));
            break;

            case R.id.login_button:
                userLogin();
                break;

            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
        }

    }


    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a Valid Email Address!!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Enter a Password!!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Enter at least 6 Characters!!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        //redirect to user profile
                        startActivity(new Intent(LoginActivity.this, CatalogActivity.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Unable to Login please check Email or Password",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });


    }
}