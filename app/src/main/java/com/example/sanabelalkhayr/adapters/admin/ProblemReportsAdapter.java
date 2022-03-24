package com.example.sanabelalkhayr.adapters.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.CharitableEvent;
import com.example.sanabelalkhayr.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class ProblemReportsAdapter extends RecyclerView.Adapter<ProblemReportsAdapter.ViewHolder> {

    Context context;
    private List<Feedback> list;

    public ProblemReportsAdapter(Context context, ArrayList<Feedback> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProblemReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_report, parent, false);

        return new ProblemReportsAdapter.ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ProblemReportsAdapter.ViewHolder holder, int position) {

        Feedback item = list.get(position);

        holder.title.setText(item.getTitle());
        holder.details.setText(item.getContent());
        holder.date.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, details, date;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.details = itemView.findViewById(R.id.details);
            this.date = itemView.findViewById(R.id.date);
        }
    }


}