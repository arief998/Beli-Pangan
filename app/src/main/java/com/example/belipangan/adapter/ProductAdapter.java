package com.example.belipangan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belipangan.R;
import com.example.belipangan.model.Product;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewProduct>{
    LayoutInflater iAdapter;
    LinkedList<Product> listProduct;
    int posisi;

    public ProductAdapter(Context context, LinkedList<Product> list){
        iAdapter = LayoutInflater.from(context);
        listProduct = list;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = iAdapter.inflate(R.layout.item_product_grid, parent, false);
        return new ViewProduct(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewProduct holder, int position) {
        Product product = new Product();
        product = listProduct.get(position);

        String nama = product.getNama();
        String harga = product.getHarga();
        String deskripsi = product.getDeskripsi();
        Uri imgUri = Uri.parse(product.getImgUri());

        holder.tvNama.setText(nama);
        holder.tvDeskrispi.setText(deskripsi);
        holder.tvHarga.setText(harga);

        Picasso.get()
                .load(imgUri)
                .into(holder.ivProduct);

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewProduct extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNama, tvHarga, tvDeskrispi;
        ImageView ivProduct;
        ProductAdapter productAdapter;

        public ViewProduct(@NonNull View itemView, ProductAdapter adapter) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaProduct);
            tvHarga = itemView.findViewById(R.id.tvHargaProduct);
            tvDeskrispi = itemView.findViewById(R.id.tvDeskripsiProduct);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            productAdapter = adapter;

            ivProduct.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int posisi = getLayoutPosition();
            String isi = listProduct.get(posisi).getNama();
            Toast.makeText(view.getContext(), isi, Toast.LENGTH_SHORT).show();
        }
    }


}
