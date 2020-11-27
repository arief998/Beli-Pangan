package com.example.belipangan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.belipangan.LoginActivity;
import com.example.belipangan.R;
import com.google.firebase.auth.FirebaseAuth;

public class PenjualanFragment extends Fragment {
    FirebaseAuth mAuth;
    Menu menu;

    public PenjualanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();

        return inflater.inflate(R.layout.fragment_penjualan, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.setGroupVisible(R.id.product_menu_group, false);
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_utama, menu);
        menu.setGroupVisible(R.id.product_menu_group, false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                mAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.menuAdd:
                Toast.makeText(getContext(), "add ditekan", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}