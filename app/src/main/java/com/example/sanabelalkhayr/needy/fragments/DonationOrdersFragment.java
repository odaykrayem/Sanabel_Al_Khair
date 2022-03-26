package com.example.sanabelalkhayr.needy.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.needy.adapters.DonationOrdersAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.DonationOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonationOrdersFragment extends Fragment {

    Context context;
    ArrayList<DonationOrder> donationOrders;
    RecyclerView mList;
    DonationOrdersAdapter mAdapter;
    ProgressDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DonationOrdersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donation_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);

        getOrders();
    }

    private void getOrders(){

        String url = Urls.GET_DONATION_ORDERS;
        donationOrders = new ArrayList<DonationOrder>();

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
                                    donationOrders.add(
                                            new DonationOrder(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    Integer.parseInt(jsonObject.getString("donation_id")),
                                                    jsonObject.getString("title"),
                                                    Integer.parseInt(jsonObject.getString("quantity")),
                                                    Integer.parseInt(jsonObject.getString("statue")),
                                                    jsonObject.getString("message"),
                                                    jsonObject.getString("created_at")
                                                    )
                                    );
                                }
                                mAdapter = new DonationOrdersAdapter(context, donationOrders, null);
                                mList.setAdapter(mAdapter);
                                pDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("catch", e.getMessage());
                            pDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Log.e("anError", error.getErrorBody());
                    }
                });

    }
}