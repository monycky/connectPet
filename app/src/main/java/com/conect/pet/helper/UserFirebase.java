package com.conect.pet.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static com.conect.pet.helper.SetupFirebase.getFirebaseAuth;

public class UserFirebase {

    public static String getIdUser(){
        FirebaseAuth auth = SetupFirebase.getFirebaseAuth();
        return auth.getCurrentUser().getUid();
    }

    public static FirebaseUser getCurrentUser(){
        FirebaseAuth user = SetupFirebase.getFirebaseAuth();
        return user.getCurrentUser();
    }

    public static boolean updateTypeUser(String type){
        try{
            FirebaseUser user = getCurrentUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(type)
                    .build();
            user.updateProfile(profile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
