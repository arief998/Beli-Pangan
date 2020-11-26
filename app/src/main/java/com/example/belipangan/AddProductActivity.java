package com.example.belipangan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.belipangan.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etNama, etDeksripsi, etHarga, etNoTelpon, etLokasi;
    Spinner spKategori;
    Uri imageUri;
    ImageView ivProduct;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference dbReference;
    Product product;

    String nama, deskripsi, noTelpon, lokasi, kategori, uID, imgUri;
    int harga;
    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        etNama = findViewById(R.id.etNamaProduct);
        etHarga = findViewById(R.id.etHargaProduct);
        etLokasi = findViewById(R.id.etAlamatProduct);
        etDeksripsi = findViewById(R.id.etDeskripsiProduct);
        spKategori = findViewById(R.id.spinnerKategori);
        etNoTelpon = findViewById(R.id.etNoTelpon);
        ivProduct = findViewById(R.id.ivProduk);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.array_kategori,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spKategori != null){
            spKategori.setAdapter(adapter);
        }
    }

    public void tambahProduct(View view) {
        isValid = validasi();
        if(isValid){
            uID = user.getUid();
            dbReference = database.getReference("Product").child(uID);
            product = new Product(nama, deskripsi, noTelpon, lokasi, uID, kategori, harga, imgUri);

            String productId = dbReference.push().getKey();
            dbReference.child(productId).setValue(product);

            Intent selesai = new Intent(AddProductActivity.this, MainActivity.class);
            selesai.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(selesai);
        }
    }

    public void batal(View view) {
        Intent batal = new Intent(AddProductActivity.this, MainActivity.class);
        startActivity(batal);
    }

    public boolean validasi(){
        nama = etNama.getText().toString().trim();
        String hargas = etHarga.getText().toString().trim();
        lokasi = etLokasi.getText().toString().trim();
        deskripsi = etDeksripsi.getText().toString().trim();
        noTelpon = etNoTelpon.getText().toString().trim();

        if(nama.length() == 0){
            etNama.setError("Nama produk harus di isi!");
            return false;
        }
        else if(hargas.length() == 0){
            etHarga.setError("Harga produk harus di isi!");
            return false;
        }
        else if(lokasi.length() == 0){
            etLokasi.setError("Lokasi harus di isi!");
            return false;
        }
        else if(deskripsi.length() == 0){
            etDeksripsi.setError("Deskripsi produk harus di isi!");
            return false;
        }
        else if(noTelpon.length() == 0){
            etNoTelpon.setError("Nomor telpon harus di isi!");
            return false;
        }else {
            harga = Integer.parseInt(hargas);
            return  true;
        }
    }

    public void chooseImage(View view) {
        Intent upImage = new Intent();
        upImage.setType("image/*");
        upImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(upImage, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            imgUri = imageUri.toString();
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading image...");
        progressDialog.show();

        String productId = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + user.getUid()).child(imgUri);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_SHORT).show();
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgUri = uri.toString();
                                Log.d("uri", imgUri);

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double precentage = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Progress: " + (int) precentage + "%");

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        kategori = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}