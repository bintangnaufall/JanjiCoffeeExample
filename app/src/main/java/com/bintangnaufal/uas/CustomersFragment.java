package com.bintangnaufal.uas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomersFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<User> userList;
    private LinearLayout noDataLinearLayout;
    private UserAdapter adapter;
    private FloatingActionButton buttonFAB;




    public CustomersFragment() {
        // Required empty public constructor
    }

    public static CustomersFragment newInstance() {
        CustomersFragment fragment = new CustomersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        buttonFAB = view.findViewById(R.id.CrateUser);
        noDataLinearLayout = view.findViewById(R.id.userEmpty);

        recyclerView = view.findViewById(R.id.userRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        userList = databaseHelper.getAllUsers();

        if (userList.isEmpty()) {
            noDataLinearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataLinearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new UserAdapter(getActivity(), userList);
        recyclerView.setAdapter(adapter);

        buttonFAB.setOnClickListener(View -> {
            Intent intent = new Intent(getActivity(), CreateUser.class);
            startActivity(intent);
        });

        return view;
    }
}
