package com.example.sanabelalkhayr.needy.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.needy.adapters.DonationsAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.Donation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class DonationsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Context context;
    RecyclerView mList;
    DonationsAdapter mAdapter;
    SearchView searchView;

    ArrayList<Donation> donationsList;
    ArrayList<String> categoriesList;
    String selectedCategory;

    AppCompatSpinner mCategoriesChooser;
    ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DonationsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_donations, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(() -> {
//            mSwipeRefreshLayout.setRefreshing(true);
            getDonations();
        });
        return view;
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

        getAllCategories();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter != null){
                    mAdapter.getFilter().filter(newText + ":" + selectedCategory);

                }
                return true;
            }
        });
    }

    private void setUpCategoryChooser() {

        if (categoriesList == null || categoriesList.isEmpty()) {
            mCategoriesChooser.setEnabled(false);
        } else {
            categoriesList.add(0,"all");
            selectedCategory = categoriesList.get(0);
            ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
            categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCategoriesChooser.setAdapter(categoriesAdapter);

            //set on category chosen listener
            mCategoriesChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCategory = categoriesList.get(position);
                    if (mAdapter != null) {
                        mAdapter.getFilter().filter("" + ":" + selectedCategory);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void getAllCategories() {
        String url = Urls.GET_CATEGORIES;
        categoriesList = new ArrayList<String>();
        pDialog.show();

        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Got";
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_success), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    categoriesList.add(
                                            jsonObject.getString("name")
                                    );
                                }
                                setUpCategoryChooser();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_error), Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("anError", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Log.e("anError", error.getErrorBody());
                    }
                });
    }

    private void getDonations() {
        String url = Urls.GET_DONATIONS;
        donationsList = new ArrayList<Donation>();

        pDialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Got";
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_success), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject category_data = jsonObject.getJSONObject("category_data");
                                    String category = category_data.getString("name");
                                    JSONObject region_data = jsonObject.getJSONObject("region_data");
                                    String region = region_data.getString("name");
                                    donationsList.add(
                                            new Donation(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    jsonObject.getString("title"),
                                                    jsonObject.getString("description"),
                                                    category,
                                                    Urls.IMAGE_BASE_URL+jsonObject.getString("image"),
                                                    jsonObject.getInt("quantity"),
                                                    jsonObject.getString("donor_user_name"),
                                                    region
                                            )
                                    );
                                }
                                mAdapter = new DonationsAdapter(context, donationsList);
                                mList.setAdapter(mAdapter);
                                mSwipeRefreshLayout.setRefreshing(false);
                                pDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.e("catch", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("anError", error.getErrorBody());
                    }
                });
    }

    @Override
    public void onRefresh() {
        getDonations();
    }
}