package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.MemoData;
import com.example.smartlens.DataBase.Model.TpLeaveData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class TpoliceSendMemo extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpsm_toolbar;
    Button tpsm_send;
    EditText tpsm_phone,tpsm_vnumber,tpsm_area,tpsm_city,tpsm_amount;
    MultiAutoCompleteTextView tpsm_reason;
    TpoliceData tpoliceData;
    int id;
    ProgressDialog tpsm_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_send_memo);
        String reason[] = {"Over Speed","Break Signal","Wrong Side Driving",
                "Missing Driving License","PUC Expire","Insurance expire","Missing PUC","Missing Insurance","Missing Rc"};
        tpsm_send = findViewById(R.id.tpsm_send);
        tpsm_phone = findViewById(R.id.tpsm_phone);
        tpsm_vnumber = findViewById(R.id.tpsm_vnumber);
        tpsm_area = findViewById(R.id.tpsm_area);
        tpsm_city = findViewById(R.id.tpsm_city);
        tpsm_amount = findViewById(R.id.tpsm_amount);
        tpsm_reason = findViewById(R.id.tpsm_reason);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,reason);
        tpsm_reason.setAdapter(adapter);
        tpsm_reason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        tpsm_toolbar = (Toolbar) findViewById(R.id.tpsm_toolbar);
        setSupportActionBar(tpsm_toolbar);
        setTitle("Send Memo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = tpoliceData.getTp_id();
        tpsm_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpsm_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpsm_pd = new ProgressDialog(TpoliceSendMemo.this);
                    tpsm_pd.show();
                    tpsm_pd.setContentView(R.layout.progress_dialog);
                    tpsm_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpsm_pd.setCanceledOnTouchOutside(false);
                    tpsm();
                }

            }
        });
    }
    public boolean validate(){
        String phone = tpsm_phone.getText().toString().trim();
        String vnumber = tpsm_vnumber.getText().toString().trim();
        String reason = tpsm_reason.getText().toString().trim();
        String amount = tpsm_amount.getText().toString().trim();
        String area = tpsm_area.getText().toString().trim();
        String city = tpsm_city.getText().toString().trim();
        String vehicalPatten="[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,2}[0-9]{4}";
        String cityPatten = "[A-Za-z ]{3,50}";
        if(phone.length()!=10){
            tpsm_phone.setError("Enter Valid Contact no");
            Toast.makeText(TpoliceSendMemo.this,"Please Enter Driver Contact no",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!vnumber.matches(vehicalPatten)){
            tpsm_vnumber.setError("Enter Valid Vehicle number (Don't Use Space)");
            Toast.makeText(TpoliceSendMemo.this,"Please enter valid vehicle number",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(reason.equals("")){
            tpsm_reason.setError("Enter Reason");
            Toast.makeText(TpoliceSendMemo.this,"Please enter Reason",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(amount.equals("")){
            tpsm_amount.setError("Enter Amount");
            Toast.makeText(TpoliceSendMemo.this,"Please enter Amount",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(area.equals("")){
            tpsm_area.setError("Enter area");
            Toast.makeText(TpoliceSendMemo.this,"Please enter area",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!city.matches(cityPatten)){
            tpsm_city.setError("Enter city");
            Toast.makeText(TpoliceSendMemo.this,"Please enter city",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void tpsm(){

        MemoData memo = new MemoData(id,tpsm_vnumber.getText().toString(),tpsm_reason.getText().toString(),
                tpsm_amount.getText().toString(),tpsm_area.getText().toString(),tpsm_city.getText().toString(),tpsm_phone.getText().toString());
        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_send_memo.php")
            .addBodyParameter(memo)
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    tpsm_pd.dismiss();
                    String result = null;
                    try {
                        result = response.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(result.equals("number")){
                        Toast.makeText(getApplicationContext(),"Any Driver is not Registered with this Contact no.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent tphome = new Intent(TpoliceSendMemo.this, TPoliceHome.class);
                        Toast.makeText(getApplicationContext(), "Memo Send Successfully", Toast.LENGTH_LONG).show();
                        startActivity(tphome);
                    }
                }
                @Override
                public void onError(ANError error) {
                    tpsm_pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                }
            });
    }
}