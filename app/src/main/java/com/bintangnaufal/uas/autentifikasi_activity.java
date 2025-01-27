package com.bintangnaufal.uas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class autentifikasi_activity extends AppCompatActivity {

    private TextView KU, em;
    private EditText inpVerif;
    private String otp;
    private SharedPreferences sharedPreferences;

    private boolean isCountingDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentifikasi);
        KU = findViewById(R.id.kirimUlang);
        em = findViewById(R.id.email);
        inpVerif = findViewById(R.id.inputVerifikasi);

        SharedPreferences sharedPreferences = getSharedPreferences("OTPSession", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("EMAIL", null);
        em.setText("Email : " + email);
    }

    public void verifikasi(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("OTPSession", Context.MODE_PRIVATE);
        otp = sharedPreferences.getString("OTP", "OTP tidak ada");
        String username = sharedPreferences.getString("USERNAME", null);
        String email = sharedPreferences.getString("EMAIL", null);
        String pw = sharedPreferences.getString("PASSWORD", null);
        String verif = inpVerif.getText().toString().trim();
        if (verif.isEmpty()) {
            Toast.makeText(this, "Kode OTP belum diisi. Silakan masukkan kode OTP Anda.", Toast.LENGTH_SHORT).show();
        }else if (!verif.equals(otp)){
            Toast.makeText(this, "Kode OTP salah. Silakan periksa kembali.", Toast.LENGTH_SHORT).show();
        } else if (verif.equals(otp)) {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.createUser( username, email, pw,false);

            sharedPreferences = getSharedPreferences("OTPSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }
    }

    public void KirimUlang(View view) {
        if (isCountingDown) return;

        isCountingDown = true;
        KU.setTextColor(ContextCompat.getColor(this, R.color.gray));
        KU.setEnabled(false);

        new CountDownTimer(90000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                KU.setText("Kirim Ulang (" + millisUntilFinished / 1000 + ")");
            }
            @Override
            public void onFinish() {
                KU.setText("Kirim Ulang");
                KU.setTextColor(ContextCompat.getColor(autentifikasi_activity.this, R.color.text));
                KU.setEnabled(true);
                isCountingDown = false;
            }
        }.start();

        sharedPreferences = getSharedPreferences("OTPSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("OTP");
        editor.apply();

        otp = OTPGenerator.generateOTP(6);
        String subject = "Kode OTP Anda";
        String messageBody = "Kode OTP Anda adalah: " + otp;

        editor.putString("OTP", otp);
        editor.apply();

        String email = sharedPreferences.getString("EMAIL", "Email tidak ditemukan");

        new autentifikasi_activity.SendMailTask(result -> {
            if (result.equals("Email berhasil dikirim!")) {
                Toast.makeText(this, "OTP terkirim ke email!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, autentifikasi_activity.class);
//                startActivity(intent);
            } else {
                Toast.makeText(this, "Gagal mengirim OTP.", Toast.LENGTH_SHORT).show();
            }
        }).execute(email, subject, messageBody);
    }

    public interface MailCallback {
        void onMailSent(String result);
    }

    private class SendMailTask extends AsyncTask<String, Void, String> {

        private autentifikasi_activity.MailCallback callback;

        public SendMailTask(autentifikasi_activity.MailCallback callback) {
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
                callback.onMailSent(result);
            }
        }
    }
}