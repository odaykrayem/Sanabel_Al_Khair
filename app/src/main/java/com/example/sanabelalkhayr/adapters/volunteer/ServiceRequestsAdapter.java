package com.example.sanabelalkhayr.adapters.volunteer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestsAdapter extends RecyclerView.Adapter<ServiceRequestsAdapter.ViewHolder>{


    Context context;
    private List<ServiceRequest> serviceOrders;
    private String message;


    // RecyclerView recyclerView;
    public ServiceRequestsAdapter(Context context, ArrayList<ServiceRequest> serviceOrders) {
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

        ServiceRequest serviceOrder = serviceOrders.get(position);


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


        holder.date.setText(serviceOrder.getDate());

        holder.title.setText(serviceOrder.getDonationName());

        holder.user_name.setText(serviceOrder.getUserName());

        holder.accept.setOnClickListener(v -> {
            message = "donation accepted";
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

    private void changeStatus(ServiceRequest serviceRequest, int newStatus, String message) {

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