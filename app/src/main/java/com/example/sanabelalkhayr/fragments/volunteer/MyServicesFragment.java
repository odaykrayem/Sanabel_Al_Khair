package com.example.sanabelalkhayr.fragments.volunteer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.donor.MyDonationsAdapter;
import com.example.sanabelalkhayr.adapters.volunteer.MyServicesAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyServicesFragment extends Fragment {

    SearchView searchView;
    ArrayList<Service> services;
    RecyclerView mList;
    MyServicesAdapter mAdapter;

    FloatingActionButton mAddBtn;
    NavController navController;

    Context ctx;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public MyServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.search);
        mAddBtn = view.findViewById(R.id.add_btn);

        services = new ArrayList<Service>(){{
           add(new Service(1, "new service", "ahsaa"));
           add(new Service(1, "new service", "ahsaa"));
           add(new Service(1, "my service", "ahsaa"));
           add(new Service(1, "order", "ahsaa"));

        }};

        mAdapter = new MyServicesAdapter(getContext(), services);
        mList.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });

        mAddBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_myServicesFragment_to_addServiceFragment);
        });

    }
}