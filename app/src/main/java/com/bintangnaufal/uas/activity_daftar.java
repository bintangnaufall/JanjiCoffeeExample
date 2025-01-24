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

public class activity_daftar extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText, UsernameET;
    private ImageView eyeIcon, eyeIconKonfirmasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        emailEditText = findViewById(R.id.emailET);
        passwordEditText = findViewById(R.id.passwordET);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordET);
        UsernameET = findViewById(R.id.UsernameET);

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


    public interface MailCallback {
        void onMailSent(String result);
    }

    private class SendMailTask extends AsyncTask<String, Void, String> {

        private MailCallback callback;

        public SendMailTask(MailCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String recipientEmail = params[0];
                String subject = params[1];
                String messageBody = params[2];

                MailSender mailSender = new MailSender();
                mailSender.sendMail(recipientEmail, subject, messageBody);
                return "Email berhasil dikirim!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Gagal mengirim email.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                callback.onMailSent(result); // Kirim hasil ke callback
            }
        }
    }

    public void masuk(View view) {
        onBackPressed();
    }

    public void daftar(View view) {
        String username = UsernameET.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        DatabaseHelper user = new DatabaseHelper(this);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Mohon lengkapi semua kolom yang tersedia.", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Harap pastikan password dan konfirmasi password Anda sama.", Toast.LENGTH_SHORT).show();
        }else if (user.isEmailRegistered(email)) {
            Toast.makeText(this, "Akun dengan email ini sudah terdaftar.", Toast.LENGTH_SHORT).show();
        }else {
//            DatabaseHelper databaseHelper = new DatabaseHelper(this);
//            databaseHelper.createUser(username, email, password, false);
            String otp = OTPGenerator.generateOTP(6);
            String subject = "Kode OTP Anda";
            String messageBody = "Kode OTP Anda adalah: " + otp;

            SharedPreferences sharedPreferences = getSharedPreferences("OTPSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("OTP", otp);
            editor.putString("USERNAME", username);
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.apply();

            new SendMailTask(result -> {
                if (result.equals("Email berhasil dikirim!")) {
                    Toast.makeText(this, "OTP terkirim ke email!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, autentifikasi_activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Gagal mengirim OTP.", Toast.LENGTH_SHORT).show();
                }
            }).execute(email, subject, messageBody);
        }
    }
}