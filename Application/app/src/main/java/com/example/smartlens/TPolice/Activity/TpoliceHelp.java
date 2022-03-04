package com.example.smartlens.TPolice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.example.smartlens.R;
import com.example.smartlens.TPolice.TpoliceExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TpoliceHelp extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tph_toolbar;
    Map<String, List<String>> listHashMap;
    List<String> childItem;
    private ExpandableListView tp_expandableListView;
    List<String> ParentList = new ArrayList<>();
    {
        ParentList.add("How can i Check Driver's Documents?");
        ParentList.add("How can i Submit my Attendance?");
        ParentList.add("How can i Send Memo to Driver's?");
        ParentList.add("How can i Apply for Leave?");
        ParentList.add("How can i view my Leave Application Status?");
        ParentList.add("How can i Delete my Leave Application?");
        ParentList.add("How can i View my Attendance?");
        ParentList.add("How can i View given Memo?");
        ParentList.add("How can i View Circular?");
        ParentList.add("How can i download Circular?");
        ParentList.add("How can i change my profile Detail's?");
        ParentList.add("How can i change my Password?");
        ParentList.add("Other Questions?");
    }
    String[] class1 = {"Click on Check id > Take a driver picture > Click on find Documents."};
    String[] class2 = {"Click on Submit Attendance > Take your picture and select Attendance Type > Click on Submit."};
    String[] class3 = {"Click on Send Memo > Enter all Details > Click on Send."};
    String[] class4 = {"Click on Apply for Leave > Enter all Details > Click on Apply."};
    String[] class5 = {"Click on Leave Application > Enter Details > Click on Show."};
    String[] class6 = {"Click on Leave Application > Enter Details > Click on Show > Long press on Leave > Click on Delete Leave."};
    String[] class7 = {"Click on Attendance > Enter Details > Click on Show."};
    String[] class8 = {"Click on Memo"};
    String[] class9 = {"Click on Circular > Click on any Circular which you want to View."};
    String[] class10 = {"Click on Circular > Click on Circular which you want to download > Click on download button give in Tool bar."};
    String[] class11 = {"Click on Side Navigation > Open Profile > Click on Edit Profile Button > Enter Changes > Click on Save Button."};
    String[] class12 = {"Click on Side Navigation > Open Profile > Click on Three Dot given in Title Bar > Click on Change Password > Enter New Password and Click on Save."};
    String[] class13 = {"For further Question you can Contact us on our mail : smartlenscustomercare.com"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpolice_help);
        tph_toolbar = (Toolbar) findViewById(R.id.tph_toolbar);
        setSupportActionBar(tph_toolbar);
        setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tph_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tp_expandableListView = findViewById(R.id.tp_expandablelistview);
        listHashMap = new LinkedHashMap<>();


        for(String HoldItem:ParentList){
            if(HoldItem.equals("How can i Check Driver's Documents?")){
                loadChild(class1);
            }
            else if(HoldItem.equals("How can i Submit my Attendance?")){
                loadChild(class2);
            }
            else if(HoldItem.equals("How can i Send Memo to Driver's?")){
                loadChild(class3);
            }
            else if(HoldItem.equals("How can i Apply for Leave?")){
                loadChild(class4);
            }
            else if(HoldItem.equals("How can i view my Leave Application Status?")){
                loadChild(class5);
            }
            else if(HoldItem.equals("How can i Delete my Leave Application?")){
                loadChild(class6);
            }
            else if(HoldItem.equals("How can i View my Attendance?")){
                loadChild(class7);
            }
            else if(HoldItem.equals("How can i View given Memo?")){
                loadChild(class8);
            }
            else if(HoldItem.equals("How can i View Circular?")){
                loadChild(class9);
            }
            else if(HoldItem.equals("How can i download Circular?")){
                loadChild(class10);
            }
            else if(HoldItem.equals("How can i change my profile Detail's?")){
                loadChild(class11);
            }
            else if(HoldItem.equals("How can i change my Password?")){
                loadChild(class12);
            }
            else if(HoldItem.equals("Other Questions?")){
                loadChild(class13);
            }
            listHashMap.put(HoldItem,childItem);
        }
        ExpandableListAdapter adapter = new TpoliceExpandableListViewAdapter(this,ParentList,(HashMap<String,List<String>>) listHashMap);
        tp_expandableListView.setAdapter(adapter);
    }
    private void loadChild(String[] parentElementName){
        childItem = new ArrayList<>();
        Collections.addAll(childItem,parentElementName);
    }
}