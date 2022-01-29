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

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;

import java.util.ArrayList;


public class DonationsFragment extends Fragment {

    Context ctx;

    ArrayList<Donation> donations;
    RecyclerView mList;
    DonationsAdapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public DonationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        donations = new ArrayList<Donation>(){{
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "good burger", null, 7));

        }};

        mAdapter = new DonationsAdapter(getContext(), donations);
        mList.setAdapter(mAdapter);


    }
}