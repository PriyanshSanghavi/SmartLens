package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dmemo extends AppCompatActivity {
    TextInputEditText name,vehicalno,reason,amount,area,city,pstatus,date,time;
    Button pnow;
    String id,m_amount;
    ProgressDialog dmemo_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmemo);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +"MEMO"+"</font>"));
        final Intent dmemo = getIntent();
        id = dmemo.getStringExtra("id");
        name =(TextInputEditText)findViewById(R.id.dmemo_name);
        vehicalno =(TextInputEditText)findViewById(R.id.dmemo_vehicalno);
         reason=(TextInputEditText)findViewById(R.id.dmemo_reason);
         amount=(TextInputEditText)findViewById(R.id.dmemo_amount);
         area=(TextInputEditText)findViewById(R.id.dmemo_area);
         city=(TextInputEditText)findViewById(R.id.dmemo_city);
         pstatus=(TextInputEditText)findViewById(R.id.dmemo_pstatus);
         date=(TextInputEditText)findViewById(R.id.dmemo_date);
         time=(TextInputEditText)findViewById(R.id.dmemo_time);
         pnow = (Button)findViewById(R.id.dmemo_pnow);
        dmemo_pd = new ProgressDialog(Dmemo.this);
        dmemo_pd.show();
        dmemo_pd.setContentView(R.layout.progress_dialog);
        dmemo_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dmemo_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.post(URLs.ROOT_URL + "view_memo.php?id=" + id )
        .setTag("test")
        .setPriority(Priority.MEDIUM)
        .build()
        .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                dmemo_pd.dismiss();
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
                    m_amount=response.getString("amount").trim();
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
                    pnow.setVisibility(View.GONE);
                }
                else{
                    pstatus.setText("Payment Due");
                    pstatus.setInputType(InputType.TYPE_NULL);
                    pnow.setVisibility(View.VISIBLE);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError error) {
                dmemo_pd.dismiss();
                Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
            }
        });
         pnow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent pay = new Intent(Dmemo.this, DriverMemoPayment.class);
                 pay.putExtra("id",id);
                 pay.putExtra("amount",m_amount);
                 startActivity(pay);
             }
         });
    }
    @Override
    public void onBackPressed() {
        Intent dviewmemo = new Intent(getApplicationContext(), DviewMemo.class);
        startActivity(dviewmemo);
    }
}