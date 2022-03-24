package com.example.sanabelalkhayr.adapters;

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

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {


    Context context;
    private List<CharitableEvent> events;
    public NavController navController;

    // RecyclerView recyclerView;
    public EventsAdapter(Context context, ArrayList<CharitableEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_charitable_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CharitableEvent event = events.get(position);

        holder.address.setText(event.getAddress());

        holder.description.setText(event.getDescription());

        holder.start_at.setText(event.getStart_at());
        holder.end_at.setText(event.getEnd_at());


        if(event.isInterested()){
            holder.interested.setText(context.getResources().getString(R.string.waiting));
        }else {
            holder.interested.setText(context.getResources().getString(R.string.interested_in));
        }

        holder.interested.setOnClickListener(v -> {
            registerInterested(event);
        });


    }

    private void registerInterested(CharitableEvent event) {

    }


    @Override
    public int getItemCount() {
        return events.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address;
        public TextView description;
        public TextView start_at;
        public TextView end_at;
        public Button interested;

        public ViewHolder(View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.address);
            this.description = itemView.findViewById(R.id.description);
            this.start_at = itemView.findViewById(R.id.start_at);
            this.end_at = itemView.findViewById(R.id.end_at);
            this.interested = itemView.findViewById(R.id.interested);
        }
    }
    private Date stringToDate(String aDate, String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

}