package com.example.smartlens.Driver.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.smartlens.R.layout.activity_dupload_documents;

public class DuploadDocumentsActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST=123;
    androidx.appcompat.widget.Toolbar ud_toolbar;
    ScrollView dud_sv;
    Button dud_save,dud_camera,dud_expdate;
    ImageView dud_pic;
    int dud_year,dud_month,dud_day;
    String expdate="",id;
    File file;
    public static final int REQUEST_IMAGE = 100;
    DriverData driverData;
    Boolean click = false;
    Spinner document_type;
    ProgressDialog dud_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dupload_documents);
        ud_toolbar = (Toolbar) findViewById(R.id.ud_toolbar);
        dud_save = (Button)findViewById(R.id.dud_save);
        dud_camera = (Button)findViewById(R.id.dud_camera);
        dud_pic = (ImageView)findViewById(R.id.dud_pic);
        dud_sv=(ScrollView)findViewById(R.id.dud_sv);
        dud_expdate = (Button) findViewById(R.id.dud_expdate);
        final Calendar calender = Calendar.getInstance();
        dud_year = calender.get(Calendar.YEAR);
        dud_month = calender.get(Calendar.MONTH);
        dud_day = calender.get(Calendar.DAY_OF_MONTH);
        setSupportActionBar(ud_toolbar);
        setTitle("Upload Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(getApplicationContext()).getUser(), DriverData.class);
        id = String.valueOf(driverData.getUser_id());
        ud_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        document_type=findViewById(R.id.document_type);
        String [] document={"Select Document","Front Side Driving License","Back Side Driving License","Front Side Vehical RC" ,"Back Side Vehical RC", "PUC", "Insurance"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,document);
        document_type.setAdapter(adapter);
        showDate(dud_year, dud_month+1,dud_day);
        if (click.equals(false)){
            dud_expdate.setText("Select Expiry Date");
        }
        dud_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(DuploadDocumentsActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        dud_expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(DuploadDocumentsActivity.this, myDateListener, dud_year, dud_month, dud_day).show();
            }
        });
        dud_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    dud_pd = new ProgressDialog(DuploadDocumentsActivity.this);
                    dud_pd.show();
                    dud_pd.setContentView(R.layout.progress_dialog);
                    dud_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dud_pd.setCanceledOnTouchOutside(false);
                    duploaddocument();
                }
            }
        });
    }
    public Boolean validate(){
        String expdate = dud_expdate.getText().toString().trim();
        if(document_type.getSelectedItemPosition() == 0){
            ((TextView)document_type.getSelectedView()).setError("Please Select Document Type");
            Toast.makeText(DuploadDocumentsActivity.this,"Please Select Document Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(expdate.equals("Select Expiry Date")){
            Toast.makeText(DuploadDocumentsActivity.this,"Please Select Document Expiry Date", Toast.LENGTH_SHORT).show();
            return false;
        }
       return true;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        dud_expdate.setText( new StringBuilder("Expiry Date is : ").append(day).append("/")
                .append(month).append("/").append(year));
        expdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }
    public void duploaddocument()
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "driver_upload_document.php?id=" + id +"&title="+document_type.getSelectedItem().toString()+"&exp_date="+expdate)
                .addMultipartFile("image",file)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dud_pd.dismiss();
                        String status="";
                        try {
                            status =response.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")) {
                            document_type.setSelection(0);
                            dud_pic.setImageResource(R.drawable.ic_camera);
                            dud_expdate.setVisibility(View.INVISIBLE);
                            dud_save.setVisibility(View.INVISIBLE);
                            dud_expdate.setText("Select Document Expiry Date");
                            expdate = "";
                            Snackbar.make(dud_sv,"Document Uploaded Successfully.",Snackbar.LENGTH_LONG).show();
                        }
                        else if(status.equals("expdate")){
                            Snackbar.make(dud_sv,"Your can't Uploaded expired Document.",Snackbar.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        dud_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
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
        dud_expdate.setVisibility(View.VISIBLE);
        dud_save.setVisibility(View.VISIBLE);
        return  imageFile;
    }
    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(DuploadDocumentsActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(DuploadDocumentsActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    dud_pic.setImageBitmap(photo);
                    Date date = new Date();
                    String fileName  = String.valueOf(date.getTime());
                    file = persistImage(photo,fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DuploadDocumentsActivity.this);
        builder.setTitle("Grant Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel),null);
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}