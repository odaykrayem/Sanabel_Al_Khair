package com.example.sanabelalkhayr.needy.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DONOR = "donor";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_QUANTITY = "quantity";

    private String name, image, details, category, donorUserName, forDonor;
    private int donationId, donationQuantity;
     ProgressDialog pDialog;

     Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DonationDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            donationId = getArguments().getInt(KEY_ID);
            donationQuantity = getArguments().getInt(KEY_QUANTITY);
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

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");

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
                LayoutInflater factory = LayoutInflater.from(context);
                final View view1 = factory.inflate(R.layout.dialog_order_confirmation, null);
                final AlertDialog orderConfirmationDialog = new AlertDialog.Builder(context).create();
                orderConfirmationDialog.setView(view1);

                TextView yes = view1.findViewById(R.id.yes_btn);
                TextView no = view1.findViewById(R.id.ok_btn);
                EditText quantityET = view1.findViewById(R.id.quantity);
                quantityET.setText("1");

                yes.setOnClickListener(v1 -> {
                    if(quantityET.getText().toString().isEmpty()){
                        Toast.makeText(context, context.getResources().getString(R.string.please_add_quantity), Toast.LENGTH_SHORT).show();
                    }else{
                        String quantityInput = quantityET.getText().toString().trim();
                        int quantityInt = Integer.parseInt(quantityInput);
                        if(quantityInt > donationQuantity || quantityInt < 1){
                            Toast.makeText(context, context.getResources().getString(R.string.please_choose_quantity), Toast.LENGTH_SHORT).show();
                        }else{
                            orderItem(donationId, quantityInput);
                            orderConfirmationDialog.dismiss();
                        }
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderConfirmationDialog.dismiss();
                    }
                });
                orderConfirmationDialog.show();
            });
        }
    }

    private void orderItem(int donationId, String q) {

        String url = Urls.ORDER_DONATION_URL;
        int userId = SharedPrefManager.getInstance(context).getUserId();
        pDialog.show();

        AndroidNetworking.post(url)
                .addBodyParameter("user_id", String.valueOf(userId))
                .addBodyParameter("quantity", q)
                .addBodyParameter("donation_id", String.valueOf(donationId))
                .addBodyParameter("status","1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("catch", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Log.e("donaAdapter", anError.getErrorBody());
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("quantity")) {
                                Toast.makeText(context, data.getJSONArray("quantity").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("donation_id")) {
                                Toast.makeText(context, data.getJSONArray("donation_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("status")) {
                                Toast.makeText(context, data.getJSONArray("status").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}