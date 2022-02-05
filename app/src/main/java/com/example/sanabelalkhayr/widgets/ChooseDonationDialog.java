package com.example.sanabelalkhayr.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import java.util.ArrayList;

public class ChooseDonationDialog extends Dialog implements DonationsAdapter.OnDonationSelected{

//        private static final String DIALOG_PROFILE_TAG =  "dialog_profile_tag";
        public Activity c;
        public Dialog d;
        public Button addBtn, cancelBtn;
        public EditText pointsET;
        public ProgressBar progressBar;
        LinearLayout buttonsLayout;
        Context ctx;

        ArrayList<Donation> donations;
        RecyclerView mList;
        DonationsAdapter mAdapter;

        int selectedDonationId;

    public ChooseDonationDialog(@NonNull Context context) {
        super(context);
        ctx = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_donations);


        mList = findViewById(R.id.rv);

        donations = new ArrayList<Donation>(){{
            add(new Donation(1, "burger", "good burger", null, 7));
            add(new Donation(1, "burger", "nice burger", null, 7));
            add(new Donation(1, "burger", "awesome burger!", null, 7));
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

        mAdapter = new DonationsAdapter(getContext(), donations, this);
        mList.setAdapter(mAdapter);

    }

    //todo send the voluntary service request from here :)
    @Override
    public void onDonationSelected(int selectedDonationId) {
        Toast.makeText(getContext(), "selected donation id = " + selectedDonationId, Toast.LENGTH_SHORT).show();
    }
}