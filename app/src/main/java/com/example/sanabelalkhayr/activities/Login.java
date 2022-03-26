package com.example.sanabelalkhayr.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button mLoginBtn,mToRegisterBtn;
    private EditText mUserNameET, mPassET;
    private ProgressDialog pDialog;

    int userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserNameET = findViewById(R.id.user_name);
        mPassET = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.btnLogin);
        mToRegisterBtn = findViewById(R.id.btnLinkToRegisterScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

        // Login button Click Event
        mLoginBtn.setOnClickListener(view -> {
            //validation
            String userName = mUserNameET.getText().toString().trim();
            String password = mPassET.getText().toString().trim();

            // Check for empty data in the form
            if (!userName.isEmpty() && !password.isEmpty()) {
                // login user
                mLoginBtn.setEnabled(false);
                login(userName, password);
            } else {
                // Prompt user to enter credentials
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.user_and_password_required), Toast.LENGTH_LONG)
                        .show();
            }
        });

        // Link to Register Screen
        mToRegisterBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Register.class));
        });
    }

    private void login(String userName, String password) {
        pDialog.show();
        mLoginBtn.setEnabled(false);
        String url = Urls.LOGIN_URL;

        AndroidNetworking.post(url)
                .addBodyParameter("password", password)
                .addBodyParameter("user_name", userName)
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
                            String message = obj.getString("message");
                            String userFounded = "User founded";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("data");
                                User user;
                                user = new User(
                                        Integer.parseInt(userJson.getString("id")),
                                        userJson.getString("name"),
                                        userJson.getString("user_name"),
                                        userJson.getString("phone"),
                                        userJson.getString("address"),
                                        userJson.getInt("type")
                                );

                                userType= user.getType();
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                goToUserMainActivity(userType);

                            }else{
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                            mLoginBtn.setEnabled(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("login catch", e.getMessage());
                            pDialog.dismiss();
                            mLoginBtn.setEnabled(true);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mLoginBtn.setEnabled(true);
                        Log.e("loginerror", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(Login.this, error.getString("message"), Toast.LENGTH_SHORT).show();
                              if (data.has("user_name")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("user_name").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("password")) {
                                Toast.makeText(getApplicationContext(), data.getJSONArray("password").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void goToUserMainActivity(int selectedUserType) {
        switch (selectedUserType){
            case Constants.USER_TYPE_MAIN:
                startActivity(new Intent(this, UserMain.class));
                finish();
                break;
            case Constants.USER_TYPE_DONOR:
                startActivity(new Intent(this, DonorMain.class));
                finish();
                break;
            case Constants.USER_TYPE_VOLUNTEER:
                startActivity(new Intent(this, VolunteerMain.class));
                finish();
                break;
            case Constants.USER_TYPE_ADMIN:
                startActivity(new Intent(this, AdminMain.class));
                finish();
                break;
        }
    }
}