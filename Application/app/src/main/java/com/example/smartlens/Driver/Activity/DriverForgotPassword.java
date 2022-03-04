package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverForgotPassword extends AppCompatActivity {
    Button dfp_btn;
    TextInputEditText dfp_email;
    TextInputLayout dfp_til;
    String email;
    ProgressDialog dfp_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_forgot_password);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        dfp_btn = (Button)findViewById(R.id.dfp_btn);
        dfp_email = (TextInputEditText)findViewById(R.id.dfp_email);
        dfp_til =(TextInputLayout)findViewById(R.id.dfp_til);
        dfp_btn.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_next),null);
        dfp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = dfp_email.getText().toString().trim();
                if(validate()){
                    dfp_pd = new ProgressDialog(DriverForgotPassword.this);
                    dfp_pd.show();
                    dfp_pd.setContentView(R.layout.forgotpassword_progress_dialog);
                    dfp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dfp_pd.setCanceledOnTouchOutside(false);
                    driverforgotpassword();
                }
            }
        });
    }
    public Boolean validate(){
        String email_id = dfp_email.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";

        if (!email_id.matches(emailPatten)) {
            dfp_til.setError("Please Enter Valid Email Id");
            Toast.makeText(DriverForgotPassword.this,"Please Enter Valid Email id", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void driverforgotpassword(){
        AndroidNetworking.post(URLs.ROOT_URL + "driver_forgot_password.php?email="+email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dfp_pd.dismiss();
                        String result = null;
                        try {
                            result = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(result.equals("server")){
                            Toast.makeText(getApplicationContext(),"Server is too Slow, Please try after Sometime.",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("false")){
                            Toast.makeText(getApplicationContext(),"email id is Not exist",Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("true")) {
                            Intent trylogin = new Intent(DriverForgotPassword.this, LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "Password Send successfully, pLease try to Login.", Toast.LENGTH_SHORT).show();
                            startActivity(trylogin);
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        dfp_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}