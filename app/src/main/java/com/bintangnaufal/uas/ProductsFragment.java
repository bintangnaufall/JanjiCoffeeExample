package com.bintangnaufal.uas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProductsFragment extends Fragment {

    private TextView tv;
    private LinearLayout  logoutIV;
    private FloatingActionButton buttonFAB;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    private LinearLayout noDataLinearLayout;
    private GoogleMap mMap;
//    private final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search.php?q=Janji+Jiwa&format=jsonv2";


    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        tv = view.findViewById(R.id.welcomeTV);
        logoutIV = view.findViewById(R.id.logoutIV);
        buttonFAB = view.findViewById(R.id.CrateProduct);
        noDataLinearLayout = view.findViewById(R.id.productEmpty);

        logoutIV.setOnClickListener(View -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("Apakah Anda yakin ingin keluar dari akun ini?");
            alertBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(getActivity(), activity_login.class);
                    startActivity(intent);
                    getActivity().finish();
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

        buttonFAB.setOnClickListener(View -> {
            Intent intent = new Intent(getActivity(), CreateProduct.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "Email tidak ditemukan");
        DatabaseHelper db = new DatabaseHelper(getActivity());
        String username = db.getUsername(email);


        if (username != null) {
            tv.setText("Selamat Datang, " + username + "!");
        }else {
            tv.setText("Selamat Datang!");
        }

        recyclerView = view.findViewById(R.id.productRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        productList = databaseHelper.getAllProducts();

        if (productList.isEmpty()) {
            noDataLinearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataLinearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);
        return view;

    }
}