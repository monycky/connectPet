package com.conect.pet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.conect.pet.R;
import com.conect.pet.model.Company;
import com.squareup.picasso.Picasso;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCompany extends RecyclerView.Adapter<AdapterCompany.MyViewHolder> {

    private List<Company> empresas;

    public AdapterCompany(List<Company> empresas) {
        this.empresas = empresas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_company, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Company empresa = empresas.get(i);
        holder.nameCompany.setText(empresa.getName());
        holder.category.setText(empresa.getCategory() + " - ");
        holder.time.setText(empresa.getTime() + " Min");
        holder.delivery.setText("R$ " + empresa.getPrice().toString());

        //Carregar imagem
        String urlImagem = empresa.getUrlImage();
        Picasso.get().load( urlImagem ).into( holder.imagemCompany);

    }

    @Override
    public int getItemCount() {
        return empresas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagemCompany;
        TextView nameCompany;
        TextView category;
        TextView time;
        TextView delivery;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameCompany = itemView.findViewById(R.id.textNomeEmpresa);
            category = itemView.findViewById(R.id.textCategoriaEmpresa);
            time = itemView.findViewById(R.id.textTempoEmpresa);
            delivery = itemView.findViewById(R.id.textEntregaEmpresa);
            imagemCompany = itemView.findViewById(R.id.imageEmpresa);
        }
    }
}
