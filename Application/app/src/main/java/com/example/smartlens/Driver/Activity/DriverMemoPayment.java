package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverMemoPayment extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar dmp_toolbar;
    TextView tv1;
    TextInputEditText dmp_card_number,dmp_card_expiry,dmp_card_cvv,dmp_card_name,dmp_upi_id;
    RelativeLayout dmp_card_info,dmp_upi_info;
    RadioGroup payment_group;
    Button dmp_pay;
    RadioButton dmp_card,dmp_upi,select_payment;
    private int prevCount = 0;
    int count =0;
    String id,amount,payment;
    ProgressDialog dmp_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_memo_payment);
        Intent pay = getIntent();
        id = pay.getStringExtra("id");
        amount = pay.getStringExtra("amount");
        dmp_toolbar = (Toolbar) findViewById(R.id.dmp_toolbar);
        setSupportActionBar(dmp_toolbar);
        setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dmp_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dmp_card_info = (RelativeLayout) findViewById(R.id.dmp_card_info);
        dmp_upi_info = (RelativeLayout) findViewById(R.id.dmp_upi_info);
        payment_group = (RadioGroup) findViewById(R.id.payment_group);
        dmp_card = (RadioButton) findViewById(R.id.dmp_card);
        dmp_card.setChecked(true);
        dmp_card_info.setVisibility(View.VISIBLE);
        dmp_upi = (RadioButton) findViewById(R.id.dmp_upi);
        dmp_card_number = findViewById(R.id.dmp_card_number);
        dmp_card_expiry = findViewById(R.id.dmp_card_expiry);
        dmp_card_cvv = findViewById(R.id.dmp_card_cvv);
        dmp_card_name = findViewById(R.id.dmp_card_username);
        dmp_upi_id =findViewById(R.id.dmp_upi_id);
        dmp_pay = findViewById(R.id.dmp_pay);
        tv1 = findViewById(R.id.tv1);
        tv1.setText("Payable Amount : " + amount);
        dmp_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dmp_card.isChecked()) {
                    dmp_upi_id.setText("");
                    dmp_upi_info.setVisibility(View.GONE);
                    dmp_card_info.setVisibility(View.VISIBLE);
                }
            }
        });
        dmp_upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dmp_upi.isChecked()) {
                    dmp_card_number.setText("");
                    dmp_card_cvv.setText("");
                    dmp_card_expiry.setText("");
                    dmp_card_name.setText("");
                    dmp_card_info.setVisibility(View.GONE);
                    dmp_upi_info.setVisibility(View.VISIBLE);
                }
            }
        });
        dmp_card_expiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { /*Empty*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) { /*Empty*/ }

            @Override
            public void afterTextChanged(Editable s) {

                int inputlength = dmp_card_expiry.getText().toString().length();

                if (count <= inputlength && inputlength == 2){

                    dmp_card_expiry.setText(dmp_card_expiry.getText().toString() + "/");

                    int pos = dmp_card_expiry.getText().length();
                    dmp_card_expiry.setSelection(pos);

                } else if (count >= inputlength && inputlength == 2) {
                    dmp_card_expiry.setText(dmp_card_expiry.getText().toString()
                            .substring(0, dmp_card_expiry.getText()
                                    .toString().length() - 1));

                    int pos = dmp_card_expiry.getText().length();
                    dmp_card_expiry.setSelection(pos);
                }
                count = dmp_card_expiry.getText().toString().length();
            }
        });
        dmp_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String field = editable.toString();
                int currCount = field.length();
                if (shouldIncrementOrDecrement(currCount, true)){
                    appendOrStrip(field, true);
                } else if (shouldIncrementOrDecrement(currCount, false)) {
                    appendOrStrip(field, false);
                }
                prevCount = dmp_card_number.getText().toString().length();
            }
        });
        dmp_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_paymenttype = payment_group.getCheckedRadioButtonId();
                if(select_paymenttype != -1){
                    select_payment = findViewById(select_paymenttype);
                    payment = select_payment.getText().toString();
                }
                if(validate()){
                    dmp_pd = new ProgressDialog(DriverMemoPayment.this);
                    dmp_pd.show();
                    dmp_pd.setContentView(R.layout.progress_dialog);
                    dmp_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dmp_pd.setCanceledOnTouchOutside(false);
                    paynow();
                }
            }
        });
    }

    private boolean validate() {
        if(payment.equals("Credit/Debit Card")){
            String namePatten = "[A-Za-z ]{3,50}";
            if(dmp_card_number.getText().toString().trim().length() != 19){
                dmp_card_number.setError("Please Enter Valid Card Number");
                Toast.makeText(getApplicationContext(),"Please Enter Valid Card Number.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(dmp_card_expiry.getText().toString().trim().length() != 5){
                dmp_card_expiry.setError("Please Enter Valid Card Expiry Date");
                Toast.makeText(getApplicationContext(),"Please Enter Valid Card Expiry Date.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(dmp_card_expiry.getText().toString().trim().length() == 5){
                int month = Integer.parseInt(dmp_card_expiry.getText().toString().substring(0,2));
                int year = Integer.parseInt(dmp_card_expiry.getText().toString().substring(3,5));
                if(month>12){
                    dmp_card_expiry.setError("Please Enter Valid Month.");
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Month.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(year<20){
                    dmp_card_expiry.setError("Please Enter Valid Year");
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Year.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if(dmp_card_cvv.getText().toString().trim().length() != 3){
                dmp_card_cvv.setError("Please Enter Valid Card CVV");
                Toast.makeText(getApplicationContext(),"Please Enter Card CVV.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!dmp_card_name.getText().toString().trim().matches(namePatten)) {
                dmp_card_name.setError("Please Enter Name");
                Toast.makeText(getApplicationContext(),"Please Enter Name.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else if(payment.equals("BHIM UPI")) {
            String upiPatten ="[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}";
            if (!dmp_upi_id.getText().toString().trim().matches(upiPatten)) {
                dmp_upi_id.setError("Please Enter Valid Upi id");
                Toast.makeText(getApplicationContext(),"Please Enter Valid Upi Id.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        return true;
    }

    private void paynow() {
        AndroidNetworking.post(URLs.ROOT_URL + "driver_memo_pay.php?id="+id+"&type="+payment)
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    dmp_pd.dismiss();
                    Intent pd = new Intent(DriverMemoPayment.this, PaymentDone.class);
                    pd.putExtra("id",id);
                    pd.putExtra("amount",amount);
                    pd.putExtra("type",payment);
                    startActivity(pd);
                }
                @Override
                public void onError(ANError error) {
                    dmp_pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                }
            });
    }

    private boolean isAtSpaceDelimiter(int currCount) {
        return currCount == 4 || currCount == 9 || currCount == 14;
    }

    private boolean shouldIncrementOrDecrement(int currCount, boolean shouldIncrement) {
        if (shouldIncrement) {
            return prevCount <= currCount && isAtSpaceDelimiter(currCount);
        } else {
            return prevCount > currCount && isAtSpaceDelimiter(currCount);
        }
    }

    private void appendOrStrip(String field, boolean shouldAppend) {
        StringBuilder sb = new StringBuilder(field);
        if (shouldAppend) {
            sb.append(" ");
        } else {
            sb.setLength(sb.length() - 1);
        }
        dmp_card_number.setText(sb.toString());
        dmp_card_number.setSelection(sb.length());
    }
}