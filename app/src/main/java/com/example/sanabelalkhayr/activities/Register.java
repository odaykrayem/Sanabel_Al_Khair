package com.example.sanabelalkhayr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.utils.Constants;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText mNameET, mUserNameET, mPassET, mPhoneET, mAddressET;
    Button mRegisterBtn, mToLoginBtn;
    RadioGroup mAccountTypeSelector;
    int selectedUserType;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameET = findViewById(R.id.name);
        mPassET = findViewById(R.id.password);
        mUserNameET = findViewById(R.id.user_name);
        mPhoneET = findViewById(R.id.phone);
        mAddressET = findViewById(R.id.address);
        mRegisterBtn = findViewById(R.id.btnRegister);
        mToLoginBtn = findViewById(R.id.btnLinkToLoginScreen);

        mAccountTypeSelector = findViewById(R.id.type_selector);
        mAccountTypeSelector.check(R.id.main_user);
        selectedUserType = Constants.USER_TYPE_MAIN;
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Progressing please wait....");

        mToLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });

        mRegisterBtn.setOnClickListener(v -> {
            if (validateUserInput()) {
                register();
            }
        });


        mAccountTypeSelector.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.main_user:
                    selectedUserType = Constants.USER_TYPE_MAIN;
                    break;
                case R.id.donor:
                    selectedUserType = Constants.USER_TYPE_DONOR;
                    break;
                case R.id.volunteer:
                    selectedUserType = Constants.USER_TYPE_VOLUNTEER;
                    break;
            }
        });
    }

    private boolean validateUserInput() {
        //first getting the values
        final String pass = mPassET.getText().toString();
        final String name = mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();
        final String userName = mUserNameET.getText().toString();
        final String address = mAddressET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, getResources().getString(R.string.name_missing_message), Toast.LENGTH_SHORT).show();
            mRegisterBtn.setEnabled(true);
            return false;
        }

        //checking if userName is empty
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, getResources().getString(R.string.user_name_missing_message), Toast.LENGTH_SHORT).show();
            mRegisterBtn.setEnabled(true);
            return false;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, getResources().getString(R.string.password_missing_message), Toast.LENGTH_SHORT).show();
            mRegisterBtn.setEnabled(true);
            return false;
        }
        //checking if phone is empty
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getResources().getString(R.string.phone_missing_message), Toast.LENGTH_SHORT).show();
            mRegisterBtn.setEnabled(true);
            return false;
        }
        //checking if address is empty
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, getResources().getString(R.string.address_missing_message), Toast.LENGTH_SHORT).show();
            mRegisterBtn.setEnabled(true);
            return false;
        }
        return true;
    }

    private void register() {
        mRegisterBtn.setEnabled(false);


        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String pass = mPassET.getText().toString();
        final String name = mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();
        final String userName = mUserNameET.getText().toString();
        final String address = mAddressET.getText().toString();

        String url = Urls.REGISTER_URL;
        AndroidNetworking.post(url)
                .addBodyParameter("type", String.valueOf(selectedUserType))
                .addBodyParameter("name", name)
                .addBodyParameter("user_name", userName)
                .addBodyParameter("password", pass)
                .addBodyParameter("phone", phone)
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
                            String userSaved = "User Saved";
                            String message = obj.getString("message");
                            //if no error in response
                            JSONObject userJson = obj.getJSONObject("data");

                            if (message.toLowerCase().contains(userSaved.toLowerCase())) {
                                Log.e("selectedUser", "inside");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                User user;

                                user = new User(
                                        Integer.parseInt(userJson.getString("id")),
                                        userJson.getString("name"),
                                        userJson.getString("user_name"),
                                        userJson.getString("phone"),
                                        userJson.getString("address"),
                                        userJson.getInt("type"),
                                        0
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                Log.e("selectedUser", selectedUserType + "");
                                Log.e("selectedUser", "inside2");

                                goToUserMainActivity();

                            }
                            pDialog.dismiss();
                            mRegisterBtn.setEnabled(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error", e.getMessage());
                            pDialog.dismiss();
                            mRegisterBtn.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mRegisterBtn.setEnabled(true);
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(Register.this, error.getString("message"), Toast.LENGTH_SHORT).show();

                            if (data.has("name")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("name").toString(), Toast.LENGTH_SHORT).show();
                            }  if (data.has("user_name")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("user_name").toString(), Toast.LENGTH_SHORT).show();
                            }  if (data.has("phone")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("phone").toString(), Toast.LENGTH_SHORT).show();

                            }  if (data.has("address")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("address").toString(), Toast.LENGTH_SHORT).show();

                            }  if (data.has("type")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("type").toString(), Toast.LENGTH_SHORT).show();

                            }  if (data.has("password")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("password").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }
                });
    }

    private void goToUserMainActivity() {
        Log.e("selectedUser", selectedUserType + "");
        switch (selectedUserType) {
            case Constants.USER_TYPE_MAIN:
                startActivity(new Intent(Register.this, UserMain.class));
                finish();
                break;
            case Constants.USER_TYPE_DONOR:
                startActivity(new Intent(Register.this, DonorMain.class));
                finish();
                break;
            case Constants.USER_TYPE_VOLUNTEER:
                startActivity(new Intent(Register.this, VolunteerMain.class));
                finish();
                break;
            case Constants.USER_TYPE_ADMIN:
                startActivity(new Intent(Register.this, AdminMain.class));
                finish();
                break;
        }
    }
}