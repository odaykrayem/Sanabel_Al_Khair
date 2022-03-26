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
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.needy.adapters.ServicesAdapter;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.needy.adapters.ServiceOrdersAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.ServiceOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceOrdersFragment extends Fragment {

    Context context;
    ArrayList<ServiceOrder> list;
    RecyclerView mList;
    ServiceOrdersAdapter mAdapter;

    ProgressDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ServiceOrdersFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);

        getServiceOrders();

    }

    private void getServiceOrders(){
        String url = Urls.GET_SERVICE_ORDERS;
        list = new ArrayList<ServiceOrder>();

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
//                                    JSONObject category_data = jsonObject.getJSONObject("category_data");
//                                    String category = category_data.getString("name");
                                    JSONObject region_data = jsonObject.getJSONObject("region_data");
                                    String region = region_data.getString("name");
                                    list.add(
                                            new ServiceOrder(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    jsonObject.getString("donation_title"),
                                                    jsonObject.getString("volunteer_name"),
                                                    Integer.parseInt(jsonObject.getString("status")),
                                                    jsonObject.getString("message"),
                                                    jsonObject.getString("created_at")
                                            )
                                    );
                                }
                                mAdapter = new ServiceOrdersAdapter(context, list);
                                mList.setAdapter(mAdapter);
                                pDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("catch", e.getMessage());
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