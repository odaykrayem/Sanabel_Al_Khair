package com.example.sanabelalkhayr.donor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.sanabelalkhayr.model.Donation;
import java.util.ArrayList;
import java.util.List;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Donation> donations;
    public NavController navController;
    private List<Donation> donationsFiltered;

    public MyDonationsAdapter(Context context, ArrayList<Donation> donations) {
        this.context = context;
        this.donations = donations;
        this.donationsFiltered = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_donation_donor, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Donation donation = donations.get(position);

        holder.details.setText(donation.getDescription());

        holder.title.setText(donation.getTitle());

        holder.quantity.setText(String.valueOf(donation.getQuantity()));

        if(donation.getImage() != null){
            Glide.with(context)
                    .load(donation.getImage())
                    .centerCrop()
                    .into(holder.image);
            Log.e("image",donation.getImage());

        }else{
            Log.e("image",donation.getImage());
        }

        holder.itemView.setOnClickListener(v -> {
            navController = Navigation.findNavController(holder.itemView);
            Bundle args = new Bundle();
            args.putInt("id", donation.getId());
            args.putInt("quantity", donation.getQuantity());
            args.putString("name", donation.getTitle());
            args.putString("category", donation.getCategory());
            args.putString("image", donation.getImage());
            args.putString("donor", donation.getDonorUserName());
            args.putString("details", donation.getDescription());
            args.putString("for_donor", "yes");
            navController.navigate(R.id.action_myDonationsFragment_to_donationDetailsFragment2, args);
        });
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
                    filterResults.count = donationsFiltered.size();
                    filterResults.values = donationsFiltered;

                }else{
                    String searchChr = charSequence.toString().toLowerCase();

                    List<Donation> resultData = new ArrayList<>();

                    for(Donation donation: donationsFiltered){
                        if(donation.getTitle().toLowerCase().contains(searchChr) || donation.getCategory().toLowerCase().contains(searchChr)){
                            resultData.add(donation);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                donations = (List<Donation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView details;
        public TextView title;
        public TextView quantity;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.details = itemView.findViewById(R.id.description);
            this.title = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.image);
            this.quantity = itemView.findViewById(R.id.quantity);
        }
    }
}