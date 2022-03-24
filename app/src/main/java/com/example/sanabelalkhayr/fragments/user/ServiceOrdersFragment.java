package com.example.sanabelalkhayr.fragments.user;

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
import com.example.sanabelalkhayr.adapters.user.ServiceOrdersAdapter;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.ServiceOrder;

import java.util.ArrayList;

public class ServiceOrdersFragment extends Fragment {

    Context ctx;
    ArrayList<ServiceOrder> list;
    RecyclerView mList;
    ServiceOrdersAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public ServiceOrdersFragment() {
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
        return inflater.inflate(R.layout.fragment_service_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        getServiceOrders();
        list = new ArrayList<ServiceOrder>(){{
            add(new ServiceOrder(1, 1, 1, "donation", "ahmad", Constants.REQUEST_STATUS_ACCEPTED, "message", "10/10/2021"));
            add(new ServiceOrder(1, 1, 1, "donation", "ahmad", Constants.REQUEST_STATUS_ACCEPTED, "message", "10/10/2021"));
            add(new ServiceOrder(1, 1, 1, "donation", "ahmad", Constants.REQUEST_STATUS_ACCEPTED, "message", "10/10/2021"));
            add(new ServiceOrder(1, 1, 1, "donation", "ahmad", Constants.REQUEST_STATUS_ACCEPTED, "message", "10/10/2021"));
    }};

        mAdapter = new ServiceOrdersAdapter(ctx, list);
        mList.setAdapter(mAdapter);
    }

    private void getServiceOrders(){
        String url = Urls.GET_SERVICE_ORDERS;
    }
}