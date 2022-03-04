package com.example.smartlens.Comman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.Activity.TPoliceHome;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 3000;
    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView tv1;
    SharedPreferences driver,tpolice ;

    Boolean dlogin,tplogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        tv1 = findViewById(R.id.tv1);
        driver = getSharedPreferences("MySharedPrefDriver", MODE_PRIVATE);
        dlogin = driver.getBoolean("status",false);
        tpolice = getSharedPreferences("MySharedPrefTpolice", MODE_PRIVATE);
        tplogin = tpolice.getBoolean("status",false);
        logo.setAnimation(topAnim);
        tv1.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(dlogin.equals(true))
                    {
                        Intent dhome = new Intent(MainActivity.this, DriverHome.class);
                        startActivity(dhome);

                    }
                    else if(tplogin.equals(true))
                    {
                        Intent tphome = new Intent(MainActivity.this, TPoliceHome.class);
                        startActivity(tphome);
                    }
                    else
                    {
                        Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    }
                    finish();
                }
            },TIME_OUT);


    }

}