package com.example.sanabelalkhayr.adapters.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.model.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestsAdapter extends RecyclerView.Adapter<ServiceRequestsAdapter.ViewHolder> {


    Context context;
    private List<ServiceRequest> serviceRequests;
    public NavController navController;

    // RecyclerView recyclerView;
    public ServiceRequestsAdapter(Context context, ArrayList<ServiceRequest> serviceRequests) {
        this.context = context;
        this.serviceRequests = serviceRequests;
    }


    @NonNull
    @Override
    public ServiceRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_service_request, parent, false);
        ServiceRequestsAdapter.ViewHolder viewHolder = new ServiceRequestsAdapter.ViewHolder(listItem);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ServiceRequestsAdapter.ViewHolder holder, int position) {

        ServiceRequest serviceRequest = serviceRequests.get(position);
//
//        holder.description.setText(service.getDescription());
//
//        holder.region.setText(service.getRegion());
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete(service.getId());
//
//            }
        });



//        holder.itemView.setOnClickListener(v -> {
//            navController = Navigation.findNavController(holder.itemView);
//            Bundle args = new Bundle();
//            args.putInt("id", service.getId());
//            args.putString("name", donation.getTitle());
//            args.putString("category", donation.getCategory());
//            args.putString("image", donation.getImage());
//            args.putString("donor", donation.getDonorUserName());
//            args.putString("details", donation.getDescription());
//            args.putString("for_donor", "yes");
//            navController.navigate(R.id.action_myDonationsFragment_to_donationDetailsFragment2, args);
//        });

    }

    private void delete(int id) {

    }


    @Override
    public int getItemCount() {
        return serviceRequests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, donationTitle, requestDate, quantity;
        public Button accept, reject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.user_name);
            this.donationTitle = itemView.findViewById(R.id.donation_title);
            this.requestDate = itemView.findViewById(R.id.request_date);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.accept = itemView.findViewById(R.id.accept);
            this.reject = itemView.findViewById(R.id.reject);
        }
    }



}
