package com.example.belipangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belipangan.model.Product;
import com.example.belipangan.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductOrderActivity extends AppCompatActivity implements TransactionFinishedCallback {

    FirebaseUser fUser;
    DatabaseReference db;
    User user;
    Product product;
    Intent intent;

    String nama, deskripsi, kategori, key, alamat, uid, noTelpon, almtCostumer, kuantitas;
    Uri imgUri;
    int harga, berat, pemesananMinimum, stok, qty=0;
    CustomerDetails cd;

    TextView tvHarga, tvNama, tvBerat, tvNamaCus, tvEmailCus, tvTelpCus;
    ImageView ivProduk;
    EditText etQty, etAddressCus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_order);

        intent = getIntent();
        getIntentData();


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("user").child(fUser.getUid());

        initMidtransSdk();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                cd = detailCustomer(user);
                initView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        tvNama = findViewById(R.id.tvNamaProductReview);
        tvHarga = findViewById(R.id.tvHargaReview);
        etQty = (EditText) findViewById(R.id.etQty);
        tvNamaCus = findViewById(R.id.tvNamaCustomer);
        tvEmailCus = findViewById(R.id.tvEmailCustomer);
        tvTelpCus = findViewById(R.id.tvTelponCustomer);
        etAddressCus = (EditText) findViewById(R.id.etAlamatReview);
        tvBerat = findViewById(R.id.tvBeratReview);
        ivProduk = findViewById(R.id.ivProdukReview);

        tvNama.setText(nama);
        tvHarga.setText(formatRupiah.format(harga)+"/pcs");
        tvBerat.setText("Berat/pcs "+berat+ " gr");
        tvNamaCus.setText(this.user.getNama());
        tvEmailCus.setText(this.user.getEmail());
        tvTelpCus.setText(this.user.getNoTelpon());

        Picasso.get()
                .load(imgUri)
                .placeholder(R.drawable.ic_image)
                .fit()
                .into(ivProduk);
    }

    private boolean validasi(){
        kuantitas = etQty.getText().toString().trim();
        almtCostumer = etAddressCus.getText().toString().trim();

        if(kuantitas.length() == 0){
            etQty.setError("Kuantitas tidak boleh kosong atau kurang dari " + String.valueOf(pemesananMinimum));
            return false;
        }else if(almtCostumer.length() == 0){
            etAddressCus.setError("Masukkan alamat anda saat ini");
            return false;
        }else {
            qty = Integer.parseInt(kuantitas);
            if (qty < pemesananMinimum) {
                etQty.setError("Kuantitas tidak kurang dari " + String.valueOf(pemesananMinimum));
                return false;
            }else if(qty > stok){
                etQty.setError("Kuantitas tidak boleh lebih dari " + String.valueOf(stok));
                return false;
            }else{
                return true;
            }
        }
    }

    private void getIntentData(){
        product = (Product)intent.getSerializableExtra("EXTRA_PRODUCT");
        nama = product.getNama();
        harga = product.getHarga();
        deskripsi = product.getDeskripsi();
        kategori = product.getKategori();
        imgUri = Uri.parse(product.getImgUri());
        key = intent.getStringExtra("EXTRA_KEY");
        alamat = product.getAlamat();
        uid = product.getuID();
        noTelpon = product.getNoTelpon();
        berat = product.getBerat();
        pemesananMinimum = product.getMinPemesanan();
        stok = product.getStok();
    }

    private void initMidtransSdk() {
        SdkUIFlowBuilder.init()
                .setContext(this)
                .setMerchantBaseUrl(BuildConfig.BASE_URL)
                .setClientKey(BuildConfig.CLIENT_KEY)
                .setTransactionFinishedCallback(this)
                .buildSDK();
    }

    public void pilihPembayaran(View view) {
        boolean isValid = validasi();

        if(isValid){
            Log.d("harga", String.valueOf(harga));
            Log.d("qty", String.valueOf(qty) );
            Log.d("nama", String.valueOf(nama) );
            MidtransSDK.getInstance().setTransactionRequest(transactionRequest(
                    "1", harga, qty, nama, cd
            ));
            MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER_BCA);
        }

    }

    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {
        if(transactionResult.getResponse() != null){
            switch (transactionResult.getStatus()){
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Finished ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed ID : " + transactionResult.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;

            }
            transactionResult.getResponse().getValidationMessages();
        }else if(transactionResult.isTransactionCanceled()){
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_SHORT).show();
        }else {
            if(transactionResult.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)){
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Transaction Finished with failure", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private CustomerDetails detailCustomer(User user){
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName(user.getNama());
        cd.setEmail(user.getEmail());
        cd.setPhone(user.getNoTelpon());
        return cd;

    }

    @SuppressLint("WrongConstant")
    private TransactionRequest transactionRequest(String id, int price, int qty, String name, CustomerDetails cd){
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis()+" ", price*qty);
        request.setCustomerDetails(cd);
        ItemDetails item = new ItemDetails(id, price, qty, name);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(item);
        request.setItemDetails(itemDetails);

        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.RBA);
        creditCard.setBank(BankType.BCA);
        request.setCreditCard(creditCard);

        return request;
    }
}