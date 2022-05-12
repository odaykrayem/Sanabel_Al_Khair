package com.example.sanabelalkhayr.donor.adapters;

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
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Donation> donations;
    public NavController navController;
    private List<Donation> donationsFiltered;
    ProgressDialog pDialog;

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

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");

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
            args.putString("area", donation.getRegion());
            args.putString("for_donor", "yes");
            navController.navigate(R.id.action_myDonationsFragment_to_donationDetailsFragment2, args);
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View view1 = factory.inflate(R.layout.dialog_delete_donation, null);
                final AlertDialog deleteConfirmationDialog = new AlertDialog.Builder(context).create();
                deleteConfirmationDialog.setView(view1);

                TextView yes = view1.findViewById(R.id.yes_btn);
                TextView no = view1.findViewById(R.id.ok_btn);

                yes.setOnClickListener(v1 -> {
                    deleteDonation(donation.getId(), position);
                    deleteConfirmationDialog.dismiss();

                });

                no.setOnClickListener(v12 ->
                        deleteConfirmationDialog.dismiss());
                deleteConfirmationDialog.show();

            }
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
    private void deleteDonation(int donationID, int position) {
        pDialog.show();
        String url = Urls.DELETE_DONATION_URL;

        AndroidNetworking.post(url)
                .addBodyParameter("id", String.valueOf(donationID))
                .setPriority(Priority.MEDIUM)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String dataGot = "Deleted";
                            //if no error in response
                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                donations.remove(position);
                                notifyItemRemoved(position);
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
                        Log.e("anError", anError.getMessage());
                        Log.e("anError", anError.getErrorDetail());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("id")) {
                                Toast.makeText(context, data.getJSONArray("id").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView details;
        public TextView title;
        public TextView quantity;
        public ImageView image;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.details = itemView.findViewById(R.id.description);
            this.title = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.image);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.delete = itemView.findViewById(R.id.delete);
        }
    }
}