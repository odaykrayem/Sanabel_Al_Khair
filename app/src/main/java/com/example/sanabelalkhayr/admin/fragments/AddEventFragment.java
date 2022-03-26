package com.example.sanabelalkhayr.admin.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class AddEventFragment extends Fragment {


    Context context;
    EditText mStartDateET, mEndDateET, mAddressET, mDescriptionET;
    String startDate, endDate, address, description;
    Button mSaveBtn;

    ProgressDialog pDialog;
    NavController navController;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AddEventFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStartDateET = view.findViewById(R.id.start_date);
        mEndDateET = view.findViewById(R.id.end_date);
        mDescriptionET = view.findViewById(R.id.description);
        mAddressET = view.findViewById(R.id.address);
        mSaveBtn = view.findViewById(R.id.save);
        navController = Navigation.findNavController(view);


        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
        mStartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog picker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mStartDateET.setText(""+dayOfMonth+"-"+monthOfYear+"-"+year);
                    }

                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                picker.show();
            }
        });

        mEndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog picker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEndDateET.setText(""+dayOfMonth+"-"+monthOfYear+"-"+year);
                    }

                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                picker.show();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validation.validateInput(context, mStartDateET, mEndDateET, mAddressET, mDescriptionET)){
                    postEvent();
                }
            }
        });
    }

    private void postEvent() {
        String url = Urls.ADD_EVENT_URL;
        pDialog.show();
        mSaveBtn.setEnabled(false);

        address = mAddressET.getText().toString();
        description = mDescriptionET.getText().toString();
        startDate = mEndDateET.getText().toString();
        endDate = mStartDateET.getText().toString();

        AndroidNetworking.post(url)
                .addBodyParameter("description", description)
                .addBodyParameter("address", address )
                .addBodyParameter("start_at", startDate )
                .addBodyParameter("end_at", endDate )
                //todelete
                .addBodyParameter("interested","1")
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
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                navController.popBackStack();
                            }else{
                                Toast.makeText(context, context.getResources().getString(R.string.add_event_error), Toast.LENGTH_SHORT).show();
                            }
                            mSaveBtn.setEnabled(true);
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch", e.getMessage());
                            mSaveBtn.setEnabled(true);
                            pDialog.dismiss();                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        mSaveBtn.setEnabled(true);
                        pDialog.dismiss();
                        Log.e("anError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("description")) {
                                Toast.makeText(context, data.getJSONArray("description").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("address")) {
                                Toast.makeText(context, data.getJSONArray("address").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("start_at")) {
                                Toast.makeText(context, data.getJSONArray("start_at").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("end_at")) {
                                Toast.makeText(context, data.getJSONArray("end_at").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}