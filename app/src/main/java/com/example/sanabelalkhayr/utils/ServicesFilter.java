package com.example.sanabelalkhayr.utils;

import android.widget.Filter;

import com.example.sanabelalkhayr.adapters.user.ServicesAdapter;
import com.example.sanabelalkhayr.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServicesFilter extends Filter {

    private List<Service> serviceList;
    private List<Service> filteredServiceList;
    private ServicesAdapter adapter;

    public ServicesFilter(List<Service> serviceList, ServicesAdapter adapter) {
        this.adapter = adapter;
        this.serviceList = serviceList;
        this.filteredServiceList = new ArrayList();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredServiceList.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredServiceList
        for (final Service item : serviceList) {



            String pattern = "[a-zA-Z]*"+constraint+"[a-zA-Z]*";

            if (item.getRegion().toLowerCase().trim().matches(pattern)) {
                filteredServiceList.add(item);
            }
        }

        results.values = filteredServiceList;
        results.count = filteredServiceList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.notifyDataSetChanged();
    }

}