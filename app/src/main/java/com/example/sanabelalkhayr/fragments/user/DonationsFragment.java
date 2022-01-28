package com.example.sanabelalkhayr.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.example.sanabelalkhayr.R;


public class DonationsFragment extends Fragment {

    Context ctx;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public DonationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}