package com.example.sanabelalkhayr.fragments.admin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.Validation;

import java.util.Calendar;

public class AddEventFragment extends Fragment {


    Context ctx;
    EditText mStartDateET, mEndDateET, mAddressET, mDescriptionET;
    String startDate, endDate, address, description;
    Button mSaveBtn;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public AddEventFragment() {
    }

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

        address = mAddressET.getText().toString();
        description = mDescriptionET.getText().toString();

        mStartDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog picker = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
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
                final DatePickerDialog picker = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
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
                if(Validation.validateInput(ctx, mStartDateET, mEndDateET, mAddressET, mDescriptionET)){
                    postEvent();
                }
            }
        });
    }

    private void postEvent() {
        String url = Urls.ADD_EVENT_URL;
    }


}