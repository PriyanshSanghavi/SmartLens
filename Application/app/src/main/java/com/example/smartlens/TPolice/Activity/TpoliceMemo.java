package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.Dmemo;
import com.example.smartlens.Driver.Activity.DriverMemoPayment;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class TpoliceMemo extends AppCompatActivity {
    TextInputEditText name,vehicalno,reason,amount,area,city,pstatus,date,time;
    String id;
    ProgressDialog tpm_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_memo);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +"MEMO"+"</font>"));
        final Intent tpmemo = getIntent();
        id = tpmemo.getStringExtra("id");
        name =(TextInputEditText)findViewById(R.id.tpmemo_name);
        vehicalno =(TextInputEditText)findViewById(R.id.tpmemo_vehicalno);
        reason=(TextInputEditText)findViewById(R.id.tpmemo_reason);
        amount=(TextInputEditText)findViewById(R.id.tpmemo_amount);
        area=(TextInputEditText)findViewById(R.id.tpmemo_area);
        city=(TextInputEditText)findViewById(R.id.tpmemo_city);
        pstatus=(TextInputEditText)findViewById(R.id.tpmemo_pstatus);
        date=(TextInputEditText)findViewById(R.id.tpmemo_date);
        time=(TextInputEditText)findViewById(R.id.tpmemo_time);
        tpm_pd = new ProgressDialog(TpoliceMemo.this);
        tpm_pd.show();
        tpm_pd.setContentView(R.layout.progress_dialog);
        tpm_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpm_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(URLs.ROOT_URL + "view_memo.php?id=" + id )
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpm_pd.dismiss();
                        try {
                            name.setText(response.getString("name"));
                            name.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            vehicalno.setText(response.getString("vehical_no"));
                            vehicalno.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            reason.setText(response.getString("reason"));
                            reason.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            amount.setText(response.getString("amount"));
                            amount.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            area.setText(response.getString("area"));
                            area.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            city.setText(response.getString("city"));
                            city.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String sdate = response.getString("date");
                            String year,month,day;
                            year = sdate.substring(0,4);
                            month = sdate.substring(5,7);
                            day = sdate.substring(8);
                            sdate = day+"-"+month+"-"+year;
                            date.setText(sdate);
                            date.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            time.setText(response.getString("time"));
                            time.setInputType(InputType.TYPE_NULL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if(response.getString("p_status").equals("1")){
                                pstatus.setText("Payment Done");
                                pstatus.setInputType(InputType.TYPE_NULL);
                            }
                            else{
                                pstatus.setText("Payment Due");
                                pstatus.setInputType(InputType.TYPE_NULL);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpm_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}