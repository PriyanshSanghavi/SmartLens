package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.Comman.MainActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.Driver.Activity.DriverRating;
import com.example.smartlens.Driver.Activity.DriverRegister;
import com.example.smartlens.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TpoliceSubmitAttendance extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpsa_toolbar;
    ImageView tpsa_pic;
    Button tpsa_camera,tpsa_submit;
    private static final int CAMERA_REQUEST=123,REQUEST_CHECK_SETTING = 1001,PERMISSION_CODE =3;
    Spinner tpsa_atype;
    File file;
    String id;
    TpoliceData tpoliceData;
    String latitude = "latitude",longitude = "longitude";
    LocationRequest locationRequest;
    FusedLocationProviderClient Client;
    Boolean isphoto=false;
    ProgressDialog tpsa_pd;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_submit_attendance);
        tpsa_toolbar = (Toolbar) findViewById(R.id.tpsa_toolbar);
        tpsa_camera = findViewById(R.id.tpsa_camera);
        tpsa_submit = findViewById(R.id.tpsa_submit);
        tpsa_pic = findViewById(R.id.tpsa_pic);
        tpsa_atype=findViewById(R.id.tpsa_atype);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        requestgps();
        Client = LocationServices.getFusedLocationProviderClient(this);
        setSupportActionBar(tpsa_toolbar);
        setTitle("Submit Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tpsa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String [] atype={"Select Attendance type","In Time","Out Time"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,atype);
        tpsa_atype.setAdapter(adapter);
        tpsa_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    getLocation();
                }
                else{
                    requestPermission();
                }
                checkCameraPermission();
            }
        });
        tpsa_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpsa_pd = new ProgressDialog(TpoliceSubmitAttendance.this);
                    tpsa_pd.show();
                    tpsa_pd.setContentView(R.layout.progress_dialog);
                    tpsa_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpsa_pd.setCanceledOnTouchOutside(false);
                    submitattendance();
                }

            }
        });
    }
    public Boolean validate() {
        if(tpsa_atype.getSelectedItemPosition() == 0){
            ((TextView)tpsa_atype.getSelectedView()).setError("Please Select Attendance Type.");
            Toast.makeText(getApplicationContext(),"Please Select Attendance Type.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isphoto.equals(true)){
            Toast.makeText(getApplicationContext(),"Please, Take a picture", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(latitude.equals("latitude") || longitude.equals("longitude")){
            Toast.makeText(getApplicationContext(),"Something Wrong, We Couldn't fetch your GPS Location.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void submitattendance()
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "tpolice_submit_attendance.php?id=" + id +"&type="+tpsa_atype.getSelectedItem()+"&latitude="+latitude+"&longitude="+longitude)
                .addMultipartFile("image",file)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpsa_pd.dismiss();
                        String status="";
                        try {
                            status =response.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")) {
                            Intent LoginIntent = new Intent(TpoliceSubmitAttendance.this, TPoliceHome.class);
                            Toast.makeText(getApplicationContext(), "Attendance Submitted Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(LoginIntent);
                        }
                        else if(status.equals("face")){
                            Toast.makeText(getApplicationContext(), "We couldn't Recognise your face. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("intimeexsist")){
                            Toast.makeText(getApplicationContext(), "Your In Time Attendance is Already Submitted.", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("outtimeexsist")){
                            Toast.makeText(getApplicationContext(), "Your Out Time Attendance is Already Submitted.", Toast.LENGTH_SHORT).show();
                        }
                        else if(status.equals("nointime")){
                            Toast.makeText(getApplicationContext(), "First you have try to Submit In Time Attendance.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpsa_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void getLocation(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                    if(location!=null){
                        latitude = String.valueOf(location.getLatitude());
                        longitude= String.valueOf(location.getLongitude());
                    }else{
                        LocationRequest request= new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(500)
                                .setNumUpdates(1);
                        checkPermission();
                        LocationCallback callback = new LocationCallback(){
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult){
                                super.onLocationResult(locationResult);
                                Location location = locationResult.getLastLocation();
                                latitude = String.valueOf(location.getLatitude());
                                longitude= String.valueOf(location.getLongitude());
                            }
                        };
                        Client.requestLocationUpdates(locationRequest,callback,Looper.myLooper());
                    }
                }
            });
        }
        else{
            requestgps();
        }
    }
    public boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    public void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                requestCamera();
            }
            else{
                Intent intent =  new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Toast.makeText(getApplicationContext(),"Please Provide Camera Permission",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        if(requestCode==PERMISSION_CODE && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            getLocation();
        }
        else{
            Toast.makeText(this,"Please Provide Location Permission",Toast.LENGTH_SHORT).show();
        }
    }

    public void requestgps(){
        locationRequest =LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                }
                catch (ApiException e) {
                    switch(e.getStatusCode()){
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                resolvableApiException.startResolutionForResult(TpoliceSubmitAttendance.this,1001);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                            }
                            break;
                    }
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            tpsa_pic.setImageBitmap(photo);
            Date date = new Date();
            String fileName  = String.valueOf(date.getTime());
            file = persistImage(photo,fileName);
            tpsa_submit.setVisibility(View.VISIBLE);
        }
        if(requestCode ==REQUEST_CHECK_SETTING){
            switch(resultCode){
                case Activity.RESULT_OK:
                    Toast.makeText(getApplicationContext(),"GPS is now turn on",Toast.LENGTH_SHORT).show();
                    getLocation();
                    break;
                case Activity.RESULT_CANCELED:
                    Intent tphome = new Intent(TpoliceSubmitAttendance.this, TPoliceHome.class);
                    Toast.makeText(getApplicationContext(),"GPS required to be turn on",Toast.LENGTH_SHORT).show();
                    startActivity(tphome);
                    break;
            }
        }
    }
    private File persistImage(Bitmap bitmap, String name) {

        final String savefilename = name + ".jpg";
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, savefilename );
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
            isphoto=true;
        }
        catch (Exception e) {
        }
        return  imageFile;
    }
    public void checkCameraPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                requestCamera();
            }
            else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
            }
        }else{
            requestCamera();
        }
    }
    public void requestCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }
}