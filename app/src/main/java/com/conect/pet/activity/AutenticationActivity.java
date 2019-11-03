package com.conect.pet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.conect.pet.R;
import com.conect.pet.helper.UserFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.conect.pet.helper.SetupFirebase;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AutenticationActivity extends AppCompatActivity {

    private Button accessButton;
    private EditText fieldEmail, fieldPassword;
    private Switch typeAccess, typeUser;
    private LinearLayout linearTypeUserCompany;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentication);

        initializeComponents();
        auth = SetupFirebase.getFirebaseAuth();
        //auth.signOut();
        verifySignedUser();


        typeAccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(typeAccess.isChecked()){ //company
                    linearTypeUserCompany.setVisibility(View.VISIBLE);
                }else{ //profile
                    linearTypeUserCompany.setVisibility(View.GONE);
                }
            }
        });

        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  fieldEmail.getText().toString();
                String password = fieldPassword.getText().toString();

                if(!email.isEmpty()){
                    if(!password.isEmpty()){
                        //If not empty, you can auth!
                        if(typeAccess.isChecked()){ //register

                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(AutenticationActivity.this,
                                                        "Cadastro Realizado com sucesso!"
                                                        , Toast.LENGTH_SHORT).show();
                                                String typeUser = getTypeUser();
                                                UserFirebase.updateTypeUser(typeUser);
                                                openMainScreen(typeUser);

                                            }else{
                                                String errorExeption = "";
                                                try{
                                                    throw task.getException() ;
                                                }catch(FirebaseAuthWeakPasswordException e){
                                                    errorExeption = "Digite uma senha mais forte";
                                                }catch(FirebaseAuthInvalidCredentialsException e){
                                                    errorExeption = "Por favor digite um e-mail válido";
                                                }catch (FirebaseAuthUserCollisionException e) {
                                                    errorExeption= "Essa conta ja foi cadastrada";
                                                }catch (Exception e) {
                                                    errorExeption = "Ao cadastrar usuário: " + e.getMessage();
                                                    e.printStackTrace();

                                                    Toast.makeText(AutenticationActivity.this,
                                                            "Erro: " + errorExeption
                                                            , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                            );

                        }else{//login
                            auth.signInWithEmailAndPassword(
                                    email, password
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AutenticationActivity.this,
                                                "Logado com sucesso "
                                                , Toast.LENGTH_SHORT).show();
                                        String typeUser = task.getResult().getUser().getDisplayName();
                                        openMainScreen(typeUser);
                                    }else{
                                        Toast.makeText(AutenticationActivity.this,
                                                "Erro ao fazer o login: " + task.getException()
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }else {
                        Toast.makeText(AutenticationActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AutenticationActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTypeUser(){
        if(typeUser.isChecked()){
            return "company";
        }else{
            return "profile";
        }

    }

    private void verifySignedUser(){
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){
            String typeUser = currentUser.getDisplayName();
            openMainScreen(typeUser);
        }
    }

    private void openMainScreen(String typeUser){
        if(typeUser.equals("company")){ //C is Company
            startActivity(new Intent(getApplicationContext(), CompanyActivity.class));
        }else{//profile
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void initializeComponents(){
        fieldEmail = findViewById(R.id.editRegisterEmail);
        fieldPassword = findViewById(R.id.editRegisterPassword);
        accessButton = findViewById(R.id.buttonAccess);
        typeAccess = findViewById(R.id.switchTypeAccess);
        typeUser = findViewById(R.id.switchTypeUser);
        linearTypeUserCompany = findViewById(R.id.linearUserCompany);
    }


}
