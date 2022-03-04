package com.example.smartlens.TPolice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.smartlens.DataBase.Model.MemoData;
import com.example.smartlens.DataBase.Model.TpAttendanceData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TpoliceViewAttendance extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpva_toolbar;
    List<TpAttendanceData> list;
    TpoliceData tpoliceData;
    ListView tpva_lv;
    int tp_year,tp_jyear,tp_day,tp_jday,tp_month,tp_jmonth;
    String fromdate,todate,id;
    Button tpva_from_date,tpva_to_date,tpva_show;
    Boolean click = false,click1 = false;
    ProgressDialog tpva_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_view_attendance);
        tpva_toolbar = (Toolbar) findViewById(R.id.tpva_toolbar);
        tpva_from_date = findViewById(R.id.tpva_from_date);
        tpva_to_date = findViewById(R.id.tpva_to_date);
        tpva_show = findViewById(R.id.tpva_show);
        setSupportActionBar(tpva_toolbar);
        tpva_lv = findViewById(R.id.tpva_lv);
        final Calendar calender = Calendar.getInstance();
        final Calendar calender1 = Calendar.getInstance();
        tp_year = calender.get(Calendar.YEAR);
        tp_jyear = calender1.get(Calendar.YEAR);
        tp_month = calender.get(Calendar.MONTH);
        tp_jmonth = calender1.get(Calendar.MONTH);
        tp_day = calender.get(Calendar.DAY_OF_MONTH);
        tp_jday = calender1.get(Calendar.DAY_OF_MONTH);
        setTitle("View Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        list = new ArrayList<>();
        tpva_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpva_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpva_pd = new ProgressDialog(TpoliceViewAttendance.this);
                    tpva_pd.show();
                    tpva_pd.setContentView(R.layout.progress_dialog);
                    tpva_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpva_pd.setCanceledOnTouchOutside(false);
                    tpva();
                }
            }
        });
        showfromDate(tp_year, tp_month+1,tp_day);
        if (click == false){
            tpva_from_date.setText("From Date");
        }
        tpva_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(TpoliceViewAttendance.this, myDateListener, tp_year, tp_month, tp_day).show();
            }
        });
        showtoDate(tp_jyear, tp_jmonth+1,tp_jday);
        if (click1 == false){
            tpva_to_date.setText("To Date");
        }
        tpva_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                click1 = true;
                new DatePickerDialog(TpoliceViewAttendance.this, myjDateListener, tp_jyear, tp_jmonth, tp_jday).show();
            }
        });
    }
    public boolean validate(){
        String fdate = tpva_from_date.getText().toString().trim();
        String tdate = tpva_to_date.getText().toString().trim();
        if(fdate.equals("From Date")){
            Toast.makeText(TpoliceViewAttendance.this,"Please Enter From Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tdate.equals("To Date")){
            Toast.makeText(TpoliceViewAttendance.this,"Please Enter To Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void tpva(){

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_attendance.php?id=" + id+"&fromdate="+fromdate+"&todate="+todate)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tpva_pd.dismiss();
                        int count = response.length();
                        if(count<=0) {
                            Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            loadIntoListView(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        tpva_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showfromDate(arg1, arg2+1, arg3);
                }
            };
    private void showfromDate(int year, int month, int day) {
        tpva_from_date.setText( new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        fromdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }
    private DatePickerDialog.OnDateSetListener myjDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showtoDate(arg1, arg2+1, arg3);
                }
            };
    private void showtoDate(int year, int month, int day) {
        tpva_to_date.setText( new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        todate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

    }
    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        list.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new TpAttendanceData(
                    obj.getInt("tp_id"),
                    obj.getString("date"),
                    obj.getString("in_time"),
                    obj.getString("out_time")
            ));
        }

        tpva_lv.setAdapter(new TpoliceViewAttendance.ListAdapter(getApplicationContext(), R.layout.custom_view_attendance,list));

    }
    class ListAdapter extends ArrayAdapter {

        Context context;

        List<TpAttendanceData> list;
        public ListAdapter(Context context, int resource, List<TpAttendanceData> list) {
            super(context, R.layout.custom_view_attendance,list);

            this.context=context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_view_attendance,parent,false);

            final TpAttendanceData  data = list.get(position);
            final String aid = String.valueOf(data.getA_id());
            String adate = data.getDate();
            String year,month,day;
            year = adate.substring(0,4);
            month = adate.substring(5,7);
            day = adate.substring(8);
            adate = day+"-"+month+"-"+year;
            final String ain_time = data.getIn_time();
            final String aout_time = data.getOut_time();
            TextView txt1 = (TextView) v.findViewById(R.id.tpva_date);
            txt1.setText(adate);
            TextView txt2 = (TextView) v.findViewById(R.id.tpva_intime);
            txt2.setText(ain_time);
            TextView txt3 = (TextView) v.findViewById(R.id.tpva_outtime);
            txt3.setText(aout_time);
            return v;
        }
    }
}