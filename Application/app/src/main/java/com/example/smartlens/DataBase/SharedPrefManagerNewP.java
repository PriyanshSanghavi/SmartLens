package com.example.smartlens.DataBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartlens.Comman.MainActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.google.gson.Gson;

public class SharedPrefManagerNewP {

    //the constants
    private static final String SHARED_PREF_NAME = "key_name";
    private static final String  SHARED_PREF_KEY_ID = "key_obj";

    private static SharedPrefManagerNewP mInstance;
    private static Context mCtx;

    private SharedPrefManagerNewP(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerNewP getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerNewP(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(TpoliceData tpoliceData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        editor.putString( SHARED_PREF_KEY_ID, gson.toJson(tpoliceData));

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
//    public boolean isLoggedIn() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_ID, null) != null;
//    }

    //this method will give the logged in user
    public String getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new String(
                sharedPreferences.getString( SHARED_PREF_KEY_ID, "")
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREF_NAME,"");
        editor.clear();
        editor.apply();
    }
}