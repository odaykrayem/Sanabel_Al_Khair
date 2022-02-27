package com.example.sanabelalkhayr.adapters.volunteer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.volunteer.MyServicesAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.Service;

import java.util.ArrayList;
import java.util.List;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.ViewHolder> implements Filterable {


    Context context;
    private List<Service> services;
    public NavController navController;
    private List<Service> servicesFiltered;

    // RecyclerView recyclerView;
    public MyServicesAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
        this.servicesFiltered = services;
    }


    @NonNull
    @Override
    public MyServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_my_service, parent, false);
        MyServicesAdapter.ViewHolder viewHolder = new MyServicesAdapter.ViewHolder(listItem);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyServicesAdapter.ViewHolder holder, int position) {

        Service service = services.get(position);

        holder.description.setText(service.getDescription());

        holder.region.setText(service.getRegion());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(service.getId());

            }
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
        return services.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
                    filterResults.count = servicesFiltered.size();
                    filterResults.values = servicesFiltered;

                }else{
                    String searchChr = charSequence.toString().toLowerCase();

                    List<Service> resultData = new ArrayList<>();

                    for(Service service: servicesFiltered){
                        if(service.getDescription().toLowerCase().contains(searchChr)){
                            resultData.add(service);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                services = (List<Service>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public TextView region;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.description);
            this.region = itemView.findViewById(R.id.region);
            this.delete = itemView.findViewById(R.id.delete);
        }
    }



}
