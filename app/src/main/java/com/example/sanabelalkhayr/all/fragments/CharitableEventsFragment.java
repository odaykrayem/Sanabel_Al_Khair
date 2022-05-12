package com.example.sanabelalkhayr.all.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.sanabelalkhayr.all.adapters.EventsAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CharitableEventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Context context;

    ArrayList<CharitableEvent> eventsList;
    RecyclerView mList;
    EventsAdapter mAdapter;
    ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public CharitableEventsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charitable_events, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getEvents();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
    }

    private void getEvents() {
        pDialog.show();

        String url = Urls.GET_EVENTS_FOR_USERS;
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        eventsList = new ArrayList<CharitableEvent>();
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
                                    eventsList.add(
                                            new CharitableEvent(
                                                    Integer.parseInt(obj.getString("id")),
                                                    obj.getString("description"),
                                                    obj.getString("start_at"),
                                                    obj.getString("end_at"),
                                                    obj.getString("address"),
                                                    obj.getBoolean("intersted")
                                            )
                                    );
                                }
                                mAdapter = new EventsAdapter(context, eventsList);
                                mList.setAdapter(mAdapter);
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.e("event catch", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("event anerror",error.getErrorBody());
                    }
                });
    }

    @Override
    public void onRefresh() {
        getEvents();
    }
}