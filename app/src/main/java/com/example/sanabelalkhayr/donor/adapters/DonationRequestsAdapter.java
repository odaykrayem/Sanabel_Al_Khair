package com.example.sanabelalkhayr.donor.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.DonationOrder;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DonationRequestsAdapter extends RecyclerView.Adapter<DonationRequestsAdapter.ViewHolder>{


    Context context;
    private List<DonationOrder> donationOrders;
    private String message;
    private ProgressDialog pDialog;


    // RecyclerView recyclerView;
    public DonationRequestsAdapter(Context context, ArrayList<DonationOrder> donationOrders) {
        this.context = context;
        this.donationOrders = donationOrders;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_order_donor, parent, false);

        return new ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DonationOrder donationOrder = donationOrders.get(position);

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

        switch (donationOrder.getStatus()){
            case Constants.REQUEST_STATUS_ACCEPTED:
                holder.status.setTextColor(context.getResources().getColor(R.color.status_accepted));
                holder.status.setText(context.getResources().getString(R.string.status_accepted));
                break;

            case Constants.REQUEST_STATUS_REJECTED :
                holder.status.setTextColor(context.getResources().getColor(R.color.status_rejected));
                holder.status.setText(context.getResources().getString(R.string.status_rejected));
                break;
            default:
                holder.status.setTextColor(context.getResources().getColor(R.color.status_new));
                holder.status.setText(context.getResources().getString(R.string.status_new));
        }


        holder.date.setText(donationOrder.getCreatedAt());

        holder.title.setText(donationOrder.getDonation_title());

        holder.quantity.setText(String.valueOf(donationOrder.getQuantity()));

        holder.accept.setOnClickListener(v -> {
            message = "donation accepted";
            changeStatus(donationOrder, Constants.REQUEST_STATUS_ACCEPTED, message);
        });

        holder.reject.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.change_status_dialog, null);
            final AlertDialog message_dialog = new AlertDialog.Builder(context).create();
            message_dialog.setView(view);

            EditText reasonTv = view.findViewById(R.id.status);
            Button save = view.findViewById(R.id.save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String reason = reasonTv.getText().toString();
                    if(TextUtils.isEmpty(reason)){
                        reason = "no reason";
                    }
                    message = reason;
                    changeStatus(donationOrder, Constants.REQUEST_STATUS_REJECTED, message);
                    message_dialog.dismiss();
                }
            });
            message_dialog.show();
        });




    }

    private void changeStatus(DonationOrder donationOrder, int status, String message) {
        pDialog.show();
        String url = Urls.UPDATE_DONATION_REQUEST_STATUS;

        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", id)
                .addBodyParameter("donation_id", String.valueOf(donationOrder.getId()))
                .addBodyParameter("message", message)
                .addBodyParameter("status", String.valueOf(status))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        pDialog.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String dataGot = "Data Got";
                            //if no error in response
                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
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
                        Log.e("anError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("donation_id")) {
                                Toast.makeText(context, data.getJSONArray("donation_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("message")) {
                                Toast.makeText(context, data.getJSONArray("message").toString(), Toast.LENGTH_SHORT).show();
                            }if (data.has("status")) {
                                Toast.makeText(context, data.getJSONArray("status").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public int getItemCount() {
        return donationOrders.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView title;
        public TextView quantity;
        public TextView status;
        public Button accept;
        public Button reject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.title = itemView.findViewById(R.id.title);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.status = itemView.findViewById(R.id.response);
            this.accept = itemView.findViewById(R.id.accept);
            this.reject = itemView.findViewById(R.id.reject);
        }
    }





}