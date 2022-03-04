package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.Model.CircularData;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TpoliceViewCircular extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar tpvc_toolbar;
    ListView tpvc_lv;
    List<CircularData> list;
    ProgressDialog tpvc_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_view_circular);
        tpvc_toolbar = (Toolbar) findViewById(R.id.tpvc_toolbar);
        setSupportActionBar(tpvc_toolbar);
        tpvc_lv =(ListView) findViewById(R.id.tpvc_lv);
        setTitle("View Circular");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list = new ArrayList<>();

        tpvc_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpvc_pd = new ProgressDialog(TpoliceViewCircular.this);
        tpvc_pd.show();
        tpvc_pd.setContentView(R.layout.progress_dialog);
        tpvc_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpvc_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_circular.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tpvc_pd.dismiss();
                        int count = response.length();
                        if(count>0){
                            try {
                                loadIntoListView(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Circular Uploaded.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        tpvc_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        list.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new CircularData(
                    obj.getInt("c_id"),
                    obj.getString("title"),
                    obj.getString("file"),
                    obj.getString("date")

            ));
        }

        tpvc_lv.setAdapter(new ListAdapter(getApplicationContext(), R.layout.custom_view_circular,list));

    }
    class ListAdapter extends ArrayAdapter {

        Context context;

        List<CircularData> list;
        public ListAdapter(Context context, int resource, List<CircularData> list) {
            super(context, R.layout.custom_view_circular,list);

            this.context=context;
            this.list = list;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_view_circular,parent,false);

            final CircularData  data = list.get(position);
            final String sid = String.valueOf(data.getC_id());
            String sname = data.getTitle();
            if(sname.length()>28){
                sname = sname.substring(0,28)+"...";
            }
            String sdate = data.getDate();
            String year,month,day;
            year = sdate.substring(0,4);
            month = sdate.substring(5,7);
            day = sdate.substring(8);
            sdate = day+"-"+month+"-"+year;
            final String sfile = data.getFile();
            RelativeLayout pdf_view = (RelativeLayout)v.findViewById(R.id.pdf_view);
            final String finalSname = sname;
            pdf_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String c_id = sid;
                    Intent cpdf = new Intent(getApplicationContext(),TpoliceCircularPdf.class);
                    cpdf.putExtra("ctitle", finalSname);
                    cpdf.putExtra("cfile",URLs.ROOT_URL + sfile);
                    Log.i("File url",URLs.ROOT_URL + sfile);
                    startActivity(cpdf);
                }
            });
            TextView txt1 = (TextView) v.findViewById(R.id.tpvc_title);
            txt1.setText(sname);
            TextView txt2 = (TextView) v.findViewById(R.id.tpvc_date);
            txt2.setText(sdate);
            return v;
        }
    }
}