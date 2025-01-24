package com.bintangnaufal.uas;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class CreateProduct extends AppCompatActivity {

    private EditText namaProdukET, hargaRegularET, hargaLargeET, deskripsiET;
    private LinearLayout hargaRegularLL, hargaLargeLL;
    private LinearLayout btnSelect;
    private ImageView imageView;
    private Uri filePath;
    private RadioButton iyaRb, tidakRb;
    private Boolean isRekomen;
    private CheckBox regularCB, largeCB;
    private TextView timeLabel;
    private Button selectTimeButton;

    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        namaProdukET = findViewById(R.id.namaProdukET);
        deskripsiET = findViewById(R.id.deskripsiET);
        hargaRegularET = findViewById(R.id.hargaRegularET);
        hargaLargeET = findViewById(R.id.hargaLargeET);

        regularCB = findViewById(R.id.regularCB);
        hargaRegularLL = findViewById(R.id.hargaRegularLL);
        largeCB = findViewById(R.id.largeCB);
        hargaLargeLL = findViewById(R.id.hargaLargeLL);
        selectTimeButton = findViewById(R.id.selectTimeButton);

        selectTimeButton.setOnClickListener(v -> showTimePicker());

        regularCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hargaRegularLL.setVisibility(View.VISIBLE);
            } else {
                hargaRegularLL.setVisibility(View.GONE);
            }
        });

        largeCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hargaLargeLL.setVisibility(View.VISIBLE);
            } else {
                hargaLargeLL.setVisibility(View.GONE);
            }
        });

        iyaRb = findViewById(R.id.iyaRB);
        tidakRb = findViewById(R.id.tidakRB);

        iyaRb.setOnClickListener(View -> isRekomen = true);
        tidakRb.setOnClickListener(View -> isRekomen = false);

        btnSelect = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgView);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfHour) -> {
                    selectTimeButton.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour));
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String saveImageToInternalStorage(Bitmap bitmap) {
        FileOutputStream fos = null;
        String imagePath = null;

        try {
            File directory = getFilesDir();

            String uniqueFileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(directory, uniqueFileName);

            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

            imagePath = file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imagePath;
    }

    public void SaveProduct(View view) {
        String namaProduk = namaProdukET.getText().toString().trim();
        String hargaRegular = hargaRegularET.getText().toString().trim();
        String hargaLarge = hargaLargeET.getText().toString().trim();
        String deskripsi = deskripsiET.getText().toString().trim();
        String waktu = selectTimeButton.getText().toString().trim();

        if (regularCB.isChecked() && largeCB.isChecked()) {
            if (namaProduk.isEmpty() || hargaRegular.isEmpty() || hargaLarge.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked())) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if (regularCB.isChecked() && !largeCB.isChecked()) {
            if (namaProduk.isEmpty() || hargaRegular.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked())) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (!regularCB.isChecked() && largeCB.isChecked()) {
            if (namaProduk.isEmpty() || hargaLarge.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked())) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (namaProduk.isEmpty() || hargaRegular.isEmpty() || hargaLarge.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked()) || waktu.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (filePath == null) {
            Toast.makeText(this, "Silakan pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap;
        String imagePath = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            imagePath = saveImageToInternalStorage(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Boolean result = databaseHelper.createProduct(namaProduk, isRekomen, regularCB.isChecked(), largeCB.isChecked(), hargaRegular, hargaLarge, deskripsi, imagePath, waktu);

        if (result) {
            Toast.makeText(this, "Produk berhasil disimpan!", Toast.LENGTH_SHORT).show();

            namaProdukET.setText("");
            hargaRegularET.setText("");
            hargaLargeET.setText("");
            deskripsiET.setText("");

            Intent intent = new Intent(this, activity_adminpanel.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan produk", Toast.LENGTH_LONG).show();
        }
    }
}