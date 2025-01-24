package com.bintangnaufal.uas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    private ImageView eyeIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.emailET);
        passwordEditText = findViewById(R.id.passwordET);

        ImageView eyeIcon = findViewById(R.id.eyeIcon);


        eyeIcon.setOnClickListener(view -> {
            if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye_off);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye);
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }

    public void login(View view) {
        if (TextUtils.isEmpty(emailEditText.getText().toString().trim()) ||
                TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
            Toast.makeText(this, "Silahkan lengkapi email dan password terlebih dahulu", Toast.LENGTH_SHORT).show();

        }else if (emailEditText.getText().toString().equals("admin@gmail.com") &&
                passwordEditText.getText().toString().equals("admin")) {
            Intent intent = new Intent(this, activity_adminpanel.class);
            startActivity(intent);

            SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("EMAIL", emailEditText.getText().toString());
            editor.apply();

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            DatabaseHelper users = new DatabaseHelper(this);
            if (users.isValidUserAdmin(emailEditText.getText().toString(), passwordEditText.getText().toString())) {
                Intent intent = new Intent(this, activity_adminpanel.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", emailEditText.getText().toString());
                editor.apply();

            } else if (users.isValidUser(emailEditText.getText().toString(), passwordEditText.getText().toString())) {
//                SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("EMAIL", emailEditText.getText().toString());
//                editor.apply();
            }else {
                Toast.makeText(this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void daftar(View view) {
        Intent intent = new Intent(this, activity_daftar.class);
        startActivity(intent);
    }
}