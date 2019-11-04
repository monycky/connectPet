
package com.conect.pet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.conect.pet.R;
import com.conect.pet.adapter.AdapterCompany;
import com.conect.pet.helper.SetupFirebase;
import com.conect.pet.listener.RecyclerItemClickListener;
import com.conect.pet.model.Company;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialSearchView searchView;
    private RecyclerView recyclerCompany;
    private List<Company> companies = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private AdapterCompany adapterCompany;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeComponent();
        auth = SetupFirebase.getFirebaseAuth();
        firebaseRef = SetupFirebase.getFirebase();


        /*toolbar settings*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Connect Pet");
        setSupportActionBar(toolbar);

        //Configuraçao recyclerView
        recyclerCompany.setLayoutManager( new LinearLayoutManager(this));
        recyclerCompany.setHasFixedSize(true);
        adapterCompany = new AdapterCompany(companies);
        recyclerCompany.setAdapter(adapterCompany);

        //Recuperar Produtos para empresa
        recoveryCompany();

        searchView.setHint("Pesquisar serviços");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCompany( newText );
                return true;
            }
        });

        //Configurar click
        recyclerCompany.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerCompany,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Company empresaSelecionada = companies.get(position);
                                Intent i = new Intent(HomeActivity.this, MenuActivity.class);
                                i.putExtra("companies", empresaSelecionada);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }

    private void searchCompany(String search){
        DatabaseReference companyRef = firebaseRef
                .child("companies");
        Query query = companyRef.orderByChild("name")
                .startAt(search)
                .endAt(search + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companies.clear();


                for ( DataSnapshot ds: dataSnapshot.getChildren()) {
                    companies.add( ds.getValue(Company.class));
                }

                adapterCompany.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void recoveryCompany(){
        DatabaseReference companyRef = firebaseRef.child("companies");
        companyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companies.clear();

                for ( DataSnapshot ds: dataSnapshot.getChildren()) {
                    companies.add( ds.getValue(Company.class));
                }

                adapterCompany.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        recyclerCompany = findViewById(R.id.recyclerCompany);

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
