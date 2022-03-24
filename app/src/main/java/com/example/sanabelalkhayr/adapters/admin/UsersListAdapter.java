package com.example.sanabelalkhayr.adapters.admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {


    Context context;
    private List<User> users;
    public NavController navController;

    // RecyclerView recyclerView;
    public UsersListAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_user, parent, false);

        return new ViewHolder(listItem);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);

        holder.userName.setText(user.getUserName());

        holder.fullName.setText(user.getName());

        switch (user.getType()){
            case Constants.USER_TYPE_DONOR:
                holder.type.setText(context.getResources().getString(R.string.donor));
                holder.type.setTextColor(context.getResources().getColor(R.color.donor));
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.bg_donor_card));
                break;
            case Constants.USER_TYPE_MAIN:
                holder.type.setText(context.getResources().getString(R.string.main_user));
                holder.type.setTextColor(context.getResources().getColor(R.color.needy));
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.bg_user_card));
                break;
            case Constants.USER_TYPE_VOLUNTEER:
                holder.type.setText(context.getResources().getString(R.string.volunteer));
                holder.type.setTextColor(context.getResources().getColor(R.color.volunteer));
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.bg_volunteer_card));
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            navController = Navigation.findNavController(holder.itemView);
            Bundle args = new Bundle();
            args.putInt("id", user.getId());
            args.putString("name", user.getName());
            args.putString("userName", user.getUserName());
            args.putString("phone", user.getPhone());
            args.putString("address", user.getAddress());
            args.putInt("type", user.getType());
            navController.navigate(R.id.action_UsersListFragment_to_UserDetailsFragment, args);
        });

    }


    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView fullName;
        public TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.user_name);
            this.fullName = itemView.findViewById(R.id.user_full_name);
            this.type = itemView.findViewById(R.id.user_type);
        }
    }




}