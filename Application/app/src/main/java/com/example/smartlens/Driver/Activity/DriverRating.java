package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.RatingData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverRating extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar dr_toolbar;
    RatingBar dr_rating;
    EditText dr_fb;
    int  id,rating;
    DriverData driverData;
    Button dr_submit;
    ProgressDialog dr_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rating);
        dr_rating= findViewById(R.id.dr_rating);
        dr_rating.setStepSize(1);
        dr_fb= findViewById(R.id.dr_fb);
        dr_submit= findViewById(R.id.dr_submit);
        dr_toolbar = (Toolbar) findViewById(R.id.dr_toolbar);
        setSupportActionBar(dr_toolbar);
        setTitle("Rate us!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dr_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(this).getUser(),DriverData.class);
        id = driverData.getUser_id();

        dr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    dr_pd = new ProgressDialog(DriverRating.this);
                    dr_pd.show();
                    dr_pd.setContentView(R.layout.progress_dialog);
                    dr_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dr_pd.setCanceledOnTouchOutside(false);
                    drating();
                }
            }
        });
    }
    public Boolean validate() {
        rating = (int) dr_rating.getRating();
        if(rating == 0){
            Toast.makeText(DriverRating.this,"Please rate us!",Toast.LENGTH_SHORT).show();
            return false;
        }
    return true;
    }
    public void drating(){

        RatingData myrating = new RatingData(id,rating,dr_fb.getText().toString());
        AndroidNetworking.post(URLs.ROOT_URL + "driver_rating.php")
                .addBodyParameter(myrating)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dr_pd.dismiss();
                        Intent dhome = new Intent(DriverRating.this, DriverHome.class);
                        Toast.makeText(DriverRating.this,"Thank You for your feedback." ,Toast.LENGTH_SHORT).show();
                        startActivity(dhome);
                    }
                    @Override
                    public void onError(ANError error) {
                        dr_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}