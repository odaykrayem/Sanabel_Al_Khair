package com.example.sanabelalkhayr.adapters.donor;

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
import com.example.sanabelalkhayr.model.DonationOrder;

import java.util.ArrayList;
import java.util.List;

public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.ViewHolder>{


    Context context;
    private List<DonationOrder> donationOrders;
    private String message;


    // RecyclerView recyclerView;
    public UserOrdersAdapter(Context context, ArrayList<DonationOrder> donationOrders) {
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