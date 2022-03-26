package com.example.sanabelalkhayr.donor.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.sanabelalkhayr.model.Category;
import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Category> {

    private Context context;
    private ArrayList<Category> list;

    public CategoriesAdapter(Context context, int textViewResourceId,
                             ArrayList<Category>list) {
        super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;
        list.add(new Category(-1, "Choose category"));

    }

    @Override
    public int getCount(){
        return super.getCount()-1;
    }

    @Override
    public Category getItem(int position){
        return list.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        if (position == getCount()) {
            label.setText("");
            label.setHintTextColor(Color.GRAY);
            label.setHint(list.get(position).getName());
            return label;//"Hint to be displayed"
        }
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(list.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        if(position == getCount()){
            return null;
        }
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getName());

        return label;
    }
}