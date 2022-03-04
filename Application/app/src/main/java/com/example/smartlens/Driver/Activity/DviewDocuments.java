package com.example.smartlens.Driver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DocumentData;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.DocumentData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DviewDocuments extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar vd_toolbar;
    Dialog docDialog;
    ListView d_document;
    List<DocumentData> list;
    String id;
    DriverData driverData;
    RelativeLayout dd_rl;
    ProgressDialog dvd_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dview_documents);
        vd_toolbar = (Toolbar) findViewById(R.id.vd_toolbar);
        d_document = findViewById(R.id.d_document);
        dd_rl = findViewById(R.id.dd_rl);
        docDialog = new Dialog(this);
        setSupportActionBar(vd_toolbar);
        setTitle("Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(this).getUser(), DriverData.class);
        id = String.valueOf(driverData.getUser_id());

        list = new ArrayList<>();
        vd_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dvd_pd = new ProgressDialog(DviewDocuments.this);
        dvd_pd.show();
        dvd_pd.setContentView(R.layout.progress_dialog);
        dvd_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dvd_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "driver_view_document.php?user_id=" + id )
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = response.length();
                        dvd_pd.dismiss();
                        if(count>0){
                            try {
                                loadIntoListView(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Document Uploaded.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        dvd_pd.dismiss();
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

        d_document.setAdapter(new DviewDocuments.ListAdapter(getApplicationContext(), R.layout.custom_view_document,list));
        registerForContextMenu(d_document);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Do you want to Delete?");
        menu.add(0,v.getId(),0,"Delete Document");
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int listPosition = info.position;
        final int doc_id = list.get(listPosition).getDoc_id();
        if(item.getTitle()=="Delete Document"){
            dvd_pd.show();
            AndroidNetworking.post(URLs.ROOT_URL + "driver_delete_document.php?id=" + doc_id)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String status="";
                            try {
                                status =response.getString("status");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(status.equals("true")){
                                Snackbar.make(dd_rl,"Document Deleted Successfully",Snackbar.LENGTH_SHORT).show();
                                AndroidNetworking.get(URLs.ROOT_URL + "driver_view_document.php?user_id=" + id )
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONArray(new JSONArrayRequestListener() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            dvd_pd.dismiss();
                                            int count = response.length();
                                            if(count>0){
                                                try {
                                                    loadIntoListView(response);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),"No Document Uploaded.",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onError(ANError error) {
                                            dvd_pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            dvd_pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                        }
        });
        }
        return true;
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
            final String d_verify = data.getVerification();
            final ImageView dvd_pic = (ImageView)v.findViewById(R.id.dvd_pic);
            dvd_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dvd_pd.show();
                    docDialog.setContentView(R.layout.custom_popup_document);
                    docDialog.setCanceledOnTouchOutside(false);
                    Button btn_close = docDialog.findViewById(R.id.popup_close);
                    AndroidNetworking.get(URLs.ROOT_URL + dfile)
                            .setTag("imageRequestTag")
                            .setPriority(Priority.MEDIUM)
                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                            .build()
                            .getAsBitmap(new BitmapRequestListener() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    // do anything with bitmap
                                    dvd_pd.dismiss();
                                    ImageView doc_pic = (ImageView)docDialog.findViewById(R.id.doc_img);
                                    doc_pic.setImageBitmap(bitmap);
                                }
                                @Override
                                public void onError(ANError error) {
                                    dvd_pd.dismiss();
                                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't fetch Document Image.",Toast.LENGTH_SHORT).show();
                                }
                            });
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            docDialog.dismiss();
                        }
                    });
                    docDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    docDialog.show();
                }
            });
            TextView txt1 = (TextView) v.findViewById(R.id.dvd_title);
            txt1.setText(dtitle);
            TextView txt2 = (TextView) v.findViewById(R.id.dvd_expdate);
            txt2.setText(dexpdate);
            AndroidNetworking.get(URLs.ROOT_URL + dfile)
                    .setTag("imageRequestTag")
                    .setPriority(Priority.MEDIUM)
                    .setBitmapConfig(Bitmap.Config.ARGB_8888)
                    .build()
                    .getAsBitmap(new BitmapRequestListener() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // do anything with bitmap
                            dvd_pic.setImageBitmap(bitmap);
                        }
                        @Override
                        public void onError(ANError error) {
                            Toast.makeText(getApplicationContext(),"Something wrong, Can't open Document.",Toast.LENGTH_SHORT).show();
                        }
                    });
            TextView txt3 = (TextView) v.findViewById(R.id.dvd_vstatus);
            if(d_verify.equals("approved")){
                txt3.setText("Verify");
                txt3.setTextColor(Color.parseColor("#28B463"));
            }
            else if(d_verify.equals("reject")){
                txt3.setText("Rejected");
                txt3.setTextColor(Color.RED);
            }
            else{
                txt3.setText("In Process");
                txt3.setTextColor(Color.rgb(255,128,0));
            }
            return v;
        }
    }
}