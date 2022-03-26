package com.example.sanabelalkhayr.volunteer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.donor.adapters.RegionsAdapter;
import com.example.sanabelalkhayr.model.Region;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class AddServiceFragment extends Fragment {

    Context context;

    int userId;

    ArrayList<Region> regionsList;
    int selectedRegion = -1;
    AppCompatSpinner mRegionsChooser;

    EditText mDescET;
    Button mSaveBtn;
    ProgressDialog pDialog;
    NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AddServiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId =SharedPrefManager.getInstance(context).getUserId();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.setCancelable(false);
        navController = Navigation.findNavController(view);

        mRegionsChooser = view.findViewById(R.id.region_chooser);
        mDescET = view.findViewById(R.id.description);
        mSaveBtn = view.findViewById(R.id.save);

        setUpRegionsChooser();
        mSaveBtn.setOnClickListener(v -> {
            if (Validation.validateInput(context, mDescET)) {
                if (selectedRegion != -1) {
                    addService();
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.missing_fields_message), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void setUpRegionsChooser() {
        RegionsAdapter regionsAdapter = new RegionsAdapter(context, R.layout.support_simple_spinner_dropdown_item, regionsList);
        mRegionsChooser.setAdapter(regionsAdapter);
        mRegionsChooser.setSelection(regionsAdapter.getCount());
        mRegionsChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regionsList.get(position).getId();
                Log.e("selected region", regionsList.get(position).getName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addService() {
        pDialog.show();
        mSaveBtn.setEnabled(false);
        String url = Urls.ADD_SERVICE_URL;
        String description = mDescET.getText().toString().trim();

        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", id)
                .addBodyParameter("description", description)
                .addBodyParameter("region", String.valueOf(selectedRegion))
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

                                navController.popBackStack();
                            }
                            pDialog.dismiss();
                            mSaveBtn.setEnabled(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            mSaveBtn.setEnabled(true);
                            Log.e("add don catch", e.getMessage());

                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mSaveBtn.setEnabled(true);
                        Log.e("add don anError", anError.getErrorBody());

                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("user_name")) {
                                Toast.makeText(context, data.getJSONArray("user_name").toString(), Toast.LENGTH_SHORT).show();
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

    private void getAllRegions() {
        String url = Urls.GET_REGIONS;
        regionsList = new ArrayList<Region>();
        pDialog.show();

        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Data Got";
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.get_regions_success), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    regionsList.add(
                                            new Region(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    jsonObject.getString("name")
                                            )
                                    );
                                }
                                pDialog.dismiss();
                                setUpRegionsChooser();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_error), Toast.LENGTH_SHORT).show();
                            }

                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("anError", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Log.e("anError", error.getErrorBody());
                        Toast.makeText(context, context.getResources().getString(R.string.error_get_info), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}