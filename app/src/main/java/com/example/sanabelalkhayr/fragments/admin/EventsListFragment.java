package com.example.sanabelalkhayr.fragments.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.EventsAdapter;
import com.example.sanabelalkhayr.adapters.admin.EventsListAdapter;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EventsListFragment extends Fragment {

    Context ctx;

    RecyclerView mList;
    EventsListAdapter mAdapter;
    ArrayList<CharitableEvent> events;

    FloatingActionButton addEventBtn;

    NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }
    public EventsListFragment() {
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
        return inflater.inflate(R.layout.fragment_events_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);
        addEventBtn = view.findViewById(R.id.add_btn);

        addEventBtn.setOnClickListener(v -> {
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_EventsListFragment_to_AddEventFragment);
        });

        events = new ArrayList<CharitableEvent>(){{
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));
            add(new CharitableEvent(1, "jjj", "2-2-2022", "3-2-2022", "jaddah"));

        }};

        mAdapter = new EventsListAdapter(ctx, events);
        mList.setAdapter(mAdapter);
    }
}