package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.Model.DocumentData;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DviewDocuments;
import com.example.smartlens.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TpoliceViewDdocuments extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpvdd_toolbar;
    Dialog tpdocDialog;
    ListView tpvdd_document;
    List<DocumentData> list;
    TextView tpvdd_tv;
    String id,name;
    ProgressDialog tpvd_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_view_ddocuments);
        tpvdd_toolbar = (Toolbar) findViewById(R.id.tpvdd_toolbar);
        tpvdd_document = findViewById(R.id.tpvdd_document);
        tpvdd_tv = findViewById(R.id.tpvdd_tv);
        Intent driverDocument = getIntent();
        id = driverDocument.getStringExtra("id");
        name = driverDocument.getStringExtra("name");
        tpdocDialog = new Dialog(this);
        setSupportActionBar(tpvdd_toolbar);
        setTitle("Driver Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tpvdd_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpvdd_tv.setText("Driver Name : " + name);
        list = new ArrayList<>();
        tpvd_pd = new ProgressDialog(TpoliceViewDdocuments.this);
        tpvd_pd.show();
        tpvd_pd.setContentView(R.layout.progress_dialog);
        tpvd_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpvd_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_document.php?user_id=" + id )
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tpvd_pd.dismiss();
                        int count = response.length();
                        if(count>0){
                            try {
                                loadIntoListView(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Document Uploaded by Driver.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpvd_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        list.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new DocumentData(
                    obj.getInt("doc_id"),
                    obj.getInt("user_id"),
                    obj.getString("title"),
                    obj.getString("file"),
                    obj.getString("exp_date"),
                    obj.getString("verification")
            ));
        }

        tpvdd_document.setAdapter(new TpoliceViewDdocuments.ListAdapter(getApplicationContext(), R.layout.custom_view_document,list));

    }
    class ListAdapter extends ArrayAdapter {

        Context context;

        List<DocumentData> list;
        public ListAdapter(Context context, int resource, List<DocumentData> list) {
            super(context, R.layout.custom_view_document,list);

            this.context=context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View v = getLayoutInflater().inflate(R.layout.custom_view_document,parent,false);

            final DocumentData  data = list.get(position);
            final String dtitle = data.getTitle();
            final String dfile = data.getFile();
            String dexpdate = data.getExp_date();
            String year,month,day;
            year = dexpdate.substring(0,4);
            month = dexpdate.substring(5,7);
            day = dexpdate.substring(8);
            dexpdate = "Exp Date : "+day+"-"+month+"-"+year;
            RelativeLayout doc_view = v.findViewById(R.id.doc_view);
            doc_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tpdocDialog.setContentView(R.layout.custom_popup_document);
                    tpdocDialog.setCanceledOnTouchOutside(false);
                    Button btn_close = tpdocDialog.findViewById(R.id.popup_close);
                    AndroidNetworking.get(URLs.ROOT_URL + dfile)
                            .setTag("imageRequestTag")
                            .setPriority(Priority.MEDIUM)
                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                            .build()
                            .getAsBitmap(new BitmapRequestListener() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    // do anything with bitmap
                                    ImageView doc_pic = (ImageView)tpdocDialog.findViewById(R.id.doc_img);
                                    doc_pic.setImageBitmap(bitmap);
                                }
                                @Override
                                public void onError(ANError error) {
                                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't fetch Document Image.",Toast.LENGTH_SHORT).show();
                                }
                            });
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tpdocDialog.dismiss();
                        }
                    });
                    tpdocDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    tpdocDialog.show();
                }
            });
            TextView txt1 = (TextView) v.findViewById(R.id.dvd_title);
            txt1.setText(dtitle);
            TextView txt2 = (TextView) v.findViewById(R.id.dvd_expdate);
            txt2.setText(dexpdate);
            TextView txt3 = (TextView) v.findViewById(R.id.dvd_vstatus);
            txt3.setVisibility(View.INVISIBLE);
            AndroidNetworking.get(URLs.ROOT_URL + dfile)
                    .setTag("imageRequestTag")
                    .setPriority(Priority.MEDIUM)
                    .setBitmapConfig(Bitmap.Config.ARGB_8888)
                    .build()
                    .getAsBitmap(new BitmapRequestListener() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // do anything with bitmap
                            ImageView dvd_pic = (ImageView)v.findViewById(R.id.dvd_pic);
                            dvd_pic.setImageBitmap(bitmap);
                        }
                        @Override
                        public void onError(ANError error) {
                            Toast.makeText(getApplicationContext(),"Something wrong, Can't open Document.",Toast.LENGTH_SHORT).show();
                        }
                    });
            return v;
        }
    }
}