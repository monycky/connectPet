package com.conect.pet.model;
import com.conect.pet.helper.SetupFirebase;
import com.google.firebase.database.DatabaseReference;

public class Product {

    private String idUser;
    private String name;
    private String description;
    private Double price;
    private String idProduct;


    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public Product() {
        DatabaseReference firebaseRef = SetupFirebase.getFirebase();
        DatabaseReference productRef = firebaseRef
                .child("products");
        setIdProduct(productRef.push().getKey());
    }


    public void save() {
        DatabaseReference firebaseRef = SetupFirebase.getFirebase();
        DatabaseReference productRef = firebaseRef
                .child("product")
                .child(getIdUser())
                .child(getIdProduct())
                .push();
        productRef.setValue(this);
    }

    public void remove() {
        DatabaseReference firebaseRef = SetupFirebase.getFirebase();
        DatabaseReference productRef = firebaseRef
                .child("product")
                .child(getIdUser())
                .child(getIdProduct());
        productRef.removeValue();

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
