package com.example.sanabelalkhayr.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;

import java.util.ArrayList;


public class DonationsFragment extends Fragment {

    Context ctx;

    ArrayList<Donation> donations;
    RecyclerView mList;
    DonationsAdapter mAdapter;
    SearchView searchView;

    ArrayList<String> categoriesList;

    String selectedCategory;

    Spinner donationSpinner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public DonationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesList = new ArrayList<>();
        categoriesList.add("food");
        categoriesList.add("clothes");
        selectedCategory= categoriesList.get(0);

        mList = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.search);
        donationSpinner = view.findViewById(R.id.donations_spinner);
        setUpCategoryChooser();
        donations = new ArrayList<Donation>(){{
            add(new Donation(1, "burger", "good burger","food", null, 7));
            add(new Donation(1, "burger", "good burger","clothes", null, 7));
            add(new Donation(1, "burger", "good cheese","clothes", null, 7));
            add(new Donation(1, "burger", "good burger","food", null, 7));
            add(new Donation(1, "burger", "good cheese","food", null, 7));


        }};

        donationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoriesList.get(position);
                Log.e("selected cat", selectedCategory);
                mAdapter.getFilter().filter("" + ":" + selectedCategory);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAdapter = new DonationsAdapter(getContext(), donations, false);
        mList.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: setFilter
                Log.e("cat in filter", selectedCategory);
                mAdapter.getFilter().filter(newText + ":" + selectedCategory);
                return true;
            }
        });

    }

    private void setUpCategoryChooser() {
//        categoriesList = new ArrayList<>();
//        for(int i = 1; i <= 12; i++){
//            if(i%2 == 0){
//                categoriesList.add("food");
//            }
//
//        }

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationSpinner.setAdapter(categoriesAdapter);
    }
}