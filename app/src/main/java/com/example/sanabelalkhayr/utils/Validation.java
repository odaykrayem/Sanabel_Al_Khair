package com.example.sanabelalkhayr.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanabelalkhayr.R;


public class Validation {

    public static boolean validateInput(Context ctx, EditText...fields){
        for (EditText editText: fields) {
            if (editText.getText().toString().trim().isEmpty()){
                Toast.makeText(ctx, ctx.getResources().getString(R.string.missing_fields_message), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}