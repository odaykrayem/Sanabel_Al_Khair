package com.example.sanabelalkhayr.fragments.donor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.donor.MyDonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MyDonationsFragment extends Fragment {

    Context ctx;

    SearchView searchView;
    ArrayList<Donation> donations;
    RecyclerView mList;
    MyDonationsAdapter mAdapter;

    FloatingActionButton mAddBtn;
    NavController navController;

    Context context;

    public MyDonationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_donations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.search);
        mAddBtn = view.findViewById(R.id.add_btn);

        donations = new ArrayList<Donation>(){{
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "pizza", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "cloths", "good burger", null, "cloth", 7, "peace maker"));
            add(new Donation(1, "sandals", "good burger", null, "cloth", 7, "peace maker"));
            add(new Donation(1, "cheese", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null, "food", 7, "peace maker"));


        }};

        mAdapter = new MyDonationsAdapter(getContext(), donations);
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
            navController.navigate(R.id.action_myDonationsFragment_to_addDonationFragment);
        });

    }
}