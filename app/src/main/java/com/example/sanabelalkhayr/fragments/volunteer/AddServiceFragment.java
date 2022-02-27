package com.example.sanabelalkhayr.fragments.volunteer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.adapters.donor.MyDonationsAdapter;
import com.example.sanabelalkhayr.model.Donation;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Validation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddServiceFragment extends Fragment {

    Context ctx;

    int userId;
    SharedPrefManager prefManager;

    ArrayList<String> regionsList;
    String selectedRegion = null;
    AppCompatSpinner mRegionsChooser;

    EditText mDescET;
    Button mSaveBtn;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }


    public AddServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = SharedPrefManager.getInstance(ctx);
        userId = prefManager.getUserId();

        mRegionsChooser = view.findViewById(R.id.region_chooser);
        mDescET = view.findViewById(R.id.description);
        mSaveBtn = view.findViewById(R.id.save);

        setUpRegionsChooser();
        mSaveBtn.setOnClickListener(v -> {
            if(Validation.validateInput(ctx, mDescET)){
                if(selectedRegion != null){
                    saveService();
                }else {
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.missing_fields_message), Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void setUpRegionsChooser() {

        //get and show the categories data
        getAllRegions();
        regionsList = new ArrayList<>();
        regionsList.add("jaddah");
        regionsList.add("alryadh");

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, regionsList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionsChooser.setAdapter(categoriesAdapter);
        mRegionsChooser.setSelection(0);
        selectedRegion = regionsList.get(0);

        //set on category chosen listener
        mRegionsChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regionsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveService() {
    }
    private void getAllRegions() {
    }
}