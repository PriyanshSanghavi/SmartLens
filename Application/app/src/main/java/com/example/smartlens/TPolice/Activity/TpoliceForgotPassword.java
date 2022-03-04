package com.example.smartlens.TPolice.Activity;

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

public class TpoliceForgotPassword extends AppCompatActivity {
    Button tpfp_btn;
    TextInputLayout tpfp_til;
    TextInputEditText tpfp_email;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_forgot_password);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        tpfp_btn = (Button)findViewById(R.id.tpfp_btn);
        tpfp_email = (TextInputEditText)findViewById(R.id.tpfp_email);
        tpfp_til =(TextInputLayout)findViewById(R.id.tpfp_til);
        tpfp_btn.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_next),null);
        tpfp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = tpfp_email.getText().toString().trim();
                if(validate()){
                    progressDialog = new ProgressDialog(TpoliceForgotPassword.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.forgotpassword_progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialog.setCanceledOnTouchOutside(false);
                    tpoliceforgotpassword();
                }
            }
        });
    }
    public Boolean validate(){
        String email_id = tpfp_email.getText().toString().trim();
        String emailPatten = "[A-Za-z0-9._-]+@[a-zA-Z]+\\.+[A-Za-z]+";

        if (!email_id.matches(emailPatten)) {
            tpfp_til.setError("Please Enter Valid Email Id");
            Toast.makeText(getApplicationContext(),"Please Enter Valid Email id", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void tpoliceforgotpassword(){
        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_forgot_password.php?email="+email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
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
                            Intent trylogin = new Intent(TpoliceForgotPassword.this, LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "Password Send successfully, pLease try to Login.", Toast.LENGTH_SHORT).show();
                            startActivity(trylogin);
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}