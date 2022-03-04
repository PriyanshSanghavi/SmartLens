package com.example.smartlens.TPolice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.smartlens.DataBase.Model.TpoliceData;
import com.example.smartlens.DataBase.SharedPrefManagerNewD;
import com.example.smartlens.DataBase.URLs;
import com.example.smartlens.Driver.Activity.DriverHome;
import com.example.smartlens.Driver.Activity.DviewDocuments;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.Activity.TpoliceApplyLeave;
import com.example.smartlens.TPolice.Activity.TpoliceCheckid;
import com.example.smartlens.TPolice.Activity.TpoliceProfilePic;
import com.example.smartlens.TPolice.Activity.TpoliceSendMemo;
import com.example.smartlens.TPolice.Activity.TpoliceSubmitAttendance;
import com.example.smartlens.TPolice.Activity.TpoliceViewAttendance;
import com.example.smartlens.TPolice.Activity.TpoliceViewCircular;
import com.example.smartlens.TPolice.Activity.TpoliceViewLeave;
import com.example.smartlens.TPolice.Activity.TpoliceViewMemo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TPoliceHomeFrag extends Fragment {

    ImageView view_attendance,view_leave,view_memo,view_circular;
    TextView dh_tv3;
    RelativeLayout tppp_view;
    TpoliceData tpoliceData;
    String id;
    Button check_id,submit_attendance,apply_leave,send_memo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tpolice_home, container, false);
        dh_tv3 =(TextView)v.findViewById(R.id.dh_tv3);
        check_id=(Button)v.findViewById(R.id.check_id);
        submit_attendance=(Button)v.findViewById(R.id.submit_attendance);
        apply_leave=(Button)v.findViewById(R.id.apply_leave);
        send_memo=(Button)v.findViewById(R.id.send_memo);
        tppp_view = (RelativeLayout)v.findViewById(R.id.tppp_view);
        view_attendance=(ImageView)v.findViewById(R.id.view_attendance);
        view_leave=(ImageView)v.findViewById(R.id.view_leave);
        view_memo=(ImageView)v.findViewById(R.id.view_memo);
        view_circular=(ImageView)v.findViewById(R.id.view_circular);
        ImageSlider imageSlider =v.findViewById(R.id.tpolice_slider);
        List<SlideModel> sliders = new ArrayList<>();
        sliders.add(new SlideModel(R.drawable.tp1, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.tp2, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.tp3, ScaleTypes.FIT));
        imageSlider.setImageList(sliders, ScaleTypes.FIT);
        Gson gson = new Gson();
        tpoliceData = gson.fromJson(SharedPrefManagerNewD.getInstance(getActivity()).getUser(), TpoliceData.class);
        id = String.valueOf(tpoliceData.getTp_id());

        AndroidNetworking.get(URLs.ROOT_URL + "check_tpolice_profile_picture.php?id=" + id )
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
                            tppp_view.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });
        check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpcid_intent = new Intent(getActivity(), TpoliceCheckid.class);
                startActivity(tpcid_intent);
            }
        });
        send_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpsm_intent = new Intent(getActivity(), TpoliceSendMemo.class);
                startActivity(tpsm_intent);
            }
        });
        submit_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpsa_intent = new Intent(getActivity(), TpoliceSubmitAttendance.class);
                startActivity(tpsa_intent);
            }
        });
        apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpal_intent = new Intent(getActivity(), TpoliceApplyLeave.class);
                startActivity(tpal_intent);
            }
        });
        view_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpva_intent = new Intent(getActivity(), TpoliceViewAttendance.class);
                startActivity(tpva_intent);
            }
        });
        view_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpvl_intent = new Intent(getActivity(), TpoliceViewLeave.class);
                startActivity(tpvl_intent);
            }
        });
        view_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpvm_intent = new Intent(getActivity(), TpoliceViewMemo.class);
                startActivity(tpvm_intent);
            }
        });
        view_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tpvc_intent = new Intent(getActivity(), TpoliceViewCircular.class);
                startActivity(tpvc_intent);
            }
        });
        dh_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ep = new Intent(getActivity(), TpoliceProfilePic.class);
                startActivity(ep);
            }
        });
        return v;
    }
}