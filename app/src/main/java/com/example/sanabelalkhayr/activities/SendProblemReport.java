package com.example.sanabelalkhayr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SendProblemReport extends AppCompatActivity {

    SharedPrefManager prefManager;
    int userId;

    EditText mTitleET, mDetailsET;
    Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_problem_report);

        prefManager = SharedPrefManager.getInstance(this);
        userId = prefManager.getUserId();


        mSendBtn = findViewById(R.id.send_report_btn);
        mTitleET = findViewById(R.id.title);
        mDetailsET = findViewById(R.id.details);


        mSendBtn.setOnClickListener(v -> {
            mSendBtn.setEnabled(false);
            if(validateUserData()){
                sendReport();
            }
        });
    }

    private boolean validateUserData() {

        //first getting the values
        final String title = mTitleET.getText().toString();
        final String details = mDetailsET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "report title must not be empty!", Toast.LENGTH_SHORT).show();
            mSendBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(details)) {
            Toast.makeText(this, "please provide us with your opinion before sending!", Toast.LENGTH_LONG).show();
            mSendBtn.setEnabled(true);
            return false;
        }

        return true;

    }


    private void sendReport() {

        String myId = String.valueOf(userId);

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String title = mTitleET.getText().toString();
        final String details = mDetailsET.getText().toString();

        String url = "";

        AndroidNetworking.post(url)
                .addBodyParameter("title", title)
                .addBodyParameter("content", details)
                .addBodyParameter("user_id", myId)
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
                            Toast.makeText(SendProblemReport.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            //if no error in response
                            if (obj.getInt("status") == 1) {
                                mSendBtn.setEnabled(true);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Toast.makeText(SendProblemReport.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}