package com.example.android.bookie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private ImageView banner;
    private EditText editTextName,editTextAge,editTextEmail,editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (ImageView) findViewById(R.id.bookie);
        banner.setOnClickListener(this);

        register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(this);

        editTextAge = (EditText) findViewById(R.id.age);
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bookie:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.register_button:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Name is required!!");
            editTextName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required!!");
            editTextAge.requestFocus();
            return;
        }
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
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                User user = new User(name,age,email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this,"Failed to Register",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);

                                        }
                                    }
                                });
                            }
                            else{

                                Toast.makeText(RegisterActivity.this,"Failed to Register",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
























    }
}