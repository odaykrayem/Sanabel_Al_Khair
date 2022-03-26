package com.example.sanabelalkhayr.admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.sanabelalkhayr.admin.adapters.ProblemReportsAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Feedback;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProblemReports extends Fragment {

    Context context;
    RecyclerView mList;
    ProblemReportsAdapter mAdapter;
    ArrayList<Feedback> list;
    ProgressDialog pDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ProblemReports() {
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
        return inflater.inflate(R.layout.fragment_problem_reports, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        mList = view.findViewById(R.id.rv);

        getReports();
    }

    private void getReports() {

        pDialog.show();

        String url = Urls.GET_EVENTS_URL;
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        list = new ArrayList<Feedback>();
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
                                    list.add(
                                            new Feedback(
                                                    Integer.parseInt(obj.getString("id")),
                                                    obj.getString("title"),
                                                    obj.getString("content"),
                                                    Integer.parseInt(obj.getString("user_id"))
                                            )
                                    );
                                }
                                mAdapter = new ProblemReportsAdapter(context, list);
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