package com.bintangnaufal.uas;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UpdateProduct extends AppCompatActivity {

    private EditText namaProdukET, hargaRegularET, hargaLargeET, deskripsiET;
    private RadioButton iyaRb, tidakRb;
    private LinearLayout btnSelect, hargaRegularLLUpdate, hargaLargeLLUpdate;
    private CheckBox regularCBUpdate, largeCBUpdate;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    String id, NameProduk, HargaRegular, HargaLarge, Deskripsi, ImgPth, wkt;
    Boolean IsRekomended, IsRegular, IsLarge;
    private Button selectTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        namaProdukET = findViewById(R.id.namaProdukETUpdate);
        hargaRegularET = findViewById(R.id.hargaRegularETUpdate);
        hargaLargeET = findViewById(R.id.hargaLargeETUpdate);
        deskripsiET = findViewById(R.id.deskripsiETUpdate);
        selectTimeButton = findViewById(R.id.selectTimeButton);

        hargaRegularLLUpdate = findViewById(R.id.hargaRegularLLUpdate);
        hargaLargeLLUpdate = findViewById(R.id.hargaLargeLLUpdate);

        regularCBUpdate = findViewById(R.id.regularCBUpdate);
        largeCBUpdate = findViewById(R.id.largeCBUpdate);

        regularCBUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hargaRegularLLUpdate.setVisibility(View.VISIBLE);
            } else {
                hargaRegularLLUpdate.setVisibility(View.GONE);
            }
        });

        largeCBUpdate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                hargaLargeLLUpdate.setVisibility(View.VISIBLE);
            } else {
                hargaLargeLLUpdate.setVisibility(View.GONE);
            }
        });

        iyaRb = findViewById(R.id.iyaRBUpdate);
        tidakRb = findViewById(R.id.tidakRBUpdate);

        btnSelect = findViewById(R.id.btnChooseUpdate);
        imageView = findViewById(R.id.imgViewUpdate);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        NameProduk = intent.getStringExtra("NamaProduk");
        IsRekomended = intent.getBooleanExtra("Is_Rekomended",false);
        IsRegular = intent.getBooleanExtra("Is_Regular", false);
        IsLarge = intent.getBooleanExtra("Is_Large", false);
        HargaRegular = intent.getStringExtra("HargaRegular");
        HargaLarge = intent.getStringExtra("HargaLarge");
        Deskripsi = intent.getStringExtra("Deskripsi");
        ImgPth = intent.getStringExtra("ImgPth");
        wkt = intent.getStringExtra("WAKTU");

        selectTimeButton.setOnClickListener(v -> showTimePicker());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        selectTimeButton.setText(wkt);
        namaProdukET.setText(NameProduk);
        hargaRegularET.setText(HargaRegular);
        hargaLargeET.setText(HargaLarge);
        deskripsiET.setText(Deskripsi);

        if (IsRegular) {
            regularCBUpdate.setChecked(true);
            hargaRegularLLUpdate.setVisibility(View.VISIBLE);
        }

        if (IsLarge) {
            largeCBUpdate.setChecked(true);
            hargaLargeLLUpdate.setVisibility(View.VISIBLE);
        }

        if (IsRekomended) {
            iyaRb.setChecked(true);
        }else {
            tidakRb.setChecked(true);
        }

        iyaRb.setOnClickListener(View -> IsRekomended = true);
        tidakRb.setOnClickListener(View -> IsRekomended = false);

        Bitmap bitmap = BitmapFactory.decodeFile(ImgPth);
        imageView.setImageBitmap(bitmap);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });
    }

    private void showTimePicker() {
        String wkt = getIntent().getStringExtra("WAKTU");
        if (wkt != null && wkt.contains(":")) {
            String[] timeParts = wkt.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minuteOfHour) -> {
                        selectTimeButton.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour));
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        }
    }

    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
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

    private boolean deleteOldImage(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }
    private String updateImageToInternalStorage(Bitmap bitmap) {
        FileOutputStream fos = null;
        String imagePath = null;

        try {
            File directory = getFilesDir();

            String uniqueFileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(directory, uniqueFileName);

            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

            deleteOldImage(ImgPth);
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

    public void SaveEditProduct(View view) {
        String namaProduk = namaProdukET.getText().toString().trim();
        String hargaRegular = hargaRegularET.getText().toString().trim();
        String hargaLarge = hargaLargeET.getText().toString().trim();
        String deskripsi = deskripsiET.getText().toString().trim();
        String waktu = selectTimeButton.getText().toString().trim();


        if (regularCBUpdate.isChecked() && largeCBUpdate.isChecked()) {
            if (namaProduk.isEmpty() || hargaRegular.isEmpty() || hargaLarge.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked())) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if (regularCBUpdate.isChecked() && !largeCBUpdate.isChecked()) {
            if (namaProduk.isEmpty() || hargaRegular.isEmpty() || deskripsi.isEmpty() || (!iyaRb.isChecked() && !tidakRb.isChecked())) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (!regularCBUpdate.isChecked() && largeCBUpdate.isChecked()) {
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
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateProductWithoutPict(id, namaProduk, IsRekomended, regularCBUpdate.isChecked(), largeCBUpdate.isChecked(), hargaRegular, hargaLarge, deskripsi, waktu);

            Toast.makeText(this, "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show();

            namaProdukET.setText("");
            deskripsiET.setText("");

            Intent intent = new Intent(this, activity_adminpanel.class);
            startActivity(intent);
            finish();
        }else {
            Bitmap bitmap;
            String imagePath = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePath = updateImageToInternalStorage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateProduct(id, namaProduk, IsRekomended, regularCBUpdate.isChecked(), largeCBUpdate.isChecked(), hargaRegular, hargaLarge, deskripsi, imagePath, waktu);

            Toast.makeText(this, "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show();

            namaProdukET.setText("");
            deskripsiET.setText("");

            Intent intent = new Intent(this, activity_adminpanel.class);
            startActivity(intent);
            finish();
        }
    }
}