package com.conect.pet.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conect.pet.R;
import com.conect.pet.adapter.AdapterProduct;
import com.conect.pet.helper.SetupFirebase;
import com.conect.pet.helper.UserFirebase;
import com.conect.pet.listener.RecyclerItemClickListener;
import com.conect.pet.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView recyclerProducts;
    private AdapterProduct adapterProduct;
    private List<Product> products = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idLoggedUser;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        initializeComponents();
        auth = SetupFirebase.getFirebaseAuth();
        firebaseRef = SetupFirebase.getFirebase();
        idLoggedUser = UserFirebase.getIdUser();


        /*toolbar settings*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Connect Pet - Company");
        setSupportActionBar(toolbar);


        //Setup recyclerView
        recyclerProducts.setLayoutManager( new LinearLayoutManager(this));
        recyclerProducts.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(products, this);
        recyclerProducts.setAdapter(adapterProduct);

        recoveryProducts();

        recyclerProducts.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this, recyclerProducts,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Product selectedProduct = products.get(position);
                                selectedProduct.remove();
                                Toast.makeText(CompanyActivity.this, "Serviço Excluido com sucesso", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


    private void recoveryProducts() {

        final DatabaseReference produtosRef = firebaseRef
                .child("product")
                .child(idLoggedUser);
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot ds: dataSnapshot.getChildren()) {
                    products.add( ds.getValue(Product.class));
                }

                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void initializeComponents(){
        recyclerProducts = findViewById(R.id.recyclerProducts);

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
