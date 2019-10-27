package com.conect.pet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.conect.pet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openAutentication();
            }
        }, 3000);

    }

    public void openAutentication(){
        Intent i = new Intent(MainActivity.this, AutenticationActivity.class);
        startActivity(i);
        finish();
    }
}
