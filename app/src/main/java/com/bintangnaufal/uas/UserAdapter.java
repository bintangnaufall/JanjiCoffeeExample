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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getUsername());
        holder.userEmail.setText(user.getEmail());
        holder.userPassword.setText(user.getPassword());

        if (user.getIsAdmin()) {
            holder.userAdminLL.setVisibility(View.VISIBLE);
        }

        holder.updateUser.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateUser.class);
            intent.putExtra("ID", String.valueOf(user.getId()));
            intent.putExtra("Username", user.getUsername());
            intent.putExtra("Email", user.getEmail());
            intent.putExtra("Password", user.getPassword());
            intent.putExtra("IS_ADMIN", user.getIsAdmin());
            context.startActivity(intent);
        });

        holder.deleteUser.setOnClickListener(view -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage(Html.fromHtml("Apakah Anda yakin ingin menghapus Akun <b>"
                    + user.getUsername() + "</b>? Tindakan ini tidak dapat dibatalkan."));
            alertBuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    databaseHelper.deleteUser(String.valueOf(user.getId()));
                    userList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, userList.size());

                    if (userList.isEmpty()) {
                        ((Activity) context).findViewById(R.id.productEmpty).setVisibility(View.VISIBLE);
                    } else {
                        ((Activity) context).findViewById(R.id.productEmpty).setVisibility(View.GONE);
                    }

                    Toast.makeText(context, "Akun " + user.getUsername() + " berhasil dihapus.", Toast.LENGTH_LONG).show();
                }
            });
            alertBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(context, "Tindakan dibatalkan.", Toast.LENGTH_LONG).show();
                }
            });
            alertBuilder.show();
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userPassword;
        LinearLayout updateUser, deleteUser, userAdminLL;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.Username);
            userEmail = itemView.findViewById(R.id.emailUser);
            userPassword = itemView.findViewById(R.id.passwordUser);

            updateUser = itemView.findViewById(R.id.updateUser);
            deleteUser = itemView.findViewById(R.id.deleteUser);
            userAdminLL = itemView.findViewById(R.id.userAdminLL);
        }
    }
}

