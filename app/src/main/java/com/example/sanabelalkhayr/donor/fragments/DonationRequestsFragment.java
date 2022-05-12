package com.example.sanabelalkhayr.donor.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.sanabelalkhayr.donor.adapters.DonationRequestsAdapter;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonationRequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;

    ArrayList<DonationOrder> donationOrders;
    RecyclerView mList;
    DonationRequestsAdapter mAdapter;

    ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DonationRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_requests, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getDonationRequests();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
    }

    private void getDonationRequests() {
        String url = Urls.GET_ORDERS_REQUESTS;
        donationOrders = new ArrayList<DonationOrder>();
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
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
                                    JSONObject donation_data = jsonObject.getJSONObject("donation");
                                    donationOrders.add(
                                            new DonationOrder(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    "username",
                                                    donation_data.getString("title"),
                                                    Integer.parseInt(jsonObject.getString("quantity")),
                                                    Integer.parseInt(jsonObject.getString("status")),
                                                    jsonObject.getString("message"),
                                                    jsonObject.getString("created_at")
                                            )
                                    );
                                }
                                mAdapter = new DonationRequestsAdapter(context, donationOrders);
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
        getDonationRequests();
    }
}