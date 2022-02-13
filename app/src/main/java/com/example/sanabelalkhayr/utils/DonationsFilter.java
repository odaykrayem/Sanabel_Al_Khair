package com.example.sanabelalkhayr.utils;

import android.widget.Filter;
import com.example.sanabelalkhayr.adapters.user.DonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import java.util.ArrayList;
import java.util.List;

public class DonationsFilter extends Filter {

        private List<Donation> donationList;
        private List<Donation> filteredDonationList;
        private DonationsAdapter adapter;

        public DonationsFilter(List<Donation> donationList, DonationsAdapter adapter) {
            this.adapter = adapter;
            this.donationList = donationList;
            this.filteredDonationList = new ArrayList();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredDonationList.clear();
            final FilterResults results = new FilterResults();

            //here you need to add proper items do filteredServiceList
            for (final Donation item : donationList) {

                String search = constraint.toString().split(":")[0];
                String category = constraint.toString().split(":")[1];
                String pattern = "[a-zA-Z]*"+category+"[a-zA-Z]*";
                if ((item.getTitle().toLowerCase().trim().matches(pattern) || item.getDescription().toLowerCase().trim().matches(pattern)) && (item.getCategory().toLowerCase().equals(category))) {
                    filteredDonationList.add(item);
                }
            }

            results.values = filteredDonationList;
            results.count = filteredDonationList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.notifyDataSetChanged();
        }

}
