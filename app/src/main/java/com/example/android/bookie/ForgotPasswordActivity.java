package com.example.android.bookie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

private EditText emailEditText;
private Button resetButton;
private ProgressBar progressBar;

FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.email_edit_text_forgot_password);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_forgot);

        resetButton = (Button)findViewById(R.id.reset_button);

        auth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }

    private void resetPassword() {

        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Email is required!!");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter a Valid Email Address!!");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this,"Check your Email",Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this,"An Error occurred",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}