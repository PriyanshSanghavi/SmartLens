package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.TpAttendanceData;
import com.example.smartlens.DataBase.Model.TpLeaveData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TpoliceViewLeave extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpvl_toolbar;
    List<TpLeaveData> list;
    TpoliceData tpoliceData;
    ListView tpvl_lv;
    int tp_year,tp_jyear,tp_day,tp_jday,tp_month,tp_jmonth;
    ProgressDialog tpvl_pd;
    String fromdate,todate,id;
    Button tpvl_from_date,tpvl_to_date,tpvl_show;
    Boolean click = false,click1 = false;
    RelativeLayout tpvl_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_view_leave);
        tpvl_toolbar = (Toolbar) findViewById(R.id.tpvl_toolbar);
        setSupportActionBar(tpvl_toolbar);
        tpvl_from_date = findViewById(R.id.tpvl_from_date);
        tpvl_to_date = findViewById(R.id.tpvl_to_date);
        tpvl_show = (Button) findViewById(R.id.tpvl_show);
        tpvl_lv = findViewById(R.id.tpvl_lv);
        final Calendar calender = Calendar.getInstance();
        final Calendar calender1 = Calendar.getInstance();
        tp_year = calender.get(Calendar.YEAR);
        tp_jyear = calender1.get(Calendar.YEAR);
        tp_month = calender.get(Calendar.MONTH);
        tp_jmonth = calender1.get(Calendar.MONTH);
        tp_day = calender.get(Calendar.DAY_OF_MONTH);
        tp_jday = calender1.get(Calendar.DAY_OF_MONTH);
        tpvl_rl = (RelativeLayout)findViewById(R.id.tpvl_rl);
        setTitle("View Leave");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());
        list = new ArrayList<>();
        tpvl_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tpvl_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    tpvl_pd = new ProgressDialog(TpoliceViewLeave.this);
                    tpvl_pd.show();
                    tpvl_pd.setContentView(R.layout.progress_dialog);
                    tpvl_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpvl_pd.setCanceledOnTouchOutside(false);
                    tpvl();
                }
            }
        });
        showfromDate(tp_year, tp_month+1,tp_day);
        if (click == false){
            tpvl_from_date.setText("From Date");
        }
        tpvl_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(TpoliceViewLeave.this, myDateListener, tp_year, tp_month, tp_day).show();
            }
        });
        showtoDate(tp_jyear, tp_jmonth+1,tp_jday);
        if (click1 == false){
            tpvl_to_date.setText("To Date");
        }
        tpvl_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                click1 = true;
                new DatePickerDialog(TpoliceViewLeave.this, myjDateListener, tp_jyear, tp_jmonth, tp_jday).show();
            }
        });
    }
    public boolean validate(){
        String fdate = tpvl_from_date.getText().toString().trim();
        String tdate = tpvl_to_date.getText().toString().trim();
        if(fdate.equals("From Date")){
            Toast.makeText(TpoliceViewLeave.this,"Please Enter From Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tdate.equals("To Date")){
            Toast.makeText(TpoliceViewLeave.this,"Please Enter To Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void tpvl(){

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_leave.php?id=" + id+"&fromdate="+fromdate+"&todate="+todate)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    tpvl_pd.dismiss();
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
                    tpvl_pd.dismiss();
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
        tpvl_from_date.setText( new StringBuilder().append(day).append("/")
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
        tpvl_to_date.setText( new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        todate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

    }
    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        list.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            list.add(new TpLeaveData(
                    obj.getInt("l_id"),
                    obj.getString("type"),
                    obj.getString("from_date"),
                    obj.getString("to_date"),
                    obj.getString("permission")
            ));
        }

        tpvl_lv.setAdapter(new TpoliceViewLeave.ListAdapter(getApplicationContext(), R.layout.custom_view_lapplication, list));
        registerForContextMenu(tpvl_lv);

    }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Do you want to Delete?");
            menu.add(0,v.getId(),0,"Delete Leave");
        }

        @Override
        public boolean onContextItemSelected(final MenuItem item) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int listPosition = info.position;
            final int l_id = list.get(listPosition).getL_id();
            if(item.getTitle()=="Delete Leave"){
                tpvl_pd.show();
                AndroidNetworking.post(URLs.ROOT_URL + "tpolice_delete_leave.php?id=" + l_id)
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
                                if(status.equals("false")){
                                    tpvl_pd.dismiss();
                                    Snackbar.make(tpvl_rl,"You can Delete only Future Leave.",Snackbar.LENGTH_SHORT).show();
                                }
                                else if(status.equals("true")){
                                    Snackbar.make(tpvl_rl,"Leave Application Deleted Successfully.",Snackbar.LENGTH_SHORT).show();
                                    AndroidNetworking.get(URLs.ROOT_URL + "tpolice_view_leave.php?id=" + id+"&fromdate="+fromdate+"&todate="+todate)
                                            .setPriority(Priority.MEDIUM)
                                            .build()
                                            .getAsJSONArray(new JSONArrayRequestListener() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                    tpvl_pd.dismiss();
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
                                                    tpvl_pd.dismiss();
                                                    Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                tpvl_pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            return true;
        }
    class ListAdapter extends ArrayAdapter {

        Context context;

        List<TpLeaveData> list;
        public ListAdapter(Context context, int resource, List<TpLeaveData> list) {
            super(context, R.layout.custom_view_lapplication,list);

            this.context=context;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_view_lapplication,parent,false);

            final TpLeaveData  data = list.get(position);
            final String lid = String.valueOf(data.getL_id());
            final String ltype = data.getType();
            String lfrom_date = data.getFrom_date();
            String year,month,day;
            year = lfrom_date.substring(0,4);
            month = lfrom_date.substring(5,7);
            day = lfrom_date.substring(8);
            lfrom_date = day+"-"+month+"-"+year;
            String lto_date = data.getTo_date();
            year = lto_date.substring(0,4);
            month = lto_date.substring(5,7);
            day = lto_date.substring(8);
            lto_date = day+"-"+month+"-"+year;
            final String permission = data.getPermission();
            TextView txt1 = (TextView) v.findViewById(R.id.tpvl_leavetype);
            txt1.setText(ltype);
            TextView txt2 = (TextView) v.findViewById(R.id.tpvl_fromdate);
            txt2.setText(lfrom_date);
            TextView txt3 = (TextView) v.findViewById(R.id.tpvl_todate);
            txt3.setText(lto_date);
            TextView txt4 = (TextView) v.findViewById(R.id.tpvl_permission);
            if(permission.equals("approved")){
                txt4.setText("Approved");
                txt4.setTextColor(Color.parseColor("#28B463"));
            }
            else if(permission.equals("reject")){
                txt4.setText("Rejected");
                txt4.setTextColor(Color.RED);
            }
            else{
                txt4.setText("In Process");
                txt4.setTextColor(Color.rgb(255,128,0));
            }
            return v;
        }
    }
}