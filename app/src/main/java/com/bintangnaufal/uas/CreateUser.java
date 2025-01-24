package com.bintangnaufal.uas;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateUser extends AppCompatActivity {
    private ImageView eyeIcon, eyeIconKonfirmasi;
    private EditText passwordEditText, confirmPasswordEditText, usernameET, emailET;

    RadioButton iyaRB, tidakRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);
        passwordEditText = findViewById(R.id.passwordET);
        confirmPasswordEditText = findViewById(R.id.passwordKonfirmasiET);
        iyaRB = findViewById(R.id.iyaRB);
        tidakRB = findViewById(R.id.tidakRB);

        eyeIcon = findViewById(R.id.eyeIcon);
        eyeIconKonfirmasi = findViewById(R.id.eyeIconConfirm);

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

        eyeIconKonfirmasi.setOnClickListener(view -> {
            if (confirmPasswordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eyeIconKonfirmasi.setImageResource(R.drawable.ic_eye_off);
            } else {
                confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eyeIconKonfirmasi.setImageResource(R.drawable.ic_eye);
            }
            confirmPasswordEditText.setSelection(confirmPasswordEditText.getText().length());
        });
    }

    public void SaveUser(View view) {
        String username = usernameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String pw = passwordEditText.getText().toString().trim();
        String pwc = confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || pw.isEmpty() || (!iyaRB.isChecked() && !tidakRB.isChecked())) {
            Toast.makeText(this, "Lengkapi Semua Data", Toast.LENGTH_SHORT).show();
        } else if (!pw.equals(pwc)) {
            Toast.makeText(this, "Password dan Konfirmasi Password Harus sama", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(this);
            db.createUser(username, email, pw, iyaRB.isChecked());

            Intent intent = new Intent(this, activity_adminpanel.class);
            startActivity(intent);
        }
    }
}