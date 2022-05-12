package com.example.sanabelalkhayr.volunteer.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.Service;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<Service> services;
    public NavController navController;
    private List<Service> servicesFiltered;
    ProgressDialog pDialog;

    public MyServicesAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
        this.servicesFiltered = services;
    }

    @NonNull
    @Override
    public MyServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_my_service, parent, false);
        MyServicesAdapter.ViewHolder viewHolder = new MyServicesAdapter.ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyServicesAdapter.ViewHolder holder, int position) {

        Service service = services.get(position);

        holder.description.setText(service.getDescription());

        holder.region.setText(service.getRegion());
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View view1 = factory.inflate(R.layout.dialog_delete_service, null);
                final AlertDialog deleteConfirmationDialog = new AlertDialog.Builder(context).create();
                deleteConfirmationDialog.setView(view1);

                TextView yes = view1.findViewById(R.id.yes_btn);
                TextView no = view1.findViewById(R.id.ok_btn);

                yes.setOnClickListener(v1 -> {
                    deleteService(service.getId(), position);
                    deleteConfirmationDialog.dismiss();

                });

                no.setOnClickListener(v12 ->
                        deleteConfirmationDialog.dismiss());
                deleteConfirmationDialog.show();

            }
        });
    }

    private void deleteService(int serviceID, int position) {
        pDialog.show();
        String url = Urls.DELETE_SERVICE_URL;

        String userId = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("service_id", String.valueOf(serviceID))
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
                            String dataGot = "Data Deleted";
                            //if no error in response
                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                services.remove(position);
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
                        Log.e("anError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = servicesFiltered.size();
                    filterResults.values = servicesFiltered;

                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    List<Service> resultData = new ArrayList<>();

                    for (Service service : servicesFiltered) {
                        if (service.getDescription().toLowerCase().contains(searchChr)) {
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
