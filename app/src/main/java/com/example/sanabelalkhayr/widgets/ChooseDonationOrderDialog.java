package com.example.sanabelalkhayr.widgets;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.needy.adapters.DonationOrdersAdapter;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChooseDonationOrderDialog extends Dialog implements DonationOrdersAdapter.OnDonationOrderSelected{

        private static final String DIALOG_PROFILE_TAG =  "dialog_choose_from_orders_tag";
        Context context;

        ArrayList<DonationOrder> donationOrders;
        RecyclerView mList;
        DonationOrdersAdapter mAdapter;

        ProgressDialog pDialog;

        int selectedDonationId;
        int serviceId;

    public ChooseDonationOrderDialog(@NonNull Context context, int serviceId) {
        super(context);
        this.context = context;
        this.serviceId = serviceId;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_donations);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);

        mList = findViewById(R.id.rv);

        getAllAcceptedDonations();

    }

    @Override
    public void onDonationOrderSelected(int selectedDonationOrderId) {
        Log.e("selected donation id = " ,String.valueOf(selectedDonationOrderId));

        String url = Urls.ORDER_SERVICE_URL;
        int userId = SharedPrefManager.getInstance(context).getUserId();
        pDialog.show();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("service_id", String.valueOf(serviceId))
                //it is in api like this but it refer to donation_order_id
                .addBodyParameter("donation_id", String.valueOf(selectedDonationOrderId))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("catch", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Log.e("donaAdapter", anError.getErrorBody());
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("service_id")) {
                                Toast.makeText(context, data.getJSONArray("service_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("donation_id")) {
                                Toast.makeText(context, data.getJSONArray("donation_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    void getAllAcceptedDonations(){
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
//                                    JSONObject category_data = jsonObject.getJSONObject("category_data");
//                                    String category = category_data.getString("name");
//                                    JSONObject region_data = jsonObject.getJSONObject("region_data");
//                                    String region = region_data.getString("name");
                                    int stat = Integer.parseInt(jsonObject.getString("statue"));
                                    if(stat == Constants.REQUEST_STATUS_ACCEPTED){
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

                                }
                                pDialog.dismiss();
                                mAdapter = new DonationOrdersAdapter(getContext(), donationOrders, ChooseDonationOrderDialog.this);
                                mList.setAdapter(mAdapter);
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