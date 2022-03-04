package com.example.smartlens.TPolice.Activity;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.MemoData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.Dmemo;
import com.example.smartlens.Driver.Activity.DviewMemo;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TpoliceViewMemo extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpvm_toolbar;
    ListView tp_memo;
    List<MemoData> list;
    String id;
    TpoliceData tpoliceData;
    ProgressDialog tpvm_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_view_memo);
        tpvm_toolbar = (Toolbar) findViewById(R.id.tpvm_toolbar);
        tp_memo = findViewById(R.id.tp_memo);
        setSupportActionBar(tpvm_toolbar);
        setTitle("View Memo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        list = new ArrayList<>();
        tpvm_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpvm_pd = new ProgressDialog(TpoliceViewMemo.this);
        tpvm_pd.show();
        tpvm_pd.setContentView(R.layout.progress_dialog);
        tpvm_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpvm_pd.setCanceledOnTouchOutside(false);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_memo.php?id=" + id )
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                tpvm_pd.dismiss();
                int count = response.length();
                if(count>0){
                    try {
                        loadIntoListView(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Data Found.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError error) {
                tpvm_pd.dismiss();
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

        tp_memo.setAdapter(new TpoliceViewMemo.ListAdapter(getApplicationContext(), R.layout.custom_view_memo,list));

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
        String sdate = data.getDate();
        String year,month,day;
        year = sdate.substring(0,4);
        month = sdate.substring(5,7);
        day = sdate.substring(8);
        sdate = day+"-"+month+"-"+year;
        final String samount = data.getAmount();
        final int p_status = data.getP_status();
        RelativeLayout tpmemo = (RelativeLayout)v.findViewById(R.id.cvm);
        tpmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c_id = sid;
                Intent tpmemo = new Intent(getApplicationContext(), TpoliceMemo.class);
                tpmemo.putExtra("id",sid);
                startActivity(tpmemo);
            }
        });
        TextView txt1 = (TextView) v.findViewById(R.id.dvm_title);
        txt1.setText(stitle);
        TextView txt2 = (TextView) v.findViewById(R.id.dvm_amount);
        txt2.setText(samount);
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
}