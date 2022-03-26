package com.example.sanabelalkhayr.admin.adapters;

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
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {


    Context context;
    private List<CharitableEvent> events;
    public NavController navController;
    ProgressDialog pDialog;
    // RecyclerView recyclerView;
    public EventsListAdapter(Context context, ArrayList<CharitableEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_admin_event, parent, false);

        return new ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CharitableEvent event = events.get(position);

        holder.address.setText(event.getAddress());

        holder.description.setText(event.getDescription());

        holder.start_at.setText(event.getStart_at());
        holder.end_at.setText(event.getEnd_at());

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");
        holder.delete.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view1 = factory.inflate(R.layout.dialog_delete_event, null);
            final AlertDialog deleteConfirmationDialog = new AlertDialog.Builder(context).create();
            deleteConfirmationDialog.setView(view1);

            TextView yes = view1.findViewById(R.id.yes_btn);
            TextView no = view1.findViewById(R.id.ok_btn);

            yes.setOnClickListener(v1 -> {
                deleteEvent(event);
                deleteConfirmationDialog.dismiss();

            });

            no.setOnClickListener(v12 ->
                    deleteConfirmationDialog.dismiss());
            deleteConfirmationDialog.show();
        });
    }

    private void deleteEvent(CharitableEvent event) {
        pDialog.show();
        String url = Urls.DELETE_EVENT_URL;

        AndroidNetworking.post(url)
                .addBodyParameter("id", String.valueOf(event.getId()))
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
                            String dataGot = "Data Deleted";
                            //if no error in response
                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
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
        return events.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address;
        public TextView description;
        public TextView start_at;
        public TextView end_at;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.address);
            this.description = itemView.findViewById(R.id.description);
            this.start_at = itemView.findViewById(R.id.start_at);
            this.end_at = itemView.findViewById(R.id.end_at);
            this.delete = itemView.findViewById(R.id.delete);
        }
    }


}