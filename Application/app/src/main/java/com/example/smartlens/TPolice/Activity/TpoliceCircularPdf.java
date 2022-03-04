package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.Driver.Activity.DviewDocuments;
import com.example.smartlens.R;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.stream.Stream;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class TpoliceCircularPdf extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpcp_toolbar;
    PDFView circular_pdf;
    Button tpcp_download;
    String file,title;
    private final String CHANNEL_ID = "SmartLens_Notification";
    private final  int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_circular_pdf);
        Intent cpdf = getIntent();
        title = cpdf.getStringExtra("ctitle");
        file = cpdf.getStringExtra("cfile");
        tpcp_toolbar = (Toolbar) findViewById(R.id.tpcp_toolbar);
        tpcp_download = (Button)findViewById(R.id.tpcp_download);
        circular_pdf = (PDFView) findViewById(R.id.circular_pdf);
        new pdfdownload().execute(file);
        setSupportActionBar(tpcp_toolbar);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tpcp_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpcp_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });
    }

    private class pdfdownload extends AsyncTask<String, Void, InputStream>{
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            circular_pdf.fromStream(inputStream).load();
        }
    }
    class DownloadPdfFile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... circularURL) {
            int count;
            try {
                URL url = new URL(circularURL[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                Random random = new Random();
                int low=10,high=100;
                File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), title+random.nextInt(high-low)+low+".pdf");
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(),"Error : " + e,Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String message = "Downloaded Complete";
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(TpoliceCircularPdf.this,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.notification_logo); //set icon for notification
            builder.setContentTitle(message); //set title of notification
            builder.setContentText(title+".Pdf Successfully Downloaded.");//this is notification message
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);

            Intent notificationIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(TpoliceCircularPdf.this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(TpoliceCircularPdf.this);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        }
    }
    public void checkStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                requestDownload();
            }
            else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }else{
            requestDownload();
        }
    }
    public void requestDownload(){
        new DownloadPdfFile().execute(file);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                requestDownload();
            }
            else{
                Intent intent =  new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Toast.makeText(getApplicationContext(),"Please Provide Storage Permission",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
    public void  createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "Smart Lens";
            String description = "Download Complete";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}