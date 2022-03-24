package com.example.sanabelalkhayr.fragments.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.ServicesAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.Service;

import java.util.ArrayList;

public class ServicesFragment extends Fragment {

    Context ctx;

    ArrayList<Service> services;
    RecyclerView mList;
    ServicesAdapter mAdapter;
    SearchView searchView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        searchView = view.findViewById(R.id.search);
        getServices();
        services = new ArrayList<Service>(){{
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
            add(new Service(1, "burger", "good burger", "jaddah"));
        }};

        mAdapter = new ServicesAdapter(ctx, services);
        mList.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    }

    private void getServices() {
      String url = Urls.GET_SERVICES;
    }
}