package com.example.smartlens.Driver.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverRegister extends AppCompatActivity {
    CircleImageView dr_pic;
    private static final int CAMERA_REQUEST=123;
    TextInputEditText dr_email, dr_name, dr_phone, dr_password, dr_cpassword, dr_address,dr_city;
    Button dr_take_pic,dr_bdate,dr_signup;
    RadioGroup dr_gender;
    RadioButton select_gender;
    int dr_year;
    int dr_month;
    TextView dr_password_msg;
    ProgressDialog progressDialog;
    int dr_day;
    File file;
    String gender="",birthdate;
    Spinner dr_state;

    Boolean click = false,isphoto = false;
    List<DriverData> driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        dr_take_pic=(Button) findViewById(R.id.dr_take_pic);
        dr_signup=(Button) findViewById(R.id.dr_signup);
        dr_gender=findViewById(R.id.dr_gender);
        dr_bdate=findViewById(R.id.dr_bdate);
        dr_name = findViewById(R.id.dr_name);
        dr_address = findViewById(R.id.dr_address);
        dr_city = findViewById(R.id.dr_city);
        dr_email = findViewById(R.id.dr_email);
        dr_phone = findViewById(R.id.dr_phone);
        dr_password = findViewById(R.id.dr_password);
        dr_cpassword = findViewById(R.id.dr_cpassword);
        dr_pic=(CircleImageView) findViewById(R.id.dr_pic);
        dr_password_msg=(TextView)findViewById(R.id.dr_password_msg);
        dr_take_pic.setPaintFlags(dr_take_pic.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        final Calendar calender = Calendar.getInstance();
        dr_year = calender.get(Calendar.YEAR);
        dr_month = calender.get(Calendar.MONTH);
        dr_day = calender.get(Calendar.DAY_OF_MONTH);
        dr_state=findViewById(R.id.dr_state);
        driver = new ArrayList<>();
        AndroidNetworking.initialize(this);
        String [] states={"Select State OR Union Territory","Andaman and Nicobar Islands","Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar","Chandigarh",
                "Chhattisgarh", "Dadra & Nagar Haveli", "Daman & Dium ","Delhi","Goa", "Gujarat", "Haryana", "Himachal Pradesh","Jammu & Kashmir", "Jharkhand", "Karnataka",
                "Kerala", "Lakshadweep","Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha","Puducherry", "Punjab", "Rajasthan",
                "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,states);
        dr_state.setAdapter(adapter);

        showDate(dr_year, dr_month+1,dr_day);
        if (click.equals(false)){
            dr_bdate.setText("Select Birth Date");
        }
        dr_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(DriverRegister.this, myDateListener, dr_year, dr_month, dr_day).show();
            }
        });


        dr_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });


        dr_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_genderid = dr_gender.getCheckedRadioButtonId();
                if(select_genderid != -1){
                    select_gender = findViewById(select_genderid);
                    gender = select_gender.getText().toString();
                }
                if(validate()){
                    progressDialog = new ProgressDialog(DriverRegister.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialog.setCanceledOnTouchOutside(false);
                    dsignup();
                }
            }
        });
    }
    public Boolean validate(){
        String name = dr_name.getText().toString().trim();
        String password = dr_password.getText().toString().trim();
        String cpassword = dr_cpassword.getText().toString().trim();
        String bdate = dr_bdate.getText().toString().trim();
        String email = dr_email.getText().toString().trim();
        String contact_no = dr_phone.getText().toString().trim();
        String city = dr_city.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";
        String passwordPatten = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
        String namePatten = "[A-Za-z ]{3,50}";
        String cityPatten = "[A-Za-z ]{3,50}";

        if(!isphoto.equals(true)){

            Toast.makeText(DriverRegister.this,"Please Select Profile picture", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!name.matches(namePatten)) {
            dr_name.setError("Please Enter Valid Name");
            Toast.makeText(DriverRegister.this,"Please Enter Your Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(gender.equals("")){
            Toast.makeText(DriverRegister.this,"Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bdate.equals("Select Birth Date")){
            Toast.makeText(DriverRegister.this,"Please Select Birth date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.matches(emailPatten)) {
            dr_email.setError("Please Enter Valid Email Id");
            Toast.makeText(DriverRegister.this,"Please Enter Your Email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(contact_no.length() != 10){
            dr_phone.setError("Please Enter Valid Contact No.");
            Toast.makeText(DriverRegister.this,"Please Enter Valid Contact no.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.matches(passwordPatten)) {
            dr_password_msg.setTextColor(Color.RED);
            dr_password.setText("");
            dr_cpassword.setText("");
            Toast.makeText(DriverRegister.this,"Password Must Contain Upper case, Lower Case and Numbers and minimum 8 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(cpassword)){
            dr_cpassword.setError("Confirm Password is Not Matched");
            Toast.makeText(DriverRegister.this,"Confirm Password Not Match With Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dr_address.getText().toString().isEmpty()) {
            dr_address.setError("Please Enter Address");
            Toast.makeText(DriverRegister.this,"Please Enter your Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!city.matches(cityPatten)) {
            dr_city.setError("Please Enter City Name");
            Toast.makeText(DriverRegister.this,"Please Enter your City", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dr_state.getSelectedItemPosition() == 0){
            ((TextView)dr_state.getSelectedView()).setError("Please Select State");
            Toast.makeText(DriverRegister.this,"Please Select Your State", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void dsignup(){

        DriverData driver = new DriverData(dr_name.getText().toString(),dr_password.getText().toString(),birthdate, gender,dr_email.getText().toString(),
                dr_phone.getText().toString(),dr_address.getText().toString(), dr_city.getText().toString(),dr_state.getSelectedItem().toString());

        AndroidNetworking.post(URLs.ROOT_URL + "driver_signup.php")
                .addBodyParameter(driver)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = null;
                        try {
                            result = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(result.equals("birthdate")){
                            progressDialog.dismiss();
                            dr_bdate.setText("Select Birth Date");
                            Toast.makeText(getApplicationContext(),"Your age Must be 18+ for Sign up.",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("number")){
                            progressDialog.dismiss();
                            dr_phone.setText("");
                            Toast.makeText(getApplicationContext(),"Contact number is Already exist",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("email")){
                            progressDialog.dismiss();
                            dr_email.setText("");
                            Toast.makeText(getApplicationContext(),"Email id is Already exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try {
                                uploadImage(response.getString("driverid"));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void uploadImage(String id)
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "driverimage.php?id=" + id )
            .addMultipartFile("image",file)
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Account Created Successfully, Please try to Login",Toast.LENGTH_SHORT).show();
                    Intent LoginIntent = new Intent(DriverRegister.this, LoginActivity.class);
                    startActivity(LoginIntent);
                }
                @Override
                public void onError(ANError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                }
            });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            dr_pic.setImageBitmap(photo);
            Date date = new Date();
            String fileName  = String.valueOf(date.getTime());
            file = persistImage(photo,fileName);
        }
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
        dr_bdate.setText( new StringBuilder("Birth Date is : ").append(day).append("/")
                .append(month).append("/").append(year));
        birthdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
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