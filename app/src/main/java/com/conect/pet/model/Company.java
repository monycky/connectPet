package com.conect.pet.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.conect.pet.helper.SetupFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;


public class Company implements Serializable {

    public String idUser;
    public String urlImage;
    public String name;
    public String category;
    public String time;
    public Double price;


    public Company() {
    }

    public void save() {
        DatabaseReference firebaseRef = SetupFirebase.getFirebase();
        DatabaseReference companyRef = firebaseRef.child("companies")
                .child(getIdUser());
        companyRef.setValue(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Company write", "success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Company write", "failed");
                    }
                });
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
