package com.example.belipangan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailBuyerActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser fUser;
    Intent intent;
    String nama, deskripsi, kategori, key;
    Uri imgUri;
    int harga, berat, pemesananMinimum;
    DatabaseReference dbReference;
    StorageReference storageReference;

    ImageView ivProduk;
    TextView tvdesk, tvHarga, tvNama, tvKategori, tvMinPesanan, tvBerat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_buyer);

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();

        instansiasiView();
        intent = getIntent();
        getIntentData();

        dbReference = FirebaseDatabase.getInstance().getReference("Product").child(fUser.getUid()).child(key);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imgUri.toString());

        setView();

    }

    private void instansiasiView(){
        ivProduk = findViewById(R.id.ivDetailProduct);
        tvdesk = findViewById(R.id.tvDeskripsiProduct);
        tvHarga = findViewById(R.id.tvHargaProductDetail);
        tvNama = findViewById(R.id.tvNamaProductDetail);
        tvKategori = findViewById(R.id.tvKategori);
        tvMinPesanan = findViewById(R.id.tvPemesananMin);
        tvBerat = findViewById(R.id.tvBeratProduct);
    }

    private void getIntentData(){
        nama = intent.getStringExtra("EXTRA_NAMA");
        harga = intent.getIntExtra("EXTRA_HARGA", 0);
        deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI");
        kategori = intent.getStringExtra("EXTRA_KATEGORI");
        imgUri = Uri.parse(intent.getStringExtra("EXTRA_IMAGE_URL"));
        key = intent.getStringExtra("EXTRA_KEY");
        berat = intent.getIntExtra("EXTRA_BERAT", 0);
        pemesananMinimum = intent.getIntExtra("EXTRA_PEMESANAN", 0);
    }

    private void setView(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        tvHarga.setText(formatRupiah.format((double) harga));
        tvNama.setText(nama);
        tvdesk.setText(deskripsi);
        tvKategori.setText(kategori);
        tvBerat.setText(String.valueOf(berat));
        tvMinPesanan.setText(String.valueOf(pemesananMinimum));


        Picasso.get()
                .load(imgUri)
                .placeholder(R.drawable.ic_image)
                .fit()
                .into(ivProduk);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.product_menu_group, false);
        menu.setGroupVisible(R.id.edit_menu_product, false);
        menu.setGroupVisible(R.id.group_logout, false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_utama, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                mAuth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}