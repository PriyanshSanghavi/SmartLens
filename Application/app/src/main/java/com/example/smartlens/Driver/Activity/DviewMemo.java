package com.example.smartlens.Driver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.Model.CircularData;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.MemoData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.Activity.TpoliceCircularPdf;
import com.example.smartlens.TPolice.Activity.TpoliceViewCircular;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DviewMemo extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar vm_toolbar;
    ListView d_memo;
    List<MemoData> list;
    String id;
    DriverData driverData;
    ProgressDialog dvm_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dview_memo);
        vm_toolbar = (Toolbar) findViewById(R.id.vm_toolbar);
        d_memo = (ListView) findViewById(R.id.d_memo);
        setSupportActionBar(vm_toolbar);
        setTitle("Memo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        driverData = gson.fromJson(SharedPrefManagerNewD.getInstance(this).getUser(), DriverData.class);
        id = String.valueOf(driverData.getUser_id());

        list = new ArrayList<>();

        vm_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dhome = new Intent(getApplicationContext(), DriverHome.class);
                startActivity(dhome);
            }
        });

        dvm_pd = new ProgressDialog(DviewMemo.this);
        dvm_pd.show();
        dvm_pd.setContentView(R.layout.progress_dialog);
        dvm_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dvm_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "driver_view_memo.php?id=" + id )
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dvm_pd.dismiss();
                        int count = response.length();
                        if(count>0){
                            try {
                                loadIntoListView(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No Memo Received.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        dvm_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        list.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new MemoData(
                    obj.getInt("m_id"),
                    obj.getInt("p_status"),
                    obj.getString("title"),
                    obj.getString("amount"),
                    obj.getString("date")
            ));
        }

        d_memo.setAdapter(new DviewMemo.ListAdapter(getApplicationContext(), R.layout.custom_view_memo,list));

    }
    class ListAdapter extends ArrayAdapter {

        Context context;

        List<MemoData> list;
        public ListAdapter(Context context, int resource, List<MemoData> list) {
            super(context, R.layout.custom_view_memo,list);

            this.context=context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_view_memo,parent,false);

            final MemoData  data = list.get(position);
            final String sid = String.valueOf(data.getM_id());
            String stitle = data.getTitle();
            if(stitle.length()>21){
                stitle = stitle.substring(0,21)+"...";
            }
            final String samount = data.getAmount();
            String sdate = data.getDate();
            String year,month,day;
            year = sdate.substring(0,4);
            month = sdate.substring(5,7);
            day = sdate.substring(8);
            sdate = day+"-"+month+"-"+year;
            final int p_status = data.getP_status();
            RelativeLayout dmemo = (RelativeLayout)v.findViewById(R.id.cvm);
            dmemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String c_id = sid;
                    Intent dmemo = new Intent(getApplicationContext(), Dmemo.class);
                    dmemo.putExtra("id",sid);
                    startActivity(dmemo);
                }
            });
            TextView txt1 = (TextView) v.findViewById(R.id.dvm_title);
            txt1.setText(stitle);
            TextView txt2 = (TextView) v.findViewById(R.id.dvm_amount);
            txt2.setText("Rs. " + samount);
            TextView txt3 = (TextView) v.findViewById(R.id.dvm_date);
            txt3.setText(sdate);
            TextView txt4 = (TextView) v.findViewById(R.id.dvm_payment);
            if(p_status == 1){
            txt4.setText("Payment Done");
                txt4.setTextColor(Color.parseColor("#28B463"));
            }
            else{
                txt4.setText("Payment Due");
                txt4.setTextColor(Color.RED);
            }
            return v;
        }
    }
    @Override
    public void onBackPressed() {
        Intent dhome = new Intent(getApplicationContext(), DriverHome.class);
        startActivity(dhome);
    }
}