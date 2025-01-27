package com.bintangnaufal.uas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ProgressBar statusProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.light_coral));
        }

        statusProgressBar = findViewById(R.id.statusPB);
        statusProgressBar.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("Email", null);

            if (email != null) {
                DatabaseHelper db = new DatabaseHelper(this);
                Boolean is_admin = db.isAdmin(email);

                if (is_admin) {
                    Intent intent = new Intent(MainActivity.this, activity_adminpanel.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, Activity_Menu.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(MainActivity.this, activity_login.class);
                startActivity(intent);
            }
            finish();
        }, 2000);
    }
}