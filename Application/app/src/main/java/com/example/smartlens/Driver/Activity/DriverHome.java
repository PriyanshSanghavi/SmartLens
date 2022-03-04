package com.example.smartlens.Driver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.Comman.LoginActivity;
import com.example.smartlens.Comman.MainActivity;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.DriverHomeFrag;
import com.example.smartlens.Driver.DriverHelp;
import com.example.smartlens.Driver.DriverProfile;
import com.example.smartlens.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverHome extends AppCompatActivity {
    BottomNavigationView bnavig;
    DriverData driverData;
    SharedPreferences sharedPreferences;
    String user_id;
    private final String CHANNEL_ID = "SmartLens_Notification";
    private final  int NOTIFICATION_ID = 001,NOTIFICATION_ID1 = 002;
    ProgressDialog dh_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name)+"</font>"));
        bnavig=(BottomNavigationView)findViewById(R.id.bnavig);
        bnavig.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new DriverHomeFrag()).commit();
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(getApplicationContext()).getUser(), DriverData.class);
        user_id = String.valueOf(driverData.getUser_id());
        sharedPreferences = getSharedPreferences("MySharedPrefDriver",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("status", true);
        myEdit.commit();
        AndroidNetworking.get(URLs.ROOT_URL + "check_document_expiry.php?id=" + user_id )
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status="";
                        try {
                            status = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")){
                            int soon_exp=0,already_exp=0;
                            try {
                                soon_exp=response.getInt("soon_exp");
                                already_exp=response.getInt("already_exp");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(soon_exp>0){
                                String message = "Your Some Document Expire Soon!";
                                createNotificationChannel();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(DriverHome.this,CHANNEL_ID);
                                builder.setSmallIcon(R.drawable.notification_logo); //set icon for notification
                                builder.setContentTitle(message); //set title of notification
                                builder.setContentText("Please renew your Documents as soon as possible.");//this is notification message
                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                builder.setAutoCancel(true);

                                Intent notificationIntent = new Intent(DriverHome.this, DviewDocuments.class);
                                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(DriverHome.this, 0, notificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(DriverHome.this);
                                notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                            }
                            if(already_exp>0){
                                String message = "Your Some Document are Expired!";
                                createNotificationChannel();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(DriverHome.this,CHANNEL_ID);
                                builder.setSmallIcon(R.drawable.notification_logo); //set icon for notification
                                builder.setContentTitle(message); //set title of notification
                                builder.setContentText("Please renew your Documents and upload again.");//this is notification message
                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                builder.setAutoCancel(true);

                                Intent notificationIntent = new Intent(DriverHome.this, DviewDocuments.class);
                                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(DriverHome.this, 0, notificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(DriverHome.this);
                                notificationManagerCompat.notify(NOTIFICATION_ID1,builder.build());
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drivermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.driver_password_change:
                Intent fpassword = new Intent(this, DriverChangePassword.class);
                startActivity(fpassword);
                break;
            case R.id.driver_logout:
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putBoolean("status", false);
                myEdit.commit();
                Intent driver_logout = new Intent(this, LoginActivity.class);
                startActivity(driver_logout);
                break;
            case R.id.driver_delete_account:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to Delete your Account Permanently?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dh_pd = new ProgressDialog(DriverHome.this);
                                dh_pd.show();
                                dh_pd.setContentView(R.layout.progress_dialog);
                                dh_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dh_pd.setCanceledOnTouchOutside(false);
                            AndroidNetworking.post(URLs.ROOT_URL + "driver_delete_account.php?id="+ user_id)
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        dh_pd.dismiss();
                                        String status=null;
                                        try {
                                            status =response.getString("status");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if(status.equals("due")){
                                            Toast.makeText(getApplicationContext(),"Please first pay your due memo amount.",Toast.LENGTH_SHORT).show();
                                        }
                                        else if(status.equals("true")) {
                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                            myEdit.putBoolean("status", false);
                                            myEdit.commit();
                                            Intent driver_logout = new Intent(DriverHome.this, LoginActivity.class);
                                            Toast.makeText(getApplicationContext(),"Your Account Deleted Successfully.",Toast.LENGTH_SHORT).show();
                                            startActivity(driver_logout);
                                        }
                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        dh_pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null;
            switch (menuItem.getItemId()){
                case R.id.home:
                    fragment = new DriverHomeFrag();
                    break;

                case R.id.profile:
                    fragment = new DriverProfile();
                    break;

                case R.id.help:
                    fragment = new DriverHelp();
                    break;

            }
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
                return true;
            }
            return true;
        }
    };
    public void  createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "Smart Lens";
            String description = "Document expiry Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}