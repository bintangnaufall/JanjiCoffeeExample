package com.bintangnaufal.uas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;

    public MenuAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);

        Bitmap bitmap = BitmapFactory.decodeFile(product.getImagePath());
        holder.menuImage.setImageBitmap(bitmap);
        holder.menuName.setText(product.getNama());

        holder.detail.setOnClickListener(v -> {
            showBottomSheet(product);
        });
    }

    private void showBottomSheet(Product product) {
        BottomSheetFragment bottomSheet = new BottomSheetFragment();

        // Kirim data menggunakan Bundle
        Bundle bundle = new Bundle();
        bundle.putString("namaMenu", product.getNama());
        bundle.putString("hargaR", product.getHargaRegular());
        bundle.putString("hargaL", product.getHargaLarge());
        bundle.putString("desk", product.getDeskripsi());
        bundle.putString("imagePath", product.getImagePath());

        bottomSheet.setArguments(bundle);
        bottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuImage;
        TextView menuName;
        Button detail;

        public ViewHolder(View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.imgMenu);
            menuName = itemView.findViewById(R.id.namaMenu);
            detail = itemView.findViewById(R.id.addToCart);
        }
    }
}

