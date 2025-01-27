package com.bintangnaufal.uas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Activity_Menu extends AppCompatActivity {

    RecyclerView recyclerViewR;
    MenuAdapter MenuAdapter;
    private List<Product> productList;
    private TextView nameUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        2.recyclerview recomended belum tampil

        recyclerViewR = findViewById(R.id.menuRecomended);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewR.setLayoutManager(layoutManager);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        productList = databaseHelper.getAllProductsRecommended();

        MenuAdapter = new MenuAdapter(this, productList);
        recyclerViewR.setAdapter(MenuAdapter);
    }

    public void logout(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Warning");
        alertBuilder.setMessage("Apakah Anda yakin ingin keluar dari akun ini?");
        alertBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(Activity_Menu.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        });
        alertBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertBuilder.show();
    }

}