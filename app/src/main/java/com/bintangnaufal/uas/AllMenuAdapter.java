package com.bintangnaufal.uas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;

    public AllMenuAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_allmenu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);

        Bitmap bitmap = BitmapFactory.decodeFile(product.getImagePath());
        holder.menuImage.setImageBitmap(bitmap);
        holder.menuName.setText(product.getNama());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuImage;
        TextView menuName;

        public ViewHolder(View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.imgMenu);
            menuName = itemView.findViewById(R.id.namaMenu);
        }
    }
}

