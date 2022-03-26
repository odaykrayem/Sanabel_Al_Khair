package com.example.sanabelalkhayr.all.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class SendProblemReportFragment extends Fragment {

    Context context;

    EditText mTitleET, mDetailsET;
    Button mSendReportBtn;

    ProgressDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public SendProblemReportFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_problem_report, container, false);
        mTitleET = view.findViewById(R.id.title);
        mDetailsET = view.findViewById(R.id.details);
        mSendReportBtn = view.findViewById(R.id.send_report_btn);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");

        mSendReportBtn.setOnClickListener(v -> {
            if(Validation.validateInput(context, mTitleET, mDetailsET)){
                sendReport();
            }

        });


    }

    private void sendReport() {
        String url = Urls.FEEDBACK_URL;


        pDialog.show();
        mSendReportBtn.setEnabled(false);

        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        String title = mTitleET.getText().toString().trim();
        String content = mDetailsET.getText().toString().trim();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", id)
                .addBodyParameter("title", title )
                .addBodyParameter("content", content )
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
                            String userFounded = "Data Got";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.report_success), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, context.getResources().getString(R.string.add_event_error), Toast.LENGTH_SHORT).show();
                            }
                            mSendReportBtn.setEnabled(true);
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch", e.getMessage());
                            mSendReportBtn.setEnabled(true);
                            pDialog.dismiss();                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        mSendReportBtn.setEnabled(true);
                        pDialog.dismiss();
                        Log.e("anError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("title")) {
                                Toast.makeText(context, data.getJSONArray("title").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("content")) {
                                Toast.makeText(context, data.getJSONArray("content").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

