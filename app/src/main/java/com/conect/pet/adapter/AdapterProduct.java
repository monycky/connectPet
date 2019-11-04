package com.conect.pet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conect.pet.R;
import com.conect.pet.model.Product;

import java.util.List;


/**
 * Created by Jamilton
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder>{

    private List<Product> products;
    private Context context;

    public AdapterProduct(List<Product> produtos, Context context) {
        this.products = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produto, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Product product = products.get(i);
        holder.nome.setText(product.getName());
        holder.descricao.setText(product.getDescription());
        holder.valor.setText("R$ " + product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView descricao;
        TextView valor;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomeRefeicao);
            descricao = itemView.findViewById(R.id.textDescricaoRefeicao);
            valor = itemView.findViewById(R.id.textPreco);
        }
    }
}
