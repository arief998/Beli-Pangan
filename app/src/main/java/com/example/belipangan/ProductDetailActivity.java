package com.example.belipangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    Intent intent;
    String nama, deskripsi, kategori;
    Uri imgUri;
    int harga;

    ImageView ivProduk;
    TextView tvdesk, tvHarga, tvNama, tvKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        instansiasiView();
        intent = getIntent();
        getIntentData();
        setView();

    }

    private void instansiasiView(){
        ivProduk = findViewById(R.id.ivDetailProduct);
        tvdesk = findViewById(R.id.tvDeskripsiProduct);
        tvHarga = findViewById(R.id.tvHargaProductDetail);
        tvNama = findViewById(R.id.tvNamaProductDetail);
        tvKategori = findViewById(R.id.tvKategori);
    }

    private void getIntentData(){
        nama = intent.getStringExtra("EXTRA_NAMA");
        harga = intent.getIntExtra("EXTRA_HARGA", 0);
        deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI");
        kategori = intent.getStringExtra("EXTRA_KATEGORI");
        imgUri = Uri.parse(intent.getStringExtra("EXTRA_IMAGE_URL"));
    }

    private void setView(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        tvHarga.setText(formatRupiah.format((double) harga));
        tvNama.setText(nama);
        tvdesk.setText(deskripsi);
        tvKategori.setText(kategori);

        Picasso.get()
                .load(imgUri)
                .into(ivProduk);
    }
}