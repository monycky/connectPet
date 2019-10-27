package com.conect.pet.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.conect.pet.R;
import com.conect.pet.helper.SetupFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        auth = SetupFirebase.getFirebaseAuth();

        /*toolbar settings*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Connect Pet - Company");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_company, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menuSignOut:
                signOutUser();
                break;
            case  R.id.menuSettings:
                openSettings();
                break;
            case  R.id.menuNewProcut:
                openNewProduct();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOutUser(){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void openSettings() {
        startActivity(new Intent(CompanyActivity.this, SettingsCompanyActivity.class));

    }

    private void openNewProduct(){
        startActivity(new Intent(CompanyActivity.this, NewProductCompanyActivity.class));
    }
}
