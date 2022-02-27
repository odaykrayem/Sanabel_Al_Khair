package com.example.sanabelalkhayr.fragments.volunteer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.donor.MyDonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddServiceFragment extends Fragment {

    Context ctx;

    SearchView searchView;
    ArrayList<Donation> donations;
    RecyclerView mList;
    MyDonationsAdapter mAdapter;

    FloatingActionButton mAddBtn;
    NavController navController;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }


    public AddServiceFragment() {
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
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}