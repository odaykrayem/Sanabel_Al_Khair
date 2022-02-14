package com.example.sanabelalkhayr.fragments;

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
import com.example.sanabelalkhayr.adapters.EventsAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;

import java.util.ArrayList;


public class CharitableEventsFragment extends Fragment {


    Context ctx;

    ArrayList<CharitableEvent> charitableEvents;
    RecyclerView mList;
    EventsAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }
    public CharitableEventsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charitable_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);

        charitableEvents = new ArrayList<CharitableEvent>(){{
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));

            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));

        }};

        mAdapter = new EventsAdapter(ctx, charitableEvents);
        mList.setAdapter(mAdapter);
    }
}