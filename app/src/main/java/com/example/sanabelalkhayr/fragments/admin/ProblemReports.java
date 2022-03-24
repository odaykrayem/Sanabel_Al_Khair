package com.example.sanabelalkhayr.fragments.admin;

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
import com.example.sanabelalkhayr.adapters.admin.ProblemReportsAdapter;
import com.example.sanabelalkhayr.model.Feedback;

import java.util.ArrayList;

public class ProblemReports extends Fragment {

    Context ctx;
    RecyclerView mList;
    ProblemReportsAdapter mAdapter;
    ArrayList<Feedback> list;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
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
        mList = view.findViewById(R.id.rv);
        list = new ArrayList<Feedback>(){{
            add(new Feedback(1, "new report", "this is a new report", 1,"27-2-2022"));
            add(new Feedback(1, "new report", "this is a new report", 1,"27-2-2022"));
            add(new Feedback(1, "new report", "this is a new report", 1,"27-2-2022"));
            add(new Feedback(1, "new report", "this is a new report", 1,"27-2-2022"));
            add(new Feedback(1, "new report", "this is a new report", 1,"27-2-2022"));

        }};
        mAdapter = new ProblemReportsAdapter(ctx, list);
        mList.setAdapter(mAdapter);
    }
}