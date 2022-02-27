package com.example.sanabelalkhayr.fragments.user;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.api.Urls;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;


public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

        Context ctx;

        TextView mNameTV, mUserNameTV, mPhoneTV, mAddressTV, mNumOfResponsesTV;
        Button mGenerateCertBtn;
        SwipeRefreshLayout mSwipeRefreshLayout;
        RelativeLayout mNumOfResponsesLayout;


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            ctx = context;
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
            if(user.getType() == Constants.USER_TYPE_VOLUNTEER){
                mNumOfResponsesLayout.setVisibility(View.GONE);
            }else{
                mNumOfResponsesLayout.setVisibility(View.VISIBLE);
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
            String url = Urls.UPDATE_ACC_URL;
    }
}
