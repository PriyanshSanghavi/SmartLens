package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TpoliceProfilePic extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tppp_toolbar;
    private static final int CAMERA_REQUEST=123;
    CircleImageView tppp_pic;
    Button tppp_camera,tppp_save;
    String id;
    File file;
    TpoliceData tpoliceData;
    ProgressDialog tppp_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_profile_pic);
        tppp_camera = (Button)findViewById(R.id.tppp_camera);
        tppp_save = (Button)findViewById(R.id.tppp_save);
        tppp_pic = (CircleImageView)findViewById(R.id.tppp_pic);
        tppp_toolbar = (Toolbar) findViewById(R.id.tppp_toolbar);
        setSupportActionBar(tppp_toolbar);
        setTitle("Profile Picture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(getApplicationContext()).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        tppp_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tppp_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
        tppp_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tppp_pd = new ProgressDialog(TpoliceProfilePic.this);
                tppp_pd.show();
                tppp_pd.setContentView(R.layout.progress_dialog);
                tppp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                tppp_pd.setCanceledOnTouchOutside(false);
                Tppp();
            }
        });
    }
    public void Tppp()
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "tpoliceimage.php?id=" + id)
                .addMultipartFile("image",file)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tppp_pd.dismiss();
                        try {
                            String photo = response.getString("photo");
                            tpoliceData.setPhoto(photo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent tphome = new Intent(TpoliceProfilePic.this, TPoliceHome.class);
                        Toast.makeText(getApplicationContext(),"Profile Picture Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(tphome);
                    }
                    @Override
                    public void onError(ANError error) {
                        tppp_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            tppp_pic.setImageBitmap(photo);
            Date date = new Date();
            String fileName  = String.valueOf(date.getTime());
            file = persistImage(photo,fileName);
            tppp_save.setVisibility(View.VISIBLE);
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
        } catch (Exception e) {
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