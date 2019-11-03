package com.conect.pet.model;

import com.conect.pet.helper.SetupFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;


public class Company implements Serializable {

    private String idUser;
    private String urlImage;
    private String name;
    private String category;
    private String time;
    private Double price;


    public Company() {
    }

    public void save() {
        DatabaseReference firebaseRef = SetupFirebase.getFirebase();
        DatabaseReference empresaRef = firebaseRef.child("companies")
                .child(getidUser());
        empresaRef.setValue(this);
    }

    public String getidUser() {
        return idUser;
    }

    public void setidUser(String idUser) {
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
