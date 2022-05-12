package com.example.sanabelalkhayr.needy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.widgets.ChooseDonationOrderDialog;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Service> services;
    private List<Service> servicesFiltered;

    public ServicesAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
        this.servicesFiltered = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_service, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {

        Service service = services.get(position);

        holder.description.setText(service.getDescription());

        holder.volunteerName.setText(service.getSubject());

        holder.region.setText(String.valueOf(service.getRegion()));


        holder.order.setOnClickListener(v -> {

            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.dialog_service_confirmation, null);
            final AlertDialog orderConfirmationDialog = new AlertDialog.Builder(context).create();
            orderConfirmationDialog.setView(view);

            TextView yes = view.findViewById(R.id.yes_btn);
            TextView no = view.findViewById(R.id.ok_btn);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChooseDonationOrderDialog cd = new ChooseDonationOrderDialog(context, service.getId());
                    cd.show();
                    orderConfirmationDialog.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderConfirmationDialog.dismiss();
                }
            });
            orderConfirmationDialog.show();
        });
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
                        if(service.getRegion().toLowerCase().contains(searchChr)){
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

        public TextView volunteerName;
        public TextView description;
        public TextView region;
        public Button order;

        public ViewHolder(View itemView) {
            super(itemView);
            this.volunteerName = itemView.findViewById(R.id.volunteer_name);
            this.description = itemView.findViewById(R.id.description);
            this.region = itemView.findViewById(R.id.region);
            this.order = itemView.findViewById(R.id.donationOrder);
        }
    }





}