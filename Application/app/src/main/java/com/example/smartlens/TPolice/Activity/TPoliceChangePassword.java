package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverChangePassword;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class TPoliceChangePassword extends AppCompatActivity {
    TextInputEditText tpoldpassword,tpnewpassword,tpcnewpassword;
    Button tpcp_save;
    TpoliceData tpoliceData;
    String id,opassword,npassword;
    TextView tpcp_password_msg;
    ProgressDialog tpcp_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_change_password);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        tpoldpassword = findViewById(R.id.tpoldpassword);
        tpnewpassword = findViewById(R.id.tpnewpassword);
        tpcnewpassword = findViewById(R.id.tpcnewpassword);
        tpcp_save = findViewById(R.id.tpcp_save);
        tpcp_password_msg = (TextView)findViewById(R.id.tpcp_password_msg);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        tpcp_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpcp_pd = new ProgressDialog(TPoliceChangePassword.this);
                    tpcp_pd.show();
                    tpcp_pd.setContentView(R.layout.progress_dialog);
                    tpcp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpcp_pd.setCanceledOnTouchOutside(false);
                    tpcpassword();
                }
            }
        });
    }
    public boolean validate(){
        String passwordPatten = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
        String oldpassword = tpoldpassword.getText().toString().trim();
        String newpassword = tpnewpassword.getText().toString().trim();
        String cnewpassword = tpcnewpassword.getText().toString().trim();
        if(oldpassword.equals("")){
            Toast.makeText(TPoliceChangePassword.this,"Please Enter Correct Old Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!newpassword.matches(passwordPatten)) {
            tpcp_password_msg.setTextColor(Color.RED);
            tpnewpassword.setText("");
            tpcnewpassword.setText("");
            Toast.makeText(TPoliceChangePassword.this,"Password Must Contain Upper case, Lower Case and Numbers and minimum 8 character", Toast.LENGTH_LONG).show();
            return false;
        }
        if(newpassword.equals(oldpassword)) {
            Toast.makeText(TPoliceChangePassword.this,"New Password is same as Old Password, Please enter New Password", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!cnewpassword.equals(newpassword)) {
            Toast.makeText(TPoliceChangePassword.this,"Confirm Password is not Matched.", Toast.LENGTH_LONG).show();
            return false;
        }
        npassword = newpassword;
        opassword = oldpassword;
        return true;
    }
    public void tpcpassword(){

        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_change_password.php?id=" + id+"&opassword="+opassword+"&npassword="+npassword)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpcp_pd.dismiss();
                        String status="";
                        try {
                            status =response.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")){
                            Intent tphome = new Intent(TPoliceChangePassword.this, TPoliceHome.class);
                            Toast.makeText(TPoliceChangePassword.this,"Password Change Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(tphome);
                        }
                        else if(status.equals("oldpassword")){
                            Toast.makeText(TPoliceChangePassword.this,"old Password is not Match",Toast.LENGTH_SHORT).show();
                            tpoldpassword.setError("Enter Correct Old Password");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpcp_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}