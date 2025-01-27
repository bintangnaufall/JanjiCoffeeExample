package com.bintangnaufal.uas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JanjiJiwa.db";
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_PRODUCTS = "Products";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, EMAIL TEXT, PASSWORD TEXT, IS_ADMIN BOOLEAN)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTS +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NamaProduk TEXT,is_Rokomended BOOLEAN, is_Regular BOOLEAN, is_Large BOOLEAN,HargaRegular TEXT, HargaLarge TEXT,Deskripsi TEXT, ImagePath TEXT, Waktu TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public boolean createUser(String username,String email, String password, Boolean is_admin) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);
        contentValues.put("IS_ADMIN", is_admin);
        long result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query untuk mendapatkan data dengan urutan ID dari yang terbesar ke terkecil
        String query = "SELECT * FROM users ORDER BY id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setIsAdmin(cursor.getInt(4) == 1);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public boolean isValidUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE EMAIL = ? AND PASSWORD = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email, password});

        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        sqLiteDatabase.close();

        return exists;
    }

    public boolean isValidUserAdmin(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE EMAIL = ? AND PASSWORD = ? AND IS_ADMIN = 1";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email, password});

        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        sqLiteDatabase.close();

        return exists;
    }

    public String getUsername(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT USERNAME FROM " + TABLE_USERS + " WHERE EMAIL = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});

        String username = null;
        if (cursor.moveToFirst()) { // Jika data ditemukan
            username = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME")); // Ambil kolom USERNAME
        }

        cursor.close();
        sqLiteDatabase.close();

        return username; // Null jika tidak ditemukan
    }
    public Boolean isAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "SELECT IS_ADMIN FROM " + TABLE_USERS + " WHERE EMAIL = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});

        Boolean is_admin = false; // Nilai default
        if (cursor.moveToFirst()) { // Jika data ditemukan
            is_admin = cursor.getInt(cursor.getColumnIndexOrThrow("IS_ADMIN")) == 1;
        }

        cursor.close();
        sqLiteDatabase.close();

        return is_admin;
    }



    public boolean isEmailRegistered (String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE EMAIL = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});

        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        sqLiteDatabase.close();

        return exists;
    }

    public void updateUser(String id, String usernmae, String email, String password, Boolean isadmin) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("USERNAME", usernmae);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);
        contentValues.put("IS_ADMIN", isadmin);
        sqLiteDatabase.update(TABLE_USERS, contentValues, "ID=?", new String[] {id});
    }

    public void deleteUser(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_USERS, "ID=?", new String[] {id});
    }

    public boolean createProduct(String nama, Boolean rekomended, Boolean regular, Boolean large, String hargaregular, String hargelarge, String deskripsi, String imgPth, String waktu) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NamaProduk", nama);
        contentValues.put("is_Rokomended", rekomended);
        contentValues.put("is_Regular", regular);
        contentValues.put("is_Large", large);
        contentValues.put("HargaRegular", regular ? hargaregular : null);
        contentValues.put("HargaLarge", large ? hargelarge : null);
        contentValues.put("Deskripsi", deskripsi);
        contentValues.put("ImagePath", imgPth);
        contentValues.put("Waktu", waktu);
        long result = sqLiteDatabase.insert(TABLE_PRODUCTS, null, contentValues);
        return result != -1;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query untuk mendapatkan data dengan urutan ID dari yang terbesar ke terkecil
        String query = "SELECT * FROM products ORDER BY id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setNama(cursor.getString(1));
                product.setIsRecommended(cursor.getInt(2) == 1);
                product.setIsRegular(cursor.getInt(3) == 1);
                product.setisIsLarge(cursor.getInt(4) == 1);
                product.setHargaRegular(cursor.getString(5));
                product.setHargaLarge(cursor.getString(6));
                product.setDeskripsi(cursor.getString(7));
                product.setImagePath(cursor.getString(8));
                product.setWaktu(cursor.getString(9));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

    public List<Product> getAllProductsRecommended() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query untuk mendapatkan data produk yang direkomendasikan
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE is_Rokomended = 1 ORDER BY id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                product.setNama(cursor.getString(cursor.getColumnIndexOrThrow("NamaProduk")));
                product.setIsRecommended(cursor.getInt(cursor.getColumnIndexOrThrow("is_Rokomended")) == 1);
                product.setIsRegular(cursor.getInt(cursor.getColumnIndexOrThrow("is_Regular")) == 1);
                product.setisIsLarge(cursor.getInt(cursor.getColumnIndexOrThrow("is_Large")) == 1);
                product.setHargaRegular(cursor.getString(cursor.getColumnIndexOrThrow("HargaRegular")));
                product.setHargaLarge(cursor.getString(cursor.getColumnIndexOrThrow("HargaLarge")));
                product.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow("Deskripsi")));
                product.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow("ImagePath")));
                product.setWaktu(cursor.getString(cursor.getColumnIndexOrThrow("Waktu")));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }


    public void updateProduct(String id, String nama, Boolean rekomended, Boolean regular, Boolean large, String hargaregular, String hargelarge, String deskripsi, String imgPth, String waktu) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NamaProduk", nama);
        contentValues.put("is_Rokomended", rekomended);
        contentValues.put("is_Regular", regular);
        contentValues.put("is_Large", large);
        contentValues.put("HargaRegular", regular ? hargaregular : null);
        contentValues.put("HargaLarge", large ? hargelarge : null);
        contentValues.put("Deskripsi", deskripsi);
        contentValues.put("ImagePath", imgPth);
        contentValues.put("Waktu", waktu);
        sqLiteDatabase.update(TABLE_PRODUCTS, contentValues, "ID=?", new String[]{id});
    }

    public void updateProductWithoutPict(String id, String nama, Boolean rekomended, Boolean regular, Boolean large, String hargaregular, String hargelarge, String deskripsi, String waktu) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NamaProduk", nama);
        contentValues.put("is_Rokomended", rekomended);
        contentValues.put("is_Regular", regular);
        contentValues.put("is_Large", large);
        contentValues.put("HargaRegular", regular ? hargaregular : null);
        contentValues.put("HargaLarge", large ? hargelarge : null);
        contentValues.put("Deskripsi", deskripsi);
        contentValues.put("Waktu", waktu);
        sqLiteDatabase.update(TABLE_PRODUCTS, contentValues, "ID=?", new String[]{id});
    }

    public void deleteProduct(String id, String ImgPth) {
        if (ImgPth != null) {
            File file = new File(ImgPth);
            if (file.exists()) {
                file.delete();
            }
        }
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_PRODUCTS, "ID=?", new String[] {id});
    }
}
