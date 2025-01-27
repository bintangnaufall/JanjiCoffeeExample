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

//        String hargaRegularStr = product.getHargaRegular();
//        int hargaR = 0;
//        String hargaFormattedR = "";
//
//        if (hargaRegularStr != null) {
//            hargaR = Integer.parseInt(hargaRegularStr);
//            if (hargaR >= 1000) {
//                hargaFormattedR = (hargaR / 1000) + "k"; // Format ribuan
//            } else {
//                hargaFormattedR = String.valueOf(hargaR); // Menampilkan harga biasa
//            }
//        }

//        String hargaLargeStr = product.getHargaLarge();
//        int hargaL = 0;
//        String hargaFormattedL = "";
//
//        if (hargaLargeStr != null) {
//            hargaL = Integer.parseInt(hargaLargeStr);
//            if (hargaL >= 1000) {
//                hargaFormattedL = (hargaL / 1000) + "k"; // Format ribuan
//            } else {
//                hargaFormattedL = String.valueOf(hargaL); // Menampilkan harga biasa
//            }
//        }



//        if (hargaRegularStr != null && hargaLargeStr != null) {
//            holder.productHarga.setText(hargaFormattedR + "-" + hargaFormattedL); // Menampilkan kedua harga jika ada
//        } else if (hargaRegularStr != null) {
//            holder.productHarga.setText(hargaFormattedR); // Menampilkan hanya harga regular
//        } else if (hargaLargeStr != null) {
//            holder.productHarga.setText(hargaFormattedL); // Menampilkan hanya harga large
//        } else {
//            holder.productHarga.setText(""); // Jika kedua harga null
//        }

//        String reg = product.getIsRegular() ? "R" : null;
//        String lar = product.getIsLarge() ? "L" : null;
//        if (reg != null & lar != null) {
//            holder.productUkuran.setText(reg + "&" + lar);
//        }else if (reg != null & lar == null) {
//            holder.productUkuran.setText(reg);
//        } else if (reg == null & lar != null) {
//            holder.productUkuran.setText(lar);
//        }
//        if (product.getIsRokemended()) {
//            holder.productRekommended.setVisibility(View.VISIBLE);
//        }
//        holder.productDesk.setText(product.getDeskripsi());
//        holder.updateProduk.setOnClickListener(view -> {
//            Intent intent = new Intent(context, UpdateProduct.class);
//            intent.putExtra("ID", String.valueOf(product.getId()));
//            intent.putExtra("NamaProduk", product.getNama());
//            intent.putExtra("Is_Rekomended", product.getIsRokemended());
//            intent.putExtra("Is_Regular", product.getIsRegular());
//            intent.putExtra("Is_Large", product.getIsLarge());
//            intent.putExtra("HargaRegular", product.getHargaRegular());
//            intent.putExtra("HargaLarge", product.getHargaLarge());
//            intent.putExtra("Deskripsi", product.getDeskripsi());
//            intent.putExtra("ImgPth", product.getImagePath());
//            intent.putExtra("WAKTU", product.getWaktu());
//            context.startActivity(intent);
//        });

//        holder.deleteProduk.setOnClickListener(view -> {
//            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//            alertBuilder.setTitle("Warning");
//            alertBuilder.setMessage(Html.fromHtml("Apakah Anda yakin ingin menghapus produk <b>"
//                    + product.getNama() + "</b>? Tindakan ini tidak dapat dibatalkan."));
//            alertBuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
//                    databaseHelper.deleteProduct(String.valueOf(product.getId()), product.getImagePath());
//                    productList.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, productList.size());
//
//                    if (productList.isEmpty()) {
//                        ((Activity) context).findViewById(R.id.productEmpty).setVisibility(View.VISIBLE);
//                    } else {
//                        ((Activity) context).findViewById(R.id.productEmpty).setVisibility(View.GONE);
//                    }
//
//                    Toast.makeText(context, "Produk " + product.getNama() + " berhasil dihapus.", Toast.LENGTH_LONG).show();
//                }
//            });
//            alertBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
////                    Toast.makeText(context, "Tindakan dibatalkan.", Toast.LENGTH_LONG).show();
//                }
//            });
//            alertBuilder.show();
//        });

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

