package com.example.sanabelalkhayr.all.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.activities.Register;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfileFragment extends Fragment {

    EditText mNameET, mPasswordET, mPhoneET, mAddressET;
    Button mUpdateBtn;

    private ProgressDialog pDialog;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public UpdateProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

        mNameET = view.findViewById(R.id.name);
        mPasswordET = view.findViewById(R.id.password);
        mPhoneET = view.findViewById(R.id.phone);
        mAddressET = view.findViewById(R.id.address);
        mUpdateBtn = view.findViewById(R.id.btnRegister);
        User user = SharedPrefManager.getInstance(context).getUserData();
        mNameET.setText(user.getName());
        mPhoneET.setText(user.getPhone());
        mAddressET.setText(user.getAddress());
        mUpdateBtn.setOnClickListener(v -> {
            update();
        });
    }

    private void update() {
        pDialog.show();
        mUpdateBtn.setEnabled(false);
        setEditTextEnabled(false, mNameET, mPhoneET, mPasswordET, mAddressET);

        //first getting the values
        final String name = mNameET.getText().toString().trim();
        final String phone = mPhoneET.getText().toString().trim();
        final String password = mPasswordET.getText().toString().trim();
        final String address = mAddressET.getText().toString().trim();
        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        String url = Urls.UPDATE_ACC_URL;

        AndroidNetworking.post(url)
                .addBodyParameter("user_id", id)
                .addBodyParameter("name", name)
                .addBodyParameter("phone", phone)
                .addBodyParameter("password", password)
                .addBodyParameter("address", address)
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
                            String userUpdated = "User Updated";
                            String message = obj.getString("message");
                            //if no error in response
                            if (message.toLowerCase().contains(userUpdated.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                JSONObject userJson = obj.getJSONObject("data");

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(context).userUpdate(
                                        userJson.getString("name"),
                                        userJson.getString("phone"),
                                        userJson.getString("address")
                                );
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                            mUpdateBtn.setEnabled(true);
                            setEditTextEnabled(true, mNameET, mPhoneET, mPasswordET, mAddressET);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            mUpdateBtn.setEnabled(true);
                            setEditTextEnabled(true, mNameET, mPhoneET, mPasswordET, mAddressET);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mUpdateBtn.setEnabled(true);
                        setEditTextEnabled(true, mNameET, mPhoneET, mPasswordET, mAddressET);
                        JSONObject error = null;
                        try {
                            error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();

                            if (data.has("name")) {
                                Toast.makeText(context, data.getJSONArray("name").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("phone")) {
                                Toast.makeText(context, data.getJSONArray("phone").toString(), Toast.LENGTH_SHORT).show();

                            }
                            if (data.has("address")) {
                                Toast.makeText(context, data.getJSONArray("address").toString(), Toast.LENGTH_SHORT).show();

                            }
                            if (data.has("password")) {
                                Toast.makeText(context, data.getJSONArray("password").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setEditTextEnabled(boolean isEnabled, EditText... fields) {
        for (EditText editText : fields) {
            editText.setEnabled(isEnabled);
        }
    }
}