package com.example.sanabelalkhayr.fragments.volunteer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.volunteer.ServiceRequestsAdapter;
import com.example.sanabelalkhayr.model.ServiceRequest;

import java.util.ArrayList;

public class ServiceRequestsFragment extends Fragment {

    Context ctx;

    ArrayList<ServiceRequest> requests;
    ServiceRequestsAdapter mAdapter;
    RecyclerView mList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public ServiceRequestsFragment() {
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
        return inflater.inflate(R.layout.fragment_service_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        requests = new ArrayList<ServiceRequest>()
        {{
           add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", Constants.REQUEST_STATUS_REJECTED));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", Constants.REQUEST_STATUS_REJECTED));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", -1));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", -1));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", Constants.REQUEST_STATUS_REJECTED));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", -1));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", Constants.REQUEST_STATUS_REJECTED));
            add(new ServiceRequest(1, 1, "hiba", 1, "cloths", 4, 1, "10/10/2021", Constants.REQUEST_STATUS_ACCEPTED));
        }};

        mAdapter = new ServiceRequestsAdapter(ctx, requests);
        mList.setAdapter(mAdapter);
    }
}