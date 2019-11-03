
package com.conect.pet.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.view.View;
import android.provider.MediaStore;
import java.io.ByteArrayOutputStream;
import com.conect.pet.helper.SetupFirebase;
import com.conect.pet.helper.UserFirebase;
import com.conect.pet.model.Company;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.widget.Toast;


import com.conect.pet.R;

public class SettingsCompanyActivity extends AppCompatActivity {

    private EditText editCompanyName, editCompanyCategory, editCompanyTime, editCompanyPrice;
    private ImageView imageCompanyProfile;

    private static final int SELECT_GALLERY = 200;

    private StorageReference storageReference;
    private String idLoggedUser;
    private String urlSelectedImage = "";
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_company);


        /*Initial Settings*/
        initializeComponents();
        storageReference = SetupFirebase.getFirebaseStorage();
        idLoggedUser = UserFirebase.getIdUser();
        firebaseRef = SetupFirebase.getFirebase();


        /*toolbar settings*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageCompanyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                if( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECT_GALLERY);
                }
            }
        });

    }

    public  void validateCompanyData(View view){
        String name = editCompanyName.getText().toString();
        String price = editCompanyPrice.getText().toString();
        String category = editCompanyCategory.getText().toString();
        String time = editCompanyTime.getText().toString();


        if( !name.isEmpty() ){
            if( !category.isEmpty() ){
                if( !price.isEmpty() ){

                    Company company = new Company();
                    company.setidUser(idLoggedUser);
                    company.setName(name);
                    company.setCategory(category);
                    company.setTime(time);
                    company.setPrice( Double.parseDouble(price));
                    company.setUrlImage(urlSelectedImage);
                    company.save();
                    finish();

                }else{
                    showMenssage("Digite um valor médio dos serviços");
                }

            }else{
                showMenssage("Digite os tipos de serviços");
            }

        }else{
            showMenssage("Digite um nome para a Empresa");
        }

    }

    private void showMenssage(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bitmap image = null;

            try{

                switch (requestCode){
                    case SELECT_GALLERY:
                        Uri localImage = data.getData();
                        image = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        localImage
                                );
                        break;
                }

                if(image != null){

                    imageCompanyProfile.setImageBitmap(image);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG,70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    StorageReference imageRef = storageReference
                            .child("images")
                            .child(idLoggedUser + "jpeg");

                    UploadTask uploadTask = imageRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsCompanyActivity.this, "Error on image upload"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            urlSelectedImage = taskSnapshot.getDownloadUrl().toString();
                            Toast.makeText(SettingsCompanyActivity.this, "Success on image upload"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void initializeComponents(){
        editCompanyName = findViewById(R.id.editCompanyName);
        editCompanyCategory = findViewById(R.id.editCompanyCategory);
        editCompanyTime = findViewById(R.id.editCompanyTime);
        editCompanyPrice = findViewById(R.id.editCompanyPrice);
        imageCompanyProfile = findViewById(R.id.imageCompanyProfile);

    }

}
