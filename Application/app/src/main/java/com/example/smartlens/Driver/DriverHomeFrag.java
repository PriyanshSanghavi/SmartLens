package com.example.smartlens.Driver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.smartlens.Driver.Activity.DriverRating;
import com.example.smartlens.Driver.Activity.DuploadDocumentsActivity;
import com.example.smartlens.Driver.Activity.DviewDocuments;
import com.example.smartlens.Driver.Activity.DviewMemo;
import com.example.smartlens.R;

import java.util.ArrayList;
import java.util.List;


public class DriverHomeFrag extends Fragment {

    ImageView ud,vd,vm,rateus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_home, container, false);
        ud=(ImageView) v.findViewById(R.id.ud);
        vd=(ImageView) v.findViewById(R.id.vd);
        vm=(ImageView) v.findViewById(R.id.vm);
        rateus=(ImageView) v.findViewById(R.id.rateus);
        ImageSlider imageSlider =v.findViewById(R.id.driver_slider);
        List<SlideModel> sliders = new ArrayList<>();
        sliders.add(new SlideModel(R.drawable.d1, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.d2, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.d3, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.d4, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.d5, ScaleTypes.FIT));
        sliders.add(new SlideModel(R.drawable.d6, ScaleTypes.FIT));
        imageSlider.setImageList(sliders, ScaleTypes.FIT);
        ud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ud_intent = new Intent(getActivity(), DuploadDocumentsActivity.class);
                startActivity(ud_intent);
            }
        });
        vd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vd_intent = new Intent(getActivity(), DviewDocuments.class);
                startActivity(vd_intent);
            }
        });
        vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vm_intent = new Intent(getActivity(), DviewMemo.class);
                startActivity(vm_intent);
            }
        });
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dr_intent = new Intent(getActivity(), DriverRating.class);
                startActivity(dr_intent);
            }
        });
        return v;
    }
}