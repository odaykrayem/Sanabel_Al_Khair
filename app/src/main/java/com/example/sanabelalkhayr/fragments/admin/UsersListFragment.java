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

import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.admin.UsersListAdapter;
import com.example.sanabelalkhayr.model.User;

import java.util.ArrayList;

public class UsersListFragment extends Fragment {

    Context ctx;

    RecyclerView mList;
    UsersListAdapter mAdapter;
    ArrayList<User> list;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public UsersListFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);

        User user = new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_DONOR);
        list = new ArrayList<User>();
        list.add(user);
        list = new ArrayList<User>(){{
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_DONOR));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_DONOR));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_DONOR));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_MAIN));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_MAIN));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_MAIN));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_VOLUNTEER));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_VOLUNTEER));
           add(new User(1, "User", "User_name", "00966980890088","jaddah", Constants.USER_TYPE_VOLUNTEER));
        }};
        mAdapter = new UsersListAdapter(ctx, list);
        mList.setAdapter(mAdapter);

    }
}