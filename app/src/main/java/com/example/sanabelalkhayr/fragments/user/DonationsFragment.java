package com.example.sanabelalkhayr.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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

    AppCompatSpinner mCategoriesChooser;

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


        mList = view.findViewById(R.id.rv);
        mCategoriesChooser = view.findViewById(R.id.category_chooser);
        searchView = view.findViewById(R.id.search);

        setUpCategoryChooser();

        donations = new ArrayList<Donation>(){{
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
        }};



        mAdapter = new DonationsAdapter(ctx, donations, null);

        mList.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText + ":" + selectedCategory);
                return true;
            }
        });

    }

    private void setUpCategoryChooser() {

        //get and show the categories data
        getAllCategories();

        categoriesList = new ArrayList<>();
        categoriesList.add("food");
        categoriesList.add("clothes");
        selectedCategory= categoriesList.get(0);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategoriesChooser.setAdapter(categoriesAdapter);

        //set on category chosen listener
        mCategoriesChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoriesList.get(position);
                mAdapter.getFilter().filter("" + ":" + selectedCategory);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //todo api call (get all categories)
    private void getAllCategories() {

    }
}