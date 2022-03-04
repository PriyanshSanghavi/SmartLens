package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.smartlens.DataBase.Model.DriverData;
import com.example.smartlens.DataBase.Model.TpLeaveData;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.SharedPrefManagerNewP;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.Dmemo;
import com.example.smartlens.Driver.Activity.DriverRegister;
import com.example.smartlens.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class TpoliceApplyLeave extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tpal_toolbar;
    Spinner tpal_leave_type;
    Button tpal_apply,tpal_fromdate,tpal_todate;
    ScrollView tpal_sv;
    EditText tpal_reason;
    int tp_year,tp_jyear,id,tp_day,tp_jday,tp_month,tp_jmonth;
    TpoliceData tpoliceData;
    String fromdate,todate;
    RadioGroup r_bg;
    ProgressDialog tpal_pd;
    RadioButton daytype,tpal_half,tpal_full;
    String day_type="";
    Boolean click = false,click1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_apply_leave);
        tpal_toolbar = (Toolbar) findViewById(R.id.tpal_toolbar);
        tpal_sv = (ScrollView)findViewById(R.id.tpal_sv);
        tpal_apply = (Button) findViewById(R.id.tpal_apply);
        tpal_fromdate = (Button) findViewById(R.id.tpal_fromdate);
        tpal_todate = (Button) findViewById(R.id.tpal_todate);
        tpal_reason = (EditText)findViewById(R.id.tpal_reason);
        tpal_half=(RadioButton)findViewById(R.id.tpal_half);
        tpal_full=(RadioButton)findViewById(R.id.tpal_full);
        r_bg = findViewById(R.id.r_bg);
        tpal_leave_type=findViewById(R.id.tpal_leave_type);
        final Calendar calender = Calendar.getInstance();
        final Calendar calender1 = Calendar.getInstance();
        tp_year = calender.get(Calendar.YEAR);
        tp_jyear = calender1.get(Calendar.YEAR);
        tp_month = calender.get(Calendar.MONTH);
        tp_jmonth = calender1.get(Calendar.MONTH);
        tp_day = calender.get(Calendar.DAY_OF_MONTH);
        tp_jday = calender1.get(Calendar.DAY_OF_MONTH);
        setSupportActionBar(tpal_toolbar);
        setTitle("Apply for Leave");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewP.getInstance(this).getUser(), TpoliceData.class);
        id = tpoliceData.getTp_id();
        tpal_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String [] leave={"Select Leave Type","C.L. (Casual Leave)", "S.L. (Sick Leave)","P.L. (Privileged Leave)", "L.W.P. (Leave Without Pay)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,leave);
        tpal_leave_type.setAdapter(adapter);
        showDate(tp_year, tp_month+1,tp_day);
        if (click == false){
            tpal_fromdate.setText("Select From Date");
        }
        tpal_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                new DatePickerDialog(TpoliceApplyLeave.this, myDateListener, tp_year, tp_month, tp_day).show();
            }
        });
        showJDate(tp_jyear, tp_jmonth+1,tp_jday);
        if (click1 == false){
            tpal_todate.setText("Select To Date");
        }
        tpal_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                click1 = true;
                new DatePickerDialog(TpoliceApplyLeave.this, myjDateListener, tp_jyear, tp_jmonth, tp_jday).show();
            }
        });
        tpal_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_dayid = r_bg.getCheckedRadioButtonId();
                if(select_dayid != -1){
                    daytype = findViewById(select_dayid);
                    day_type = daytype.getText().toString();}
                if(validate()){
                    tpal_pd = new ProgressDialog(TpoliceApplyLeave.this);
                    tpal_pd.show();
                    tpal_pd.setContentView(R.layout.progress_dialog);
                    tpal_pd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    tpal_pd.setCanceledOnTouchOutside(false);
                    tpal();
                }
            }
        });
    }
    public boolean validate(){
        String tpal_fdate = tpal_fromdate.getText().toString().trim();
        String tpal_tdate = tpal_todate.getText().toString().trim();
        String reason = tpal_reason.getText().toString().trim();
        if(tpal_leave_type.getSelectedItemPosition() == 0){
            ((TextView)tpal_leave_type.getSelectedView()).setError("Please Select Leave Type");
            Toast.makeText(TpoliceApplyLeave.this,"Please Select Leave Type", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tpal_fdate.equals("Select From Date")){
            Toast.makeText(TpoliceApplyLeave.this,"Please Select From Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tpal_tdate.equals("Select To Date")){
            Toast.makeText(TpoliceApplyLeave.this,"Please Select To Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if(day_type.equals("")){
            Toast.makeText(TpoliceApplyLeave.this,"Please Select Day Type", Toast.LENGTH_LONG).show();
            return false;
        }
        if(reason.equals("")){
            Toast.makeText(TpoliceApplyLeave.this,"Please Enter Reason", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        tpal_fromdate.setText( new StringBuilder("From Date : ").append(day).append("/")
                .append(month).append("/").append(year));
        fromdate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }
    private DatePickerDialog.OnDateSetListener myjDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showJDate(arg1, arg2+1, arg3);
                }
            };
    private void showJDate(int year, int month, int day) {
        tpal_todate.setText( new StringBuilder("To Date : ").append(day).append("/")
                .append(month).append("/").append(year));
        todate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }
    public void tpal(){

        TpLeaveData leave = new TpLeaveData(id,tpal_leave_type.getSelectedItem().toString(),tpal_reason.getText().toString(),fromdate,todate,day_type);
        AndroidNetworking.post(URLs.ROOT_URL + "tpolice_apply_leave.php")
                .addBodyParameter(leave)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tpal_pd.dismiss();
                        String status="";
                        try {
                            status =response.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equals("true")) {
                            tpal_leave_type.setSelection(0);
                            tpal_fromdate.setText("Select From Date");
                            tpal_todate.setText("Select To Date");
                            tpal_half.setChecked(false);
                            tpal_full.setChecked(false);
                            tpal_reason.setText("");
                            Snackbar.make(tpal_sv,"Successfully Apply for Leave.",Snackbar.LENGTH_LONG).show();
                        }
                        else if (status.equals("todate"))
                        {
                            Snackbar.make(tpal_sv,"Please Enter Valid To Date.",Snackbar.LENGTH_LONG).show();
                        }
                        else if(status.equals("fromdate"))
                        {
                            Snackbar.make(tpal_sv,"Please Enter Valid From Date.",Snackbar.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        tpal_pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something wrong, Couldn't Connect with the DataBase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}