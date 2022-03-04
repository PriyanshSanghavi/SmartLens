package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TpoliceProfile extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpp_toolbar;
    CircleImageView tp_pic;
    private static final int CAMERA_REQUEST=123;
    SharedPreferences sharedPreferences;
    RadioGroup tp_gender;
    RadioButton tp_male,tp_female,tp_other,select_gender;
    Button tp_edit_profile, tp_jdate,tp_bdate,tp_take_pic;
    EditText tp_name,tp_phone,tp_email, tp_branch, tp_address, tp_city;
    int tp_year,tp_month,tp_day;
    Spinner tp_state;
    File file;
    TpoliceData tpoliceData;
    String id,state,gender="",birthdate;
    ProgressDialog tpp_pd;
    Boolean click = false, tpolicepic=false,pic=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_profile);
        tpp_toolbar = (Toolbar) findViewById(R.id.tpp_toolbar);
        setSupportActionBar(tpp_toolbar);
        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tpp_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tphome = new Intent(TpoliceProfile.this,TPoliceHome.class);
                startActivity(tphome);
            }
        });
        sharedPreferences = getSharedPreferences("MySharedPrefTpolice",MODE_PRIVATE);
        tp_gender =(RadioGroup) findViewById(R.id.tp_gender);
        tp_male =(RadioButton) findViewById(R.id.tp_male);
        tp_female =(RadioButton) findViewById(R.id.tp_female);
        tp_other =(RadioButton) findViewById(R.id.tp_other);
        tp_name =(EditText) findViewById(R.id.tp_name);
        tp_bdate =(Button) findViewById(R.id.tp_bdate);
        tp_email =(EditText) findViewById(R.id.tp_email);
        tp_phone =(EditText) findViewById(R.id.tp_phone);
        tp_branch =(EditText) findViewById(R.id.tp_branch);
        tp_jdate =(Button) findViewById(R.id.tp_jdate);
        tp_address =(EditText) findViewById(R.id.tp_address);
        tp_city =(EditText) findViewById(R.id.tp_city);
        tp_state =(Spinner) findViewById(R.id.tp_state);
        tp_edit_profile=(Button)findViewById(R.id.tp_edit_profile);
        tp_pic = (CircleImageView)findViewById(R.id.tp_pic);
        tp_take_pic=(Button)findViewById(R.id.tp_take_pic);
        final Calendar calender = Calendar.getInstance();
        tp_year = calender.get(Calendar.YEAR);
        tp_month = calender.get(Calendar.MONTH);
        tp_day = calender.get(Calendar.DAY_OF_MONTH);

        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        
        String [] states={"Select State OR Union Territory","Andaman and Nicobar Islands","Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar","Chandigarh",
                "Chhattisgarh", "Dadra & Nagar Haveli", "Daman & Dium ","Delhi","Goa", "Gujarat", "Haryana", "Himachal Pradesh","Jammu & Kashmir", "Jharkhand", "Karnataka",
                "Kerala", "Lakshadweep","Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha","Puducherry", "Punjab", "Rajasthan",
                "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,states);
        tp_state.setAdapter(adapter);
        tp_edit_profile.setText("EDIT PROFILE");
        tpp_pd = new ProgressDialog(TpoliceProfile.this);
        tpp_pd.show();
        tpp_pd.setContentView(R.layout.progress_dialog);
        tpp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpp_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_view_profile.php?id="+id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                            tpp_pd.dismiss();
                        try {
                            AndroidNetworking.get(URLs.ROOT_URL + response.getString("photo"))
                                    .setTag("imageRequestTag")
                                    .setPriority(Priority.MEDIUM)
                                    .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                    .build()
                                    .getAsBitmap(new BitmapRequestListener() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            // do anything with bitmap
                                            tp_pic.setImageBitmap(bitmap);
                                        }
                                        @Override
                                        public void onError(ANError error) {
                                            pic = false;
                                        }
                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_name.setText(response.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String birthdate = response.getString("birthdate");
                            tp_year = Integer.parseInt(birthdate.substring(6));
                            tp_month = Integer.parseInt(birthdate.substring(3,5));
                            tp_day = Integer.parseInt(birthdate.substring(0,2));
                            showDate(tp_year,tp_month,tp_day);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String gender = null;
                        try {
                            gender = response.getString("gender");
                            if(gender.equals("MALE")){
                                tp_male.setChecked(true);}
                            if(gender.equals("FEMALE")){
                                tp_female.setChecked(true);}
                            if(gender.equals("OTHER")){
                                tp_other.setChecked(true);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_email.setText(response.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_phone.setText(response.getString("contact_no"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_branch.setText(response.getString("branch"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_jdate.setText("Join Date is : " + response.getString("joindate"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_address.setText(response.getString("address"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            tp_city.setText(response.getString("city"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            state = response.getString("state");
                            if(!state.equals("")) {
                                int statepos = adapter.getPosition(state);
                                tp_state.setSelection(statepos);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpp_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_LONG).show();
                    }
                });
        tp_take_pic.setVisibility(View.GONE);
        tp_name.setInputType(InputType.TYPE_NULL);
        tp_bdate.setEnabled(false);
        tp_male.setEnabled(false);
        tp_female.setEnabled(false);
        tp_other.setEnabled(false);
        tp_email.setInputType(InputType.TYPE_NULL);
        tp_phone.setInputType(InputType.TYPE_NULL);
        tp_branch.setInputType(InputType.TYPE_NULL);
        tp_jdate.setEnabled(false);
        tp_address.setInputType(InputType.TYPE_NULL);
        tp_city.setInputType(InputType.TYPE_NULL);
        tp_state.setEnabled(false);
        tp_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tp_edit_profile.getText()=="EDIT PROFILE")
                {
                    tp_take_pic.setVisibility(View.VISIBLE);
                    tp_male.setEnabled(true);
                    tp_female.setEnabled(true);
                    tp_other.setEnabled(true);
                    tp_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    tp_bdate.setEnabled(true);
                    tp_jdate.setEnabled(true);
                    tp_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    tp_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
                    tp_address.setInputType(InputType.TYPE_CLASS_TEXT);
                    tp_city.setInputType(InputType.TYPE_CLASS_TEXT);
                    tp_state.setEnabled(true);
                    tp_edit_profile.setText("SAVE");
                }
                else
                {
                    int select_genderid = tp_gender.getCheckedRadioButtonId();
                    if(select_genderid != -1){
                        select_gender = findViewById(select_genderid);
                        gender = select_gender.getText().toString();
                    }
                    if(validate()) {
                        tpp_pd.show();
                        tpprofile();
                    }
                }
            }
        });
        showDate(tp_year, tp_month+1,tp_day);
        tp_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(TpoliceProfile.this, myDateListener, tp_year, tp_month, tp_day).show();
            }
        });
        tp_jdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Toast.makeText(getApplicationContext(),"You can't Modify Join Date",Toast.LENGTH_SHORT).show();
            }
        });
        tp_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent tphome = new Intent(TpoliceProfile.this,TPoliceHome.class);
        startActivity(tphome);
    }

    public Boolean validate(){
        String name = tp_name.getText().toString().trim();
        String bdate = tp_bdate.getText().toString().trim();
        String email = tp_email.getText().toString().trim();
        String contact_no = tp_phone.getText().toString().trim();
        String city = tp_city.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";
        String namePatten = "[A-Za-z ]{3,50}";
        String cityPatten = "[A-Za-z ]{3,50}";

        if(pic.equals(false) && tpolicepic.equals(false)){
            Toast.makeText(this,"Please Select Profile picture", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!name.matches(namePatten)) {
            tp_name.setError("Please Enter Valid Name");
            Toast.makeText(this,"Please Enter Your Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if(gender.equals("")){
            Toast.makeText(this,"Please Select Gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if(bdate.equals("Select Birth Date")){
            Toast.makeText(this,"Please Select Birth date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!email.matches(emailPatten)) {
            tp_email.setError("Please Enter Valid Email Id");
            Toast.makeText(this,"Please Enter Your Email id", Toast.LENGTH_LONG).show();
            return false;
        }
        if(contact_no.length() != 10){
            tp_phone.setError("Please Enter Valid Contact No.");
            Toast.makeText(this,"Please Enter Contact no.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (tp_address.getText().toString().isEmpty()) {
            tp_address.setError("Please Enter Address");
            Toast.makeText(this,"Please Enter your Address", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!city.matches(cityPatten)) {
            tp_city.setError("Please Enter City Name");
            Toast.makeText(this,"Please Enter your City", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tp_state.getSelectedItemPosition() == 0){
            ((TextView)tp_state.getSelectedView()).setError("Please Select State");
            Toast.makeText(this,"Please Select Your State", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void tpprofile(){

        TpoliceData tpolice1 = new TpoliceData(tp_name.getText().toString(),birthdate, gender,tp_email.getText().toString(),
                tp_phone.getText().toString(),tp_address.getText().toString(), tp_city.getText().toString(),tp_state.getSelectedItem().toString());
        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_update_profile.php?id=" + id)
                .addBodyParameter(tpolice1)
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
                            tpp_pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Please Enter Valid Birth Date.",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("number")){
                            tpp_pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Contact no is Already exist",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("email")){
                            tpp_pd.dismiss();
                            Toast.makeText(getApplicationContext(),"email id is Already exist",Toast.LENGTH_SHORT).show();
                        }
                         else{
                            if(tpolicepic.equals(true)) {
                                uploadImage(id);
                            }
                            else {
                                tpp_pd.dismiss();
                                Toast.makeText(TpoliceProfile.this, "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                            }
                            tp_take_pic.setVisibility(View.GONE);
                            tp_male.setEnabled(false);
                            tp_female.setEnabled(false);
                            tp_other.setEnabled(false);
                            tp_name.setInputType(InputType.TYPE_NULL);
                            tp_bdate.setEnabled(false);
                            tp_jdate.setEnabled(false);
                            tp_email.setInputType(InputType.TYPE_NULL);
                            tp_phone.setInputType(InputType.TYPE_NULL);
                            tp_address.setInputType(InputType.TYPE_NULL);
                            tp_city.setInputType(InputType.TYPE_NULL);
                            tp_state.setEnabled(false);
                            tp_edit_profile.setText("EDIT PROFILE");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpp_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void uploadImage(String id)
    {
        AndroidNetworking.upload(URLs.ROOT_URL +  "tpoliceimage.php?id=" + id )
            .addMultipartFile("image",file)
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    tpp_pd.dismiss();
                    Toast.makeText(TpoliceProfile.this, "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onError(ANError error) {
                    tpp_pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            tp_pic.setImageBitmap(photo);
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
        tp_bdate.setText( new StringBuilder("Birth Date is : ").append(day).append("/")
                .append(month).append("/").append(year));
        birthdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tpolicemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.password_change:
                Intent fpassword = new Intent(this, TPoliceChangePassword.class);
                startActivity(fpassword);
                break;
            case R.id.logout:
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putBoolean("status", false);
                myEdit.commit();
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                break;
        }
        return true;
    }
    private File persistImage(Bitmap bitmap, String name) {
        final String savefilename = name + ".jpg";
        File filesDir = this.getFilesDir();
        File imageFile = new File(filesDir, savefilename );
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
            tpolicepic=true;
            pic = true;
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