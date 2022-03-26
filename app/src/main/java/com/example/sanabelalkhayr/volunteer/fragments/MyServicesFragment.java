package com.example.sanabelalkhayr.volunteer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.donor.adapters.MyDonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.volunteer.adapters.MyServicesAdapter;
import com.example.sanabelalkhayr.model.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyServicesFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{

    SearchView searchView;
    ArrayList<Service> services;
    RecyclerView mList;
    MyServicesAdapter mAdapter;

    FloatingActionButton mAddBtn;
    NavController navController;

    Context context;
    ProgressDialog pDialog;

    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public MyServicesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_services, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getMyServices();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.search);
        mAddBtn = view.findViewById(R.id.add_btn);

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);


        getMyServices();
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

    private void getMyServices() {
        String url = Urls.GET_VOLUNTEER_SERVICES_URL;
        services = new ArrayList<Service>();
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        pDialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .addQueryParameter("user_id", id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Got";
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.get_my_dnotion_success), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject donation = jsonObject.getJSONObject("donation");
                                    JSONObject region_data = donation.getJSONObject("region_data");
                                    String region = region_data.getString("name");
                                    services.add(
                                            new Service(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    jsonObject.getString("description"),
                                                    region
                                            )
                                    );
                                }
                                mAdapter = new MyServicesAdapter(getContext(), services);
                                mList.setAdapter(mAdapter);
                                pDialog.dismiss();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("catch", e.getMessage());
                            pDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("anError", error.getErrorBody());
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRefresh() {
        getMyServices();
    }
}