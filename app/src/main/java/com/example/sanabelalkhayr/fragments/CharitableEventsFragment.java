package com.example.sanabelalkhayr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.EventsAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CharitableEventsFragment extends Fragment {

    Context context;

    ArrayList<CharitableEvent> eventsList;
    RecyclerView mList;
    EventsAdapter mAdapter;
    ProgressDialog pDialog;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public CharitableEventsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_charitable_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();
//
//        eventsList = new ArrayList<CharitableEvent>(){{
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", false));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah", true));
//        }};
//
//        mAdapter = new EventsAdapter(context, eventsList);
//        mList.setAdapter(mAdapter);

        getEvents();
    }

    private void getEvents() {
        String url = Urls.GET_EVENTS_URL;
        eventsList = new ArrayList<CharitableEvent>();
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
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
                                    JSONObject obj = jsonArray.getJSONObject(0);
                                    eventsList.add(
                                            new CharitableEvent(
                                                    Integer.parseInt(obj.getString("id")),
                                                    obj.getString("description"),
                                                    obj.getString("start_date"),
                                                    obj.getString("end_date"),
                                                    obj.getString("address"),
                                                    obj.getInt("interested")==1?true:false
                                            )
                                    );
                                }
                                mAdapter = new EventsAdapter(context, eventsList);
                                mList.setAdapter(mAdapter);
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Toast.makeText(context, error.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}