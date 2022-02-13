package com.example.sanabelalkhayr.adapters.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.api.Urls;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.ViewHolder> implements Filterable {


    Context context;
    private List<Donation> donations;

    public NavController navController;
    private Dialog dialog;
    OnDonationSelected mSelectionListener;
    private List<Donation> donationsFiltered;

    // RecyclerView recyclerView;
    public DonationsAdapter(Context context, ArrayList<Donation> donations, Dialog dialog) {
        this.context = context;
        this.donations = donations;
        this.dialog = dialog;
        this.donationsFiltered = donations;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_donation, parent, false);

        return new ViewHolder(listItem);
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if(dialog instanceof DonationsAdapter.OnDonationSelected){
            mSelectionListener = (DonationsAdapter.OnDonationSelected) dialog;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mSelectionListener = null;
        super.onDetachedFromRecyclerView(recyclerView);

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
                    .into(holder.image);
        }


        holder.itemView.setOnClickListener(v -> {
            if(dialog!=null){
                mSelectionListener.onDonationSelected(donation.getId());
            }else {
                navController = Navigation.findNavController(holder.itemView);
                Bundle args = new Bundle();
                args.putInt("id", donation.getId());
                args.putString("name", donation.getTitle());
                args.putString("category", donation.getCategory());
                args.putString("image", donation.getImage());
                args.putString("donor", donation.getDonorUserName());
                args.putString("details", donation.getDescription());
                args.putString("for_donor", "no");
                navController.navigate(R.id.action_donationsFragment_to_donationDetailsFragment, args);
            }
        });


        if(dialog!=null){
            holder.orderBtn.setVisibility(View.GONE);
        }else {
            holder.orderBtn.setOnClickListener(v -> {
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.order_confirmation_dialog, null);
                final AlertDialog orderConfirmationDialog = new AlertDialog.Builder(context).create();
                orderConfirmationDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.no_btn);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        orderItem(donation.getId());

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





    }

    private void orderItem(int id) {

        int userId = SharedPrefManager.getInstance(context).getUserId();

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        AndroidNetworking.post(Urls.GET_DONATIONS)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("donation_id", String.valueOf(id))
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

                            //if no error in response
                            if (obj.getInt("status") == 1) {

                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if(obj.getInt("status") == -1){
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Toast.makeText(context, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return this.donations.size();
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
                    String selectedCategory =  searchChr.split(":")[1];
                    String search =  searchChr.split(":")[0];

                    Log.e("filter", selectedCategory);
                    List<Donation> resultData = new ArrayList<>();

                    if(search.equals("")){
                        for(Donation donation : donationsFiltered){
                            if(donation.getCategory().toLowerCase().equals(selectedCategory))
                                resultData.add(donation);
                        }
                    }else{
                        for(Donation donation: donationsFiltered){
                            if((donation.getTitle().toLowerCase().contains(search) || donation.getDescription().toLowerCase().contains(search))){
                                if(donation.getCategory().toLowerCase().equals(selectedCategory)){

                                    resultData.add(donation);
                                }
                            }
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
        public Button orderBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.details = itemView.findViewById(R.id.description);
            this.title = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.image);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.orderBtn = itemView.findViewById(R.id.order);
        }
    }


     public interface OnDonationSelected{
        void onDonationSelected(int id);
     }



}