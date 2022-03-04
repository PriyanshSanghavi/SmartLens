package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class TpoliceCheckid extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpcid_toolbar;
    ImageView tpcid_pic;
    Button tpcid_camera,tpcid_submit;
    private static final int CAMERA_REQUEST=123;
    File file;
    ProgressDialog tpcid_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_checkid);
        tpcid_pic = (ImageView)findViewById(R.id.tpcid_pic);
        tpcid_camera = (Button)findViewById(R.id.tpcid_camera);
        tpcid_submit = (Button)findViewById(R.id.tpcid_submit);
        tpcid_toolbar = (Toolbar) findViewById(R.id.tpcid_toolbar);
        setSupportActionBar(tpcid_toolbar);
        setTitle("Check Id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tpcid_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpcid_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
        tpcid_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpcid_pd = new ProgressDialog(TpoliceCheckid.this);
                tpcid_pd.show();
                tpcid_pd.setContentView(R.layout.progress_dialog);
                tpcid_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                tpcid_pd.setCanceledOnTouchOutside(false);
                checkid();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            tpcid_pic.setImageBitmap(photo);
            Date date = new Date();
            String fileName  = String.valueOf(date.getTime());
            file = persistImage(photo,fileName);
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
            tpcid_submit.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {
        }
        return  imageFile;
    }
    public void checkid()
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "tpolice_check_id.php")
                .addMultipartFile("image",file)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpcid_pd.dismiss();
                        String status="";
                        try {
                            status =response.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")) {
                            String id=null,name=null;
                            try {
                                id = response.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                name=response.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent DriverDocument = new Intent(TpoliceCheckid.this, TpoliceViewDdocuments.class);
                            Toast.makeText(getApplicationContext(), "Driver Document Fetch Successfully", Toast.LENGTH_SHORT).show();
                            DriverDocument.putExtra("id",id);
                            DriverDocument.putExtra("name",name);
                            startActivity(DriverDocument);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "We couldn't Recognise Driver face. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpcid_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
    }
}