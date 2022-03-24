package com.example.sanabelalkhayr.fragments.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.Donation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class DonationsFragment extends Fragment {

    Context context;
    RecyclerView mList;
    DonationsAdapter mAdapter;
    SearchView searchView;

    ArrayList<Donation> donationsList;
    ArrayList<String> categoriesList;
    String selectedCategory;

    AppCompatSpinner mCategoriesChooser;
    ProgressDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DonationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        mCategoriesChooser = view.findViewById(R.id.category_chooser);
        searchView = view.findViewById(R.id.search);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

//        donationsList = new ArrayList<Donation>(){{
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//            add(new Donation(1, "burger", "good burger", null,"food",  7, "peace maker"));
//        }};


        getDonations();
        getAllCategories();
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

        if (categoriesList == null || categoriesList.isEmpty()) {
            mCategoriesChooser.setEnabled(false);
        } else {
            selectedCategory = categoriesList.get(0);
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
    }

    //todo api call (get all categories)
    private void getAllCategories() {
        String url = Urls.GET_CATEGORIES;
        categoriesList = new ArrayList<String>();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(0);
                                categoriesList.add(
                                        obj.getString("name")
                                );
                            }
                            setUpCategoryChooser();
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //todo api call (get all donations)
    private void getDonations() {
        String url = Urls.GET_DONATIONS;
        donationsList = new ArrayList<Donation>();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(0);
                                donationsList.add(
                                        new Donation(
                                                Integer.parseInt(obj.getString("id")),
                                                obj.getString("title"),
                                                obj.getString("description"),
                                                obj.getString("image"),
                                                obj.getString("category"),
                                                Integer.parseInt(obj.getString("quantity")),
                                                obj.getString("donorUserName")
                                        )
                                );
                            }
                            mAdapter = new DonationsAdapter(context, donationsList, null);
                            mList.setAdapter(mAdapter);
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}