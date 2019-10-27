package com.conect.pet.helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SetupFirebase {

    public static DatabaseReference referenceFirebase;
    public static FirebaseAuth referenceAuth;
    public static StorageReference referenceStorage;

    public static DatabaseReference getFirebase(){
        if(referenceFirebase == null){
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(referenceAuth == null){
            referenceAuth = FirebaseAuth.getInstance();
        }
        return referenceAuth;
    }

    public static StorageReference getFirebaseStorage(){
        if(referenceStorage == null){
            referenceStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenceStorage;
    }

}
