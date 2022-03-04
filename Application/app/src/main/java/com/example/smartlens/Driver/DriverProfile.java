package com.example.smartlens.Driver;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverChangePassword;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.Driver.Activity.DriverRegister;
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

public class DriverProfile extends Fragment {
    CircleImageView dp_pic;
    private static final int CAMERA_REQUEST=123;
    Spinner dp_state;
    RadioGroup dp_gender;
    RadioButton dp_male,dp_female,dp_other,select_gender;
    EditText dp_name,dp_phone, dp_email, dp_address, dp_city;
    Button dp_edit_profile,dp_take_pic,dp_bdate;
    File file;
    DriverData driverData;
    int dp_year,dp_month,dp_day;
    Boolean click = false, driverpic=false;
    String id,state,gender="",birthdate;
    ProgressDialog dp_pd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_driver_profile, container, false);
        dp_gender =(RadioGroup) v.findViewById(R.id.dp_gender);
        dp_male =(RadioButton) v.findViewById(R.id.dp_male);
        dp_female =(RadioButton) v.findViewById(R.id.dp_female);
        dp_other =(RadioButton) v.findViewById(R.id.dp_other);
        dp_name =(EditText) v.findViewById(R.id.dp_name);
        dp_bdate =(Button) v.findViewById(R.id.dp_bdate);
        dp_email =(EditText) v.findViewById(R.id.dp_email);
        dp_phone =(EditText) v.findViewById(R.id.dp_phone);
        dp_address =(EditText) v.findViewById(R.id.dp_address);
        dp_city =(EditText) v.findViewById(R.id.dp_city);
        dp_state =(Spinner) v.findViewById(R.id.dp_state);
        dp_pic = (CircleImageView)v.findViewById(R.id.dp_pic);
        dp_edit_profile=(Button)v.findViewById(R.id.dp_edit_profile);
        dp_take_pic=(Button)v.findViewById(R.id.dp_take_pic);
        final Calendar calender = Calendar.getInstance();
        dp_year = calender.get(Calendar.YEAR);
        dp_month = calender.get(Calendar.MONTH);
        dp_day = calender.get(Calendar.DAY_OF_MONTH);

        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(getActivity()).getUser(),DriverData.class);
        id = String.valueOf(driverData.getUser_id());

        String [] states={"Select State OR Union Territory","Andaman and Nicobar Islands","Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar","Chandigarh",
                "Chhattisgarh", "Dadra & Nagar Haveli", "Daman & Dium ","Delhi","Goa", "Gujarat", "Haryana", "Himachal Pradesh","Jammu & Kashmir", "Jharkhand", "Karnataka",
                "Kerala", "Lakshadweep","Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha","Puducherry", "Punjab", "Rajasthan",
                "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,states);
        dp_state.setAdapter(adapter);
        dp_pd = new ProgressDialog(getActivity());
        dp_pd.show();
        dp_pd.setContentView(R.layout.progress_dialog);
        dp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dp_pd.setCanceledOnTouchOutside(false);
        dp_edit_profile.setText("EDIT PROFILE");
        AndroidNetworking.post(URLs.ROOT_URL + "driver_view_profile.php?id="+id)
        .setTag("test")
        .setPriority(Priority.MEDIUM)
        .build()
        .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                dp_pd.dismiss();
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
                                    dp_pic.setImageBitmap(bitmap);
                                }
                                @Override
                                public void onError(ANError error) {
                                    Toast.makeText(getActivity(),"Something wrong, Couldn't fetch Profile Picture.",Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dp_name.setText(response.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String birthdate = response.getString("birthdate");
                    dp_year = Integer.parseInt(birthdate.substring(6));
                    dp_month = Integer.parseInt(birthdate.substring(3,5));
                    dp_day = Integer.parseInt(birthdate.substring(0,2));
                    showDate(dp_year,dp_month,dp_day);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String gender = null;
                try {
                    gender = response.getString("gender");
                    if(gender.equals("MALE")){
                        dp_male.setChecked(true);}
                    if(gender.equals("FEMALE")){
                        dp_female.setChecked(true);}
                    if(gender.equals("OTHER")){
                        dp_other.setChecked(true);}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dp_email.setText(response.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dp_phone.setText(response.getString("contact_no"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dp_address.setText(response.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    dp_city.setText(response.getString("city"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    state = response.getString("state");
                    if(state != null) {
                        int statepos = adapter.getPosition(state);
                        dp_state.setSelection(statepos);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError error) {
                dp_pd.dismiss();
                Toast.makeText(getActivity(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
            }
        });
        dp_take_pic.setVisibility(View.GONE);
        dp_name.setInputType(InputType.TYPE_NULL);
        dp_male.setEnabled(false);
        dp_female.setEnabled(false);
        dp_other.setEnabled(false);
        dp_email.setInputType(InputType.TYPE_NULL);
        dp_phone.setInputType(InputType.TYPE_NULL);
        dp_address.setInputType(InputType.TYPE_NULL);
        dp_city.setInputType(InputType.TYPE_NULL);
        dp_state.setEnabled(false);

        dp_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dp_edit_profile.getText()=="EDIT PROFILE")
                {
                    dp_take_pic.setVisibility(View.VISIBLE);
                    dp_male.setEnabled(true);
                    dp_female.setEnabled(true);
                    dp_other.setEnabled(true);
                    dp_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    dp_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    dp_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
                    dp_address.setInputType(InputType.TYPE_CLASS_TEXT);
                    dp_city.setInputType(InputType.TYPE_CLASS_TEXT);
                    dp_state.setEnabled(true);
                    dp_edit_profile.setText("SAVE");
                }
                else
                {
                    int select_genderid = dp_gender.getCheckedRadioButtonId();
                    if(select_genderid != -1){
                        select_gender = v.findViewById(select_genderid);
                        gender = select_gender.getText().toString();
                    }
                    if(validate()){
                        dp_pd.show();
                        dprofile();
                    }
                }
            }
        });
        showDate(dp_year, dp_month+1,dp_day);
        dp_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dp_edit_profile.getText().toString().equals("EDIT PROFILE")){

                }
                else{
                click = true;
                new DatePickerDialog(getActivity(), myDateListener, dp_year, dp_month, dp_day).show();
                }
            }
        });
        dp_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
        return v;
    }
    public Boolean validate(){
        String name = dp_name.getText().toString().trim();
        String bdate = dp_bdate.getText().toString().trim();
        String email = dp_email.getText().toString().trim();
        String contact_no = dp_phone.getText().toString().trim();
        String city = dp_city.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";
        String namePatten = "[A-Za-z ]{3,50}";
        String cityPatten = "[A-Za-z ]{3,50}";

        if (!name.matches(namePatten)) {
            dp_name.setError("Please Enter Valid Name");
            Toast.makeText(getActivity(),"Please Enter Your Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if(gender.equals("")){
            Toast.makeText(getActivity(),"Please Select Gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if(bdate.equals("Select Birth Date")){
            Toast.makeText(getActivity(),"Please Select Birth date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!email.matches(emailPatten)) {
            dp_email.setError("Please Enter Valid Email Id");
            Toast.makeText(getActivity(),"Please Enter Your Email id", Toast.LENGTH_LONG).show();
            return false;
        }
        if(contact_no.length() != 10){
            dp_phone.setError("Please Enter Valid Contact No.");
            Toast.makeText(getActivity(),"Please Enter Contact no.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (dp_address.getText().toString().isEmpty()) {
            dp_address.setError("Please Enter Address");
            Toast.makeText(getActivity(),"Please Enter your Address", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!city.matches(cityPatten)) {
            dp_city.setError("Please Enter City Name");
            Toast.makeText(getActivity(),"Please Enter your City", Toast.LENGTH_LONG).show();
            return false;
        }
        if(dp_state.getSelectedItemPosition() == 0){
            ((TextView)dp_state.getSelectedView()).setError("Please Select State");
            Toast.makeText(getActivity(),"Please Select Your State", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void dprofile(){

        DriverData driver = new DriverData(dp_name.getText().toString(),birthdate, gender,dp_email.getText().toString(),
                dp_phone.getText().toString(),dp_address.getText().toString(), dp_city.getText().toString(),dp_state.getSelectedItem().toString());
        AndroidNetworking.post(URLs.ROOT_URL + "driver_update_profile.php?id=" + id)
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
                    dp_pd.dismiss();
                    Toast.makeText(getActivity(),"Your age Must be 18+.",Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("number")){
                    dp_pd.dismiss();
                    dp_phone.setText("");
                    Toast.makeText(getActivity(),"Contact no is Already exist",Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("email")){
                    dp_pd.dismiss();
                    dp_email.setText("");
                    Toast.makeText(getActivity(),"email id is Already exist",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(driverpic.equals(true))
                    {
                        uploadImage(id);
                    }
                    else {
                        dp_pd.dismiss();
                        Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    dp_take_pic.setVisibility(View.GONE);
                    dp_male.setEnabled(false);
                    dp_female.setEnabled(false);
                    dp_other.setEnabled(false);
                    dp_name.setInputType(InputType.TYPE_NULL);
                    dp_email.setInputType(InputType.TYPE_NULL);
                    dp_phone.setInputType(InputType.TYPE_NULL);
                    dp_address.setInputType(InputType.TYPE_NULL);
                    dp_city.setInputType(InputType.TYPE_NULL);
                    dp_state.setEnabled(false);
                    dp_edit_profile.setText("EDIT PROFILE");
                }
            }
            @Override
            public void onError(ANError error) {
                Toast.makeText(getActivity(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void uploadImage(String id)
    {
        if(driverpic.equals(true)){
            AndroidNetworking.upload(URLs.ROOT_URL +  "driverimage.php?id=" + id )
                    .addMultipartFile("image",file)
                    .setTag("uploadTest")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dp_pd.dismiss();
                            Toast.makeText(getActivity(),"Profile Updated Successfully.",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(ANError error) {
                            dp_pd.dismiss();
                            Toast.makeText(getActivity(),"Something wrong, Couldn't Update Profile Picture.",Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
            Toast.makeText(getActivity(),"Please Select Image",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            dp_pic.setImageBitmap(photo);
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
        dp_bdate.setText( new StringBuilder("Birth Date is : ").append(day).append("/")
                .append(month).append("/").append(year));
        birthdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }

    private File persistImage(Bitmap bitmap, String name) {
        final String savefilename = name + ".jpg";
        File filesDir = getActivity().getFilesDir();
        File imageFile = new File(filesDir, savefilename );
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
            driverpic=true;
        } catch (Exception e) {
        }
        return  imageFile;
    }
    public void checkCameraPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
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
                Toast.makeText(getActivity(),"Please Provide Camera Permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

}