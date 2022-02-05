package com.example.sanabelalkhayr.adapters.user;

import android.app.Activity;
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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.utils.ServicesFilter;
import com.example.sanabelalkhayr.widgets.ChooseDonationDialog;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Service> services;
    private List<Service> servicesFiltered;
    private Activity a;
    // RecyclerView recyclerView;
    public ServicesAdapter(Context context, ArrayList<Service> services ,Activity a) {
        this.context = context;
        this.services = services;
        this.servicesFiltered = services;
        this.a=a;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_service, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {

        Service Service = services.get(position);

        holder.description.setText(Service.getDescription());

        holder.volunteerName.setText(Service.getSubject());

        holder.region.setText(String.valueOf(Service.getRegion()));


        holder.order.setOnClickListener(v -> {
            ChooseDonationDialog cd = new ChooseDonationDialog(context);
            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.service_confirmation_dialog, null);
            final AlertDialog orderConfirmationDialog = new AlertDialog.Builder(context).create();
            orderConfirmationDialog.setView(view);

            TextView yes = view.findViewById(R.id.yes_btn);
            TextView no = view.findViewById(R.id.no_btn);


            //Todo : display User Donations
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderConfirmationDialog.dismiss();
                    cd.show();


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
        public TextView status;
        public Button order;

        public ViewHolder(View itemView) {
            super(itemView);
            this.volunteerName = itemView.findViewById(R.id.volunteer_name);
            this.description = itemView.findViewById(R.id.description);
            this.region = itemView.findViewById(R.id.region);
            this.status = itemView.findViewById(R.id.status);
            this.order = itemView.findViewById(R.id.order);
        }
    }





}