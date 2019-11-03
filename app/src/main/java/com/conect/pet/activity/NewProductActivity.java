package com.conect.pet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.conect.pet.R;
import com.conect.pet.helper.UserFirebase;
import com.conect.pet.model.Product;

public class NewProductActivity extends AppCompatActivity {

    private EditText editProductName, editProductDescription,
            editProductPrice;
    private String idLoggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_company);

        initializeComponents();
        idLoggedUser = UserFirebase.getIdUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Serviço");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validProductData(View view){
        String name = editProductName.getText().toString();
        String description = editProductDescription.getText().toString();
        String price = editProductPrice.getText().toString();

        if(!name.isEmpty()){
            if(!description.isEmpty()){
                if(!price.isEmpty()){

                    Product product = new Product();
                    product.setIdUser(idLoggedUser);
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(Double.parseDouble(price));
                    product.save();
                    showMessage("Serviço Salvo com Sucesso");
                    finish();

                }else{
                    showMessage("Digite um nome para o Serviço");
                }

            }else{
                showMessage("Digite a descrição do serviço");
            }

        }else{
            showMessage("Digite valor para o serviço");
        }

    }

    private void showMessage(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void initializeComponents(){
        editProductDescription = findViewById(R.id.editProductDescription);
        editProductName = findViewById(R.id.editProductName);
        editProductPrice = findViewById(R.id.editProductPrice);
    }
}
