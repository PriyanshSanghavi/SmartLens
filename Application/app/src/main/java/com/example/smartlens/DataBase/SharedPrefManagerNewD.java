package com.example.smartlens.DataBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartlens.Comman.MainActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class SharedPrefManagerNewD {

    //the constants
    private static final String KEY_ID = "key_obj";
    private static final String KEY_NAME = "key_name";

    private static SharedPrefManagerNewD mInstance;
    private static Context mCtx;

    private SharedPrefManagerNewD(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerNewD getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerNewD(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(DriverData driverData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        editor.putString(KEY_ID,gson.toJson(driverData));

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
//    public boolean isLoggedIn() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_ID, null) != null;
//    }

    //this method will give the logged in user
    public String getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        return new String(
                sharedPreferences.getString(KEY_ID, "")
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,"");
        editor.clear();
        editor.apply();
    }
}