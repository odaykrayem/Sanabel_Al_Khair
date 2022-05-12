package com.example.sanabelalkhayr.admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.utils.Urls;
import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailsFragment extends Fragment {

    Button deleteBtn;
    TextView mIDTV, mNameTV, mUserNameTV,mEmailTV, mPhoneTV, mAddressTV, mType;
    String id, name, userName,email, phone, address;
    int typeInt;
    Context context;
    ProgressDialog pDialog;
    NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public UserDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = String.valueOf(getArguments().getInt("id"));
            name = getArguments().getString("name");
            userName = getArguments().getString("userName");
            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
            address = getArguments().getString("address");
            typeInt = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing Please wait...");

        navController = Navigation.findNavController(view);
        mIDTV = view.findViewById(R.id.tv_id);
        mNameTV = view.findViewById(R.id.name);
        mUserNameTV = view.findViewById(R.id.user_name);
        mEmailTV = view.findViewById(R.id.email);
        mAddressTV = view.findViewById(R.id.address);
        mPhoneTV = view.findViewById(R.id.phone);
        mType = view.findViewById(R.id.title);
        deleteBtn = view.findViewById(R.id.delete);

        mIDTV.setText(id);
        mNameTV.setText(name);
        mUserNameTV.setText(userName);
        mEmailTV.setText(email);
        mAddressTV.setText(address);
        mPhoneTV.setText(phone);
        switch (typeInt){
            case Constants.USER_TYPE_DONOR:
                mType.setText(context.getResources().getString(R.string.donor));
                break;
            case Constants.USER_TYPE_VOLUNTEER:
                mType.setText(context.getResources().getString(R.string.volunteer));
                break;
            case Constants.USER_TYPE_MAIN:
                mType.setText(context.getResources().getString(R.string.needy));
                break;
        }

        deleteBtn.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view1 = factory.inflate(R.layout.dialog_delete_user, null);
            final AlertDialog deleteConfirmationDialog = new AlertDialog.Builder(context).create();
            deleteConfirmationDialog.setView(view1);

            TextView yes = view1.findViewById(R.id.yes_btn);
            TextView no = view1.findViewById(R.id.ok_btn);

            yes.setOnClickListener(v1 -> {
                deleteUser();
                deleteConfirmationDialog.dismiss();

            });

            no.setOnClickListener(v12 ->
                    deleteConfirmationDialog.dismiss());
            deleteConfirmationDialog.show();
        });

    }

    private void deleteUser() {
        pDialog.show();
        deleteBtn.setEnabled(false);
        String url = Urls.DELETE_USER_URL;

        AndroidNetworking.post(url)
                .addBodyParameter("id", id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String dataGot = "Data Deleted";

                            //if no error in response

                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                navController.popBackStack();
                            }
                            pDialog.dismiss();
                            deleteBtn.setEnabled(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            deleteBtn.setEnabled(true);
                            Log.e("catch", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        deleteBtn.setEnabled(true);
                        Log.e("anError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}