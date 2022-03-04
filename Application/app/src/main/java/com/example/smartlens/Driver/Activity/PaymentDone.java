package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlens.R;

public class PaymentDone extends AppCompatActivity {
    String id ,amount,type;
    TextView pd_mid,pd_amount,pd_method;
    Button pd_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_done);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +"Payment Done"+"</font>"));
        pd_mid = (TextView)findViewById(R.id.pd_mid);
        pd_amount = (TextView)findViewById(R.id.pd_amount);
        pd_method = (TextView)findViewById(R.id.pd_method);
        pd_home = (Button)findViewById(R.id.pd_home);
        Intent paymentdone = getIntent();
        id = paymentdone.getStringExtra("id");
        amount = paymentdone.getStringExtra("amount");
        type = paymentdone.getStringExtra("type");
        pd_mid.setText(id);
        pd_amount.setText(amount);
        pd_method.setText(type);
        pd_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dhome = new Intent(PaymentDone.this, DriverHome.class);
                startActivity(dhome);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent dmemo = new Intent(getApplicationContext(), Dmemo.class);
        dmemo.putExtra("id",id);
        startActivity(dmemo);
    }
}