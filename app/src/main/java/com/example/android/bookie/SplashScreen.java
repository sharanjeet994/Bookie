package com.example.android.bookie;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Thread thread = new Thread(){

          public void run(){
              try {
                  sleep(3000);
              }
              catch (Exception e){
                  e.printStackTrace();
              }
              finally {
                  if (user!=null){
                      startActivity(new Intent(SplashScreen.this,CatalogActivity.class));
                      finish();
                  }
                  else {
                      startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                      finish();
                  }
              }
          }

        };thread.start();

    }
}