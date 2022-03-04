package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.TPoliceHomeFrag;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class TPoliceHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    TpoliceData tpoliceData;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        sharedPreferences = getSharedPreferences("MySharedPrefTpolice",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("status", true);
        myEdit.commit();
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewD.getInstance(this).getUser(), TpoliceData.class);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TPoliceHomeFrag()).commit();
            navigationView.setCheckedItem(R.id.tphome);
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tphome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TPoliceHomeFrag()).commit();
                break;
            case R.id.tprofile:
                Intent tpp_intent = new Intent(this, TpoliceProfile.class);
                startActivity(tpp_intent);
                break;
            case R.id.thelp:
                Intent tph_intent = new Intent(this, TpoliceHelp.class);
                startActivity(tph_intent);
                break;
            case R.id.view_attendance:
                Intent tpva_intent = new Intent(this, TpoliceViewAttendance.class);
                startActivity(tpva_intent);
                break;
            case R.id.view_Leave:
                Intent tpvl_intent = new Intent(this, TpoliceViewLeave.class);
                startActivity(tpvl_intent);
                break;
            case R.id.view_memo:
                Intent tpvm_intent = new Intent(this, TpoliceViewMemo.class);
                startActivity(tpvm_intent);
                break;
            case R.id.view_circular:
                Intent tpvc_intent = new Intent(this, TpoliceViewCircular.class);
                startActivity(tpvc_intent);
                break;
            case R.id.check_id:
                Intent tpcid_intent = new Intent(this, TpoliceCheckid.class);
                startActivity(tpcid_intent);
                break;
            case R.id.submit_attendance:
                Intent tpsa_intent = new Intent(this, TpoliceSubmitAttendance.class);
                startActivity(tpsa_intent);
                break;
            case R.id.send_memo:
                Intent tpsm_intent = new Intent(this, TpoliceSendMemo.class);
                startActivity(tpsm_intent);
                break;
            case R.id.apply_Leave:
                Intent tpal_intent = new Intent(this, TpoliceApplyLeave.class);
                startActivity(tpal_intent);
                break;
            case R.id.logout:
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putBoolean("status", false);
                myEdit.commit();
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}