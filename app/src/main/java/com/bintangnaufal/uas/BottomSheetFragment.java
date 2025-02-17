package com.bintangnaufal.uas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ambil data dari Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String namaMenu = bundle.getString("namaMenu");
            String hargaR = bundle.getString("hargaR");
            String hargaL = bundle.getString("hargaL");
            String desk = bundle.getString("desk");
            String imagePath = bundle.getString("imagePath");

            // Set ke UI
            TextView textNama = view.findViewById(R.id.produkName);
            TextView textHargaR = view.findViewById(R.id.priceR);
            TextView textHargaL = view.findViewById(R.id.priceL);
            TextView textDesk = view.findViewById(R.id.produkDesk);
            ImageView imageView = view.findViewById(R.id.produkImage);

            textNama.setText(namaMenu);
            textDesk.setText(desk);

            if (hargaR != null) {
                String displayedHargaR = formatHarga(hargaR);
                textHargaR.setText("R/" + displayedHargaR + "K");
                textHargaR.setVisibility(View.VISIBLE);
            } else {
                textHargaR.setVisibility(View.GONE);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) textHargaL.getLayoutParams();
                params.topMargin = 180;
                textHargaL.setLayoutParams(params);
            }

            if (hargaL != null) {
                String displayedHargaL = formatHarga(hargaL);
                textHargaL.setText("L/" + displayedHargaL + "K");
                textHargaL.setVisibility(View.VISIBLE);
            } else {
                textHargaL.setVisibility(View.INVISIBLE);
            }

            
            // Set image jika ada
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private String formatHarga(String harga) {
        int length = harga.length();
        if (length >= 6) {
            return harga.substring(0, 3); // 6 angka → ambil 3 digit pertama
        } else if (length == 5) {
            return harga.substring(0, 2); // 5 angka → ambil 2 digit pertama
        } else if (length == 4) {
            return harga.substring(0, 1); // 4 angka → ambil 1 digit pertama
        } else {
            return harga; // Jika kurang dari 4 angka, tampilkan apa adanya
        }
    }

}
