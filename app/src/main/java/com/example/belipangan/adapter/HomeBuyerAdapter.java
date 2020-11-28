package com.example.belipangan.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeBuyerAdapter extends RecyclerView.Adapter<HomeBuyerAdapter.HomeBuyer> {
    private LinkedList<Product> list;
    private LayoutInflater iAdapter;

    public HomeBuyerAdapter(Context context, LinkedList<Product> list){
        this.iAdapter = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public HomeBuyerAdapter.HomeBuyer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = iAdapter.inflate(R.layout.adapter_home_buyer, parent, false);
        return new HomeBuyer(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBuyerAdapter.HomeBuyer holder, int position) {
        Product product;
        product = list.get(position);

        String nama = product.getNama();
        String harga = String.valueOf(product.getHarga());

        holder.tvNama.setText(nama);
        holder.tvHarga.setText(harga);

        Picasso.get()
                .load(Uri.parse(product.getImgUri()))
                .into(holder.ivProduct);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeBuyer extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvHarga;
        ImageView ivProduct;
        HomeBuyerAdapter homeBuyerAdapter;

        public HomeBuyer(@NonNull View itemView, HomeBuyerAdapter adapter) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaProductBuyer);
            tvHarga = itemView.findViewById(R.id.tvHargaProductBuyer);
            ivProduct = itemView.findViewById(R.id.ivProductBuyer);
            homeBuyerAdapter = adapter;

            ivProduct.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "DITEKAN", Toast.LENGTH_SHORT).show();
        }
    }
}
