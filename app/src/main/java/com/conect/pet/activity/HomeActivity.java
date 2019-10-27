
package com.conect.pet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.conect.pet.R;
import com.conect.pet.helper.SetupFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeComponent();
        auth = SetupFirebase.getFirebaseAuth();

        /*toolbar settings*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Connect Pet");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        /*Config */
        MenuItem item = menu.findItem(R.id.menuSearch);
        searchView.setMenuItem(item);

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

        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeComponent(){
        searchView = findViewById(R.id.materialSearchView);
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
        startActivity(new Intent(HomeActivity.this, SettingsUserActivity.class));

    }

}
