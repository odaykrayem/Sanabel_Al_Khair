package com.example.sanabelalkhayr.admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.admin.adapters.EventsListAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventsListFragment extends Fragment {

    Context context;

    RecyclerView mList;
    EventsListAdapter mAdapter;
    ArrayList<CharitableEvent> events;

    FloatingActionButton addEventBtn;

    NavController navController;
    ProgressDialog pDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public EventsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        addEventBtn = view.findViewById(R.id.add_btn);

        addEventBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_EventsListFragment_to_AddEventFragment);
        });
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");

    }



    private void getEvents() {
        pDialog.show();

        String url = Urls.GET_EVENTS_FOR_ADMIN;
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        events = new ArrayList<CharitableEvent>();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .addQueryParameter("user_id", id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String messageGot = "Data Got";
                            String message = response.getString("message");
                            if(message.toLowerCase().contains(messageGot.toLowerCase())){
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    events.add(
                                            new CharitableEvent(
                                                    Integer.parseInt(obj.getString("id")),
                                                    obj.getString("description"),
                                                    obj.getString("start_at"),
                                                    obj.getString("end_at"),
                                                    obj.getString("address"),
                                                    obj.getBoolean("interested")
                                            )
                                    );
                                }
                                mAdapter = new EventsListAdapter(context, events);
                                mList.setAdapter(mAdapter);
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("event catch", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Log.e("event anerror",error.getErrorBody());
                    }
                });
    }
}