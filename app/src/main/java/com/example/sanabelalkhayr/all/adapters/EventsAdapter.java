package com.example.sanabelalkhayr.all.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {


    Context context;
    private List<CharitableEvent> events;
    public NavController navController;
    private ProgressDialog pDialog;


    // RecyclerView recyclerView;
    public EventsAdapter(Context context, ArrayList<CharitableEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_charitable_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CharitableEvent event = events.get(position);

        holder.address.setText(event.getAddress());

        holder.description.setText(event.getDescription());

        holder.start_at.setText(event.getStart_at().substring(0,10));
        holder.end_at.setText(event.getEnd_at().substring(0,10));

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
        if(event.isInterested()){
            holder.interested.setText(context.getResources().getString(R.string.waiting));
        }else {
            holder.interested.setText(context.getResources().getString(R.string.interested_in));
        }

        holder.interested.setOnClickListener(v -> {
            registerInterested(event);
        });


    }

    private void registerInterested(CharitableEvent event) {
        pDialog.show();
        String url = Urls.INTERESTED;

        AndroidNetworking.post(url)
                .addBodyParameter("user_id", String.valueOf(SharedPrefManager.getInstance(context).getUserId()))
                .addBodyParameter("event_id", String.valueOf(event.getId()))
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
                            String message = obj.getString("message");
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch", e.getMessage());
                            pDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Log.e("anError", anError.getErrorBody());
                    }
                });
    }


    @Override
    public int getItemCount() {
        return events.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address;
        public TextView description;
        public TextView start_at;
        public TextView end_at;
        public Button interested;

        public ViewHolder(View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.address);
            this.description = itemView.findViewById(R.id.description);
            this.start_at = itemView.findViewById(R.id.start_at);
            this.end_at = itemView.findViewById(R.id.end_at);
            this.interested = itemView.findViewById(R.id.interested);
        }
    }


}