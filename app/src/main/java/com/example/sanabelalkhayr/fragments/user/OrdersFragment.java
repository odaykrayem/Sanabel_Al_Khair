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

import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.adapters.user.OrdersAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.model.Order;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context ctx;

    ArrayList<Order> orders;
    RecyclerView mList;
    OrdersAdapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        orders = new ArrayList<Order>(){{
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
            add(new Order(1, 1, "butger", 10, Constants.REQUEST_STATUS_NEW, "reason why", "10/10/2021"));
        }};

        mAdapter = new OrdersAdapter(ctx, orders);
        mList.setAdapter(mAdapter);
    }
}