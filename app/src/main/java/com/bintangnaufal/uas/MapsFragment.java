package com.bintangnaufal.uas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private RecyclerView recyclerView;
    private List<User> userList;
    private LinearLayout noDataLinearLayout;
    private UserAdapter adapter;
    private FloatingActionButton buttonFAB;
    private GoogleMap mMap;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fetchLocationData();


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng lokasi1 = new LatLng(-0.0496068, 109.3266838);  // Koordinat Janji Jiwa Coffee Shop
        String nama1 = "Janji Jiwa Coffee Shop";

        LatLng lokasi2 = new LatLng(-0.0655267, 109.355293 );  // Koordinat Janji Jiwa Pontianak Paris 2
        String nama2 = "Janji Jiwa Pontianak Paris 2";

        mMap.addMarker(new MarkerOptions().position(lokasi1).title(nama1));
        mMap.addMarker(new MarkerOptions().position(lokasi2).title(nama2));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lokasi2, 15);  // Zoom level 15
        mMap.moveCamera(cameraUpdate);
    }

    private void fetchLocationData() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://nominatim.openstreetmap.org/search.php?q=Janji+Jiwa&limit=100&format=jsonv2";

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make an asynchronous request to fetch the data
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ActivityMaps", "Error fetching data: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    try {
                        // Parse the location data and add markers
                        parseLocationData(jsonResponse);
                    } catch (Exception e) {
                        Log.e("ActivityMaps", "Error parsing data: " + e.getMessage());
                    }
                } else {
                    Log.e("ActivityMaps", "Request failed with code: " + response.code());
                }
            }
        });
    }

    private void parseLocationData(String jsonResponse) {
        try {
            Gson gson = new Gson();
            activity_maps.Location[] locations = gson.fromJson(jsonResponse, activity_maps.Location[].class);

            LatLng selectedLocation = null;

            for (activity_maps.Location locationData : locations) {
                LatLng location = new LatLng(Double.parseDouble(locationData.lat), Double.parseDouble(locationData.lon));

                requireActivity().runOnUiThread(() -> {
                    if (mMap != null) {
                        mMap.addMarker(new MarkerOptions().position(location).title(locationData.display_name));
                    }
                });

                if (selectedLocation == null) {
                    selectedLocation = location;
                }
            }
        } catch (Exception e) {
            Log.e("ActivityMaps", "Error parsing location data: " + e.getMessage());
        }
    }

    public class Location {
        String display_name;
        String lat;
        String lon;
    }
}
