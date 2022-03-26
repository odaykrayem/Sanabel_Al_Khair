package com.example.sanabelalkhayr.admin.fragments;

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
import com.example.sanabelalkhayr.activities.Login;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewFragment extends Fragment {

    Context context;
    Button mSaveAreaBtn, mSaveCategoryBtn;
    EditText mAreaET, mCategoryET;
    ProgressDialog pDialog;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AddNewFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
        mAreaET = view.findViewById(R.id.area);
        mCategoryET = view.findViewById(R.id.category);
        mSaveAreaBtn = view.findViewById(R.id.save_area);
        mSaveCategoryBtn = view.findViewById(R.id.save_category);

        mSaveAreaBtn.setOnClickListener(v->{
            if(Validation.validateInput(context, mAreaET)){
                saveArea();
            }
        });

        mSaveCategoryBtn.setOnClickListener(v->{
            if(Validation.validateInput(context, mCategoryET)){
                saveCategory();
            }
        });
    }

    private void saveCategory() {
        pDialog.show();
        String url = Urls.ADD_CATEGORY_URL;

        String name = mCategoryET.getText().toString().trim();
        AndroidNetworking.post(url)
                .addBodyParameter("name", name)
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
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(" catch", e.getMessage());
                            pDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Log.e("error", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("name")) {
                                Toast.makeText(context, data.getJSONArray("name").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void saveArea() {
        pDialog.show();
        String url = Urls.ADD_AREA_URL;

        String name = mAreaET.getText().toString().trim();
        AndroidNetworking.post(url)
                .addBodyParameter("name", name)
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
                            String userFounded = "Data Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(" catch", e.getMessage());
                            pDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Log.e("error", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("name")) {
                                Toast.makeText(context, data.getJSONArray("name").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}