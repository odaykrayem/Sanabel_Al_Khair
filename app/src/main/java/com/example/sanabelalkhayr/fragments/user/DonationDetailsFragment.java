package com.example.sanabelalkhayr.fragments.user;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;



public class DonationDetailsFragment extends Fragment {

    ImageView mImage;
    TextView mNameTV, mCategoryTV, mDonorUserNameTV, mDetailsTV;
    Button mOrderBtn;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DONOR = "donor";
    private static final String KEY_CATEGORY = "category";

    private String name, image, details, category, donorUserName, forDonor;
    private int id;

    public DonationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(KEY_ID);
            name = getArguments().getString(KEY_NAME);
            details = getArguments().getString(KEY_DETAILS);
            donorUserName = getArguments().getString(KEY_DONOR);
            image = getArguments().getString(KEY_IMAGE);
            category = getArguments().getString(KEY_CATEGORY);
            forDonor = getArguments().getString("for_donor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donation_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImage = view.findViewById(R.id.image);
        mNameTV = view.findViewById(R.id.name);
        mCategoryTV = view.findViewById(R.id.category);
        mDonorUserNameTV = view.findViewById(R.id.donor_user_name);
        mDetailsTV = view.findViewById(R.id.details);
        mOrderBtn = view.findViewById(R.id.order_btn);
        if(image != null)
            Glide.with(getContext())
                    .load(image)
                    .into(mImage);
        mNameTV.setText(name);
        mCategoryTV.setText(category);
        mDetailsTV.setText(details);
        mDonorUserNameTV.setText(donorUserName);

        if(forDonor.equals("yes"))
            mOrderBtn.setVisibility(View.GONE);
        else {
            mOrderBtn.setOnClickListener(v -> {
                orderItem(id);
            });
        }
    }


    private void orderItem(int id) {

        int userId = SharedPrefManager.getInstance(getContext()).getUserId();

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        AndroidNetworking.post(Urls.ORDER_DONATION_URL)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("donation_id", String.valueOf(id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        pDialog.dismiss();

                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            //if no error in response
                            if (obj.getInt("status") == 1) {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if(obj.getInt("status") == -1){
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Toast.makeText(getContext(), anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}