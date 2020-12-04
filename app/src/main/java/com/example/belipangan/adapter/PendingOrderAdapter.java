package com.example.belipangan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belipangan.R;
import com.example.belipangan.model.Order;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.PendingOrder> {
    private LinkedList<Order> list;
    private LayoutInflater iAdapter;

    public PendingOrderAdapter(Context context, LinkedList<Order> list){
        this.list = list;
        this.iAdapter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PendingOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = iAdapter.inflate(R.layout.adapter_pending_order, parent, false);
        return new PendingOrder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrder holder, int position) {
        Order order = list.get(position);
        String namaProduk = order.getNamaProduct();
        int harga = order.getTotalHarga();
        String status = order.getStatus();

        holder.tvNama.setText(namaProduk);
        holder.tvHarga.setText(String.valueOf(harga));
        holder.tvStatus.setText(status);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PendingOrder extends RecyclerView.ViewHolder {
        PendingOrderAdapter adapter;
        TextView tvNama, tvHarga, tvStatus;
//        ImageView ivProduct;

        public PendingOrder(@NonNull View itemView, PendingOrderAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            tvNama = itemView.findViewById(R.id.tvNamaProduct);
            tvHarga = itemView.findViewById(R.id.tvTotalHarga);
            tvStatus = itemView.findViewById(R.id.tvStatusProduct);
//            ivProduct = itemView.findViewById(R.id.ivProductOrder);
        }
    }
}
