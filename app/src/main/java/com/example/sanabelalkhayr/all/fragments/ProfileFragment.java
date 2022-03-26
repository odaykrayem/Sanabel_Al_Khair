package com.example.sanabelalkhayr.all.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;


public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;

    TextView mNameTV, mUserNameTV, mPhoneTV, mAddressTV, mNumOfResponsesTV;
    Button mGenerateCertBtn, mUpdateBtn;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout mNumOfResponsesLayout;

    NavController navController;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mNameTV = view.findViewById(R.id.name);
        mUserNameTV = view.findViewById(R.id.user_name);
        mAddressTV = view.findViewById(R.id.address);
        mPhoneTV = view.findViewById(R.id.phone);
        mNumOfResponsesTV = view.findViewById(R.id.num_of_responses);
        mGenerateCertBtn = view.findViewById(R.id.generate_cert);
        mUpdateBtn = view.findViewById(R.id.update);
        mNumOfResponsesLayout = view.findViewById(R.id.num_of_requests_layout);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                updateUserData();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        User user = sharedPrefManager.getUserData();
        mNameTV.setText(user.getName());
        mUserNameTV.setText(user.getUserName());
        mPhoneTV.setText(user.getPhone());
        mAddressTV.setText(user.getAddress());
        mUpdateBtn.setOnClickListener(v->{
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileTOUpdateProfileFragment);
        });
        if (user.getType() == Constants.USER_TYPE_VOLUNTEER) {
            mNumOfResponsesLayout.setVisibility(View.VISIBLE);
            mNumOfResponsesTV.setText(String.valueOf(user.getNumOfResponses()));
        } else {
            mNumOfResponsesLayout.setVisibility(View.GONE);
        }
        if(user.getNumOfResponses() > 3){
            mGenerateCertBtn.setEnabled(true);
        }else{
            mGenerateCertBtn.setEnabled(false);
        }
        mGenerateCertBtn.setOnClickListener(v -> {
            //TODO: generate cert
        });
    }

    @Override
    public void onRefresh() {
        updateUserData();
    }

    private void updateUserData() {
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        User user = sharedPrefManager.getUserData();
        mNameTV.setText(user.getName());
        mUserNameTV.setText(user.getUserName());
        mPhoneTV.setText(user.getPhone());
        mAddressTV.setText(user.getAddress());
        if (user.getType() == Constants.USER_TYPE_VOLUNTEER) {
            mNumOfResponsesLayout.setVisibility(View.VISIBLE);
            mNumOfResponsesTV.setText(String.valueOf(user.getNumOfResponses()));
        } else {
            mNumOfResponsesLayout.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
