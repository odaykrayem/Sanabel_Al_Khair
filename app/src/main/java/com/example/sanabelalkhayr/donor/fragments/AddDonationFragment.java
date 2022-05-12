package com.example.sanabelalkhayr.donor.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.activities.Login;
import com.example.sanabelalkhayr.donor.adapters.CategoriesAdapter;
import com.example.sanabelalkhayr.donor.adapters.RegionsAdapter;
import com.example.sanabelalkhayr.model.Category;
import com.example.sanabelalkhayr.model.Region;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Urls;
import com.example.sanabelalkhayr.utils.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class AddDonationFragment extends Fragment {

    Context context;
    int userId;
    SharedPrefManager prefManager;

    ArrayList<Category> categoriesList;
    int selectedCategory = -1;
    AppCompatSpinner mCategoriesChooser;

    ArrayList<Region> regionsList;
    int selectedRegion = -1;
    AppCompatSpinner mRegionsChooser;

    ImageView plus, minus;
    TextView mQuantityTV;
    int quantity = 1;

    EditText mTitleET;
    EditText mDescET;
    ImageView mImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 101;
    Uri imageUri;
    String filePath = null;
    Bitmap bitmap;


    Button mSaveBtn;
    ProgressDialog pDialog;

    NavController navController;


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
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
        mCategoriesChooser = view.findViewById(R.id.category_chooser);//
        mRegionsChooser = view.findViewById(R.id.region_chooser);//
        mImage = view.findViewById(R.id.image);
        mTitleET = view.findViewById(R.id.name);//
        mDescET = view.findViewById(R.id.details);//
        mSaveBtn = view.findViewById(R.id.save);
        mQuantityTV = view.findViewById(R.id.quantity);
        mQuantityTV.setText("1");
        plus = view.findViewById(R.id.add_icon);
        minus = view.findViewById(R.id.minus_icon);

        navController = Navigation.findNavController(view);
        plus.setOnClickListener(v -> {
            quantity++;
            mQuantityTV.setText(String.valueOf(quantity));
        });

        minus.setOnClickListener(v -> {
            if(quantity > 1){
                quantity--;
                mQuantityTV.setText(String.valueOf(quantity));
            }

        });

        mImage.setOnClickListener(v -> {
            requestRead();
        });

        getAllCategories();
        getAllRegions();
        mSaveBtn.setOnClickListener(v -> {
            if(Validation.validateInput(context, mTitleET, mDescET)){
                if(selectedCategory != -1 && selectedRegion != -1 && filePath != null && quantity > 0){
                    saveDonation();
                }else {
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

    private void setUpCategoryChooser() {

        //get and show the categories data

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(context, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        mCategoriesChooser.setAdapter(categoriesAdapter);
        mCategoriesChooser.setSelection(categoriesAdapter.getCount());
        mCategoriesChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoriesList.get(position).getId();
                Log.e("selected Category", categoriesList.get(position).getName());
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

    private void getAllCategories() {
        String url = Urls.GET_CATEGORIES;
        categoriesList = new ArrayList<Category>();

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
                            String dataGot = "Data Got";
                            if (message.toLowerCase().contains(dataGot.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_success), Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    categoriesList.add(
                                            new Category(
                                                    Integer.parseInt(jsonObject.getString("id")),
                                                    jsonObject.getString("name")
                                            )
                                    );
                                }
                                pDialog.dismiss();
                                setUpCategoryChooser();

                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.get_categories_error), Toast.LENGTH_SHORT).show();
                            }

                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("anError", e.getMessage());
                            pDialog.dismiss();
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

    private void saveDonation() {
        pDialog.show();
        mSaveBtn.setEnabled(false);
        String url = Urls.ADD_DONATION_URL;
        String description = mDescET.getText().toString().trim();
        String title = mTitleET.getText().toString().trim();
        Log.e("username",SharedPrefManager.getInstance(context).getUserData().getUserName());

        String id = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.upload(url)
                .addMultipartFile("image_url", new File(filePath))
                .addMultipartParameter("user_id", id)
                .addMultipartParameter("title", title)
                .addMultipartParameter("description", description)
                .addMultipartParameter("category", String.valueOf(selectedCategory))
                .addMultipartParameter("region", String.valueOf(selectedRegion))
                .addMultipartParameter("donor_user_name", SharedPrefManager.getInstance(context).getUserData().getUserName())
                .addMultipartParameter("quantity", String.valueOf(quantity))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            Toast.makeText(context, error.getString("data"), Toast.LENGTH_SHORT).show();
                            if (data.has("image_url")) {
                                Toast.makeText(context, data.getJSONArray("image_url").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("user_id")) {
                                Toast.makeText(context, data.getJSONArray("user_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("title")) {
                                Toast.makeText(context, data.getJSONArray("title").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("description")) {
                                Toast.makeText(context, data.getJSONArray("description").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("category")) {
                                Toast.makeText(context, data.getJSONArray("category").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("region")) {
                                Toast.makeText(context, data.getJSONArray("region").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("donor_user_name")) {
                                Toast.makeText(context, data.getJSONArray("donor_user_name").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("quantity")) {
                                Toast.makeText(context, data.getJSONArray("quantity").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}