package com.example.sanabelalkhayr.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sanabelalkhayr.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

public class SendProblemReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    Context ctx;

    EditText mTitleET, mDetailsET;
    Button mSendReportBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public SendProblemReportFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static com.example.sanabelalkhayr.fragments.SendProblemReportFragment newInstance(String param1, String param2) {
        com.example.sanabelalkhayr.fragments.SendProblemReportFragment fragment = new com.example.sanabelalkhayr.fragments.SendProblemReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_send_problem_report, container, false);
        mTitleET = view.findViewById(R.id.title);
        mDetailsET = view.findViewById(R.id.details);
        mSendReportBtn = view.findViewById(R.id.send_report_btn);

        return view;
    }

    private void updateUserData() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
        User user = sharedPrefManager.getUserData();


        mSendReportBtn.setOnClickListener(v -> {
            String title = mTitleET.getText().toString().trim();
            String details = mDetailsET.getText().toString().trim();

            sendReport(title, details);
        });


    }

    private void sendReport(String title, String details) {
    }


}

