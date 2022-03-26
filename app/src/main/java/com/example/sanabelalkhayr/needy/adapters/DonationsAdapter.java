package com.example.sanabelalkhayr.needy.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sanabelalkhayr.activities.Login;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.Urls;
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
    private List<Donation> donationsFiltered;
    ProgressDialog pDialog;
    public DonationsAdapter(Context context, ArrayList<Donation> donations) {
        this.context = context;
        this.donations = donations;
        this.donationsFiltered = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_donation, parent, false);

        return new ViewHolder(listItem);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Donation donation = donations.get(position);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);
        holder.details.setText(donation.getDescription());

        holder.title.setText(donation.getTitle());
        holder.category.setText(donation.getCategory());

        holder.quantity.setText(String.valueOf(donation.getQuantity()));

        if (donation.getImage() != null) {
            Glide.with(context)
                    .load(donation.getImage())
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(v -> {

                navController = Navigation.findNavController(holder.itemView);
                Bundle args = new Bundle();
                args.putInt("id", donation.getId());
                args.putString("name", donation.getTitle());
                args.putString("category", donation.getCategory());
                args.putString("image", donation.getImage());
                args.putString("donor", donation.getDonorUserName());
                args.putString("details", donation.getDescription());
                args.putString("for_donor", "no");
                args.putInt("quantity", donation.getQuantity());
                navController.navigate(R.id.action_donationsFragment_to_donationDetailsFragment, args);

        });
            holder.orderBtn.setOnClickListener(v -> {
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.dialog_order_confirmation, null);
                final AlertDialog orderConfirmationDialog = new AlertDialog.Builder(context).create();
                orderConfirmationDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.ok_btn);
                EditText quantityET = view.findViewById(R.id.quantity);
                quantityET.setText("1");

                yes.setOnClickListener(v1 -> {
                    if(quantityET.getText().toString().isEmpty()){
                        Toast.makeText(context, context.getResources().getString(R.string.please_add_quantity), Toast.LENGTH_SHORT).show();
                    }else{
                        String quantity = quantityET.getText().toString().trim();
                        int quantityInt = Integer.parseInt(quantity);
                        if(quantityInt > donation.getQuantity() || quantityInt < 1){
                            Toast.makeText(context, context.getResources().getString(R.string.please_choose_quantity), Toast.LENGTH_SHORT).show();
                        }else{
                            orderItem(donation.getId(), quantity);
                            orderConfirmationDialog.dismiss();
                        }

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

    private void orderItem(int id, String quantity) {

        String url = Urls.ORDER_DONATION_URL;
        int userId = SharedPrefManager.getInstance(context).getUserId();


        pDialog.show();

        AndroidNetworking.post(url)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("quantity", quantity)
                .addBodyParameter("donation_id", String.valueOf(id))
                .addBodyParameter("status","1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

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
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Log.e("donaAdapter", anError.getErrorBody());
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("quantity")) {
                                Toast.makeText(context, data.getJSONArray("quantity").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("donation_id")) {
                                Toast.makeText(context, data.getJSONArray("donation_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("status")) {
                                Toast.makeText(context, data.getJSONArray("status").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = donationsFiltered.size();
                    filterResults.values = donationsFiltered;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    String selectedCategory = searchChr.split(":")[1];
                    String search = searchChr.split(":")[0];

                    Log.e("filter", selectedCategory);
                    List<Donation> resultData = new ArrayList<>();

                    if (search.equals("")) {

                        if(selectedCategory.equals("all")){
                            resultData.addAll(donationsFiltered);
                        }else{
                            for (Donation donation : donationsFiltered) {
                                if (donation.getCategory().toLowerCase().contains(selectedCategory))
                                    resultData.add(donation);
                            }
                        }
                    } else {
                        for (Donation donation : donationsFiltered) {
                            if ((donation.getTitle().toLowerCase().contains(search) || donation.getDescription().toLowerCase().contains(search))) {
                                if (donation.getCategory().toLowerCase().equals(selectedCategory)) {
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
        public TextView category;
        public TextView quantity;
        public ImageView image;
        public Button orderBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.details = itemView.findViewById(R.id.description);
            this.title = itemView.findViewById(R.id.title);
            this.category = itemView.findViewById(R.id.category);
            this.image = itemView.findViewById(R.id.image);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.orderBtn = itemView.findViewById(R.id.donationOrder);
        }
    }



}