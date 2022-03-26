package com.example.sanabelalkhayr.volunteer.adapters;

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
import com.androidnetworking.model.Progress;
import com.example.sanabelalkhayr.model.ServiceOrder;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestsAdapter extends RecyclerView.Adapter<ServiceRequestsAdapter.ViewHolder>{

    Context context;
    private List<ServiceOrder> serviceOrders;
    private String message;
    ProgressDialog pDialog;

    public ServiceRequestsAdapter(Context context, ArrayList<ServiceOrder> serviceOrders) {
        this.context = context;
        this.serviceOrders = serviceOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_service_request, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ServiceOrder serviceOrder = serviceOrders.get(position);

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

        switch (serviceOrder.getStatus()){
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

        holder.date.setText(serviceOrder.getCreatedAt());

        holder.title.setText(serviceOrder.getDonation_title());

        holder.user_name.setText(serviceOrder.getVolunteer_name());

        holder.accept.setOnClickListener(v -> {
            message = "Service accepted";
            changeStatus(serviceOrder, Constants.REQUEST_STATUS_ACCEPTED, message);
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
                    changeStatus(serviceOrder, Constants.REQUEST_STATUS_REJECTED, message);
                    message_dialog.dismiss();
                }
            });
            message_dialog.show();
        });
    }

    private void changeStatus(ServiceOrder serviceRequest, int newStatus, String message) {
        pDialog.show();
        String url = Urls.UPDATE_SERVICE_REQUEST_STATUS;

        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", id)
                .addBodyParameter("service_id", String.valueOf(serviceRequest.getId()))
                .addBodyParameter("message", message)
                .addBodyParameter("status", String.valueOf(newStatus))
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
        return serviceOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView title;
        public TextView status;
        public TextView user_name;
        public Button accept;
        public Button reject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.status = itemView.findViewById(R.id.status);
            this.date = itemView.findViewById(R.id.request_date);
            this.title = itemView.findViewById(R.id.donation_title);
            this.user_name = itemView.findViewById(R.id.user_name);
            this.accept = itemView.findViewById(R.id.accept);
            this.reject = itemView.findViewById(R.id.reject);
        }
    }
}