package com.example.sanabelalkhayr.fragments.donor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Validation;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class AddDonationFragment extends Fragment {

    Context context;
    int userId;
    SharedPrefManager prefManager;

    ArrayList<String> categoriesList;
    String selectedCategory = null;
    AppCompatSpinner mCategoriesChooser;

    ArrayList<String> regionsList;
    String selectedRegion = null;
    AppCompatSpinner mRegionsChooser;

    ImageView plus, minus;
    TextView mQuantityTV;
    int quantity = 0;

    EditText mTitleET;
    EditText mDescET;
    ImageView mImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 101;
    Uri imageUri;
    String filePath = null;
    Bitmap bitmap;


    Button mSaveBtn;


    public AddDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_donation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager = SharedPrefManager.getInstance(context);
        userId = prefManager.getUserId();

        mCategoriesChooser = view.findViewById(R.id.category_chooser);//
        mRegionsChooser = view.findViewById(R.id.region_chooser);//
        mImage = view.findViewById(R.id.image);
        mTitleET = view.findViewById(R.id.name);//
        mDescET = view.findViewById(R.id.details);//
        mSaveBtn = view.findViewById(R.id.save);
        mQuantityTV = view.findViewById(R.id.quantity);
        plus = view.findViewById(R.id.add_icon);
        minus = view.findViewById(R.id.minus_icon);

        plus.setOnClickListener(v -> {
            quantity++;
            mQuantityTV.setText(String.valueOf(quantity));
        });

        minus.setOnClickListener(v -> {
            if(quantity > 0){
                quantity--;
                mQuantityTV.setText(String.valueOf(quantity));
            }

        });

        mImage.setOnClickListener(v -> {
            requestRead();
        });

        setUpCategoryChooser();
        setUpRegionsChooser();


        mSaveBtn.setOnClickListener(v -> {
            if(Validation.validateInput(context, mTitleET, mDescET)){
                if(selectedCategory != null && selectedRegion != null && filePath != null && quantity > 0){
                    saveProduct();
                }else {
                    Toast.makeText(context, context.getResources().getString(R.string.missing_fields_message), Toast.LENGTH_SHORT);
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

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionsChooser.setAdapter(categoriesAdapter);
        mRegionsChooser.setSelection(0);
        selectedRegion = categoriesList.get(0);

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




    private void setUpCategoryChooser() {

        //get and show the categories data
        getAllCategories();

        categoriesList = new ArrayList<>();
        categoriesList.add("food");
        categoriesList.add("clothes");

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategoriesChooser.setAdapter(categoriesAdapter);
        mCategoriesChooser.setSelection(0);
        selectedCategory= categoriesList.get(0);

        //set on category chosen listener
        mCategoriesChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoriesList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //..................Methods for File Chooser.................
    public void requestRead() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            openFileChooser();
        }
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Uri picUri = imageUri;
            filePath = getPath(picUri);
            if (filePath != null) {
                bitmap = BitmapFactory.decodeFile(filePath);
                Log.d("filePath", String.valueOf(filePath));
            }
            else
            {
                Toast.makeText(getContext(),"no image selected", Toast.LENGTH_LONG).show();
            }

            Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
            Glide.with(this)
                    .load(bitmap)
                    .into(mImage);
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContext().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
    //..............................................................................


    //todo api call must get all categories to choose from
    private void getAllCategories() {

    }

    //todo api call must get all regions to choose from
    private void getAllRegions() {

    }


    //todo api call to upload a product
    private void saveProduct() {
    }
}