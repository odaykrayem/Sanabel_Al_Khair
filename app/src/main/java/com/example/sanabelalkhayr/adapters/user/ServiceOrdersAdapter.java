package com.example.sanabelalkhayr.adapters.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.Order;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrdersAdapter extends RecyclerView.Adapter<ServiceOrdersAdapter.ViewHolder>{


    Context context;
    private List<Order> orders;
    public NavController navController;


    // RecyclerView recyclerView;
    public ServiceOrdersAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Order order = orders.get(position);


        switch (order.getStatus()){
//            case Constants.REQUEST_STATUS_NEW :
//                holder.status.setTextColor(context.getResources().getColor(R.color.status_new));
//                holder.status.setText(context.getResources().getString(R.string.status_new));
//                break;
            case Constants.REQUEST_STATUS_ACCEPTED:
                holder.status.setTextColor(context.getResources().getColor(R.color.status_accepted));
                holder.status.setText(context.getResources().getString(R.string.status_accepted));
                break;
//            case Constants.REQUEST_STATUS_COMPLETE :
//                holder.status.setTextColor(context.getResources().getColor(R.color.status_completed));
//                holder.status.setText(context.getResources().getString(R.string.status_completed));
//                break;
            case Constants.REQUEST_STATUS_REJECTED :
                holder.status.setTextColor(context.getResources().getColor(R.color.status_rejected));
                holder.status.setText(context.getResources().getString(R.string.status_rejected));
                break;
            default:
                holder.status.setTextColor(context.getResources().getColor(R.color.status_new));
                holder.status.setText(context.getResources().getString(R.string.status_new));
        }


        holder.date.setText(order.getCreatedAt());

        holder.title.setText(order.getDonation_title());

        holder.quantity.setText(String.valueOf(order.getQuantity()));


        holder.show_message.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.message_dialog, null);
            final AlertDialog message_dialog = new AlertDialog.Builder(context).create();
            message_dialog.setView(view);

            TextView status = view.findViewById(R.id.status);
            TextView message = view.findViewById(R.id.message);
            TextView ok = view.findViewById(R.id.no_btn);

            switch (order.getStatus()){
//                case Constants.REQUEST_STATUS_NEW :
//                    status.setTextColor(context.getResources().getColor(R.color.status_new));
//                    status.setText(context.getResources().getString(R.string.status_new));
//                    break;
                case Constants.REQUEST_STATUS_ACCEPTED:
                    status.setTextColor(context.getResources().getColor(R.color.status_accepted));
                    status.setText(context.getResources().getString(R.string.status_accepted));
                    break;
//                case Constants.REQUEST_STATUS_COMPLETE :
//                    status.setTextColor(context.getResources().getColor(R.color.status_completed));
//                    status.setText(context.getResources().getString(R.string.status_completed));
//                    break;
                case Constants.REQUEST_STATUS_REJECTED :
                    status.setTextColor(context.getResources().getColor(R.color.status_rejected));
                    status.setText(context.getResources().getString(R.string.status_rejected));
                    break;
                default:
                    status.setTextColor(context.getResources().getColor(R.color.status_new));
                    status.setText(context.getResources().getString(R.string.status_new));
            }

            message.setText(order.getMessage());

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    message_dialog.dismiss();
                }
            });
            message_dialog.show();
        });




    }




    @Override
    public int getItemCount() {
        return orders.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView title;
        public TextView quantity;
        public TextView status;
        public Button show_message;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.title = itemView.findViewById(R.id.title);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.status = itemView.findViewById(R.id.response);
            this.show_message = itemView.findViewById(R.id.show_message);
        }
    }





}