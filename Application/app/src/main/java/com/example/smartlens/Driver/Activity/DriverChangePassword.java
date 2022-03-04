package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverChangePassword extends AppCompatActivity {
    Button dcp_save;
    TextInputEditText dold_password,dnew_password,dcnew_password;
    DriverData driverData;
    String id,opassword,npassword;
    TextView dcp_password_msg;
    ProgressDialog dcp_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_change_password);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        dold_password=findViewById(R.id.dold_password);
        dnew_password=findViewById(R.id.dnew_password);
        dcnew_password=findViewById(R.id.dcnew_password);
        dcp_save = (Button)findViewById(R.id.dcp_save);
        dcp_password_msg = (TextView)findViewById(R.id.dcp_password_msg);
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(this).getUser(), DriverData.class);
        id = String.valueOf(driverData.getUser_id());
        dcp_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    dcp_pd = new ProgressDialog(DriverChangePassword.this);
                    dcp_pd.show();
                    dcp_pd.setContentView(R.layout.progress_dialog);
                    dcp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dcp_pd.setCanceledOnTouchOutside(false);
                    dcpassword();
                }
            }
        });
    }
    public boolean validate(){
        String passwordPatten = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
        String oldpassword = dold_password.getText().toString().trim();
        String newpassword = dnew_password.getText().toString().trim();
        String cnewpassword = dcnew_password.getText().toString().trim();
        if(oldpassword.equals("")){
            Toast.makeText(DriverChangePassword.this,"Please Enter Old Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!newpassword.matches(passwordPatten)) {
            dcp_password_msg.setTextColor(Color.RED);
            dnew_password.setText("");
            dcnew_password.setText("");
            Toast.makeText(DriverChangePassword.this,"Password Must Contain Upper case, Lower Case and Numbers and minimum 8 character", Toast.LENGTH_LONG).show();
            return false;
        }
        if(newpassword.equals(oldpassword)) {
            Toast.makeText(DriverChangePassword.this,"New Password is same as Old Password, Please enter New Password", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!cnewpassword.equals(newpassword)) {
            Toast.makeText(DriverChangePassword.this,"Confirm Password is not Matched.", Toast.LENGTH_LONG).show();
            return false;
        }
        npassword = newpassword;
        opassword = oldpassword;
        return true;
    }
    public void dcpassword(){

        AndroidNetworking.post(URLs.ROOT_URL + "driver_change_password.php?id=" + id+"&opassword="+opassword+"&npassword="+npassword)
        .setTag("test")
        .setPriority(Priority.MEDIUM)
        .build()
        .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                dcp_pd.dismiss();
                String status="";
                try {
                    status =response.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status.equals("true")){

                    Intent dhome = new Intent(DriverChangePassword.this, DriverHome.class);
                    Toast.makeText(DriverChangePassword.this,"Password Change Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(dhome);
                }
                else if(status.equals("oldpassword")){
                    Toast.makeText(DriverChangePassword.this,"old Password is not Match",Toast.LENGTH_SHORT).show();
                    dold_password.setError("Enter Correct Old Password");
                }
            }
            @Override
            public void onError(ANError error) {
                dcp_pd.dismiss();
                Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}